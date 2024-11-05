package org.example.project.lessons.ui

import Colors.ColorsDefaults
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import firstkmp.composeapp.generated.resources.Res
import firstkmp.composeapp.generated.resources.backgroundHome
import org.example.project.AppRouterName
import org.example.project.lessons.components.HeaderLessonsProgress
import org.jetbrains.compose.resources.painterResource
import kotlin.math.cos
import kotlin.math.sin

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun WriteWordScreen(navHostController: NavHostController) {
    val word = "ball"
    var letters = List((12 - word.length)) { ('A'..'Z').random() }
    letters = (letters + word.toList()).shuffled()
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(Color.White)
            .fillMaxWidth()
            .fillMaxHeight()
            .safeDrawingPadding()
    ) {
        HeaderLessonsProgress(3) {
            navHostController.navigate(AppRouterName.Home.name)
        }
        Text(
            "Complete the word based on the image.",
            textAlign = TextAlign.Center,
            fontSize = 29.sp,
            modifier = Modifier.padding(top = 15.dp)
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround,
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
                .paint(
                    painterResource(resource = Res.drawable.backgroundHome),
                    contentScale = ContentScale.FillBounds
                )

        ) {

            AsyncImage(
                model = "https://images-wixmp-ed30a86b8c4ca887773594c2.wixmp.com/f/25d45014-8cc3-4c98-b02c-5a0cf3a55ddd/dcgoypr-bb7aa4ed-2b44-414b-9042-e878a8542738.png/v1/fill/w_989,h_808/soccer_ball_on_a_transparent_background__by_prussiaart_dcgoypr-pre.png?token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1cm46YXBwOjdlMGQxODg5ODIyNjQzNzNhNWYwZDQxNWVhMGQyNmUwIiwiaXNzIjoidXJuOmFwcDo3ZTBkMTg4OTgyMjY0MzczYTVmMGQ0MTVlYTBkMjZlMCIsIm9iaiI6W1t7ImhlaWdodCI6Ijw9ODM2IiwicGF0aCI6IlwvZlwvMjVkNDUwMTQtOGNjMy00Yzk4LWIwMmMtNWEwY2YzYTU1ZGRkXC9kY2dveXByLWJiN2FhNGVkLTJiNDQtNDE0Yi05MDQyLWU4NzhhODU0MjczOC5wbmciLCJ3aWR0aCI6Ijw9MTAyNCJ9XV0sImF1ZCI6WyJ1cm46c2VydmljZTppbWFnZS5vcGVyYXRpb25zIl19.f5wVeyksvXP-TgMFs1oQNOLU0_it1GMYklAjGKQEXYk",
                contentDescription = "BALL",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(150.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {

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
                                .height(60.dp)
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
            FlowRow(
                modifier = Modifier.fillMaxWidth().padding(start = 50.dp),
                verticalArrangement = Arrangement.spacedBy(-(40).dp),
                horizontalArrangement = Arrangement.spacedBy(-(7).dp)
            ) {
                letters.forEachIndexed { position, letter ->
                    val padding = if (position % 2 == 0) 30.dp else 0.dp
                    RoundedPolygonBox(letter.toString().uppercase(), modifier = Modifier.padding(top = padding))
                }
            }
            Button(
                onClick = {

                },
                colors = ButtonDefaults.buttonColors(ColorsDefaults.backgroundDarkHighContrast),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .padding(
                        start = 10.dp,
                        end = 10.dp,
                    )
            ) {
                Text(
                    "Next",
                    color = ColorsDefaults.onPrimaryLight
                )
            }

        }
    }
}


private const val PI = 3.141592653589793f

fun roundedPolygonPath(numVertices: Int, radius: Float, cornerRadius: Float, centerX: Float, centerY: Float): Path {
    val path = Path()
    val angle = 360f / numVertices

    for (i in 0 until numVertices) {
        val theta = (angle * i).toRadians()
        val x = centerX + (radius - cornerRadius) * cos(theta)
        val y = centerY + (radius - cornerRadius) * sin(theta)
        if (i == 0) {
            path.moveTo(x, y)
        } else {
            path.lineTo(x, y)
        }
    }
    path.close()
    return path
}

@Composable
fun RoundedPolygonBox(
    text: String,
    numVertices: Int = 6,
    modifier: Modifier = Modifier,
    cornerRadius: Float = 10f,
    color: Color = Color.LightGray.copy(alpha = 0.5f)
) {
    Box(
        modifier = modifier
            .drawWithCache {
                val path = roundedPolygonPath(
                    numVertices = numVertices,
                    radius = size.minDimension / 2,
                    cornerRadius = cornerRadius,
                    centerX = size.width / 2,
                    centerY = size.height / 2
                )
                onDrawBehind {
                    drawPath(path, color = color)
                }
            }
            .size(80.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text, color = Color.White, style = TextStyle(fontSize = 14.sp))
    }
}

fun Float.toRadians(): Float = (this * PI / 180f)