package com.example.liftnotes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.liftnotes.ui.screens.common.MainScreen
import com.example.liftnotes.ui.theme.LiftNotesTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            LiftNotesTheme {
                MainScreen()
            }
        }
    }
}