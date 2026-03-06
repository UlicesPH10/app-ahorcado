package com.example.app_ahorcado_v1.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.app_ahorcado_v1.entidades.Categoria

@Dao
interface  CategoriaDAO {
    @Query("SELECT * FROM Categoria")
    suspend fun getAll(): List<Categoria>

    @Query("SELECT * FROM Categoria WHERE id = :id")
    suspend fun getById(id: Int): Categoria?

    @Insert
    suspend fun insert(categoria: Categoria)

    @Query("UPDATE Categoria SET nombre = :nombre, estado = :estado WHERE id = :id")
    suspend fun update(id: Int, nombre: String, estado: Boolean)

    @Query("DELETE FROM Categoria WHERE id = :id")
    suspend fun delete(id: Int)

}