package com.example.app_ahorcado_v1.entidades

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Usuario {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    var name:String = ""

    constructor(name: String){
        this.name = name
    }

}