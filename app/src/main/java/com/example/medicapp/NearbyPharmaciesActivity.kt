package com.example.medicapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class NearbyPharmaciesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nearby_pharmacies)

        val txtSearch = findViewById<EditText>(R.id.txtSearch_Pharmacy)

        val btnBack = findViewById<ImageButton>(R.id.btnBack_Pharmacy)
        val btnSearch = findViewById<ImageButton>(R.id.btnSearch_Pharmacy)
        val btnHome = findViewById<Button>(R.id.btnHome_Pharmacy)
        val btnDirections = findViewById<Button>(R.id.btnDirections_Pharmacy)
        val btnDetails = findViewById<Button>(R.id.btnDetails_Pharmacy)

        btnBack.setOnClickListener {
            finish()
        }

        btnHome.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        btnSearch.setOnClickListener {
            if (txtSearch.text.toString().trim().isEmpty()) {
                Toast.makeText(
                    this,
                    R.string.pharmacy_enter_name,
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(
                    this,
                    R.string.msg_action_pending,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        btnDirections.setOnClickListener {
            Toast.makeText(
                this,
                R.string.msg_action_pending,
                Toast.LENGTH_SHORT
            ).show()
        }

        btnDetails.setOnClickListener {
            Toast.makeText(
                this,
                R.string.msg_action_pending,
                Toast.LENGTH_SHORT
            ).show()
        }
        val btnHistory = findViewById<Button>(R.id.btnHistory_Pharmacy)

        btnHistory.setOnClickListener {
            val intent = Intent(this, IntakeHistoryActivity::class.java)
            startActivity(intent)
        }

        val btnMedicationList = findViewById<Button>(R.id.btnMedicationList_Pharmacy)

        btnMedicationList.setOnClickListener {
            val intent = Intent(this, MedicationListActivity::class.java)
            startActivity(intent)
        }
    }
}
