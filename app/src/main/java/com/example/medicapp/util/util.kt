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

        fun isAddressValid(address: String): Boolean {
            return address.trim().isNotEmpty()
        }

        fun isPhoneValid(phone: String): Boolean {
            return phone.trim().isNotEmpty()
        }

        fun isTimeValid(time: String): Boolean {
            return time.trim().isNotEmpty()
        }
    }
}
