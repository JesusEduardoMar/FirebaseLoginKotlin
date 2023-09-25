package com.example.firebaseloginkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import android.widget.RadioButton
import android.widget.RadioGroup // Agrega esta importación
import com.example.firebaseloginkotlin.R

class CuestionarioActivity : AppCompatActivity() {

    private lateinit var preguntaTextView: TextView
    private lateinit var respuestaEditText: EditText
    private lateinit var siguienteButton: Button

    private val preguntas = arrayOf(
        "Pregunta 1: ¿Cuál es tu nombre?",
        "Pregunta 2: ¿Cuál es tu edad?",
        "Pregunta 3: ¿Dónde vives?"
    )

    private var preguntaActual = 0
    private val respuestas = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cuestionario)

        preguntaTextView = findViewById(R.id.preguntaTextView)
        respuestaEditText = findViewById(R.id.respuestaEditText)
        siguienteButton = findViewById(R.id.siguienteButton)

        mostrarPreguntaActual()

        siguienteButton.setOnClickListener {
            val respuesta = respuestaEditText.text.toString()
            respuestas.add(respuesta)
            preguntaActual++

            if (preguntaActual < preguntas.size) {
                mostrarPreguntaActual()
            } else {
                // Has completado el cuestionario, puedes hacer algo con las respuestas.
                // Por ejemplo, mostrarlas en un Toast y luego finalizar la actividad.
                // Aquí solo mostramos un Toast como ejemplo:
                val respuestasString = respuestas.joinToString("\n")
                showToast("Respuestas:\n$respuestasString")
                finish()
            }
        }
    }

    private fun mostrarPreguntaActual() {
        preguntaTextView.text = preguntas[preguntaActual]
        respuestaEditText.text.clear()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
