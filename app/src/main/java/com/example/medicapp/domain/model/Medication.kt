package com.example.medicapp.domain.model

import java.time.LocalDate

data class Medication(
    val id: String,
    val name: String,
    val dosage: String,
    val presentation: String,
    val frequency: MedicationFrequency,
    val schedules: List<String>,
    val quantity: Int,
    val endDate: LocalDate,
    val imageUri: String?,
    val status: MedicationStatus,
    val notes: String
)

