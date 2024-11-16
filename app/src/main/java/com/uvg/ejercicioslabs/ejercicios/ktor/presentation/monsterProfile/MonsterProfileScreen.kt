package com.uvg.ejercicioslabs.ejercicios.ktor.presentation.monsterProfile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.uvg.ejercicioslabs.ejercicios.ktor.presentation.common.LoadingLayout

@Composable
fun MonsterProfileRoute(
    onNavigateBack: () -> Unit,
    viewModel: MonsterProfileViewModel = viewModel(factory = MonsterProfileViewModel.Factory)
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    MonsterProfileScreen(
        state = state,
        onNavigateBack = onNavigateBack,
        modifier = Modifier.fillMaxSize()
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MonsterProfileScreen(
    state: MonsterProfileState,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        TopAppBar(
            title = {
                Text("Perfil")
            },
            navigationIcon = {
                IconButton(onClick = onNavigateBack) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                }
            },
            modifier = Modifier.fillMaxWidth()
        )
        Box(modifier = Modifier.fillMaxSize()) {
            if (state.isLoading) {
                LoadingLayout(
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                checkNotNull(state.monster)
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .align(Alignment.TopCenter),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Icon(
                        Icons.Outlined.Person,
                        contentDescription = "Image",
                        modifier = Modifier
                            .size(128.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primary)
                            .padding(16.dp)
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        ProfileText(title = "Nombre", data = state.monster.name)
                        ProfileText(title = "Símbolo", data = state.monster.symbol)
                        ProfileText(title = "Precio en USD", data = state.monster.priceUsd)
                        ProfileText(title = "Cambio porcentual en 24h", data = state.monster.changePercent24Hr)
                        ProfileText(title = "Suministro", data = state.monster.supply)
                        ProfileText(title = "Suministro Máximo", data = state.monster.maxSupply ?: "N/A")
                        ProfileText(title = "Capitalización de Mercado", data = state.monster.marketCapUsd ?: "N/A")}
                }
            }
        }
    }
}

@Composable
private fun ProfileText(title: String, data: String) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
        )
        Text(
            text = data,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}