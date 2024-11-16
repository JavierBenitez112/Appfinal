package com.uvg.ejercicioslabs.ejercicios.ktor.domain.model

import com.uvg.ejercicioslabs.ejercicios.ktor.domain.network.util.Error

enum class DataError: Error {
    NO_INTERNET,
    GENERIC_ERROR
}