package com.example.medicapp.domain.model

import java.time.LocalDate

data class MedicationIntake(
    val id: String,
    val medicationId: String,
    val date: LocalDate,
    val time: String,
    val status: IntakeStatus
)