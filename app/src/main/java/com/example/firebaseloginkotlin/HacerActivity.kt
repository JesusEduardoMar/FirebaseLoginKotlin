package com.example.firebaseloginkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.media.MediaPlayer
import android.os.CountDownTimer
import android.widget.Button
import android.widget.TextView
import android.net.Uri
import android.widget.ImageView
import android.view.animation.Animation

import android.view.animation.AnimationUtils
import android.view.View
import android.os.Handler


class HacerActivity : AppCompatActivity() {

    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var timer: CountDownTimer
    private lateinit var countdownTextView: TextView
    private lateinit var startButton: Button
    private lateinit var button1: Button
    private lateinit var button2: Button
    private lateinit var button3: Button
    private lateinit var welcomeTextView: TextView
    private val meditationDuration: Long = 600000 // Duración de la meditación en milisegundos (10 minutos)
    private var counterValue = 1

    private lateinit var pauseButton: Button
    private lateinit var stopButton: Button
    private var isPaused: Boolean = false

    private lateinit var effectImageView: ImageView
    private lateinit var fadeInAnimation: Animation
    private lateinit var fadeOutAnimation: Animation


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hacer)

        countdownTextView = findViewById(R.id.countdownTextView)
        startButton = findViewById(R.id.startButton)
        button1 = findViewById(R.id.button1)
        button2 = findViewById(R.id.button2)
        button3 = findViewById(R.id.button3)

        welcomeTextView = findViewById(R.id.welcomeTextView)

        fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fadein)
        fadeOutAnimation = AnimationUtils.loadAnimation(this, R.anim.fadeon)


        // Obtiene la cantidad de sesiones de meditación gratis
        val sesionesGratis = listOf(button1, button2, button3).size
        // Muestra el mensaje de bienvenida y la cantidad de sesiones gratis en el TextView
        val welcomeMessage = "¡Hola usuario! Tienes $sesionesGratis sesiones de meditación gratis."
        welcomeTextView.text = welcomeMessage

        startButton.setOnClickListener {
            startMeditation()
        }

        button1.setOnClickListener {
            playAudio(R.raw.meditacion)
        }

        button2.setOnClickListener {
            playAudio(R.raw.meditacion)
        }

        button3.setOnClickListener {
            playAudio(R.raw.meditacion)
        }


        pauseButton = findViewById(R.id.pauseButton)
        stopButton = findViewById(R.id.stopButton)

        pauseButton.setOnClickListener {
            if (mediaPlayer.isPlaying) {
                if (!isPaused) {
                    mediaPlayer.pause()
                    isPaused = true
                    pauseButton.text = "Reanudar"
                } else {
                    mediaPlayer.start()
                    isPaused = false
                    pauseButton.text = "Pausar"
                }
            }
        }

        stopButton.setOnClickListener {
            if (mediaPlayer.isPlaying) {
                mediaPlayer.stop()
                mediaPlayer.prepare() // Prepare the player for the next play
                pauseButton.text = "Pausar"
                isPaused = false
            }
        }


    }

    private fun startMeditation() {
        startButton.isEnabled = false

        timer = object : CountDownTimer(meditationDuration, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                countdownTextView.text = counterValue.toString()
                counterValue++
            }

            override fun onFinish() {
                countdownTextView.text = "Meditación finalizada"
                mediaPlayer.stop()
                startButton.isEnabled = true
            }
        }

        // Restablece el contador al valor inicial antes de comenzar una nueva meditación
        counterValue = 1

        mediaPlayer = MediaPlayer.create(this, R.raw.meditacion)
        mediaPlayer.start()
        timer.start()
    }


    private fun playAudio(audioResourceId: Int) {

        // Detener el audio actual si se está reproduciendo
        if (mediaPlayer.isPlaying) {
            mediaPlayer.stop()
            mediaPlayer.prepare() // Prepare the player for the next play
            pauseButton.text = "Pausar"
            isPaused = false
        }

        mediaPlayer = MediaPlayer.create(this, audioResourceId)
        mediaPlayer.start()

        val audioDuration = mediaPlayer.duration.toLong()
        timer = object : CountDownTimer(audioDuration, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val secondsRemaining = millisUntilFinished / 1000
                countdownTextView.text = secondsRemaining.toString()
            }

            override fun onFinish() {
                countdownTextView.text = "Reproducción finalizada"
                mediaPlayer.stop()
                mediaPlayer.release()
            }
        }

        timer.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
        timer.cancel()
    }
}
