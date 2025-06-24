package com.appraulbana.hp_project.views

import android.graphics.BitmapFactory
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
import java.net.HttpURLConnection
import java.net.URL

class SearchCharActivity : AppCompatActivity() {
    private val apiService = HPAPIService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_search_char)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnBack = findViewById<Button>(R.id.btn_back)
        btnBack.setOnClickListener {
            finish()
        }

        val etCharId = findViewById<EditText>(R.id.et_char_id)
        val btnSearch = findViewById<Button>(R.id.btn_search)
        val tvCharInfo = findViewById<TextView>(R.id.tv_char_info)
        val ivCharImage = findViewById<ImageView>(R.id.iv_char_image)
        val progressLoader = findViewById<ProgressBar?>(R.id.progress_loader)

        btnSearch.setOnClickListener {
            val charId = etCharId.text.toString().trim()
            if (charId.isEmpty()) {
                Toast.makeText(this, "Informe o ID do personagem", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            progressLoader?.visibility = View.VISIBLE
            etCharId.setText("")
            tvCharInfo.text = ""
            ivCharImage.visibility = View.GONE

            CoroutineScope(Dispatchers.IO).launch {
                try {
                    println("DEBUG (SearchChar): Solicitando detalhes do personagem para o ID: $charId")
                    val character = apiService.getCharacterDetails(charId)
                    println("DEBUG (SearchChar): Resposta recebida para personagem: ${character.name}")

                    var bitmap: android.graphics.Bitmap? = null
                    if (character.image.isNotEmpty()) {
                        try {
                            println("DEBUG (SearchChar): Baixando imagem de: ${character.image}")
                            val imgUrl = URL(character.image)
                            val imgConn = imgUrl.openConnection() as HttpURLConnection
                            imgConn.doInput = true
                            imgConn.connect()
                            val inputStream = imgConn.inputStream
                            bitmap = BitmapFactory.decodeStream(inputStream)
                            imgConn.disconnect()
                        } catch (e: Exception) {
                            println("DEBUG (SearchChar): Erro ao baixar imagem: ${e.message}")
                            bitmap = null
                        }
                    }

                    withContext(Dispatchers.Main) {
                        val nome = character.name.uppercase()
                        val especie = character.species.uppercase()
                        val casa = if (character.house.isNotEmpty()) character.house.uppercase() else "NENHUMA"
                        tvCharInfo.text = "NOME: $nome\nESPÉCIE: $especie\nCASA: $casa"
                        if (bitmap != null) {
                            ivCharImage.visibility = ImageView.VISIBLE
                            ivCharImage.setImageBitmap(bitmap)
                        } else {
                            ivCharImage.visibility = ImageView.GONE
                        }
                        progressLoader?.visibility = View.GONE
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        tvCharInfo.text = ""
                        ivCharImage.visibility = ImageView.GONE
                        progressLoader?.visibility = View.GONE
                        println("Erro ao buscar personagem: ${e.message}")
                        Toast.makeText(this@SearchCharActivity, "Personagem não encontrado ou erro na requisição", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}