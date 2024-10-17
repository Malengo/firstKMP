package org.example.project

import Colors.ColorsDefaults
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import org.example.project.homeApp.components.cardLesson

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            App()
        }
    }
}

@androidx.compose.ui.tooling.preview.Preview(widthDp = 350, heightDp = 300)
@Composable
fun DefaultPreview() {
    cardLesson("Writing", Icons.Filled.Edit, Color.Black, ColorsDefaults.secondaryLight)
}