package com.appraulbana.hp_project.services
import retrofit2.http.GET
import retrofit2.http.Path
import com.appraulbana.hp_project.models.Character
import com.appraulbana.hp_project.models.Spell

interface HPAPI {
    @GET("character/{characterId}") 
    suspend fun getCharacterDetails(@Path("characterId") characterId: String): List<Character>

    @GET("characters")
    suspend fun getAllCharacters(): List<Character>

    @GET("characters/students")
    suspend fun getStudents(): List<Character>

    @GET("characters/staff")
    suspend fun getStaff(): List<Character>

    @GET("spells")
    suspend fun getSpells(): List<Spell>
}