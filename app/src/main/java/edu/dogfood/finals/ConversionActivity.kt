package edu.dogfood.finals

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar

class ConversionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_conversion)

        val toolbar: Toolbar = findViewById(R.id.conversion_toolbar)
        setSupportActionBar(toolbar)
    }
}