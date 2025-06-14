package com.example.kmpapp.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.ui.Modifier
import com.example.kmpapp.android.feature.root.RootScreen
import com.example.kmpapp.android.coreui.theme.ConentFrientdsClientTheme
import org.koin.androidx.compose.KoinAndroidContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ConentFrientdsClientTheme {
                KoinAndroidContext {
                    Box(Modifier.safeDrawingPadding()){
                        RootScreen()
                    }
                }
            }
        }
    }
}