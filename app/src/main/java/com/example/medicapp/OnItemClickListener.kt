package com.example.medicapp

import com.example.medicapp.domain.model.Medication

interface OnItemClickListener {
    fun onItemClick(medication: Medication)
}
