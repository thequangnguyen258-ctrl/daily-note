package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.audio.SanctuaryAudioEngine
import com.example.data.local.DevotionalStorage
import com.example.ui.MainApp
import com.example.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {

    private lateinit var storage: DevotionalStorage
    private lateinit var audioEngine: SanctuaryAudioEngine

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        storage = DevotionalStorage(applicationContext)
        audioEngine = SanctuaryAudioEngine()

        setContent {
            MyApplicationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    androidx.compose.foundation.layout.Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        MainApp(
                            storage = storage,
                            audioEngine = audioEngine
                        )
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        audioEngine.release()
    }
}
