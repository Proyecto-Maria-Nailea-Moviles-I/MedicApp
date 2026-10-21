package com.example.medicapp.util

class util {

    companion object {

        fun isNameValid(name: String): Boolean {
            return name.trim().isNotEmpty()
        }

        fun isDosageValid(dosage: String): Boolean {
            return dosage.trim().isNotEmpty()
        }

        fun isQuantityValid(quantity: Int): Boolean {
            return quantity >= 0
        }

    }
}
