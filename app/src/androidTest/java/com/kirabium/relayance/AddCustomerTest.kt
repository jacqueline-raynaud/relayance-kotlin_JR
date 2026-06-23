package com.kirabium.relayance

import androidx.lifecycle.Lifecycle
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.kirabium.relayance.data.repository.CustomerRepositoryImpl
import com.kirabium.relayance.ui.activity.AddCustomerActivity
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)

class AddCustomerScreenTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(AddCustomerActivity::class.java)

    @After
    fun tearDown() {
        CustomerRepositoryImpl.resetForTest()
    }


    //test view error name with name empty
    @Test
    fun testViewMessageNameError() {
        onView(withId(R.id.emailEditText))
            .perform(typeText("test@test.com"), closeSoftKeyboard())

        onView(withId(R.id.saveFab))
            .perform(click())

        onView(withText(R.string.invalid_name))
            .check(matches(isDisplayed()))


    }

    //test view error email with email error
    @Test
    fun testViewMessageEmailError() {

        onView(withId(R.id.nameEditText))
            .perform(typeText("customer test"), closeSoftKeyboard())

        onView(withId(R.id.emailEditText))
            .perform(typeText("testwithaoutarobasetest.com"), closeSoftKeyboard())

        onView(withId(R.id.saveFab))
            .perform(click())

        onView(withText(R.string.invalid_email))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testAddCustomer() {
        onView(withId(R.id.nameEditText))
            .perform(typeText("New Customer"), closeSoftKeyboard())

        onView(withId(R.id.emailEditText))
            .perform(typeText("newCustomer@test.com"), closeSoftKeyboard())

        onView(withId(R.id.saveFab))
            .perform(click())
        assertEquals(Lifecycle.State.DESTROYED, activityRule.scenario.state)
    }

}