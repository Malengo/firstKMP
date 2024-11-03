package org.example.project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.project.lessons.components.HeaderLessonsProgress

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            App()
        }
    }
}

@androidx.compose.ui.tooling.preview.Preview(device = "id:pixel_9")
@Composable
fun DefaultPreview() {
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(Color.White)
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        HeaderLessonsProgress(3) { }
        Text(
            "Complete the word based on the image.",
            textAlign = TextAlign.Center,
            fontSize = 29.sp,
            modifier = Modifier.padding(top = 15.dp)
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(top = 30.dp)
                .fillMaxHeight()
                .fillMaxWidth()
                .clip(
                    RoundedCornerShape(
                        topStart = 40.dp,
                        topEnd = 40.dp
                    )
                )
                .background(Color.Black)

        ) {

            Icon(
                Icons.Filled.Face,
                tint = Color.White,
                modifier = Modifier
                    .size(200.dp)
                    .padding(top = 20.dp),
                contentDescription = null
            )
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                val word = "ball"
                LazyVerticalGrid(
                    columns = GridCells.Fixed(word.length),
                    contentPadding = PaddingValues(8.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalArrangement = Arrangement.Center
                ) {
                 items(word.length) { item ->
                     Card(
                         modifier = Modifier
                             .padding(4.dp)
                             .height(100.dp)
                             .align(Alignment.CenterVertically),
                         backgroundColor = Color.LightGray.copy(alpha = 0.5f)
                     ) {
                         Box(
                             modifier = Modifier.fillMaxSize(),
                             contentAlignment = Alignment.Center
                         ) {
                             Text(
                                 text = word[item].toString().uppercase(),
                                 fontSize = 50.sp
                             )
                         }
                     }
                 }
                }
            }

        }
    }
}