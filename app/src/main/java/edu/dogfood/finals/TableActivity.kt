package edu.dogfood.finals

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.dogfood.finals.adapter.GradeTableAdapter
import edu.dogfood.finals.data.GradeEntry

class TableActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_table)

        val grades = listOf(
            // Do not translate for now
            GradeEntry("95.8 - 100", "4.0", "Excellent"),
            GradeEntry("91.5 - 95.7", "3.5", "Superior"),
            GradeEntry("87.2 - 91.4", "3.0", "Very Good"),
            GradeEntry("82.9 - 87.1", "2.5", "Good"),
            GradeEntry("78.6 - 82.8", "2.0", "Satisfactory"),
            GradeEntry("74.3 - 78.5", "1.5", "Fair"),
            GradeEntry("70.0 - 74.2", "1.0", "Pass"),
            GradeEntry("below 70.0", "0.5", "Fail"),
            GradeEntry("N/A", "0.0", "Failure due to Absences"),
            GradeEntry("N/A", "7.0", "Officially Dropped"),
            GradeEntry("N/A", "8.0", "Incomplete"),
            GradeEntry("N/A", "9.0", "Credited"),
            GradeEntry("N/A", "9.9", "Unsettled Balance")
        )

        val rvList: RecyclerView = findViewById(R.id.rv_grade_list)
        rvList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvList.adapter = GradeTableAdapter(grades)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.activity_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.main_menu_about -> {
                val aboutIntent = Intent(this, AboutActivity::class.java)
                startActivity(aboutIntent)
                true
            }

            else -> false
        }
    }
}