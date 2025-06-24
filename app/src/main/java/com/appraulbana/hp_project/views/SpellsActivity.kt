package com.appraulbana.hp_project.views

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.appraulbana.hp_project.R
import com.appraulbana.hp_project.models.Spell
import com.appraulbana.hp_project.services.HPAPIService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SpellsActivity : AppCompatActivity() {
    private val apiService = HPAPIService()
    private lateinit var spellsAdapter: SpellItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_spells)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnBack = findViewById<Button>(R.id.btn_back)
        btnBack.setOnClickListener { finish() }

        val progressLoader = findViewById<ProgressBar>(R.id.progress_loader)
        val recyclerView = findViewById<RecyclerView>(R.id.rv_spells)
        recyclerView.layoutManager = LinearLayoutManager(this)
        spellsAdapter = SpellItemAdapter { spell ->
            val intent = Intent(this, SpellDetailsActivity::class.java)
            intent.putExtra("name", spell.name)
            intent.putExtra("description", spell.description)
            startActivity(intent)
        }
        recyclerView.adapter = spellsAdapter

        progressLoader.visibility = View.VISIBLE

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val spells = apiService.getSpells()
                withContext(Dispatchers.Main) {
                    spellsAdapter.submitList(spells)
                    progressLoader.visibility = View.GONE
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    progressLoader.visibility = View.GONE
                    Toast.makeText(this@SpellsActivity, "Erro ao buscar feitiços", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}