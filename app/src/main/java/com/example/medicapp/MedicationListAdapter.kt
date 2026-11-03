package com.example.medicapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.medicapp.domain.model.Medication

class MedicationListAdapter(
    private var medicationList: List<Medication>,
    val itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<CustomViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.activity_medication_item, parent, false
        )
        return CustomViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val item = medicationList[position]
        holder.bind(item, itemClickListener)
    }

    override fun getItemCount(): Int {
        return medicationList.size
    }

    fun updateList(newList: List<Medication>) {
        medicationList = newList
        notifyDataSetChanged()
    }
}

class CustomViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val tvTitle: TextView = view.findViewById(R.id.txtTitle_Item)
    val tvDosagePresentation: TextView = view.findViewById(R.id.txtDosagePresentation_Item)
    val tvFrequency: TextView = view.findViewById(R.id.txtFrequency_Item)
    val tvStatus: TextView = view.findViewById(R.id.txtStatus_Item)
    val imgPicture: ImageView = view.findViewById(R.id.imgPicture_Item)

    fun bind(medication: Medication, itemClickListener: OnItemClickListener) {
        tvTitle.text = medication.name
        tvDosagePresentation.text = "${medication.dosage} - ${medication.presentation}"
        tvFrequency.text = itemView.context.getString(R.string.lblFrequency) + ": " + medication.frequency.name
        tvStatus.text = medication.status.name
        imgPicture.setImageResource(R.drawable.icon_pill)

        itemView.setOnClickListener {
            itemClickListener.onItemClick(medication)
        }
    }
}
