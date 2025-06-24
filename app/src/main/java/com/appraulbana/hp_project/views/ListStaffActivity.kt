package com.appraulbana.hp_project.views

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.appraulbana.hp_project.R
import com.appraulbana.hp_project.services.HPAPIService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListStaffActivity : AppCompatActivity() {
    private val apiService = HPAPIService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_list_staff)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnBack = findViewById<Button>(R.id.btn_back)

        btnBack.setOnClickListener {
            finish()
        }

        val etProfName = findViewById<EditText>(R.id.et_prof_name)
        val btnSearch = findViewById<Button>(R.id.btn_search_prof)
        val tvProfInfo = findViewById<TextView>(R.id.tv_prof_info)
        val progressLoader = findViewById<ProgressBar>(R.id.progress_loader)

        btnSearch.setOnClickListener {
            val profName = etProfName.text.toString().trim()
            if (profName.isEmpty()) {
                Toast.makeText(this, "Informe o nome do professor", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            progressLoader.visibility = View.VISIBLE
            etProfName.setText("")
            tvProfInfo.text = ""

            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val staffList = apiService.getStaff()

                    println("Lista de professores obtida: ${staffList.size} itens")

                    val found = staffList.firstOrNull { staff ->
                        staff.name.equals(profName, ignoreCase = true) ||
                        staff.name.contains(profName, ignoreCase = true) ||
                        staff.name.lowercase().startsWith(profName.lowercase()) ||
                        staff.name.lowercase().endsWith(profName.lowercase()) ||
                        (staff.alternate_names.any { it.contains(profName, ignoreCase = true) })
                    }
                    withContext(Dispatchers.Main) {
                        progressLoader.visibility = View.GONE
                        if (found != null) {
                            val altNames = if (found.alternate_names.isNullOrEmpty()) "Nenhum" else found.alternate_names.joinToString(", ")
                            val casa = if (found.house.isNotEmpty()) found.house else "Nenhuma"
                            tvProfInfo.text = "Nome: ${found.name}\nNomes alternativos: $altNames\nEspécie: ${found.species}\nCasa: $casa"
                        } else {
                            tvProfInfo.text = ""
                            Toast.makeText(this@ListStaffActivity, "Professor não encontrado", Toast.LENGTH_SHORT).show()
                        }
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        progressLoader.visibility = View.GONE
                        tvProfInfo.text = ""
                        println("Erro ao buscar professor: ${e.message}")
                        Toast.makeText(this@ListStaffActivity, "Erro ao buscar professor", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }}