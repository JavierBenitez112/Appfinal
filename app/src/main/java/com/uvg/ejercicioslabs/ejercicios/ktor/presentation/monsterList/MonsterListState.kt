package com.uvg.ejercicioslabs.ejercicios.ktor.presentation.monsterList

import com.uvg.ejercicioslabs.ejercicios.ktor.domain.model.Monster

data class MonsterListState(
    val isLoading: Boolean = true,
    val data: List<Monster> = emptyList(),
    val isGenericError: Boolean = false,
    val noInternetConnection: Boolean = false,
    val lastUpdated: Long? = null
)
