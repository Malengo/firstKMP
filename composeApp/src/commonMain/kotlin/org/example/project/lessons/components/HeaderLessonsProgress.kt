package org.example.project.lessons.components

import Colors.ColorsDefaults
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HeaderLessonsProgress(progress: Int, navToHome: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp)
    ) {
        Icon(
            modifier = Modifier
                .size(30.dp)
                .clickable {
                    navToHome()
                },
            imageVector = Icons.Filled.Close,
            contentDescription = "Closed"
        )
        LinearProgressIndicator(
            progress = progress / 4.toFloat(),
            color = ColorsDefaults.primaryLight
        )
        Text("${progress}/4")
    }
}