package com.example.medicapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.medicapp.domain.model.Medication
import com.example.medicapp.domain.model.MedicationStatus
import com.example.medicapp.domain.repository.IMedicationRepository

class HomeViewModel(
    private val repository: IMedicationRepository
) : ViewModel() {

    fun getActiveMedications(): List<Medication> {
        return repository.getMedications().filter {
            it.status == MedicationStatus.ACTIVE
        }
    }

    fun getMedicationCount(): Int {
        return repository.getMedications().size
    }
}
