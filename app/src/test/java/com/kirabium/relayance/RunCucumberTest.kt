package com.kirabium.relayance

import io.cucumber.junit.Cucumber
import io.cucumber.junit.CucumberOptions
import org.junit.runner.RunWith

@RunWith(Cucumber::class)
@CucumberOptions(
    features = ["src/test/resources/features"],   // chemin direct = fiable sur Android
    glue = ["com.kirabium.relayance.steps"],       // package des step definitions
    plugin = ["pretty", "html:build/reports/cucumber/report.html"]
)
class RunCucumberTest