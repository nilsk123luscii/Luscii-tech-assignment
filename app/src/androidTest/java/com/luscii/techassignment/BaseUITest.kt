package com.luscii.techassignment

import androidx.compose.runtime.Composable
import androidx.compose.ui.test.junit4.createComposeRule
import com.luscii.techassignment.ui.theme.TechAssignmentTheme
import org.junit.Rule

abstract class BaseUITest {
    @get:Rule
    open val composeTestRule = createComposeRule()

    protected fun setupContent(content: @Composable () -> Unit) {
        composeTestRule.setContent {
            TechAssignmentTheme {
                content()
            }
        }
        composeTestRule.waitForIdle()
    }
}
