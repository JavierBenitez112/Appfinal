package com.uvg.ejercicioslabs.ejercicios.ktor.data.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class EntryListDto(
    val data: List<EntryDto>,
    val message: String = "",
    val status: Int = 0
)
