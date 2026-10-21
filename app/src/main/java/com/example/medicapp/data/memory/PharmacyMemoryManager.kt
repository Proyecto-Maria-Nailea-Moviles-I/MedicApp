package com.example.medicapp.data.memory

import com.example.medicapp.domain.model.Pharmacy
import com.example.medicapp.domain.repository.IPharmacyRepository

class PharmacyMemoryManager : IPharmacyRepository {

    private val pharmacies = mutableListOf<Pharmacy>()

    override fun getAll(): List<Pharmacy> {
        return pharmacies
    }

    override fun getById(id: String): Pharmacy? {
        return pharmacies.firstOrNull { it.id.trim() == id.trim() }
    }

    override fun add(pharmacy: Pharmacy) {
        pharmacies.add(pharmacy)
    }

    override fun update(pharmacy: Pharmacy) {
        delete(pharmacy.id)
        add(pharmacy)
    }

    override fun delete(id: String) {
        pharmacies.removeIf { it.id.trim() == id.trim() }
    }
}
