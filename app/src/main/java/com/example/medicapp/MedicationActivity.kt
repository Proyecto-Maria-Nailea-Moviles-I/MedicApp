
package com.example.medicapp

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.medicapp.data.memory.sharedMedicationRepository
import com.example.medicapp.domain.model.Medication
import com.example.medicapp.domain.model.MedicationFrequency
import com.example.medicapp.domain.model.MedicationStatus
import java.time.LocalDate
import java.util.Calendar

class MedicationActivity : AppCompatActivity() {

    private lateinit var tvEndDate: TextView

    private var endDay: Int = 0
    private var endMonth: Int = 0
    private var endYear: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_medication)

        tvEndDate = findViewById<TextView>(R.id.tvEndDate_Medication)

        val txtName = findViewById<EditText>(R.id.txtName_Medication)
        val txtDosage = findViewById<EditText>(R.id.txtDosage_Medication)
        val txtPresentation = findViewById<EditText>(R.id.txtPresentation_Medication)
        val txtFrequency = findViewById<EditText>(R.id.txtFrequency_Medication)
        val txtSchedules = findViewById<EditText>(R.id.txtSchedules_Medication)
        val txtQuantity = findViewById<EditText>(R.id.txtQuantity_Medication)

        val btnBack = findViewById<ImageButton>(R.id.btnBack_Medication)
        val btnEndDate = findViewById<ImageButton>(R.id.btnEndDate_Medication)
        val btnSave = findViewById<Button>(R.id.btnSave_Medication)
        val btnHome = findViewById<Button>(R.id.btnHome_Medication)

        btnBack.setOnClickListener {
            finish()
        }

        btnHome.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        btnEndDate.setOnClickListener {
            val calendar = Calendar.getInstance()

            val day = calendar.get(Calendar.DAY_OF_MONTH)
            val month = calendar.get(Calendar.MONTH)
            val year = calendar.get(Calendar.YEAR)

            val datePicker = DatePickerDialog(
                this,
                { _, selectedYear, selectedMonth, selectedDay ->

                    endDay = selectedDay
                    endMonth = selectedMonth + 1
                    endYear = selectedYear

                    tvEndDate.text = "$endDay/$endMonth/$endYear"
                },
                year,
                month,
                day
            )

            datePicker.show()
        }

        btnSave.setOnClickListener {

            val name = txtName.text.toString().trim()
            val dosage = txtDosage.text.toString().trim()
            val presentation = txtPresentation.text.toString().trim()
            val frequencyText = txtFrequency.text.toString().trim()
            val schedulesText = txtSchedules.text.toString().trim()
            val quantityText = txtQuantity.text.toString().trim()

            if (name.isEmpty()) {
                Toast.makeText(this, R.string.msg_invalid_name, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (dosage.isEmpty()) {
                Toast.makeText(this, R.string.msg_invalid_dosage, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (presentation.isEmpty() || frequencyText.isEmpty() ||
                schedulesText.isEmpty() || quantityText.isEmpty()) {
                Toast.makeText(this, R.string.msg_required_field, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val quantity = quantityText.toIntOrNull()

            if (quantity == null || quantity < 0) {
                Toast.makeText(this, R.string.msg_invalid_quantity, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (endYear == 0) {
                Toast.makeText(this, R.string.medication_select_date, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val frequency = when (frequencyText) {
                "8", getString(R.string.frequency_8_hours) ->
                    MedicationFrequency.EVERY_8_HOURS

                "12", getString(R.string.frequency_12_hours) ->
                    MedicationFrequency.EVERY_12_HOURS

                "24", getString(R.string.frequency_24_hours) ->
                    MedicationFrequency.EVERY_24_HOURS

                else -> MedicationFrequency.CUSTOM
            }

            val medication = Medication(
                id = System.currentTimeMillis().toString(),
                name = name,
                dosage = dosage,
                presentation = presentation,
                frequency = frequency,
                schedules = schedulesText.split(",").map { it.trim() },
                quantity = quantity,
                endDate = LocalDate.of(endYear, endMonth, endDay),
                imageUri = null,
                status = MedicationStatus.ACTIVE,
                notes = ""
            )

            sharedMedicationRepository.addMedication(medication)

            Toast.makeText(
                this,
                R.string.msg_save_success,
                Toast.LENGTH_SHORT
            ).show()

            finish()
        }

        val btnPharmacy = findViewById<Button>(R.id.btnPharmacy_Medication)

        btnPharmacy.setOnClickListener {
            val intent = Intent(this, NearbyPharmaciesActivity::class.java)
            startActivity(intent)
        }
        val btnHistory = findViewById<Button>(R.id.btnHistory_Medication)

        btnHistory.setOnClickListener {
            val intent = Intent(this, IntakeHistoryActivity::class.java)
            startActivity(intent)
        }
    }
}
