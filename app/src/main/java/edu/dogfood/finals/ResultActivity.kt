package edu.dogfood.finals

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import java.util.*

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val toolbar: Toolbar = findViewById(R.id.result_toolbar)
        setSupportActionBar(toolbar)

        val i = intent
        val score = i.getFloatExtra(resources.getString(R.string.key_calculate), 0.0f)
        Toast.makeText(this, String.format("Received Result: %.02f", score), Toast.LENGTH_LONG)
            .show()
    }
}