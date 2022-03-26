package edu.dogfood.finals

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val contextView: CoordinatorLayout = findViewById(R.id.main_coordinator_layout)

        val toolbar: Toolbar = findViewById(R.id.main_toolbar)
        setSupportActionBar(toolbar)

        val fab: FloatingActionButton = findViewById(R.id.main_add)
        fab.setOnClickListener {
            Snackbar.make(contextView, "You clicked on a FAB!", Snackbar.LENGTH_LONG)
                .show()
        }

        val score = 4.0
        val calfab: FloatingActionButton = findViewById(R.id.main_calculate)
        calfab.setOnClickListener {
            val i = Intent(this, ResultActivity::class.java)
            i.putExtra(resources.getString(R.string.key_calculate), score)
            startActivity(i)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.main_activity, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.main_menu_about -> {
                val aboutIntent = Intent(this, AboutActivity::class.java)
                startActivity(aboutIntent)
                true
            }

            R.id.main_menu_conversion -> {
                val conversionIntent = Intent(this, ConversionActivity::class.java)
                startActivity(conversionIntent)
                true
            }

            else -> false
        }
    }
}