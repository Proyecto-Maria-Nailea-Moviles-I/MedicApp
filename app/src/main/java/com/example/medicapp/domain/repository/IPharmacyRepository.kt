package com.example.medicapp.domain.repository

import com.example.medicapp.domain.model.Pharmacy

interface IPharmacyRepository {

    fun getAll(): List<Pharmacy>

    fun getById(id: String): Pharmacy?

    fun add(pharmacy: Pharmacy)

    fun update(pharmacy: Pharmacy)

    fun delete(id: String)
}
