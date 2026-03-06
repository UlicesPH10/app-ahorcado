package com.example.app_ahorcado_v1.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.app_ahorcado_v1.entidades.Palabras

@Dao
interface PalabrasDAO {
    @Query("SELECT * FROM Palabras")
    suspend fun getAll(): List<Palabras>

    @Query("SELECT * FROM Palabras WHERE id = :id")
    suspend fun getById(id: Int): Palabras?

    @Insert
    suspend fun insert(palabra: Palabras)

    @Update
    suspend fun update(palabra: Palabras)

    @Query("DELETE FROM Palabras WHERE id = :id")
    suspend fun delete(id: Int)

    @Query("SELECT * FROM Palabras WHERE categoria_id = :categoriaId AND nivel != 99 ORDER BY nivel ASC LIMIT 1")
    suspend fun getPalabraByCategoriaAndNivel(categoriaId: Int): Palabras?
}
