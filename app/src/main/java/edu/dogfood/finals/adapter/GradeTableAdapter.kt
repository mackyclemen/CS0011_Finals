package edu.dogfood.finals.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import edu.dogfood.finals.R
import edu.dogfood.finals.data.GradeEntry

class GradeTableAdapter(private val dataSet: List<GradeEntry>):
    RecyclerView.Adapter<GradeTableAdapter.ViewHolder>() {

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val tvGradeRaw: TextView = view.findViewById(R.id.grade_raw_value)
        val tvGradePoint: TextView = view.findViewById(R.id.grade_point_value)
        val tvGradeDescription: TextView = view.findViewById(R.id.grade_description_value)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.listitem_grade_conversion, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvGradeRaw.text = dataSet[position].rawRange
        holder.tvGradePoint.text = dataSet[position].point
        holder.tvGradeDescription.text = dataSet[position].description
    }

    override fun getItemCount(): Int = dataSet.size
}