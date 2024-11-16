package com.uvg.ejercicioslabs.ejercicios.ktor.domain.repository

import com.uvg.ejercicioslabs.ejercicios.ktor.domain.model.DataError
import com.uvg.ejercicioslabs.ejercicios.ktor.domain.model.Monster
import com.uvg.ejercicioslabs.ejercicios.ktor.domain.network.util.Result

interface CoinRepository {
    suspend fun getAllMonsters(): Result<List<Monster>, DataError>
    suspend fun getOneMonster(id: String): Result<Monster, DataError>
}