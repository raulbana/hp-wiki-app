package com.appraulbana.hp_project.services
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.appraulbana.hp_project.models.Character

class HPAPIService {
private val retrofit: Retrofit = Retrofit.Builder()
    .baseUrl("https://hp-api.onrender.com/api/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

    private val api: HPAPI = retrofit.create(HPAPI::class.java)

    suspend fun getCharacterDetails(characterId: String): Character {
        val result = api.getCharacterDetails(characterId)
        if (result.isNotEmpty()) {
            return result[0]
        } else {
            throw NoSuchElementException("Nenhum personagem encontrado para o ID $characterId")
        }
    }

    suspend fun getAllCharacters() = api.getAllCharacters()

    suspend fun getStudents() = api.getStudents()

    suspend fun getStaff() = api.getStaff()

    suspend fun getSpells() = api.getSpells()
}