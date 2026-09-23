package com.contactrapide.app.models

data class Prestataire(
    val nom: String,
    val metier: String,
    val telephone: String,
    val latitude: Double,
    val longitude: Double,
    val note: Double,
    val disponible: Boolean
)
