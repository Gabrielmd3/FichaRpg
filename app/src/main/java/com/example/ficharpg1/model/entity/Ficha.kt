package com.example.ficharpg1.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "ficha")
data class Ficha(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var nome: String = "",
    var classe: String = "",
    var nivel: Int = 0,
    var raca: String = "",
    var xp: Int = 0,
    var antecedencia: String = "",
    var tendencia: String = "",
    var forca: Int = 0,
    var destreza: Int = 0,
    var constituicao: Int = 0,
    var inteligencia: Int = 0,
    var sabedoria: Int = 0,
    var carisma: Int = 0,
    var proficiencia: Int = 0,
    var ca: Int = 0,
    var deslocamento: Int = 0,
    var pv: Int = 0,
    var anotacao: String = "",
    var equipamentos: String = ""
) : Serializable{
}