package com.example.medicapp.data.memory

import com.example.medicapp.domain.model.MedicationIntake
import com.example.medicapp.domain.repository.IIntakeRepository

class IntakeMemoryManager : IIntakeRepository {

    private val intakes = mutableListOf<MedicationIntake>()

    override fun getAll(): List<MedicationIntake> {
        return intakes
    }

    override fun getById(id: String): MedicationIntake? {
        return intakes.firstOrNull { it.id.trim() == id.trim() }
    }

    override fun add(intake: MedicationIntake) {
        intakes.add(intake)
    }

    override fun update(intake: MedicationIntake) {
        delete(intake.id)
        add(intake)
    }

    override fun delete(id: String) {
        intakes.removeIf { it.id.trim() == id.trim() }
    }
}
