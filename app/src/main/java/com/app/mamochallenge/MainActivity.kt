package com.app.mamochallenge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.app.mamochallenge.ui.mainScreen.MainScreen
import com.app.mamochallenge.ui.theme.MamoChallengeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MamoChallengeTheme {
                MainScreen()
            }
        }
    }
}