package com.example.app_ahorcado_v1.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.app_ahorcado_v1.entidades.Usuario
@Dao
interface UsuarioDAO {
    @Query("SELECT * FROM Usuario")
    suspend fun getAll(): List<Usuario>

    @Query("SELECT * FROM Usuario WHERE id = :id")
    suspend fun getById(id: Int): Usuario?

    @Insert
    suspend fun insert(usuario: Usuario)

    @Delete
    suspend fun delete(usuario: Usuario)

    @Update
    suspend fun update(usuario: Usuario)


}