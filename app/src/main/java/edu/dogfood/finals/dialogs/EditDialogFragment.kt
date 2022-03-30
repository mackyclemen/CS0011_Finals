package edu.dogfood.finals.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.Button
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.DialogFragment
import com.google.android.material.textfield.TextInputLayout
import edu.dogfood.finals.R
import edu.dogfood.finals.data.Subject
import java.lang.IllegalStateException

class EditDialogFragment: DialogFragment(), DialogInterface.OnShowListener {

    interface OnEditDialogActionListener {
        fun onPositiveClick(subject: Subject)
        fun onNegativeClick()
        fun onNeutralClick()
    }

    private lateinit var editDialogActionListener: OnEditDialogActionListener
    fun setOnEditDialogActionListener(listener: OnEditDialogActionListener) {
        this.editDialogActionListener = listener
    }

    private lateinit var subjNameFieldLayout: TextInputLayout
    private lateinit var subjUnitsFieldLayout: TextInputLayout
    private lateinit var subjGradeFieldLayout: TextInputLayout

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // return super.onCreateDialog(savedInstanceState)
        val dialog = activity?.let { activity ->
            // Require that a bundle is bound
            arguments?.let { bundle ->
                val builder = AlertDialog.Builder(activity)
                val inflater = activity.layoutInflater
                val view = inflater.inflate(R.layout.dialog_subject, null)

                subjNameFieldLayout = view.findViewById(R.id.subject_name_field_layout)
                subjUnitsFieldLayout = view.findViewById(R.id.subject_units_field_layout)
                subjGradeFieldLayout = view.findViewById(R.id.subject_grade_field_layout)

                builder.setTitle("Edit subject")
                builder.setView(view)
                builder.setPositiveButton(R.string.dialog_edit_subject) { _, _ ->
                    val name: String = subjNameFieldLayout.editText?.text.toString()
                    val units: Int = subjUnitsFieldLayout.editText?.text.toString().toInt()
                    val grade: Double = subjGradeFieldLayout.editText?.text.toString().toDouble()

                    val subject = Subject(name, units, grade)

                    editDialogActionListener.onPositiveClick(subject)
                }

                builder.setNegativeButton(R.string.dialog_delete) { _, _ ->
                    // Build another dialog, and ask. If positive, then send call to listener,.
                    AlertDialog.Builder(activity)
                        .setTitle("Delete Subject?")
                        .setMessage("Are you sure? You'll be deleting a subject. This is irreversible!")
                        .setPositiveButton("Yes") { _, _ -> editDialogActionListener.onNegativeClick() }
                        .setNegativeButton("No", null)
                        .show()
                }

                builder.setNeutralButton(R.string.dialog_cancel) { dialogInterface, _ ->
                    editDialogActionListener.onNeutralClick()
                    dialogInterface.cancel()
                }

                // Set data on edit texts
                subjNameFieldLayout.editText?.setText(bundle.getString(resources.getString(R.string.key_subj_name), ""))
                subjUnitsFieldLayout.editText?.setText(bundle.getInt(resources.getString(R.string.key_subj_units), 0).toString())
                subjGradeFieldLayout.editText?.setText(bundle.getDouble(resources.getString(R.string.key_subj_grade), 0.0).toString())

                builder.create()
            } ?: throw IllegalStateException("Bundle can't be null - assign a bundle to this fragment")
        } ?: throw IllegalStateException("Activity can't be null")

        dialog.setOnShowListener(this)

        return dialog
    }

    override fun onShow(dialog: DialogInterface?) {
        val positiveButton: Button = (dialog as AlertDialog).getButton(AlertDialog.BUTTON_POSITIVE)

        var nameEmpty = false
        var unitEmpty = false
        var grdEmpty = false

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
}