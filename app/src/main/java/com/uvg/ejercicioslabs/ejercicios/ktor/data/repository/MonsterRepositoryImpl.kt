package com.uvg.ejercicioslabs.ejercicios.ktor.data.repository

import com.uvg.ejercicioslabs.ejercicios.ktor.data.local.dao.MonsterDao
import com.uvg.ejercicioslabs.ejercicios.ktor.data.local.entity.mapToMonsterModel
import com.uvg.ejercicioslabs.ejercicios.ktor.data.network.dto.mapToMonsterEntity
import com.uvg.ejercicioslabs.ejercicios.ktor.data.network.dto.mapToMonsterModel
import com.uvg.ejercicioslabs.ejercicios.ktor.domain.model.DataError
import com.uvg.ejercicioslabs.ejercicios.ktor.domain.model.Monster
import com.uvg.ejercicioslabs.ejercicios.ktor.domain.network.CoinApi
import com.uvg.ejercicioslabs.ejercicios.ktor.domain.network.util.NetworkError
import com.uvg.ejercicioslabs.ejercicios.ktor.domain.network.util.Result
import com.uvg.ejercicioslabs.ejercicios.ktor.domain.repository.CoinRepository

class MonsterRepositoryImpl(
    private val zeldaApi: CoinApi,
    private val monsterDao: MonsterDao
): CoinRepository {
    override suspend fun getAllMonsters(): Result<List<Monster>, DataError> {
        when (val result = zeldaApi.getAllCoins()) {
            is Result.Error -> {
                println(result.error)

                val localMonsters = monsterDao.getAllCoins()
                if (localMonsters.isEmpty()) {
                    if (result.error == NetworkError.NO_INTERNET) {
                        return Result.Error(
                            DataError.NO_INTERNET
                        )
                    }

                    return Result.Error(
                        DataError.GENERIC_ERROR
                    )
                } else {
                    return Result.Success(
                        localMonsters.map { it.mapToMonsterModel() }
                    )
                }
            }
            is Result.Success -> {
                val remoteMonsters = result.data.data
                val currentTime = System.currentTimeMillis()
                val monstersWithTimestamp = remoteMonsters.map { it.mapToMonsterEntity().copy(lastUpdated = currentTime) }
                monsterDao.deleteAllMonsters()
                monsterDao.insertMonsters(monstersWithTimestamp)
                return Result.Success(
                    remoteMonsters.map { it.mapToMonsterModel() }
                )
            }
        }
    }

    override suspend fun getOneMonster(id: String): Result<Monster, DataError> {
        val localMonster = monsterDao.getCoinById(id)
        return Result.Success(
            localMonster.mapToMonsterModel()
        )
    }
}