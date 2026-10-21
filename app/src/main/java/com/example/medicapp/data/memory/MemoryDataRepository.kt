package com.example.medicapp.data.memory

import com.example.medicapp.domain.model.Medication
import com.example.medicapp.domain.repository.IMedicationRepository

class MemoryDataRepository : IMedicationRepository {

    private val medications = mutableListOf<Medication>()

    override fun addMedication(medication: Medication) {
        medications.add(medication)
    }

    override fun deleteMedication(id: String) {
        medications.removeIf { it.id.trim() == id.trim() }
    }

    override fun updateMedication(medication: Medication) {
        deleteMedication(medication.id)
        addMedication(medication)
    }

    override fun getMedications(): List<Medication> {
        return medications
    }

    override fun getById(id: String): Medication? {
        val result = medications.filter {
            it.id.trim() == id.trim()
        }

        return if (result.any()) result[0] else null
    }
}
