package com.example.medicapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.medicapp.domain.model.Pharmacy
import com.example.medicapp.domain.repository.IPharmacyRepository

class PharmacyViewModel(
    private val repository: IPharmacyRepository
) : ViewModel() {

    fun getAllPharmacies(): List<Pharmacy> {
        return repository.getAll()
    }

    fun getPharmacyById(id: String): Pharmacy? {
        return repository.getById(id)
    }

    fun savePharmacy(pharmacy: Pharmacy) {
        repository.add(pharmacy)
    }

    fun updatePharmacy(pharmacy: Pharmacy) {
        repository.update(pharmacy)
    }

    fun removePharmacy(id: String) {
        repository.delete(id)
    }
}
