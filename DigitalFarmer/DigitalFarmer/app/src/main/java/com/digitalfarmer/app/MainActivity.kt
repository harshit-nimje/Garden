package com.digitalfarmer.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.digitalfarmer.app.ui.DigitalFarmerNav
import com.digitalfarmer.app.ui.theme.DigitalFarmerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DigitalFarmerTheme {
                DigitalFarmerNav()
            }
        }
    }
}
