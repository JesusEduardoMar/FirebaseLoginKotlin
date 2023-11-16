package com.example.firebaseloginkotlin

import android.media.MediaPlayer
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button

class AudiosDepresionActivity : AppCompatActivity() {
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var playButton1: Button
    private lateinit var playButton2: Button
    private lateinit var playButton3: Button
    private lateinit var playButton4: Button
    private lateinit var playButton5: Button
    private lateinit var pauseButton: Button
    private lateinit var stopButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_audios_depresion)

        playButton1 = findViewById(R.id.playButton1)
        playButton2 = findViewById(R.id.playButton2)
        playButton3 = findViewById(R.id.playButton3)
        playButton4 = findViewById(R.id.playButton4)
        playButton5 = findViewById(R.id.playButton5)

        pauseButton = findViewById(R.id.pauseButton)
        stopButton = findViewById(R.id.stopButton)

        // Reemplaza R.raw.audio_estres con el recurso de audio de estrés deseado
        mediaPlayer = MediaPlayer.create(this, R.raw.meditacion)

        playButton1.setOnClickListener {
            if (!mediaPlayer.isPlaying) {
                mediaPlayer.start()
            }
        }

        // Configura los listeners para los otros botones de reproducción de manera similar

        pauseButton.setOnClickListener {
            if (mediaPlayer.isPlaying) {
                mediaPlayer.pause()
            }
        }

        stopButton.setOnClickListener {
            if (mediaPlayer.isPlaying) {
                mediaPlayer.stop()
                mediaPlayer.prepare()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
    }
}
