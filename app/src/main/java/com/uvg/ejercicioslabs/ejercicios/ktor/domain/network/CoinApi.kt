package com.uvg.ejercicioslabs.ejercicios.ktor.domain.network

import com.uvg.ejercicioslabs.ejercicios.ktor.data.network.dto.EntryListDto
import com.uvg.ejercicioslabs.ejercicios.ktor.data.network.dto.EntryCoinProfile
import com.uvg.ejercicioslabs.ejercicios.ktor.domain.network.util.NetworkError
import com.uvg.ejercicioslabs.ejercicios.ktor.domain.network.util.Result

interface CoinApi {
    suspend fun getAllCoins(): Result<EntryListDto, NetworkError>
    suspend fun getCoinProfile(id: String): Result<EntryCoinProfile, NetworkError>
}