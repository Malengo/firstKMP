package org.example.project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            App()
        }
    }
}

@androidx.compose.ui.tooling.preview.Preview(device = "id:Nexus S")
@Composable
fun DefaultPreview() {
    Column(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxSize()
            .background(Color.White)
    ) {
        Row(
            modifier = Modifier
                .background(Color.Black)
                .fillMaxWidth()
                .height(100.dp)
        ) {

        }
        Column (
            modifier = Modifier
                .background(Color.Yellow)
                .fillMaxWidth()
                .weight(1f)
        ) {
            val itens = listOf("item 1", "item 2", "item 3", "item 4")
                itens.chunked(2).forEach { rowItems ->
                    Row (
                        modifier = Modifier.weight(1f)
                    ){
                        rowItems.forEach { item ->
                            Card(
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxHeight()
                                    .padding(4.dp)
                            ) {
                                Text(item)
                            }
                        }
                    }
                }


        }
        Row(
            modifier = Modifier
                .background(Color.Red)
                .fillMaxWidth()
                .height(100.dp)
        ) {

        }
    }
}