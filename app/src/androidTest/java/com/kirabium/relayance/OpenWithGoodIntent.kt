package com.kirabium.relayance

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createEmptyComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.kirabium.relayance.ui.activity.DetailActivity
import org.junit.Rule
import org.junit.runner.RunWith
import org.junit.Test

@RunWith(AndroidJUnit4::class)
class OpenWithGoodIntent {
    @get:Rule
    val composeTestRule = createEmptyComposeRule()

    @Test
    fun launchingDetailWithId1_displaysAliceData() {
        // The intent that will be launched
        val context = ApplicationProvider.getApplicationContext<Context>()
        val intent = Intent(context, DetailActivity::class.java).apply {
            putExtra(DetailActivity.EXTRA_CUSTOMER_ID, 1)
        }
        // launch activity with intent
        ActivityScenario.launch<DetailActivity>(intent).use {

            composeTestRule.onNodeWithText("Alice Wonderland").assertIsDisplayed()
            composeTestRule.onNodeWithText("alice@example.com").assertIsDisplayed()
        }
    }
}
