package com.appraulbana.hp_project.models
import com.appraulbana.hp_project.models.Wand

data class Character(
    val id: String,
    val name: String,
    val alternate_names: List<String>,
    val species: String,
    val wand: Wand,
    val dateOfBirth: String,
    val yearOfBirth: Int,
    val wizard: Boolean,
    val ancestry: String,
    val eyeColour: String,
    val hairColour: String,
    val patronus: String,
    val hogwartsStudent: Boolean,
    val hogwartsStaff: Boolean,
    val actor: String,
    val alternate_actors: List<String>,
    val alive: Boolean,
    val image: String,
    val house: String,
)
