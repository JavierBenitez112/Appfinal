package com.uvg.ejercicioslabs.ejercicios.ktor.di

import android.content.Context
import androidx.room.Room
import com.uvg.ejercicioslabs.ejercicios.ktor.data.local.ZeldaDatabase
import com.uvg.ejercicioslabs.ejercicios.ktor.data.network.HttpClientFactory
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO

object KtorDependencies {
    private var httpClient: HttpClient? = null
    private var localDb: ZeldaDatabase? = null

    private fun buildHttpClient(): HttpClient = HttpClientFactory.create()

    private fun buildLocalDb(context: Context): ZeldaDatabase {
        return Room.databaseBuilder(
            context,
            ZeldaDatabase::class.java,
            "zelda.db"
        ).build()
    }

    fun provideHttpClient(): HttpClient {
        return httpClient ?: synchronized(this) {
            httpClient ?: buildHttpClient().also { httpClient = it }
        }
    }

    fun provideLocalDb(context: Context): ZeldaDatabase {
        return localDb ?: synchronized(this) {
            localDb ?: buildLocalDb(context).also { localDb = it }
        }
    }
}