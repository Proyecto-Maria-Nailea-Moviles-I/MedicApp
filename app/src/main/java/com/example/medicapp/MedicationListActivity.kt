package com.example.medicapp

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.TypedValue
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.medicapp.data.memory.sharedMedicationRepository
import com.example.medicapp.domain.model.Medication
import com.example.medicapp.domain.model.MedicationStatus
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MedicationListActivity : AppCompatActivity() {

    private enum class FilterStatus { ALL, ACTIVE, COMPLETED }

    private var currentFilterStatus = FilterStatus.ALL
    private var searchQuery = ""

    private lateinit var containerMedications: LinearLayout
    private lateinit var tvEmpty: TextView
    private lateinit var txtSearch: EditText
    private lateinit var btnFilterAll: Button
    private lateinit var btnFilterActive: Button
    private lateinit var btnFilterCompleted: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_medication_list)

        containerMedications = findViewById(R.id.containerMedications_List)
        tvEmpty = findViewById(R.id.tvEmpty_List)
        txtSearch = findViewById(R.id.txtSearch_List)

        btnFilterAll = findViewById(R.id.btnFilterAll_List)
        btnFilterActive = findViewById(R.id.btnFilterActive_List)
        btnFilterCompleted = findViewById(R.id.btnFilterCompleted_List)

        val btnBack = findViewById<ImageButton>(R.id.btnBack_List)
        val btnFilter = findViewById<ImageButton>(R.id.btnFilter_List)
        val btnAdd = findViewById<FloatingActionButton>(R.id.btnAdd_List)

        val btnHome = findViewById<Button>(R.id.btnHome_List)
        val btnMedicationList = findViewById<Button>(R.id.btnMedicationList_List)
        val btnPharmacy = findViewById<Button>(R.id.btnPharmacy_List)
        val btnHistory = findViewById<Button>(R.id.btnHistory_List)

        btnBack.setOnClickListener {
            finish()
        }

        btnAdd.setOnClickListener {
            val intent = Intent(this, MedicationActivity::class.java)
            startActivity(intent)
        }

        btnFilter.setOnClickListener {
            val intent = Intent(this, MedicationFiltersActivity::class.java)
            startActivity(intent)
        }

        btnFilterAll.setOnClickListener {
            currentFilterStatus = FilterStatus.ALL
            updateFilterButtonStyles()
            displayMedications()
        }

        btnFilterActive.setOnClickListener {
            currentFilterStatus = FilterStatus.ACTIVE
            updateFilterButtonStyles()
            displayMedications()
        }

        btnFilterCompleted.setOnClickListener {
            currentFilterStatus = FilterStatus.COMPLETED
            updateFilterButtonStyles()
            displayMedications()
        }

        txtSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                searchQuery = s?.toString()?.trim() ?: ""
                displayMedications()
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        btnHome.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        btnMedicationList.setOnClickListener {
            // Already on MedicationListActivity
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

    override fun onResume() {
        super.onResume()
        displayMedications()
    }

    private fun updateFilterButtonStyles() {
        val blueColor = ContextCompat.getColor(this, R.color.home_blue)
        val whiteColor = Color.WHITE

        btnFilterAll.setBackgroundColor(if (currentFilterStatus == FilterStatus.ALL) blueColor else whiteColor)
        btnFilterAll.setTextColor(if (currentFilterStatus == FilterStatus.ALL) whiteColor else blueColor)

        btnFilterActive.setBackgroundColor(if (currentFilterStatus == FilterStatus.ACTIVE) blueColor else whiteColor)
        btnFilterActive.setTextColor(if (currentFilterStatus == FilterStatus.ACTIVE) whiteColor else blueColor)

        btnFilterCompleted.setBackgroundColor(if (currentFilterStatus == FilterStatus.COMPLETED) blueColor else whiteColor)
        btnFilterCompleted.setTextColor(if (currentFilterStatus == FilterStatus.COMPLETED) whiteColor else blueColor)
    }

    private fun displayMedications() {
        containerMedications.removeAllViews()

        var list = sharedMedicationRepository.getMedications()

        if (searchQuery.isNotEmpty()) {
            list = list.filter { it.name.contains(searchQuery, ignoreCase = true) }
        }

        when (currentFilterStatus) {
            FilterStatus.ACTIVE -> list = list.filter { it.status == MedicationStatus.ACTIVE }
            FilterStatus.COMPLETED -> list = list.filter { it.status == MedicationStatus.COMPLETED }
            FilterStatus.ALL -> {}
        }

        if (list.isEmpty()) {
            tvEmpty.visibility = View.VISIBLE
            containerMedications.addView(tvEmpty)
        } else {
            tvEmpty.visibility = View.GONE

            for (medication in list) {
                val cardView = createMedicationCard(medication)
                containerMedications.addView(cardView)
            }
        }
    }

    private fun createMedicationCard(medication: Medication): View {
        val card = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setBackgroundColor(Color.WHITE)
            val padding = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 14f, resources.displayMetrics
            ).toInt()
            setPadding(padding, padding, padding, padding)

            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                bottomMargin = TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, 12f, resources.displayMetrics
                ).toInt()
            }
            layoutParams = params
            elevation = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 2f, resources.displayMetrics
            )
        }

        val tvName = TextView(this).apply {
            text = medication.name
            textSize = 16f
            setTypeface(null, Typeface.BOLD)
            setTextColor(ContextCompat.getColor(context, R.color.home_text))
        }

        val tvDosagePresentation = TextView(this).apply {
            text = "${medication.dosage} - ${medication.presentation}"
            textSize = 14f
            setTextColor(ContextCompat.getColor(context, R.color.home_gray))
        }

        val tvFrequency = TextView(this).apply {
            text = getString(R.string.lblFrequency) + ": " + medication.frequency.name
            textSize = 12f
            setTextColor(ContextCompat.getColor(context, R.color.home_blue))
        }

        card.addView(tvName)
        card.addView(tvDosagePresentation)
        card.addView(tvFrequency)

        card.setOnClickListener {
            val intent = Intent(this, MedicationDetailActivity::class.java)
            intent.putExtra("medication_id", medication.id)
            startActivity(intent)
        }

        return card
    }
}
