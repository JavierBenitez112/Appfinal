package com.uvg.ejercicioslabs.ejercicios.ktor.data.network.dto

import com.uvg.ejercicioslabs.ejercicios.ktor.data.local.entity.MonsterEntity
import com.uvg.ejercicioslabs.ejercicios.ktor.domain.model.Monster
import kotlinx.serialization.Serializable

@Serializable
data class EntryDto(
    val id: String,
    val symbol: String,
    val name: String,
    val priceUsd: String,
    val changePercent24Hr: String,
    val supply: String,
    val maxSupply: String?,
    val marketCapUsd: String,
    val lastUpdated: Long? = null
)

fun EntryDto.mapToMonsterModel(): Monster {
    return Monster(
        id = id,
        name = name,
        symbol = symbol,
        priceUsd = priceUsd,
        changePercent24Hr = changePercent24Hr,
        supply = supply,
        maxSupply = maxSupply,
        marketCapUsd = marketCapUsd,
        lastUpdated = lastUpdated
    )
}

fun EntryDto.mapToMonsterEntity(): MonsterEntity {
    return MonsterEntity(
        id = id,
        name = name,
        symbol = symbol,
        priceUsd = priceUsd,
        changePercent24Hr = changePercent24Hr,
        supply = supply,
        maxSupply = maxSupply,
        marketCapUsd = marketCapUsd,
        lastUpdated = lastUpdated
    )
}