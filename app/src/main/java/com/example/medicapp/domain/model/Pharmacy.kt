package com.example.medicapp.domain.model

data class Pharmacy(
    val id: String,
    val name: String,
    val address: String,
    val latitude: Double,
    val longitude: Double,
    val phone: String
)