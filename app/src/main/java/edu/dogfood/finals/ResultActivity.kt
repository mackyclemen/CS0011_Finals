package edu.dogfood.finals

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import java.lang.IllegalStateException

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val toolbar: Toolbar = findViewById(R.id.result_toolbar)

        setSupportActionBar(toolbar)

        val tvResultHeader: TextView = findViewById(R.id.tv_result_subject_header)
        val tvResultGWA: TextView = findViewById(R.id.tv_result_gwa)

        intent.extras?.getBundle(resources.getString(R.string.key_result))?.let { bundle ->
            val subjectCount = bundle.getInt(resources.getString(R.string.key_total_units), 0)
            tvResultHeader.text = String.format(resources.getString(R.string.tv_result_subject_header), subjectCount)

            val gwa = bundle.getDouble(resources.getString(R.string.key_total_gwa), 0.0)
            tvResultGWA.text = String.format(resources.getString(R.string.tv_result_gwa), gwa)

            val tvQuoteMessage: TextView = findViewById(R.id.tv_quote_label)
            tvQuoteMessage.text = when(gwa) {
                in 3.0..4.0 -> getString(R.string.feedback_grade_range_excellent)
                in 2.0..3.0 -> getString(R.string.feedback_grade_range_great)
                in 1.0..2.0 -> getString(R.string.feedback_grade_range_good)
                in 0.5..1.0 -> getString(R.string.feedback_grade_range_nice)
                in 0.0..0.5 -> getString(R.string.feedback_grade_range_ok)
                else -> "" // unsupported GWA evaluation, keep it blank
            }

        } ?: throw IllegalStateException("A bundle is required on this activity to launch")
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