package edu.dogfood.finals.conversion

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import edu.dogfood.finals.R
import edu.dogfood.finals.data.Subject

class ConversionAdapter(private val dataSet: List<Subject>,
                        private val addBtnListener: View.OnClickListener,
                        private val onSelectionListener: OnSelectionListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val VIEWTYPE_ITEM : Int = 0
    private val VIEWTYPE_ACTION : Int = 1

    fun interface OnSelectionListener {
        fun onItemSelected(position: Int)
    }

    private class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val subjectName: TextView = view.findViewById(R.id.subject_name)
        val subjectUnits: TextView = view.findViewById(R.id.subject_units)
        val subjectGrade: TextView = view.findViewById(R.id.subject_grade)
    }

    private class ActionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val actionBtn: Button = view.findViewById(R.id.list_add_action_btn)
    }

    override fun getItemViewType(position: Int): Int {
        return if(position < dataSet.size) VIEWTYPE_ITEM else VIEWTYPE_ACTION
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val viewHolder: RecyclerView.ViewHolder = when (viewType) {
            VIEWTYPE_ITEM -> {
                val view = inflater.inflate(R.layout.listitem_subject, parent, false)
                ItemViewHolder(view)
            }

            VIEWTYPE_ACTION -> {
                val view = inflater.inflate(R.layout.listitem_add_subject, parent, false)
                ActionViewHolder(view)
            }

            else -> { throw NotImplementedError("ViewType $viewType not supported") }
        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            VIEWTYPE_ITEM -> {
                val _item = holder as ItemViewHolder
                _item.subjectName.text = dataSet[position].name
                _item.subjectUnits.text = String.format("Subject Units: %d", dataSet[position].units)
                _item.subjectGrade.text = String.format("Grade: %.02f", dataSet[position].grade)
                _item.itemView.setOnClickListener {
                    onSelectionListener.onItemSelected(position)
                }
            }

            VIEWTYPE_ACTION -> {
                val _action = holder as ActionViewHolder
                _action.actionBtn.setOnClickListener(addBtnListener)
            }
        }
    }

    override fun getItemCount(): Int = dataSet.size + 1
}