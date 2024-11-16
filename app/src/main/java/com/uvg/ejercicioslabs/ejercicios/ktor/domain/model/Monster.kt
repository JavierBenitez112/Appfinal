package com.uvg.ejercicioslabs.ejercicios.ktor.domain.model

data class Monster(
    val id: String,
    val symbol: String,
    val name: String,
    val supply: String,
    val maxSupply: String?,
    val marketCapUsd: String,
    val priceUsd: String,
    val changePercent24Hr: String,
    val lastUpdated: Long? = null
)
