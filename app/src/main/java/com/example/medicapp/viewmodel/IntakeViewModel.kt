package com.example.medicapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.medicapp.domain.model.MedicationIntake
import com.example.medicapp.domain.repository.IIntakeRepository

class IntakeViewModel(
    private val repository: IIntakeRepository
) : ViewModel() {

    fun getAllIntakes(): List<MedicationIntake> {
        return repository.getAll()
    }

    fun getIntakeById(id: String): MedicationIntake? {
        return repository.getById(id)
    }

    fun saveIntake(intake: MedicationIntake) {
        repository.add(intake)
    }

    fun updateIntake(intake: MedicationIntake) {
        repository.update(intake)
    }

    fun removeIntake(id: String) {
        repository.delete(id)
    }
}
