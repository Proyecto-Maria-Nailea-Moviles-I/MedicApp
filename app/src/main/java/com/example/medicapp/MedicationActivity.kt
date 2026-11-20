package com.example.medicapp

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.medicapp.data.memory.sharedMedicationRepository
import com.example.medicapp.domain.model.Medication
import com.example.medicapp.domain.model.MedicationFrequency
import com.example.medicapp.domain.model.MedicationStatus
import java.time.LocalDate
import java.util.Calendar

class MedicationActivity : AppCompatActivity(),
    DatePickerDialog.OnDateSetListener {

    private lateinit var txtId: EditText
    private lateinit var txtName: EditText
    private lateinit var txtDosage: EditText
    private lateinit var txtPresentation: EditText
    private lateinit var txtFrequency: EditText
    private lateinit var txtSchedules: EditText
    private lateinit var txtQuantity: EditText
    private lateinit var tvEndDate: TextView
    private lateinit var tvTitle: TextView
    private lateinit var btnSave: Button

    private var endDay: Int = 0
    private var endMonth: Int = 0
    private var endYear: Int = 0

    private var isEditMode: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_medication)

        txtId = findViewById<EditText>(R.id.txtId_Medication)
        txtName = findViewById<EditText>(R.id.txtName_Medication)
        txtDosage = findViewById<EditText>(R.id.txtDosage_Medication)
        txtPresentation = findViewById<EditText>(R.id.txtPresentation_Medication)
        txtFrequency = findViewById<EditText>(R.id.txtFrequency_Medication)
        txtSchedules = findViewById<EditText>(R.id.txtSchedules_Medication)
        txtQuantity = findViewById<EditText>(R.id.txtQuantity_Medication)
        tvEndDate = findViewById<TextView>(R.id.tvEndDate_Medication)
        tvTitle = findViewById<TextView>(R.id.tvTitle_Medication)
        btnSave = findViewById<Button>(R.id.btnSave_Medication)

        val btnCancel = findViewById<Button>(R.id.btnCancel_Medication)
        val btnBack = findViewById<ImageButton>(R.id.btnBack_Medication)
        val btnSearch = findViewById<ImageButton>(R.id.btnSearch_Medication)
        val btnEndDate = findViewById<ImageButton>(R.id.btnEndDate_Medication)

        val btnHome = findViewById<Button>(R.id.btnHome_Medication)
        val btnMedicationList = findViewById<Button>(R.id.btnMedicationList_Medication)
        val btnPharmacy = findViewById<Button>(R.id.btnPharmacy_Medication)
        val btnHistory = findViewById<Button>(R.id.btnHistory_Medication)

        resetDate()

        val medicationId = intent.getStringExtra("medication_id")

        if (!medicationId.isNullOrBlank()) {
            txtId.setText(medicationId)
            searchMedication(medicationId)
        }

        btnSearch.setOnClickListener(View.OnClickListener {
            searchMedication(txtId.text.toString().trim())
        })

        btnEndDate.setOnClickListener(View.OnClickListener {
            showDatePickerDialog()
        })

        btnSave.setOnClickListener(View.OnClickListener {
            saveMedication()
        })

        btnCancel.setOnClickListener(View.OnClickListener {
            cleanScreen()
        })

        btnBack.setOnClickListener(View.OnClickListener {
            finish()
        })

        btnHome.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        })

        btnMedicationList.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, MedicationListActivity::class.java)
            startActivity(intent)
        })

        btnPharmacy.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, NearbyPharmaciesActivity::class.java)
            startActivity(intent)
        })

        btnHistory.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, IntakeHistoryActivity::class.java)
            startActivity(intent)
        })
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        endDay = dayOfMonth
        endMonth = month + 1
        endYear = year

        tvEndDate.text = getDateFormatString(
            endDay,
            endMonth,
            endYear
        )
    }

    private fun resetDate() {
        val calendar = Calendar.getInstance()

        endDay = calendar.get(Calendar.DAY_OF_MONTH)
        endMonth = calendar.get(Calendar.MONTH) + 1
        endYear = calendar.get(Calendar.YEAR)
    }

    private fun showDatePickerDialog() {
        val datePickerDialog = DatePickerDialog(
            this,
            this,
            endYear,
            endMonth - 1,
            endDay
        )

        datePickerDialog.show()
    }

    private fun getDateFormatString(
        day: Int,
        month: Int,
        year: Int
    ): String {
        return "$day/$month/$year"
    }

    private fun searchMedication(id: String) {
        try {

            if (id.isEmpty()) {
                Toast.makeText(
                    this,
                    R.string.msg_required_field,
                    Toast.LENGTH_SHORT
                ).show()
                return
            }

            val medication = sharedMedicationRepository.getById(id)

            if (medication != null) {

                isEditMode = true

                txtId.setText(medication.id)
                txtId.isEnabled = false

                txtName.setText(medication.name)
                txtDosage.setText(medication.dosage)
                txtPresentation.setText(medication.presentation)

                when (medication.frequency) {

                    MedicationFrequency.EVERY_8_HOURS ->
                        txtFrequency.setText(R.string.frequency_8_hours)

                    MedicationFrequency.EVERY_12_HOURS ->
                        txtFrequency.setText(R.string.frequency_12_hours)

                    MedicationFrequency.EVERY_24_HOURS ->
                        txtFrequency.setText(R.string.frequency_24_hours)

                    MedicationFrequency.CUSTOM ->
                        txtFrequency.setText(R.string.frequency_custom)
                }

                txtSchedules.setText(
                    medication.schedules.joinToString(", ")
                )

                txtQuantity.setText(
                    medication.quantity.toString()
                )

                endDay = medication.endDate.dayOfMonth
                endMonth = medication.endDate.monthValue
                endYear = medication.endDate.year

                tvEndDate.text = getDateFormatString(
                    endDay,
                    endMonth,
                    endYear
                )

                btnSave.setText(R.string.btnUpdate_Medication)
                tvTitle.setText(R.string.btnUpdate_Medication)

            } else {

                Toast.makeText(
                    this,
                    R.string.msg_not_found,
                    Toast.LENGTH_SHORT
                ).show()
            }

        } catch (e: Exception) {

            Toast.makeText(
                this,
                e.message.toString(),
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun cleanScreen() {
        resetDate()

        isEditMode = false

        txtId.isEnabled = true
        txtId.setText("")
        txtName.setText("")
        txtDosage.setText("")
        txtPresentation.setText("")
        txtFrequency.setText("")
        txtSchedules.setText("")
        txtQuantity.setText("")
        tvEndDate.text = ""

        btnSave.setText(R.string.btnSave_Medication)
        tvTitle.setText(R.string.new_medication_title)
    }

    private fun isValidationData(): Boolean {
        return txtName.text.toString().trim().isNotBlank()
                && txtDosage.text.toString().trim().isNotBlank()
                && txtPresentation.text.toString().trim().isNotBlank()
                && txtFrequency.text.toString().trim().isNotBlank()
                && txtSchedules.text.toString().trim().isNotBlank()
                && txtQuantity.text.toString().trim().isNotBlank()
                && tvEndDate.text.toString().trim().isNotBlank()
    }

    private fun saveMedication() {
        try {

            if (!isValidationData()) {
                Toast.makeText(
                    this,
                    R.string.msg_required_field,
                    Toast.LENGTH_SHORT
                ).show()
                return
            }

            val quantity = txtQuantity.text.toString().trim().toIntOrNull()

            if (quantity == null || quantity < 0) {
                Toast.makeText(
                    this,
                    R.string.msg_invalid_quantity,
                    Toast.LENGTH_SHORT
                ).show()
                return
            }

            val frequencyText = txtFrequency.text.toString().trim()

            val frequency = when (frequencyText) {

                "8", getString(R.string.frequency_8_hours) ->
                    MedicationFrequency.EVERY_8_HOURS

                "12", getString(R.string.frequency_12_hours) ->
                    MedicationFrequency.EVERY_12_HOURS

                "24", getString(R.string.frequency_24_hours) ->
                    MedicationFrequency.EVERY_24_HOURS

                else -> MedicationFrequency.CUSTOM
            }

            var medicationId = System.currentTimeMillis().toString()
            var medicationImageUri: String? = null
            var medicationStatus = MedicationStatus.ACTIVE
            var medicationNotes = ""

            if (isEditMode) {

                val currentMedication = sharedMedicationRepository.getById(
                    txtId.text.toString().trim()
                )

                if (currentMedication == null) {
                    Toast.makeText(
                        this,
                        R.string.msg_not_found,
                        Toast.LENGTH_SHORT
                    ).show()
                    return
                }

                medicationId = currentMedication.id
                medicationImageUri = currentMedication.imageUri
                medicationStatus = currentMedication.status
                medicationNotes = currentMedication.notes
            }

            val medication = Medication(
                id = medicationId,
                name = txtName.text.toString().trim(),
                dosage = txtDosage.text.toString().trim(),
                presentation = txtPresentation.text.toString().trim(),
                frequency = frequency,
                schedules = txtSchedules.text.toString().split(",").map { it.trim() },
                quantity = quantity,
                endDate = LocalDate.of(endYear, endMonth, endDay),
                imageUri = medicationImageUri,
                status = medicationStatus,
                notes = medicationNotes
            )

            if (isEditMode) {

                AlertDialog.Builder(this)
                    .setTitle(R.string.btnUpdate_Medication)
                    .setMessage(R.string.dialog_confirmation)
                    .setPositiveButton(R.string.dialog_yes) { _, _ ->

                        sharedMedicationRepository.updateMedication(medication)

                        Toast.makeText(
                            this,
                            R.string.msg_update_success,
                            Toast.LENGTH_SHORT
                        ).show()

                        cleanScreen()
                        finish()
                    }
                    .setNegativeButton(R.string.dialog_no, null)
                    .show()

            } else {

                sharedMedicationRepository.addMedication(medication)

                Toast.makeText(
                    this,
                    R.string.msg_save_success,
                    Toast.LENGTH_SHORT
                ).show()

                cleanScreen()
                finish()
            }

        } catch (e: Exception) {

            Toast.makeText(
                this,
                e.message.toString(),
                Toast.LENGTH_LONG
            ).show()
        }
    }
}
