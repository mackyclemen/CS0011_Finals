package edu.dogfood.finals

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Button
import androidx.appcompat.widget.Toolbar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.main_toolbar)
        setSupportActionBar(toolbar)

        // Compute GWA button
        val actionComputeBtn: Button = findViewById(R.id.main_btn_compute)
        actionComputeBtn.setOnClickListener {
            val intent = Intent(this, ConversionActivity::class.java)
            startActivity(intent)
        }

        // Conversion Table button
        val actionShowConversionBtn: Button = findViewById(R.id.main_btn_conversion)
        actionShowConversionBtn.setOnClickListener {
             val intent = Intent(this, TableActivity::class.java)
             startActivity(intent)
        }
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