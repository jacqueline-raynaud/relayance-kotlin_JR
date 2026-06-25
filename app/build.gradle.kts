import com.android.build.gradle.BaseExtension

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id("jacoco")
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    alias(libs.plugins.compose.compiler)
}
jacoco {
    toolVersion = "0.8.15"
}
// ajout pour essayer de forcer la version jacoco
configurations.all {
    resolutionStrategy {
        eachDependency {
            if (requested.group == "org.jacoco") {
                useVersion("0.8.15")
            }
        }
    }
}

tasks.withType<Test> {
    extensions.configure(JacocoTaskExtension::class) {
        isIncludeNoLocationClasses = true
        excludes = listOf(
            "jdk.internal.*",
            "jdk.proxy1*",
            "sun.*",
            "com.sun.*"
        )
    }
}
android {
    namespace = "com.kirabium.relayance"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.kirabium.relayance"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    testOptions {
        unitTests.all {
            it.useJUnitPlatform()
        }
    }


    buildFeatures {
        viewBinding = true
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            // for surveillance by JaCoCo
            enableAndroidTestCoverage = true
            enableUnitTestCoverage = true
        }
    }

    kotlin {
        jvmToolchain(17)
    }

    buildFeatures {
        compose = true
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

val androidExtension = extensions.getByType<BaseExtension>()

val jacocoTestReport by tasks.registering(JacocoReport::class) {
    dependsOn("testDebugUnitTest", "createDebugAndroidTestCoverageReport")
    group = "Reporting"
    description = "Generate Jacoco coverage reports"

    reports {
        xml.required.set(true)
        html.required.set(true)
    }

    val fileFilter = listOf(
        "**/R.class", "**/R$*.class", "**/BuildConfig.*", "**/Manifest*.*",
        "**/*Test*.*", "android/**/*.*",
        "**/*_Hilt*.*", "**/Hilt_*.*", "**/*_Factory.*",
        "**/*_MembersInjector.*", "**/*Module_*.*", "**/*Dagger*.*",
        "**/ComposableSingletons*.*"
    )
    val debugTree = fileTree("${project.layout.buildDirectory.get()}/tmp/kotlin-classes/debug")


    val mainSrc = androidExtension.sourceSets.getByName("main").java.srcDirs

    classDirectories.setFrom(debugTree)
    sourceDirectories.setFrom(files(mainSrc))
    executionData.setFrom(fileTree(project.layout.buildDirectory.get()) {
        include("**/*.exec", "**/*.ec")
    })
}


dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.espresso.contrib)
    // Tests xml
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    testImplementation(libs.assertj.core)
    androidTestImplementation(libs.androidx.espresso.contrib)
    androidTestImplementation(libs.androidx.espresso.intents)
    // Test compose
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    // hilt ksp
    implementation(libs.hilt)
    ksp(libs.hilt.compiler)
    // cucumber
    testImplementation(libs.cucumber.java)
    testImplementation(libs.cucumber.junit)
    testImplementation(libs.kotlinx.coroutines.test)
    // kotest and adaptator old test junit4
    testImplementation(libs.junit.vintage)
    testImplementation(libs.kotest.runner)
    testImplementation(libs.kotest.assertions)
}
