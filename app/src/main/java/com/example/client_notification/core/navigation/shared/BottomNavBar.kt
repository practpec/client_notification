package com.example.client_notification.core.navigation.shared

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun BottomNavBar() {
    // Definir los colores
    val backgroundColor = Color(0xFF243E57) // Azul grisáceo
    val iconColor = Color(0xFFEFF0F2) // Blanco grisáceo

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp) // Aumentamos un poco la altura para permitir que los íconos estén por encima de la curva
    ) {
        // Dibujar la parte curva superior
        Canvas(modifier = Modifier.fillMaxWidth()) {
            val width = size.width
            val height = size.height
            val curveHeight = 210f // Altura de la curva hacia arriba

            // Dibujar un rectángulo con una curva en la parte superior
            drawRoundRect(
                color = backgroundColor,
                size = size.copy(height = height - curveHeight),
                topLeft = Offset(0f, curveHeight),
                style = Fill
            )

            // Dibujar la curva
            drawPath(
                path = Path().apply {
                    moveTo(0f, 0f)
                    cubicTo(width * 0.25f, -curveHeight, width * 0.75f, -curveHeight, width, 0f)
                    lineTo(width, height)
                    lineTo(0f, height)
                    close()
                },
                color = backgroundColor
            )
        }

        // Barra inferior con íconos
        BottomAppBar(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(100.dp)
                .offset(y = (-30).dp), // Levantamos la barra para que los íconos queden más arriba de la curva
            containerColor = Color.Transparent,
            tonalElevation = 0.dp,
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            // Aquí se colocan los íconos dentro del BottomAppBar
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .height(500.dp)
                    .padding(top = 1.dp), // Ajuste de posición para que se alineen mejor
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.offset(y = (10).dp)
                        .wrapContentSize()
                ) {
                    IconButton(onClick = { /* Acción Profile */ }) {
                        Icon(
                            imageVector = Icons.Filled.History,
                            contentDescription = "Profile",
                            modifier = Modifier.size(35.dp),
                            tint = iconColor
                        )
                    }
                    Text(
                        text = "Historial",
                        style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                        color = iconColor
                    )
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .offset(y = (-14).dp) // Moverlo un poco hacia arriba
                        .wrapContentHeight() // Asegura que la altura se ajuste al contenido
                ) {
                    IconButton(onClick = { /* Acción Home */ }) {
                        Icon(
                            imageVector = Icons.Filled.Home,
                            contentDescription = "Home",
                            modifier = Modifier.size(35.dp),
                            tint = iconColor
                        )
                    }
                    Text(
                        text = "Home",
                        style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                        color = iconColor
                    )
                }
                // Ícono de Perfil
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.offset(y = (10).dp)
                ) {
                    IconButton(onClick = { /* Acción Profile */ }) {
                        Icon(
                            imageVector = Icons.Filled.Person,
                            contentDescription = "Profile",
                            modifier = Modifier.size(35.dp),
                            tint = iconColor
                        )
                    }
                    Text(
                        text = "Perfil",
                        style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                        color = iconColor,
                    )
                }
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun PreviewBottomNavBar() {
    BottomNavBar()
}