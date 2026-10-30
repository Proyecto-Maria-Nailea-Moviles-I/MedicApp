package com.example.medicapp

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.Calendar

class IntakeHistoryActivity : AppCompatActivity() {

    private lateinit var tvSelectedDate: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intake_history)

        tvSelectedDate = findViewById<TextView>(
            R.id.tvSelectedDate_History
        )

        val btnBack = findViewById<ImageButton>(
            R.id.btnBack_History
        )

        val btnCalendar = findViewById<ImageButton>(
            R.id.btnCalendar_History
        )

        val btnHome = findViewById<Button>(
            R.id.btnHome_History
        )

        val btnMedicationList = findViewById<Button>(
            R.id.btnMedicationList_History
        )

        val btnPharmacy = findViewById<Button>(
            R.id.btnPharmacy_History
        )

        btnBack.setOnClickListener {
            finish()
        }

        btnHome.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        btnCalendar.setOnClickListener {
            val calendar = Calendar.getInstance()

            val day = calendar.get(Calendar.DAY_OF_MONTH)
            val month = calendar.get(Calendar.MONTH)
            val year = calendar.get(Calendar.YEAR)

            val datePicker = DatePickerDialog(
                this,
                { _, selectedYear, selectedMonth, selectedDay ->

                    tvSelectedDate.text =
                        "$selectedDay/${selectedMonth + 1}/$selectedYear"
                },
                year,
                month,
                day
            )

            datePicker.show()
        }

        btnPharmacy.setOnClickListener {
            val intent = Intent(
                this,
                NearbyPharmaciesActivity::class.java
            )
            startActivity(intent)
        }

        btnMedicationList.setOnClickListener {
            val intent = Intent(this, MedicationListActivity::class.java)
            startActivity(intent)
        }
    }
}
