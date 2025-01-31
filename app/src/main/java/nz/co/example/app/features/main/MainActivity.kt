package nz.co.example.app.features.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import nz.co.example.app.ui.theme.AppTheme
import org.koin.compose.KoinContext

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent { Content() }
    }

    @Composable
    private fun Content() {
        KoinContext() {
            AppTheme {
                MainScreen()
            }
        }
    }

}