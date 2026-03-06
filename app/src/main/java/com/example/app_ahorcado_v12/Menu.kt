package com.example.app_ahorcado_v12

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.app_ahorcado_v12.databinding.ActivityMainBinding
import com.example.app_ahorcado_v12.databinding.ActivityMenuBinding

class Menu : AppCompatActivity() {
    private lateinit var binding: ActivityMenuBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.menu)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.menu)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val amaticSCBoldTypeface = Typeface.createFromAsset(assets, "fonts/londrina_shadow_regular.ttf")
        binding.tituloMenu.typeface = amaticSCBoldTypeface
        binding.tituloCatAnimales.typeface = amaticSCBoldTypeface
        binding.tituloCatColores.typeface = amaticSCBoldTypeface
        binding.tituloCatFrutas.typeface = amaticSCBoldTypeface
        binding.btnBacktoIncio.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }


    }

    fun goAhorcado(view: View) {
        val categoria: Int = when (view.id) {
            R.id.cat_Animales -> 5
            R.id.cat_Frutas -> 6
            R.id.cat_Colores -> 7
            else -> {
                Toast.makeText(this, "Categoría no reconocida", Toast.LENGTH_SHORT).show()
                return
            }
        }

        val intent = Intent(this, Ahorcado::class.java)
        intent.putExtra("categoria", categoria)
        startActivity(intent)
    }

}