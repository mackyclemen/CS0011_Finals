package edu.dogfood.finals

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.dogfood.finals.conversion.ConversionAdapter
import edu.dogfood.finals.data.Subject
import edu.dogfood.finals.dialogs.AddDialogFragment
import edu.dogfood.finals.dialogs.EditDialogFragment

class ConversionActivity : AppCompatActivity(), View.OnClickListener, ConversionAdapter.OnSelectionListener {
    private lateinit var listOfGrades: MutableList<Subject>
    private lateinit var gradeRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_conversion)

        val toolbar: Toolbar = findViewById(R.id.conversion_toolbar)
        setSupportActionBar(toolbar)

        listOfGrades = mutableListOf()

        gradeRecyclerView = findViewById(R.id.recyclerview_grades)
        gradeRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        gradeRecyclerView.adapter = ConversionAdapter(listOfGrades, this, this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.activity_conversion, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.conversion_menu_calculate -> {
                if(listOfGrades.isEmpty()) {
                    Toast.makeText(this, "Please add a subject first!", Toast.LENGTH_LONG)
                        .show()
                } else {
                    var grades = 0.0
                    var units = 0

                    listOfGrades.forEach {
                        units += it.units
                        grades += (it.units * it.grade)
                    }

                    val gwa = grades / units

                    // Create a bundle to pass to the ResultActivity
                    val args = Bundle()
                    args.putInt(resources.getString(R.string.key_total_subjects), listOfGrades.size)
                    args.putDouble(resources.getString(R.string.key_total_gwa), gwa)

                    // Initialize an intent
                    val intent = Intent(this, ResultActivity::class.java)
                    intent.putExtra(resources.getString(R.string.key_result), args)

                    // Start activity
                    startActivity(intent)
                }

                true
            }

            else -> false
        }
    }

    override fun onClick(p0: View?) {
        val addDialogFragment = AddDialogFragment()
        addDialogFragment.setOnAddDialogActionListener(object: AddDialogFragment.OnAddDialogActionListener {
            override fun onPositiveClick(subject: Subject) {
                listOfGrades.add(subject)
                gradeRecyclerView.adapter?.notifyItemInserted(listOfGrades.size - 1)
            }

            override fun onNegativeClick() {}
        })

        addDialogFragment.show(supportFragmentManager, "fragment_dialog_onAdd")
    }

    override fun onItemSelected(position: Int) {
        // for item selection
        val subj = listOfGrades[position]
        val editDialogFragment = EditDialogFragment()
        editDialogFragment.setOnEditDialogActionListener(object: EditDialogFragment.OnEditDialogActionListener {
            override fun onPositiveClick(subject: Subject) {
                // Change subject on a certain position.
                listOfGrades[position] = subject

                // Notify adapter
                gradeRecyclerView.adapter?.notifyItemChanged(position)
            }

            override fun onNegativeClick() {
                // Remove certain item in list
                listOfGrades.removeAt(position)

                // Notify adapter
                gradeRecyclerView.adapter?.notifyItemRemoved(position)
            }

            override fun onNeutralClick() {}
        })

        val args = Bundle()
        args.putString(resources.getString(R.string.key_subj_name), subj.name)
        args.putInt(resources.getString(R.string.key_subj_units), subj.units)
        args.putDouble(resources.getString(R.string.key_subj_grade), subj.grade)

        editDialogFragment.arguments = args
        editDialogFragment.show(supportFragmentManager, "fragment_dialog_onEdit")
    }
}