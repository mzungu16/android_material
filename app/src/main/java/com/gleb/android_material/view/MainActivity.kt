package com.gleb.android_material.view

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.gleb.android_material.R
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
            when (it.itemId) {
                R.id.search -> {
                    val searchFragment = SearchFragment.newInstance(
                        findViewById<TextView>(R.id.bottom_sheet_description_header).text.toString(),
                        findViewById<TextView>(R.id.bottom_sheet_description).text.toString()
                    )
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container_layout, searchFragment)
                        .commit()

                }
                R.id.telescope -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container_layout, NoteFragment())
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
}