package com.uvg.ejercicioslabs.ejercicios.ktor.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.uvg.ejercicioslabs.ejercicios.ktor.domain.model.Monster

@Entity
data class MonsterEntity(
    @PrimaryKey val id: String,
    val name: String,
    val symbol: String,
    val priceUsd: String,
    val changePercent24Hr: String,
    val supply: String,
    val maxSupply: String?,
    val marketCapUsd: String,
    val lastUpdated: Long? = null
)

fun MonsterEntity.mapToMonsterModel(): Monster {
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