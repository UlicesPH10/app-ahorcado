package com.example.app_ahorcado_v12

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import timber.log.Timber

class BackgroundMusicService : Service() {

    private var mediaPlayer: MediaPlayer? = null

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (mediaPlayer == null) {
            try {
                mediaPlayer = MediaPlayer.create(this, R.raw.musica_fondo) // Asegúrate de que el nombre del archivo sea correcto
                mediaPlayer?.isLooping = true
                mediaPlayer?.start()
                Timber.d("Reproducción de música iniciada")
            } catch (e: Exception) {
                Timber.e(e, "Error al iniciar la reproducción de música")
            }
        }
        return START_STICKY
    }

    override fun onDestroy() {
        try {
            mediaPlayer?.stop()
            mediaPlayer?.release()
            mediaPlayer = null
            Timber.d("Reproducción de música detenida")
        } catch (e: Exception) {
            Timber.e(e, "Error al detener la reproducción de música")
        }
        super.onDestroy()
    }
}
