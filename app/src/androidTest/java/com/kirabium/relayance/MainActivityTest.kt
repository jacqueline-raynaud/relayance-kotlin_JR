package com.kirabium.relayance

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createEmptyComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withContentDescription
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.kirabium.relayance.ui.activity.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)

class MainActivityTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @get:Rule
    val composeTestRule = createEmptyComposeRule()

    @Test
    fun testAddFabClickShouldOpenAddCustomerScreen() {
        onView(withId(R.id.customerRecyclerView))
            .check(matches(isDisplayed()))

        onView(withId(R.id.addCustomerFab))
            .perform(click())

        onView(withId(R.id.nameEditText))
            .check(matches(isDisplayed()))

    }
@Test
fun clickOnBackArrowOnOpenCustomerListReturnToMain() {

    onView(withId(R.id.customerRecyclerView))
        .check(matches(isDisplayed()))

    onView(withId(R.id.addCustomerFab))
        .perform(click())

    onView(withId(R.id.nameEditText))
        .check(matches(isDisplayed()))

    onView(withContentDescription(androidx.appcompat.R.string.abc_action_bar_up_description))
        .perform(click())
    onView(withId(R.id.customerRecyclerView))
        .check(matches(isDisplayed()))
}

    @Test
    fun clickOnSpecificCustomerOpenDetailScreenAndReturnToMain() {
        // Test if the customerList is displayed
        onView(withId(R.id.customerRecyclerView))
            .perform(
                // scroll and click on Diana Dream
                RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(
                    hasDescendant(withText("Diana Dream")),
                    click()
                )
            )
        // Test if the detail screen is displayed IN COMPOSE
        composeTestRule.onNodeWithText("Diana Dream").assertIsDisplayed()

        //catch the content description of the back button an click
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val contentDescriptionBack = context.getString(R.string.contentDescription_go_back)
        composeTestRule.onNodeWithContentDescription(contentDescriptionBack).performClick()

        // Test if the customerList is displayed
        onView(withId(R.id.customerRecyclerView))
            .check(matches(isDisplayed()))

    }
}



