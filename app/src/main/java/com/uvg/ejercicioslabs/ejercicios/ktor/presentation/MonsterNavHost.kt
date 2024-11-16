package com.uvg.ejercicioslabs.ejercicios.ktor.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.uvg.ejercicioslabs.ejercicios.ktor.presentation.monsterList.MonsterListDestination
import com.uvg.ejercicioslabs.ejercicios.ktor.presentation.monsterList.MonsterListRoute
import com.uvg.ejercicioslabs.ejercicios.ktor.presentation.monsterList.monsterList
import com.uvg.ejercicioslabs.ejercicios.ktor.presentation.monsterProfile.MonsterProfileDestination
import com.uvg.ejercicioslabs.ejercicios.ktor.presentation.monsterProfile.MonsterProfileRoute
import com.uvg.ejercicioslabs.ejercicios.ktor.presentation.monsterProfile.monsterProfile
import com.uvg.ejercicioslabs.ejercicios.ktor.presentation.monsterProfile.navigateToMonsterProfile
import com.uvg.ejercicioslabs.ui.theme.EjerciciosLabsTheme

@Composable
fun MonsterNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = MonsterListDestination,
        modifier = modifier
    ) {
        monsterList(navigateToMonsterProfile = navController::navigateToMonsterProfile)
        monsterProfile(onNavigateBack = navController::navigateUp)
    }
}

@Preview
@Composable
private fun PreviewMonsterNavHost() {
    EjerciciosLabsTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            MonsterNavHost()
        }
    }
}