package com.example.app_ahorcado_v1.entidades

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(foreignKeys = [
    ForeignKey(
        entity = Categoria::class,
        parentColumns = ["id"],
        childColumns = ["categoria_id"],
        onDelete = ForeignKey.CASCADE
    )
])
class Palabras {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    var palabra_ING: String = ""
    var palabra_ESP: String = ""
    var img: Int = 0
    var nivel: Int = 1
    var categoria_id: Int = 0


        constructor(palabra_ING: String, palabra_ESP: String, img: Int, nivel: Int, categoria_id: Int) {
            this.palabra_ING = palabra_ING
            this.palabra_ESP = palabra_ESP
            this.img = img
            this.nivel = nivel
            this.categoria_id = categoria_id
        }

}