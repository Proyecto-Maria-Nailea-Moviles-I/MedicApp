package com.example.medicapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.medicapp.data.memory.sharedMedicationRepository
import com.example.medicapp.domain.model.Medication
import com.example.medicapp.domain.repository.IMedicationRepository

class MedicationViewModel(
    private val repository: IMedicationRepository = sharedMedicationRepository
) : ViewModel() {

    fun getMedications(): List<Medication> {
        return repository.getMedications()
    }

    fun getMedicationById(id: String): Medication? {
        return repository.getById(id)
    }

    fun saveMedication(medication: Medication) {
        repository.addMedication(medication)
    }

    fun updateMedication(medication: Medication) {
        repository.updateMedication(medication)
    }

    fun removeMedication(id: String) {
        repository.deleteMedication(id)
    }

    companion object {
        fun getMedications(): List<Medication> = sharedMedicationRepository.getMedications()
        fun getMedicationById(id: String): Medication? = sharedMedicationRepository.getById(id)
        fun saveMedication(medication: Medication) = sharedMedicationRepository.addMedication(medication)
        fun updateMedication(medication: Medication) = sharedMedicationRepository.updateMedication(medication)
        fun removeMedication(id: String) = sharedMedicationRepository.deleteMedication(id)
    }
}
