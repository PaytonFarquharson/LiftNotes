package com.example.liftnotes.com.example.liftnotes.ui.components

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.liftnotes.R
import com.example.liftnotes.ui.components.IconPicker
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class IconPickerTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun onClick_dialogShown() {
        composeRule.setContent {
            IconPicker({}, R.drawable.ic_ab_wheel)
        }
        composeRule.onNodeWithContentDescription("Icon").performClick()
        composeRule.onNodeWithText("Clear").assertIsDisplayed()
    }
}