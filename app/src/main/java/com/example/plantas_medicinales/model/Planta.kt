package com.example.plantas_medicinales.model

import kotlinx.serialization.Serializable

@Serializable
data class Planta(
    val id: Int,
    val nombre_comun: String,
    val nombre_cientifico: String,
    val descripcion_uso: String,
    val imagen_url: String
)
