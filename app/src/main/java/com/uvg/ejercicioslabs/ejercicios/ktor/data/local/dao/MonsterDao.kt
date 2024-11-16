package com.uvg.ejercicioslabs.ejercicios.ktor.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.uvg.ejercicioslabs.ejercicios.ktor.data.local.entity.MonsterEntity

@Dao
interface MonsterDao {
    @Query("SELECT * FROM MonsterEntity")
    suspend fun getAllCoins(): List<MonsterEntity>

    @Upsert
    suspend fun insertMonsters(monsters: List<MonsterEntity>)

    @Query("SELECT * FROM MonsterEntity WHERE id = :id")
    suspend fun getCoinById(id: String): MonsterEntity

    @Query("DELETE FROM MonsterEntity")
    suspend fun deleteAllMonsters()
}