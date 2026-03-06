package com.example.app_ahorcado_v12


import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.ActivityResultCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.app_ahorcado_v1.DataBase.AppDataBase

import com.example.app_ahorcado_v1.entidades.Palabras
import com.example.app_ahorcado_v12.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val REQUEST_WRITE_STORAGE = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val amaticSCBoldTypeface = Typeface.createFromAsset(assets, "fonts/londrina_shadow_regular.ttf")
        binding.tituloHome.typeface = amaticSCBoldTypeface
        
        // Initialize Database to trigger prepopulation
        initializeDatabase()
    }

    private fun initializeDatabase() {
        val room = AppDataBase.getDatabase(this)
        val amaticSCBoldTypeface = Typeface.createFromAsset(assets, "fonts/londrina_shadow_regular.ttf")
        binding.tituloHome.typeface = amaticSCBoldTypeface

        lifecycleScope.launch {
            val palabras = room.palabrasDAO().getAll()
            if (palabras.isEmpty()) {
                println("La base de datos está vacía, esperando a que el callback termine...")
            } else {
                for (palabra in palabras) {
                    println("Palabra cargada: ${palabra.palabra_ESP}")
                }
            }
        }
    }

    fun goMenu(view: View) {
        val intent = Intent(this, Menu::class.java)
        startActivity(intent)
    }
}
