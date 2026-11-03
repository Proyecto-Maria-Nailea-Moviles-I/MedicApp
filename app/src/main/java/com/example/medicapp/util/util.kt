package com.example.medicapp.util

import android.content.Context
import android.content.Intent

class util {

    companion object {

        fun openActivity(
            context: Context,
            objClass: Class<*>,
            extraName: String = "",
            value: String? = null
        ) {
            val intent = Intent(context, objClass).apply {
                if (extraName.isNotBlank() && value != null) {
                    putExtra(extraName, value)
                }
            }
            context.startActivity(intent)
        }

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
