package com.uvg.ejercicioslabs.ejercicios.ktor.presentation.monsterList

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.uvg.ejercicioslabs.R
import com.uvg.ejercicioslabs.ejercicios.ktor.domain.model.Monster
import com.uvg.ejercicioslabs.ejercicios.ktor.presentation.common.ErrorLayout
import com.uvg.ejercicioslabs.ejercicios.ktor.presentation.common.LoadingLayout
import com.uvg.ejercicioslabs.ui.theme.EjerciciosLabsTheme
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun MonsterListRoute(
    onMonsterClick: (String) -> Unit,
    viewModel: MonsterListViewModel = viewModel(factory = MonsterListViewModel.Factory)
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    MonsterListScreen(
        state = state,
        onRetryClick = viewModel::getMonsters,
        onMonsterClick = onMonsterClick,
        onViewOfflineClick = viewModel::viewOffline,
        modifier = Modifier.fillMaxSize()
    )
}

@Composable
fun SwitchMinimalExample(onViewOfflineClick: () -> Unit, onCheckedChange: (Boolean) -> Unit) {
    var checked by remember { mutableStateOf(false) }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(text = "Pasar a offline")
        Switch(
            checked = checked,
            onCheckedChange = {
                checked = it
                onCheckedChange(checked)
                if (checked) {
                    onViewOfflineClick()
                }
            },
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}

@Composable
private fun MonsterListScreen(
    state: MonsterListState,
    onRetryClick: () -> Unit,
    onMonsterClick: (String) -> Unit,
    onViewOfflineClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var lastUpdated by remember { mutableStateOf<Long?>(null) }
    var showLastUpdated by remember { mutableStateOf(false) }

    Box(modifier = modifier) {
        when {
            state.isLoading -> {
                LoadingLayout(
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            state.isGenericError -> {
                Column(
                    modifier = Modifier.align(Alignment.Center)
                ) {
                    ErrorLayout(
                        text = stringResource(R.string.error_fetching_data),
                        onRetryClick = onRetryClick
                    )
                    lastUpdated?.let { timestamp ->
                        val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
                        val date = Date(timestamp)
                        Text(
                            text = "Última actualización: ${sdf.format(date)}",
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }

            state.noInternetConnection -> {
                Column(
                    modifier = Modifier.align(Alignment.Center)
                ) {
                    ErrorLayout(
                        text = stringResource(R.string.no_internect_connection),
                        onRetryClick = onRetryClick
                    )
                    lastUpdated?.let { timestamp ->
                        val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
                        val date = Date(timestamp)
                        Text(
                            text = "Última actualización: ${sdf.format(date)}",
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }

            else -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    SwitchMinimalExample(
                        onViewOfflineClick = onViewOfflineClick,
                        onCheckedChange = { checked ->
                            showLastUpdated = checked
                            if (checked) {
                                lastUpdated = System.currentTimeMillis()
                            } else {
                                lastUpdated = null
                            }
                        }
                    )
                    if (showLastUpdated) {
                        lastUpdated?.let { timestamp ->
                            val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
                            val date = Date(timestamp)
                            Text(
                                text = "Última actualización: ${sdf.format(date)}",
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    }
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(state.data) { monster ->
                            MonsterItem(
                                monster = monster,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { onMonsterClick(monster.id) }
                                    .padding(8.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun MonsterItem(
    monster: Monster,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Icon(
            Icons.Outlined.Person, contentDescription = "Image",
        )
        Column {
            Text(text = "Nombre: ${monster.name}")
            Text(text = "Símbolo: ${monster.symbol}")
            Text(text = "Precio en USD: ${monster.priceUsd}")
            val changePercentColor = if (monster.changePercent24Hr.toFloat() >= 0) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.error
            }
            Text(
                text = "Cambio porcentual en 24h: ${monster.changePercent24Hr}",
                color = changePercentColor
            )
        }
    }
}