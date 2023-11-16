package com.example.firebaseloginkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.view.View
import android.widget.Button
import com.example.firebaseloginkotlin.AudiosAnsiedadActivity
import com.example.firebaseloginkotlin.AudiosDepresionActivity
import com.example.firebaseloginkotlin.AudiosEstresActivity
import com.example.firebaseloginkotlin.R

class HacerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hacer)

        val estresButton = findViewById<Button>(R.id.estresButton)
        val depresionButton = findViewById<Button>(R.id.depresionButton)
        val ansiedadButton = findViewById<Button>(R.id.ansiedadButton)

        // Configurar clics en los botones del menú
        estresButton.setOnClickListener {
            // Navegar a la actividad de audios de estrés
            val intent = Intent(this, AudiosEstresActivity::class.java)
            startActivity(intent)
        }

        depresionButton.setOnClickListener {
            // Navegar a la actividad de audios de depresión
            val intent = Intent(this, AudiosDepresionActivity::class.java)
            startActivity(intent)
        }

        ansiedadButton.setOnClickListener {
            // Navegar a la actividad de audios de ansiedad
            val intent = Intent(this, AudiosAnsiedadActivity::class.java)
            startActivity(intent)
        }
    }
}
