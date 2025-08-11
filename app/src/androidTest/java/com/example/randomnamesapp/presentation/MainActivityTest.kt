package com.example.randomnamesapp.presentation

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import org.junit.Rule
import org.junit.Test


class MainActivityTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun testClickButton() {
        // Esperar a que Compose termine de pintar
        composeTestRule.waitUntil {
            composeTestRule.onAllNodesWithTag("GenerateButton").fetchSemanticsNodes().isNotEmpty()
        }

        composeTestRule.onNodeWithTag("GenerateButton")
            .assertIsDisplayed()
            .performClick()
    }

    @Test
    fun testClickGenderButtons(){

        composeTestRule.onNodeWithTag("Gender1")
            .assertIsDisplayed()
            .performClick()

        composeTestRule.onNodeWithTag("Gender2")
            .assertIsDisplayed()
            .performClick()

        composeTestRule.onNodeWithTag("Gender3")
            .assertIsDisplayed()
            .performClick()

        composeTestRule.onNodeWithTag("GenerateButton")
            .assertIsDisplayed()
            .performClick()

    }

    @Test
    fun testClickOriginButtons(){

        composeTestRule.onNodeWithTag("Origin1")
            .assertIsDisplayed()
            .performClick()

        composeTestRule.onNodeWithTag("Origin2")
            .assertIsDisplayed()
            .performClick()

        composeTestRule.onNodeWithTag("Origin3")
            .assertIsDisplayed()
            .performClick()

        composeTestRule.onNodeWithTag("GenerateButton")
            .assertIsDisplayed()
            .performClick()

    }
}