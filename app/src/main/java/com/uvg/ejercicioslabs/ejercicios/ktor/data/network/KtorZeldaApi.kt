package com.uvg.ejercicioslabs.ejercicios.ktor.data.network

import com.uvg.ejercicioslabs.ejercicios.ktor.data.network.dto.EntryListDto
import com.uvg.ejercicioslabs.ejercicios.ktor.data.network.dto.EntryCoinProfile
import com.uvg.ejercicioslabs.ejercicios.ktor.data.network.util.safeCall
import com.uvg.ejercicioslabs.ejercicios.ktor.domain.network.CoinApi
import com.uvg.ejercicioslabs.ejercicios.ktor.domain.network.util.NetworkError
import com.uvg.ejercicioslabs.ejercicios.ktor.domain.network.util.Result
import io.ktor.client.HttpClient
import io.ktor.client.request.get

class KtorCoinApi(
    private val httpClient: HttpClient
): CoinApi {
    override suspend fun getAllCoins(): Result<EntryListDto, NetworkError> {
        return safeCall<EntryListDto> {
            httpClient.get(
                "https://api.coincap.io/v2/assets"
            )
        }
    }

    override suspend fun getCoinProfile(id: String): Result<EntryCoinProfile, NetworkError> {
        return safeCall<EntryCoinProfile> {
            httpClient.get(
                "api.coincap.io/v2/assets/{id}"
            )
        }
    }
}