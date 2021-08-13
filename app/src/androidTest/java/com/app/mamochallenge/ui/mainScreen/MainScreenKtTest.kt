package com.app.mamochallenge.ui.mainScreen

import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.filters.MediumTest
import com.app.mamochallenge.ui.theme.MamoChallengeTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
@MediumTest
class MainScreenKtTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var mainViewModel: MainViewModel

    @Before
    fun setUp() {
        mainViewModel = MainViewModel()
        composeTestRule.setContent {
            MamoChallengeTheme {
                MainScreen(mainViewModel)
            }
        }
    }

    @Test
    fun testNumberInitialValue() {
        composeTestRule
            .onNodeWithContentDescription("NumberText")
            .assertTextEquals("0.00")
    }

    @Test
    fun testKeyboardKeys() {
        composeTestRule.onNodeWithText("1").performClick()
        composeTestRule.onNodeWithText("7").performClick()
        composeTestRule.onNodeWithText("5").performClick()
        composeTestRule.onNodeWithText("5").performClick()
        composeTestRule.onNodeWithText("9").performClick()
        composeTestRule.onNodeWithText("9").performClick()
        composeTestRule.onNodeWithText("⌫").performClick()
        composeTestRule.onNodeWithText("0").performClick()
        composeTestRule.onNodeWithText("0").performClick()
        composeTestRule.onNodeWithText(".").performClick()
        composeTestRule.onNodeWithText("0").performClick()
        composeTestRule.onNodeWithText("5").performClick()
        composeTestRule
            .onNodeWithContentDescription("NumberText")
            .assertTextEquals("1,755,900.05")
    }

}