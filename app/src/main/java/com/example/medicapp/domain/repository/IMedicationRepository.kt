package com.example.medicapp.domain.repository

import com.example.medicapp.domain.model.Medication

interface IMedicationRepository {

    fun getMedications(): List<Medication>

    fun getById(id: String): Medication?

    fun addMedication(medication: Medication)

    fun updateMedication(medication: Medication)

    fun deleteMedication(id: String)

}
