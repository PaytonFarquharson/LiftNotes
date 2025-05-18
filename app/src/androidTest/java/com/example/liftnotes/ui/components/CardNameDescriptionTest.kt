package com.example.liftnotes.com.example.liftnotes.ui.components

import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.liftnotes.component.CardNameDescription
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CardNameDescriptionTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun nameValue_displays() {
        composeRule.setContent {
            CardNameDescription("testName", "testDescription", Modifier)
        }
    }
}