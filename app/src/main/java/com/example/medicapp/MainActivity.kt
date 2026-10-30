package com.example.medicapp

import android.os.Bundle
import android.content.Intent
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.medicapp.data.memory.sharedMedicationRepository

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                systemBars.bottom
            )
            insets
        }

        val btnMedication = findViewById<Button>(R.id.btnMedication_Main)

        btnMedication.setOnClickListener {
            val intent = Intent(this, MedicationActivity::class.java)
            startActivity(intent)
        }

        val btnPharmacy = findViewById<Button>(R.id.btnPharmacy_Main)

        btnPharmacy.setOnClickListener {
            val intent = Intent(this, NearbyPharmaciesActivity::class.java)
            startActivity(intent)
        }

        val btnHistory = findViewById<Button>(R.id.btnHistory_Main)

        btnHistory.setOnClickListener {
            val intent = Intent(this, IntakeHistoryActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()

        val medications = sharedMedicationRepository.getMedications()

        val tvMedications = findViewById<TextView>(
            R.id.tvNoMedications_Main
        )

        if (medications.isEmpty()) {
            tvMedications.setText(R.string.home_no_medications)
        } else {
            var medicationList = ""

            for (medication in medications) {
                medicationList += medication.name + " - "
                medicationList += medication.dosage + "\n"
            }

            tvMedications.text = medicationList
        }
    }
}
