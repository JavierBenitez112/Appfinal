package com.uvg.ejercicioslabs.ejercicios.ktor.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.uvg.ejercicioslabs.ejercicios.ktor.data.local.dao.MonsterDao
import com.uvg.ejercicioslabs.ejercicios.ktor.data.local.entity.MonsterEntity

@Database(
    entities = [MonsterEntity::class],
    version = 1
)
abstract class ZeldaDatabase: RoomDatabase() {
    abstract fun monsterDao(): MonsterDao
}