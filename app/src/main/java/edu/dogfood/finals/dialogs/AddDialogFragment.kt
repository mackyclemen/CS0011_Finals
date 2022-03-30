package edu.dogfood.finals.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.DialogFragment
import com.google.android.material.textfield.TextInputLayout
import edu.dogfood.finals.R
import edu.dogfood.finals.data.Subject
import java.lang.IllegalStateException

class AddDialogFragment: DialogFragment() {

    interface OnAddDialogActionListener {
        fun onPositiveClick(subject: Subject)
        fun onNegativeClick()
    }

    private lateinit var addDialogActionListener: OnAddDialogActionListener

    fun setOnAddDialogActionListener(listener: OnAddDialogActionListener) {
        this.addDialogActionListener = listener
    }

    private lateinit var subjNameFieldLayout: TextInputLayout
    private lateinit var subjUnitsFieldLayout: TextInputLayout
    private lateinit var subjGradeFieldLayout: TextInputLayout

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // return super.onCreateDialog(savedInstanceState)
        val dialog = activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = it.layoutInflater
            val view = inflater.inflate(R.layout.dialog_subject, null)

            subjNameFieldLayout = view.findViewById(R.id.subject_name_field_layout)
            subjUnitsFieldLayout = view.findViewById(R.id.subject_units_field_layout)
            subjGradeFieldLayout = view.findViewById(R.id.subject_grade_field_layout)

            builder.setTitle("Add a subject")
            builder.setView(view)
            builder.setPositiveButton(R.string.dialog_add_subject) { dialogInterface, i ->
                // Layout check if the EditTexts are not empty
                if(
                    subjNameFieldLayout.editText?.text?.isNotBlank() != false ||
                    subjUnitsFieldLayout.editText?.text?.isNotBlank() != false ||
                    subjGradeFieldLayout.editText?.text?.isNotBlank() != false
                ) {
                    val name: String = subjNameFieldLayout.editText?.text.toString()
                    val units: Int = subjUnitsFieldLayout.editText?.text.toString().toInt()
                    val grade: Double = subjGradeFieldLayout.editText?.text.toString().toDouble()
                    val subject = Subject(name, units, grade)

                    addDialogActionListener.onPositiveClick(subject)
                } else {
                    Toast.makeText(requireActivity().baseContext, "All fields are required", Toast.LENGTH_LONG)
                        .show()

                    dialogInterface.dismiss()
                }
            }

            builder.setNegativeButton(R.string.dialog_cancel) { _, _ ->
                addDialogActionListener.onNegativeClick()
            }

            builder.create()
        } ?: throw IllegalStateException("Activity can't be null")

        dialog.setOnShowListener {
            val positiveButton: Button = (it as AlertDialog).getButton(AlertDialog.BUTTON_POSITIVE)
            positiveButton.isEnabled = false

            var nameEmpty = true
            var unitEmpty = true
            var grdEmpty = true

            subjNameFieldLayout.editText?.doOnTextChanged { text, _, _, _ ->
                subjNameFieldLayout.error = if(text.isNullOrEmpty()) {
                    nameEmpty = true
                    getString(R.string.field_empty_error)
                } else {
                    nameEmpty = false
                    null
                }

                positiveButton.isEnabled = !nameEmpty && !unitEmpty && !grdEmpty
            }

            subjUnitsFieldLayout.editText?.doOnTextChanged { text, _, _, _ ->
                subjUnitsFieldLayout.error = if(text.isNullOrEmpty()) {
                    unitEmpty = true
                    getString(R.string.field_empty_error)
                } else {
                    unitEmpty = false
                    null
                }

                positiveButton.isEnabled = !nameEmpty && !unitEmpty && !grdEmpty
            }

            subjGradeFieldLayout.editText?.doOnTextChanged { text, _, _, _ ->
                subjGradeFieldLayout.error = if(text.isNullOrEmpty()){
                    grdEmpty = true
                    getString(R.string.field_empty_error)
                } else {
                    grdEmpty = false
                    null
                }

                positiveButton.isEnabled = !nameEmpty && !unitEmpty && !grdEmpty
            }
        }

        return dialog
    }
}