package org.example.project.lessons.ui

import Colors.ColorsDefaults
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
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

@Composable
fun SelectWordScreen(navHostController: NavHostController) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(color = Color.White)
            .safeDrawingPadding()
    ) {
        HeaderLessonsProgress(1) { navHostController.navigate(AppRouterName.Home.name) }
        Text(
            text = "Select the correct image for the word:",
            color = Color.Black,
            fontSize = 25.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 15.dp)
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(top = 30.dp)
                .fillMaxHeight()
                .fillMaxWidth()
                .clip(
                    RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp)
                )
                .paint(
                    painterResource(resource = Res.drawable.backgroundHome),
                    contentScale = ContentScale.FillBounds
                )
        ) {
            Text(
                "BALL",
                fontSize = 38.sp,
                fontWeight = FontWeight.Light,
                color = Color.White,
                modifier = Modifier.padding(top = 10.dp)
            )
            val data = remember {
                mutableListOf(
                    "https://images-wixmp-ed30a86b8c4ca887773594c2.wixmp.com/f/25d45014-8cc3-4c98-b02c-5a0cf3a55ddd/dcgoypr-bb7aa4ed-2b44-414b-9042-e878a8542738.png/v1/fill/w_989,h_808/soccer_ball_on_a_transparent_background__by_prussiaart_dcgoypr-pre.png?token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1cm46YXBwOjdlMGQxODg5ODIyNjQzNzNhNWYwZDQxNWVhMGQyNmUwIiwiaXNzIjoidXJuOmFwcDo3ZTBkMTg4OTgyMjY0MzczYTVmMGQ0MTVlYTBkMjZlMCIsIm9iaiI6W1t7ImhlaWdodCI6Ijw9ODM2IiwicGF0aCI6IlwvZlwvMjVkNDUwMTQtOGNjMy00Yzk4LWIwMmMtNWEwY2YzYTU1ZGRkXC9kY2dveXByLWJiN2FhNGVkLTJiNDQtNDE0Yi05MDQyLWU4NzhhODU0MjczOC5wbmciLCJ3aWR0aCI6Ijw9MTAyNCJ9XV0sImF1ZCI6WyJ1cm46c2VydmljZTppbWFnZS5vcGVyYXRpb25zIl19.f5wVeyksvXP-TgMFs1oQNOLU0_it1GMYklAjGKQEXYk" to mutableStateOf(false),
                    "https://www.pinclipart.com/picdir/big/558-5582023_transparent-cats-clipart-png-cartoon-cat-transparent-background.png" to mutableStateOf(false),
                    "https://www.pinclipart.com/picdir/big/529-5298022_table-matbord-clip-art-transparent-desk-clip-art.png" to mutableStateOf(false),
                    "https://www.pinclipart.com/picdir/big/580-5802020_blue-paper-plane-png-image-red-paper-plane.png" to mutableStateOf(false)
                )
            }

            LazyVerticalGrid(
                columns = GridCells.Adaptive(160.dp),
                contentPadding = PaddingValues(8.dp),
                modifier = Modifier.padding(top = 20.dp)
            ) {
                items(data) { item ->
                    Card(
                        modifier = Modifier
                            .padding(4.dp)
                            .height(200.dp)
                            .border(
                                width = if (item.second.value) 5.dp else 0.dp,
                                color = if (item.second.value) ColorsDefaults.backgroundLightHighContrast else Color.Transparent,
                                shape = RoundedCornerShape(3.dp)
                            )
                            .clickable {
                                item.second.value = !item.second.value
                                data.forEach {
                                    if (it.first != item.first) it.second.value = false
                                }
                            },
                        backgroundColor = Color.LightGray.copy(alpha = 0.5f)
                    ) {
                        AsyncImage(
                            model = item.first,
                            contentDescription = "BALL"
                        )
                    }
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 30.dp),
                verticalArrangement = Arrangement.Bottom
            ) {
                Button(
                    onClick = {

                    },
                    enabled = data.any { it.second.value },
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
}