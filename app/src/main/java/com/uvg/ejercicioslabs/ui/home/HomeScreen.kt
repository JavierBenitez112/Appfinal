package com.uvg.ejercicioslabs.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.uvg.ejercicioslabs.ui.theme.EjerciciosLabsTheme
import kotlinx.serialization.Serializable

@Serializable
data object HomeDestination

@Composable
fun HomeRoute(
    onEjerciciosClick: () -> Unit,
    onLaboratoriosClick: () -> Unit,
) {
    HomeScreen(
        onEjerciciosClick = onEjerciciosClick,
        onLaboratoriosClick = onLaboratoriosClick,
        modifier = Modifier.fillMaxSize()
    )
}

@Composable
private fun HomeScreen(
    onEjerciciosClick: () -> Unit,
    onLaboratoriosClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            text = "Programación de plataformas móviles"
        )
        Button(onClick = onEjerciciosClick) {
            Text("Navegar a ejercicios")
        }
        Button(onClick = onLaboratoriosClick) {
            Text("Navegar a laboratorios")
        }
    }
}

@Preview
@Composable
private fun PreviewHomeScreen() {
    EjerciciosLabsTheme {
        Surface {
            HomeScreen(
                onLaboratoriosClick = {},
                onEjerciciosClick = {},
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}