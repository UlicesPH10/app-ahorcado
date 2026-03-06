package com.example.app_ahorcado_v1.entidades

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Categoria {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    var nombre: String = ""
    var estado: Boolean = true

    constructor(nombre: String, estado: Boolean){
        this.nombre = nombre
        this.estado = estado
    }

}