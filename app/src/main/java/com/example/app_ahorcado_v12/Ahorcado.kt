package com.example.app_ahorcado_v12

import MotorJuego
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.app_ahorcado_v12.databinding.ActivityAhorcadoBinding
import com.example.app_ahorcado_v12.databinding.PopupLouserBinding
import com.example.app_ahorcado_v12.databinding.PopupWinnerBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Locale

class Ahorcado : AppCompatActivity() {

    private lateinit var binding: ActivityAhorcadoBinding
    private lateinit var bindingpopupLosser: PopupLouserBinding
    private lateinit var bindingpopupWinner: PopupWinnerBinding

    private lateinit var juego: MotorJuego
    private lateinit var countDownTimer: CountDownTimer

    private lateinit var tts: TextToSpeech

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityAhorcadoBinding.inflate(layoutInflater)
        bindingpopupLosser = PopupLouserBinding.inflate(layoutInflater)
        bindingpopupWinner = PopupWinnerBinding.inflate(layoutInflater)
        setContentView(binding.mainAhorcado)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main_ahorcado)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }



        val categoria = intent.getIntExtra("categoria", 0)
        cargarSonido()

        juego = MotorJuego(this, categoria)
        juego.setCategoria(categoria)

        CoroutineScope(Dispatchers.Main).launch {
            juego.reiniciarJuego(true)
            binding.palabraSecreta.text = juego.obtenerPalabraOculta()
            cargarTimer()
        }
        Toast.makeText(this, "Categoria: $categoria", Toast.LENGTH_SHORT).show()

        binding.btnBack.setOnClickListener{
            val intent = Intent(this, Menu::class.java)
            startActivity(intent)
        }

    }

    private fun cargarTimer() {
        val minutos = 1
        val segundos = 10
        val totalMilisegundos = (minutos * 60 + segundos) * 1000L

        countDownTimer = object : CountDownTimer(totalMilisegundos, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val minutos = (millisUntilFinished / 1000) / 60
                val segundos = (millisUntilFinished / 1000) % 60
                binding.chronometer.text = String.format("%02d:%02d", minutos, segundos)
                binding.chronometer.typeface = Typeface.createFromAsset(assets, "fonts/londrina_shadow_regular.ttf")
            }

            override fun onFinish() {
                binding.chronometer.text = "00:00"
                Toast.makeText(this@Ahorcado, "¡Tiempo completado!", Toast.LENGTH_SHORT).show()
                showPopupLosser()
            }
        }
        countDownTimer.start()
    }

    private fun cargarSonido() {
        tts = TextToSpeech(this) { status ->
            if (status == TextToSpeech.SUCCESS) {
                val result = tts.setLanguage(Locale.getDefault())
                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Log.e("TTS", "Este idioma no es compatible")
                }
            } else {
                Log.e("TTS", "¡Inicialización fallida!")
            }
        }
    }

    private fun showPopupLosser() {
        val view = PopupLouserBinding.inflate(layoutInflater).popuploser
        val alertDialog = AlertDialog.Builder(this)
            .setView(view)
            .create()

        val buttonPopup: Button = view.findViewById(R.id.btnPopUP)
        buttonPopup.setOnClickListener {
            alertDialog.dismiss()
            CoroutineScope(Dispatchers.Main).launch {
                reiniciarJuego(false)
            }
        }

        alertDialog.show()
        alertDialog.window?.setLayout(813, 657)
    }

    private fun showPopupWinner() {
        val view = PopupWinnerBinding.inflate(layoutInflater).root
        val alertDialog = AlertDialog.Builder(this)
            .setView(view)
            .create()

        val buttonPopup: Button = view.findViewById(R.id.btnPopUP)
        buttonPopup.setOnClickListener {
            alertDialog.dismiss()

            CoroutineScope(Dispatchers.Main).launch {
                reiniciarJuego(true)
            }
        }

        alertDialog.show()
        alertDialog.window?.setLayout(813, 657)
    }

    override fun onDestroy() {
        super.onDestroy()
        countDownTimer.cancel()
        tts.stop()
        tts.shutdown()
    }

    fun manejarClicDeLetra(view: View) {
        val letra = (view as Button).text[0]
        jugar(letra)
    }

    fun emitirSonido(view: View) {
        val textoASonar = juego.palabra.palabra_ESP
        Log.d("Texto a Sonar", textoASonar)

        if (textoASonar.isNotBlank()) {
            Toast.makeText(this, "Si letras para sonar", Toast.LENGTH_SHORT).show()
            tts.speak(textoASonar, TextToSpeech.QUEUE_FLUSH, null, "")
        } else {
            Toast.makeText(this, "No hay letras para sonar", Toast.LENGTH_SHORT).show()
        }
    }

    private fun jugar(letra: Char) {
        val letraAdivinada = juego.verificarLetra(letra)

        if (letraAdivinada) {
            binding.palabraSecreta.text = juego.obtenerPalabraOculta()
            binding.palabraSecreta.typeface = Typeface.createFromAsset(assets, "fonts/londrina_shadow_regular.ttf")
            if (juego.isJuegoGanado()) {
                mostrarMensaje("¡Felicidades, ganaste!")
                CoroutineScope(Dispatchers.Main).launch {
                    juego.actualizarPalabraGanada()
                    reiniciarJuego(true)
                    showPopupWinner()
                }
            }
        } else {
            if (juego.isJuegoPerdido()) {
                mostrarMensaje("¡Perdiste! La palabra era ${juego.palabra.palabra_ESP}")
                CoroutineScope(Dispatchers.Main).launch {
                    reiniciarJuego(false)
                    showPopupLosser()
                }
            } else {
                mostrarMensaje("Intentos restantes: ${juego.getIntentosRestantes()}")
            }
        }
        cargarElementosIntentos()
    }

    private fun cargarElementosIntentos() {
        when (juego.getIntentosRestantes()) {
            6 -> binding.homeImdAhorcado.setImageResource(R.drawable.vida6)
            5 -> binding.homeImdAhorcado.setImageResource(R.drawable.vida5)
            4 -> binding.homeImdAhorcado.setImageResource(R.drawable.vida4)
            3 -> binding.homeImdAhorcado.setImageResource(R.drawable.vida3)
            2 -> binding.homeImdAhorcado.setImageResource(R.drawable.vida2)
            1 -> binding.homeImdAhorcado.setImageResource(R.drawable.vida1)
            0 -> binding.homeImdAhorcado.setImageResource(R.drawable.vida0)
        }
    }

    private suspend fun reiniciarJuego(ganado: Boolean) {
        withContext(Dispatchers.Main) {
            juego.reiniciarJuego(ganado)
            binding.palabraSecreta.text = juego.obtenerPalabraOculta()
            countDownTimer.cancel()
            cargarTimer()
            binding.homeImdAhorcado.setImageResource(R.drawable.vida6)
        }
    }

    private fun mostrarMensaje(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }


}
