package com.uvg.ejercicioslabs.ejercicios.ktor.presentation.monsterList

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.uvg.ejercicioslabs.ejercicios.ktor.presentation.monsterProfile.navigateToMonsterProfile
import kotlinx.serialization.Serializable

@Serializable
data object MonsterListDestination

fun NavGraphBuilder.monsterList(
    navigateToMonsterProfile: (String) -> Unit
) {
    composable<MonsterListDestination> {
        MonsterListRoute(
            onMonsterClick = navigateToMonsterProfile
        )
    }
}