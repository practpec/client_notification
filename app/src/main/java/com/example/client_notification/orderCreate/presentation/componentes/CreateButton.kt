package com.example.client_notification.orderCreate.presentation.componentes

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.shape.RoundedCornerShape
import com.example.client_notification.ui.theme.Dark
import com.example.client_notification.ui.theme.Yellow

@Composable
fun CreateButton(
    text: String,
    onClick: () -> Unit,
    enabled: Boolean = true,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = Yellow,
            contentColor = Dark
        ),
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(14.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Text(
            text = text,
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            ),
            modifier = Modifier.padding(horizontal = 8.dp)
        )
    }
}