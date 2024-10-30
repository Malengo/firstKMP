package org.example.project.homeApp.components

import Colors.ColorsDefaults
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp


@Composable
fun cardLesson(name: String, icon: ImageVector, color: Color, backgroundColor: Color, navToScreen: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .width(120.dp)
            .height(150.dp)
            .clip(
                RoundedCornerShape(10.dp)
            )
            .background(backgroundColor)
            .clickable {
                navToScreen()
            },
        contentAlignment = Alignment.TopCenter
    ) {
        Row (
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ){
            Box(
                modifier = Modifier.offset(x = (-10).dp, y = (-5).dp).size(50.dp).clip(CircleShape).background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Text("1",
                    fontSize = 25.sp,
                    style = LocalTextStyle.current.merge(
                        TextStyle(
                            lineHeight = 1.0.em,
                            fontWeight = FontWeight.ExtraBold
                        )
                    ),
                    color = ColorsDefaults.secondaryLight)
            }
            Box(
                modifier = Modifier.padding(5.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    progress = 0.6f,
                    modifier = Modifier
                        .size(50.dp)
                        .align(Alignment.TopEnd),
                    color = color
                )
                Text("60%", fontSize = 12.sp, fontStyle = FontStyle.Italic, color = color)
            }
        }
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            Icon(
                icon,
                contentDescription = null,
                modifier = Modifier.size(40.dp),
                tint = color
            )
            Text(name, color = color)
        }
    }
}
