package com.uvg.ejercicioslabs.ejercicios.ktor.presentation.monsterProfile

import com.uvg.ejercicioslabs.ejercicios.ktor.domain.model.Monster

data class MonsterProfileState(
    val isLoading: Boolean = true,
    val monster: Monster? = null,
    val isGenericError: Boolean = false
)
