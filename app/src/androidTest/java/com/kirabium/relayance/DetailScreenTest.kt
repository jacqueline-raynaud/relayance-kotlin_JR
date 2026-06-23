package com.kirabium.relayance

import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.platform.app.InstrumentationRegistry
import com.kirabium.relayance.domain.model.Customer
import com.kirabium.relayance.ui.composable.DetailContent
import com.kirabium.relayance.ui.composable.DetailScreen
import com.kirabium.relayance.ui.customerdetail.DetailErrorScreen
import com.kirabium.relayance.ui.customerdetail.DetailLoadingScreen
import junit.framework.TestCase.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.experimental.theories.suppliers.TestedOn
import java.util.Calendar
import java.util.Date


class DetailScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val context = InstrumentationRegistry.getInstrumentation().targetContext

    @Test
    fun detailcreenWhenLoadingShowLoadingMessage() {
        val loadingMessage = context.resources.getString(R.string.loading_title)
        composeTestRule.setContent {
            DetailLoadingScreen(onBackClick = {})
        }
        composeTestRule.onNodeWithText(loadingMessage).assertIsDisplayed()
    }

    @Test
    fun detailScreenWhenErrorShowErrorMessage() {
        val errorMessage = context.resources.getString(R.string.error_title)
        composeTestRule.setContent {
            DetailErrorScreen(message = errorMessage, onBackClick = {})
        }
        composeTestRule.onAllNodesWithText(errorMessage)
            .onFirst()
            .assertIsDisplayed()

    }

    @Test
    fun detailContentWithNewCustomerShowNewRibbon() {
        val newCustomer = Customer(
            id = 2,
            name = "Marie Nouvelle",
            email = "marie@neuf.com",
            createdAt = Calendar.getInstance().time
        )
        composeTestRule.setContent {
            DetailContent(customer = newCustomer, onBackClick = {})
        }

        val newRibonText = context.resources.getString(R.string.new_ribbon)
        composeTestRule.onNodeWithText(context.resources.getString(R.string.new_ribbon))
            .assertIsDisplayed()
    }

    @Test
    fun detailContentWithOldCustomerNotShowNewRibbon() {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.MONTH, -6)
        val oldCustomer = Customer(
            id = 1,
            name = "Jean",
            email = "jean.ancien@test.com",
            createdAt = calendar.time
        )
        composeTestRule.setContent {
            DetailContent(customer = oldCustomer, onBackClick = {})
        }
        composeTestRule.onNodeWithText("Jean").assertIsDisplayed()
        composeTestRule.onNodeWithText("jean.ancien@test.com").assertIsDisplayed()

        val newRibonText = context.resources.getString(R.string.new_ribbon)
        composeTestRule.onNodeWithText(context.resources.getString(R.string.new_ribbon))
            .assertDoesNotExist()
    }

    @Test
    fun detailContentButtonBackClick() {
        var backClicked = false
        val customer = Customer(1, "Test", "test@test.com", Calendar.getInstance().time)

        composeTestRule.setContent {
            DetailContent(customer,onBackClick = { backClicked = true })
        }

        val backButton = context.resources.getString(R.string.contentDescription_go_back)
        composeTestRule.onNodeWithContentDescription(backButton).performClick()

        assertTrue(backClicked)
    }

}

