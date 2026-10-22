package com.example.medicapp.domain.repository

import com.example.medicapp.domain.model.MedicationIntake

interface IIntakeRepository {

    fun getAll(): List<MedicationIntake>

    fun getById(id: String): MedicationIntake?

    fun add(intake: MedicationIntake)

    fun update(intake: MedicationIntake)

    fun delete(id: String)
}