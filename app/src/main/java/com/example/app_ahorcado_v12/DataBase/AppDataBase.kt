package com.example.app_ahorcado_v1.DataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.app_ahorcado_v1.dao.CategoriaDAO
import com.example.app_ahorcado_v1.dao.PalabrasDAO
import com.example.app_ahorcado_v1.dao.UsuarioDAO
import com.example.app_ahorcado_v1.entidades.Categoria
import com.example.app_ahorcado_v1.entidades.Palabras
import com.example.app_ahorcado_v1.entidades.Usuario
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Categoria::class, Palabras::class, Usuario::class], version = 2)
abstract class AppDataBase : RoomDatabase() {

    abstract fun categoriaDAO(): CategoriaDAO
    abstract fun palabrasDAO(): PalabrasDAO
    abstract fun usuarioDAO(): UsuarioDAO

    companion object {
        @Volatile
        private var INSTANCE: AppDataBase? = null

        fun getDatabase(context: Context): AppDataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDataBase::class.java,
                    "AhorcadoDB"
                )
                .addCallback(DatabaseCallback(context))
                .build()
                INSTANCE = instance
                instance
            }
        }

        private class DatabaseCallback(private val context: Context) : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    CoroutineScope(Dispatchers.IO).launch {
                        prepopulateDatabase(database)
                    }
                }
            }

            suspend fun prepopulateDatabase(db: AppDataBase) {
                val catDao = db.categoriaDAO()
                val palDao = db.palabrasDAO()

                // Categorías con IDs fijos para el Menu
                catDao.insert(Categoria("Animales", true).apply { id = 5 })
                catDao.insert(Categoria("Frutas", true).apply { id = 6 })
                catDao.insert(Categoria("Colores", true).apply { id = 7 })

                // --- ANIMALES (ID 5) ---
                val animales = listOf(
                    Palabras("DOG", "PERRO", 0, 1, 5),
                    Palabras("CAT", "GATO", 0, 1, 5),
                    Palabras("LION", "LEON", 0, 1, 5),
                    Palabras("TIGER", "TIGRE", 0, 1, 5),
                    Palabras("BEAR", "OSO", 0, 1, 5),
                    Palabras("RABBIT", "CONEJO", 0, 1, 5),
                    Palabras("ELEPHANT", "ELEFANTE", 0, 1, 5),
                    Palabras("MONKEY", "MONO", 0, 1, 5),
                    Palabras("GIRAFFE", "JIRAFA", 0, 1, 5),
                    Palabras("ZEBRA", "CEBRA", 0, 1, 5)
                )

                // --- FRUTAS (ID 6) ---
                val frutas = listOf(
                    Palabras("APPLE", "MANZANA", 0, 1, 6),
                    Palabras("BANANA", "PLATANO", 0, 1, 6),
                    Palabras("ORANGE", "NARANJA", 0, 1, 6),
                    Palabras("GRAPE", "UVA", 0, 1, 6),
                    Palabras("STRAWBERRY", "FRESA", 0, 1, 6),
                    Palabras("WATERMELON", "SANDIA", 0, 1, 6),
                    Palabras("MANGO", "MANGO", 0, 1, 6),
                    Palabras("PINEAPPLE", "PIÑA", 0, 1, 6),
                    Palabras("CHERRY", "CEREZA", 0, 1, 6),
                    Palabras("PEACH", "DURAZNO", 0, 1, 6)
                )

                // --- COLORES (ID 7) ---
                val colores = listOf(
                    Palabras("RED", "ROJO", 0, 1, 7),
                    Palabras("BLUE", "AZUL", 0, 1, 7),
                    Palabras("GREEN", "VERDE", 0, 1, 7),
                    Palabras("YELLOW", "AMARILLO", 0, 1, 7),
                    Palabras("PURPLE", "MORADO", 0, 1, 7),
                    Palabras("PINK", "ROSA", 0, 1, 7),
                    Palabras("BLACK", "NEGRO", 0, 1, 7),
                    Palabras("WHITE", "BLANCO", 0, 1, 7),
                    Palabras("BROWN", "CAFE", 0, 1, 7),
                    Palabras("GRAY", "GRIS", 0, 1, 7)
                )

                (animales + frutas + colores).forEach { palDao.insert(it) }
            }
        }
    }
}
