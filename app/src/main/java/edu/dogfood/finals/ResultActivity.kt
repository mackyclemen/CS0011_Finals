package edu.dogfood.finals

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import java.lang.IllegalStateException
import java.util.*

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val toolbar: Toolbar = findViewById(R.id.result_toolbar)
        setSupportActionBar(toolbar)

        val tvResultHeader: TextView = findViewById(R.id.tv_result_subject_header)
        val tvResultGWA: TextView = findViewById(R.id.tv_result_gwa)

        intent.extras?.getBundle(resources.getString(R.string.key_result))?.let { bundle ->
            val subjectCount = bundle.getInt(resources.getString(R.string.key_total_subjects), 0)
            tvResultHeader.text = String.format(resources.getString(R.string.tv_result_subject_header), subjectCount)

            val gwa = bundle.getDouble(resources.getString(R.string.key_total_gwa), 0.0)
            tvResultGWA.text = String.format(resources.getString(R.string.tv_result_gwa), gwa)
        } ?: throw IllegalStateException("A bundle is required on this activity to launch")
    }
}