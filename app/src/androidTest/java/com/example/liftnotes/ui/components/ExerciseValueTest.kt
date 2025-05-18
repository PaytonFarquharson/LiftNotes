package com.example.liftnotes.com.example.liftnotes.ui.components

import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.liftnotes.R
import com.example.liftnotes.component.ExerciseValue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ExerciseValueTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun exerciseValue_displaysIconAndText() {
        val testValue = "25.5 lbs"
        val imageId = R.drawable.ic_weight

        composeTestRule.setContent {
            ExerciseValue(
                imageId = imageId,
                value = testValue,
                modifier = Modifier.testTag("ExerciseValue")
            )
        }

        // Check the Text is displayed correctly
        composeTestRule
            .onNodeWithText(testValue)
            .assertIsDisplayed()
    }
}