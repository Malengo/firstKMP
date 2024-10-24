package org.example.project.components

import Colors.ColorsDefaults
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun DialogError(messageError: String, openDialog: MutableState<Boolean>) {
    Dialog(onDismissRequest = { openDialog.value = false }) {
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .clip(
                    RoundedCornerShape(10.dp)
                )
                .background(ColorsDefaults.errorContainerLight.copy(alpha = 0.8f))
                .fillMaxWidth()
                .height(200.dp)
        ) {
            Icon(
                Icons.Default.Warning,
                contentDescription = "",
                tint = ColorsDefaults.onPrimaryLight,
                modifier = Modifier.size(50.dp)
            )
            Text(
                messageError,
                modifier = Modifier.padding(10.dp),
                fontWeight = FontWeight.Black
            )
        }
    }
}