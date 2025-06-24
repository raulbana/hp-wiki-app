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

class StudentsActivity : AppCompatActivity() {
    private val apiService = HPAPIService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_students)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnBack = findViewById<Button>(R.id.btn_back)
        btnBack.setOnClickListener { finish() }

        val radioGryffindor = findViewById<RadioButton>(R.id.radio_gryffindor)
        val radioSlytherin = findViewById<RadioButton>(R.id.radio_slytherin)
        val radioRavenclaw = findViewById<RadioButton>(R.id.radio_ravenclaw)
        val radioHufflepuff = findViewById<RadioButton>(R.id.radio_hufflepuff)
        val radioGroup = findViewById<RadioGroup>(R.id.radio_group_houses)
        val btnSearch = findViewById<Button>(R.id.btn_search_students)
        val tvStudents = findViewById<TextView>(R.id.tv_students)
        val progressLoader = findViewById<ProgressBar>(R.id.progress_loader)

        btnSearch.setOnClickListener {
            val selectedHouse = when (radioGroup.checkedRadioButtonId) {
                R.id.radio_gryffindor -> "Gryffindor"
                R.id.radio_slytherin -> "Slytherin"
                R.id.radio_ravenclaw -> "Ravenclaw"
                R.id.radio_hufflepuff -> "Hufflepuff"
                else -> ""
            }
            if (selectedHouse.isEmpty()) {
                Toast.makeText(this, "Selecione uma casa", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            progressLoader.visibility = View.VISIBLE
            tvStudents.text = ""

            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val students = apiService.getStudents()
                        .filter { it.house.equals(selectedHouse, ignoreCase = true) }
                        .map { it.name }
                    withContext(Dispatchers.Main) {
                        progressLoader.visibility = View.GONE
                        if (students.isNotEmpty()) {
                            tvStudents.text = students.joinToString("\n")
                        } else {
                            tvStudents.text = "Nenhum estudante encontrado para esta casa."
                        }
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        progressLoader.visibility = View.GONE
                        tvStudents.text = ""
                        Toast.makeText(this@StudentsActivity, "Erro ao buscar estudantes", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}