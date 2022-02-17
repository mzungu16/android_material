package com.gleb.android_material

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        supportFragmentManager.beginTransaction()
            .add(R.id.container_layout, MainFragment())
            .commit()

        findViewById<BottomNavigationView>(R.id.bottom_navigation).setOnItemSelectedListener {
            val searchFragment = SearchFragment.newInstance(
                findViewById<TextView>(R.id.bottom_sheet_description_header).text.toString(),
                findViewById<TextView>(R.id.bottom_sheet_description).text.toString()
            )
            when (it.itemId) {
                /*  R.id.search -> {
                      supportFragmentManager.beginTransaction()
                          .replace(R.id.container_layout, searchFragment)
                          .commit()

                  }*/
                R.id.telescope -> {
                    supportFragmentManager.beginTransaction()
                        .add(R.id.container_layout, ViewPagerFragment())
                        .commit()

                }
                else -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container_layout, MainFragment())
                        .commit()
                }
            }
            true
        }
    }



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }

}