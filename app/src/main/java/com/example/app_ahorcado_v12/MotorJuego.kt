import android.content.Context
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import com.example.app_ahorcado_v1.DataBase.AppDataBase
import com.example.app_ahorcado_v1.entidades.Palabras
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MotorJuego(val context: Context, categoriaInicial: Int) : DefaultLifecycleObserver {

    private val lifecycleRegistry = LifecycleRegistry(context as LifecycleOwner)

    init {
        lifecycleRegistry.currentState = Lifecycle.State.CREATED
    }

    private var categoria: Int = 0
    lateinit var palabra: Palabras
    private var intentosRestantes: Int = 6
    private var palabraMostrada: StringBuilder = StringBuilder()

    fun obtenerPalabraOculta(): String {
        return palabraMostrada.toString().replace("", " ").trim()
    }

    fun setCategoria(categoria: Int) {
        this.categoria = categoria
    }

    private suspend fun obtenerPalabra(): Palabras? = withContext(Dispatchers.IO) {
        val room = AppDataBase.getDatabase(context)
        room.palabrasDAO().getPalabraByCategoriaAndNivel(categoriaId = categoria)
    }

    fun verificarLetra(letra: Char): Boolean {
        var letraEncontrada = false
        val palabraIngUpper = palabra.palabra_ING.uppercase()
        for (i in palabraIngUpper.indices) {
            if (palabraIngUpper[i] == letra.uppercaseChar()) {
                palabraMostrada.setCharAt(i, palabraIngUpper[i])
                letraEncontrada = true
            }
        }
        if (!letraEncontrada) {
            intentosRestantes--
        }
        return letraEncontrada
    }

    fun getIntentosRestantes(): Int {
        return intentosRestantes
    }

    fun isJuegoGanado(): Boolean {
        return !palabraMostrada.contains('-')
    }

    fun isJuegoPerdido(): Boolean {
        return intentosRestantes == 0
    }

    suspend fun reiniciarJuego(ganado: Boolean) {
        if (ganado) {
            val nuevaPalabra = obtenerPalabra()
            if (nuevaPalabra != null) {
                palabra = nuevaPalabra
                palabra.palabra_ING = palabra.palabra_ING.uppercase()
                palabraMostrada = StringBuilder("-".repeat(palabra.palabra_ING.length))
                intentosRestantes = 6
            } else {
                // Si no hay más palabras, podrías manejarlo aquí (ej. resetear niveles)
                palabra = Palabras("FINISH", "TERMINADO", 0, 1, categoria)
                palabraMostrada = StringBuilder("-".repeat(palabra.palabra_ING.length))
            }
        } else {
            palabraMostrada = StringBuilder("-".repeat(palabra.palabra_ING.length))
            intentosRestantes = 6
        }
    }

    suspend fun actualizarPalabraGanada() = withContext(Dispatchers.IO) {
        val room = AppDataBase.getDatabase(context)
        palabra.nivel = 99
        room.palabrasDAO().update(palabra)
    }
}
