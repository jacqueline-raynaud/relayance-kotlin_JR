package com.kirabium.relayance

import androidx.lifecycle.Lifecycle
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.kirabium.relayance.ui.activity.AddCustomerActivity
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)

class AddCustomerTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(AddCustomerActivity::class.java)

    @Test
    fun testAddCustomer() {
        onView(withId(R.id.nameEditText))
            .perform(typeText("Nouveau client"), closeSoftKeyboard())

        onView(withId(R.id.emailEditText))
            .perform(typeText("nouveauclient@test.com"), closeSoftKeyboard())

        onView(withId(R.id.saveFab))
            .perform(click())
        assertEquals(Lifecycle.State.DESTROYED, activityRule.scenario.state)
    }
}