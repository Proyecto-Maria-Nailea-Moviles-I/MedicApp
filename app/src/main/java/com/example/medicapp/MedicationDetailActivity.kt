package com.example.medicapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.medicapp.data.memory.sharedMedicationRepository
import com.example.medicapp.domain.model.Medication

class MedicationDetailActivity : AppCompatActivity() {

    private var currentMedication: Medication? = null

    private lateinit var tvName: TextView
    private lateinit var tvDosage: TextView
    private lateinit var tvPresentation: TextView
    private lateinit var tvFrequency: TextView
    private lateinit var tvSchedules: TextView
    private lateinit var tvNextIntake: TextView
    private lateinit var tvQuantity: TextView
    private lateinit var tvEndDate: TextView
    private lateinit var tvStatus: TextView
    private lateinit var tvNotes: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_medication_detail)

        tvName = findViewById(R.id.tvName_Detail)
        tvDosage = findViewById(R.id.tvDosage_Detail)
        tvPresentation = findViewById(R.id.tvPresentation_Detail)
        tvFrequency = findViewById(R.id.tvFrequency_Detail)
        tvSchedules = findViewById(R.id.tvSchedules_Detail)
        tvNextIntake = findViewById(R.id.tvNextIntake_Detail)
        tvQuantity = findViewById(R.id.tvQuantity_Detail)
        tvEndDate = findViewById(R.id.tvEndDate_Detail)
        tvStatus = findViewById(R.id.tvStatus_Detail)
        tvNotes = findViewById(R.id.tvNotes_Detail)

        val btnBack = findViewById<ImageButton>(R.id.btnBack_Detail)
        val btnTake = findViewById<Button>(R.id.btnTake_Detail)
        val btnEdit = findViewById<Button>(R.id.btnEdit_Detail)
        val btnDelete = findViewById<Button>(R.id.btnDelete_Detail)

        val btnHome = findViewById<Button>(R.id.btnHome_Detail)
        val btnMedicationList = findViewById<Button>(R.id.btnMedicationList_Detail)
        val btnPharmacy = findViewById<Button>(R.id.btnPharmacy_Detail)
        val btnHistory = findViewById<Button>(R.id.btnHistory_Detail)

        val medicationId = intent.getStringExtra("medication_id")

        if (medicationId == null) {
            Toast.makeText(this, R.string.msg_not_found, Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        currentMedication = sharedMedicationRepository.getById(medicationId)

        if (currentMedication == null) {
            Toast.makeText(this, R.string.msg_not_found, Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        populateDetails()

        btnBack.setOnClickListener {
            finish()
        }

        btnTake.setOnClickListener {
            showTakeConfirmationDialog()
        }

        btnEdit.setOnClickListener {
            Toast.makeText(this, R.string.msg_action_pending, Toast.LENGTH_SHORT).show()
        }

        btnDelete.setOnClickListener {
            showDeleteConfirmationDialog()
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

    private fun populateDetails() {
        val med = currentMedication ?: return

        tvName.text = med.name
        tvDosage.text = med.dosage
        tvPresentation.text = med.presentation
        tvFrequency.text = med.frequency.name
        tvSchedules.text = med.schedules.joinToString(", ")
        tvNextIntake.text = if (med.schedules.isNotEmpty()) med.schedules[0] else "-"
        tvQuantity.text = med.quantity.toString()
        tvEndDate.text = med.endDate.toString()
        tvStatus.text = med.status.name
        tvNotes.text = if (med.notes.isNotEmpty()) med.notes else "-"
    }

    private fun showTakeConfirmationDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_take_medication, null)
        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .create()

        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        val btnAccept = dialogView.findViewById<Button>(R.id.btnAccept_DialogTake)
        btnAccept.setOnClickListener {
            val med = currentMedication
            if (med != null && med.quantity > 0) {
                val updated = med.copy(quantity = med.quantity - 1)
                sharedMedicationRepository.updateMedication(updated)
                currentMedication = updated
                populateDetails()
            }
            Toast.makeText(this, R.string.msg_intake_recorded, Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun showDeleteConfirmationDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_delete_medication, null)
        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .create()

        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        val btnCancel = dialogView.findViewById<Button>(R.id.btnCancel_DialogDelete)
        val btnConfirm = dialogView.findViewById<Button>(R.id.btnConfirm_DialogDelete)

        btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        btnConfirm.setOnClickListener {
            val med = currentMedication
            if (med != null) {
                sharedMedicationRepository.deleteMedication(med.id)
                Toast.makeText(this, R.string.msg_delete_success, Toast.LENGTH_SHORT).show()
                dialog.dismiss()
                finish()
            }
        }

        dialog.show()
    }
}
