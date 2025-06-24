package com.appraulbana.hp_project.views

import android.os.Bundle
import android.widget.Button
import android.content.Intent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.appraulbana.hp_project.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        findViewById<Button>(R.id.btn_search_char).setOnClickListener {
            startActivity(Intent(this, SearchCharActivity::class.java))
        }
        findViewById<Button>(R.id.btn_list_professors).setOnClickListener {
            startActivity(Intent(this, ListStaffActivity::class.java))
        }
        findViewById<Button>(R.id.btn_list_students).setOnClickListener {
            startActivity(Intent(this, StudentsActivity::class.java))
        }
        findViewById<Button>(R.id.btn_view_spells).setOnClickListener {
            startActivity(Intent(this, SpellsActivity::class.java))
        }
        findViewById<Button>(R.id.btn_exit).setOnClickListener {
            finishAffinity()
        }
    }
}