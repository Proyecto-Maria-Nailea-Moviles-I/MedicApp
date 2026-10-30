package com.example.medicapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MedicationFiltersActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_medication_filters)

        val btnBack = findViewById<ImageButton>(R.id.btnBack_Filters)
        val btnApply = findViewById<Button>(R.id.btnApply_Filters)
        val btnClear = findViewById<Button>(R.id.btnClear_Filters)

        val rgStatus = findViewById<RadioGroup>(R.id.rgStatus_Filters)
        val rgTime = findViewById<RadioGroup>(R.id.rgTime_Filters)
        val rgFrequency = findViewById<RadioGroup>(R.id.rgFrequency_Filters)
        val rgOrder = findViewById<RadioGroup>(R.id.rgOrder_Filters)

        val btnHome = findViewById<Button>(R.id.btnHome_Filters)
        val btnMedicationList = findViewById<Button>(R.id.btnMedicationList_Filters)
        val btnPharmacy = findViewById<Button>(R.id.btnPharmacy_Filters)
        val btnHistory = findViewById<Button>(R.id.btnHistory_Filters)

        btnBack.setOnClickListener {
            finish()
        }

        btnApply.setOnClickListener {
            Toast.makeText(this, R.string.msg_filters_applied, Toast.LENGTH_SHORT).show()
            finish()
        }

        btnClear.setOnClickListener {
            rgStatus.check(R.id.rbStatusAll_Filters)
            rgTime.check(R.id.rbTimeAll_Filters)
            rgFrequency.check(R.id.rbFreqAll_Filters)
            rgOrder.check(R.id.rbOrderName_Filters)
            Toast.makeText(this, R.string.msg_filters_cleared, Toast.LENGTH_SHORT).show()
        }

        btnHome.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        btnMedicationList.setOnClickListener {
            val intent = Intent(this, MedicationListActivity::class.java)
            startActivity(intent)
            finish()
        }

        btnPharmacy.setOnClickListener {
            val intent = Intent(this, NearbyPharmaciesActivity::class.java)
            startActivity(intent)
        }

        btnHistory.setOnClickListener {
            val intent = Intent(this, IntakeHistoryActivity::class.java)
            startActivity(intent)
        }
    }
}
