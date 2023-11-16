package com.example.firebaseloginkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.view.View

class CuestionarioActivity : AppCompatActivity() {

    private lateinit var opcionesRadioGroup: RadioGroup
    private lateinit var preguntaTextView: TextView
    private lateinit var siguienteButton: Button

    private lateinit var prefManager: PrefManager
    private lateinit var respuestas: MutableList<String>

    private val preguntas = arrayOf(
        "Pregunta 1: ¿Con qué frecuencia te sientes abrumado/a por la tristeza sin razón aparente?",
        "Pregunta 2: ¿Experimentas cambios significativos en tu apetito, ya sea comer en exceso o perder el interés en la comida durante períodos prolongados?",
        "Pregunta 3: ¿Sientes dificultad para conciliar el sueño o experimentas insomnio con regularidad?",
        "Pregunta 4: ¿Te resulta difícil concentrarte en tus tareas diarias debido a pensamientos persistentes o preocupaciones?",
        "Pregunta 5: ¿Has experimentado cambios significativos en tu peso sin un motivo aparente, ya sea pérdida o aumento?",
        "Pregunta 6: ¿Sientes agitación o inquietud la mayor parte del tiempo?",
        "Pregunta 7: ¿Experimentas sentimientos de desesperanza o falta de interés en actividades que solías disfrutar?",
        "Pregunta 8: ¿Te sientes constantemente fatigado/a, incluso después de descansar adecuadamente?",
        "Pregunta 9: ¿Tienes pensamientos recurrentes sobre la muerte o el deseo de morir?",
        "Pregunta 10: ¿Sientes una constante sensación de nerviosismo o tensión muscular?"
    )
    private val opcionesRespuesta = arrayOf(
        arrayOf("Nunca", "Ocasionalmente", "Frecuentemente", "Siempre"),
        arrayOf("Nunca", "Ocasionalmente", "Frecuentemente", "Siempre"),
        arrayOf("Nunca", "Ocasionalmente", "Frecuentemente", "Siempre"),
        arrayOf("Nunca", "Ocasionalmente", "Frecuentemente", "Siempre"),
        arrayOf("Nunca", "Ocasionalmente", "Frecuentemente", "Siempre"),
        arrayOf("Nunca", "Ocasionalmente", "Frecuentemente", "Siempre"),
        arrayOf("Nunca", "Ocasionalmente", "Frecuentemente", "Siempre"),
        arrayOf("Nunca", "Ocasionalmente", "Frecuentemente", "Siempre"),
        arrayOf("Nunca", "Ocasionalmente", "Frecuentemente", "Siempre"),
        arrayOf("Nunca", "Ocasionalmente", "Frecuentemente", "Siempre")
    )

    private var preguntaActual = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cuestionario)

        opcionesRadioGroup = findViewById(R.id.opcionesRadioGroup)
        preguntaTextView = findViewById(R.id.preguntaTextView)
        siguienteButton = findViewById(R.id.siguienteButton)

        prefManager = PrefManager(this)
        respuestas = prefManager.getRespuestas().toMutableList()

        siguienteButton.setOnClickListener {
            when {
                preguntaActual < preguntas.size -> {
                    val radioButtonId = opcionesRadioGroup.checkedRadioButtonId
                    if (radioButtonId != -1) {
                        val radioButton = findViewById<RadioButton>(radioButtonId)
                        val respuesta = radioButton.text.toString()
                        respuestas.add(respuesta)
                        preguntaActual++
                        mostrarPreguntaActual()
                    } else {
                        showToast("Selecciona una opción antes de continuar.")
                    }
                }
                preguntaActual == preguntas.size -> {
                    showToast("Cuestionario completado")
                    prefManager.saveRespuestas(respuestas)
                    finish()
                }
            }
        }

        mostrarPreguntaActual()
    }

    // ...

    private fun mostrarPreguntaActual() {
        if (preguntaActual < preguntas.size) {
            preguntaTextView.text = preguntas[preguntaActual]
            opcionesRadioGroup.removeAllViews()
            for (opcion in opcionesRespuesta[preguntaActual]) {
                val radioButton = RadioButton(this)
                radioButton.text = opcion
                opcionesRadioGroup.addView(radioButton)
            }
        } else {
            showToast("Cuestionario completado")
            // Aquí puedes tomar decisiones basadas en las respuestas almacenadas en `respuestas`
            // Por ejemplo, contar las respuestas y decidir qué hacer en función de los resultados.
            // Puedes agregar esa lógica aquí.
            procesarRespuestas()
        }
    }

// ...

    private fun procesarRespuestas() {
        val contadorNunca = respuestas.count { it == "Nunca" }
        val contadorOcasionalmente = respuestas.count { it == "Ocasionalmente" }
        val contadorFrecuentemente = respuestas.count { it == "Frecuentemente" }
        val contadorSiempre = respuestas.count { it == "Siempre" }

        when {
            contadorNunca > contadorOcasionalmente && contadorNunca > contadorFrecuentemente && contadorNunca > contadorSiempre -> {
                showToast("La mayoría de respuestas son 'Nunca', lo que podría indicar menor probabilidad de experimentar síntomas relacionados con la depresión, ansiedad o estrés.")
                mostrarSugerenciasBienestar()
            }
            contadorOcasionalmente > contadorNunca && contadorOcasionalmente > contadorFrecuentemente && contadorOcasionalmente > contadorSiempre -> {
                showToast("La mayoría de respuestas son 'Ocasionalmente', lo que podría indicar estrés y ansiedad.")
                mostrarSugerenciasEstrésAnsiedad()
            }
            contadorFrecuentemente > contadorNunca && contadorFrecuentemente > contadorOcasionalmente && contadorFrecuentemente > contadorSiempre -> {
                showToast("La mayoría de respuestas son 'Frecuentemente', lo que podría indicar depresión y ansiedad.")
                mostrarSugerenciasDepresionAnsiedad()
            }
            contadorSiempre > contadorNunca && contadorSiempre > contadorOcasionalmente && contadorSiempre > contadorFrecuentemente -> {
                showToast("La mayoría de respuestas son 'Siempre', lo que podría indicar depresión, ansiedad y estrés.")
                mostrarSugerenciasDepresionAnsiedadEstres()
            }
            else -> {
                showToast("Empate o igualdad en las respuestas")
                // Maneja este caso según tus necesidades
            }
        }

        val intent = Intent(this, CalendarActivity::class.java)
        startActivity(intent)

        intent.putStringArrayListExtra("respuestas", ArrayList(respuestas))
        startActivity(intent)
    }

    private fun mostrarSugerenciasBienestar() {
        // Mostrar mensaje de bienestar
        showToast("¡Felicidades! Sigue cuidando de ti mismo/a.")
        // Mostrar actividades de autocuidado
        showToast("Te sugerimos actividades de autocuidado, como meditación, paseos al aire libre y lectura.")
        // Integrar calendario de bienestar
        showToast("Integra un calendario de bienestar para recordatorios diarios.")
    }

    private fun mostrarSugerenciasEstrésAnsiedad() {
        // Sugerir técnicas de manejo del estrés
        showToast("Te sugerimos técnicas de manejo del estrés, como respiración profunda y yoga.")
        // Sugerir actividades relajantes
        showToast("Prueba actividades relajantes como escuchar música tranquila y tomar baños relajantes.")
        // Integrar registro de estrés
        showToast("Registra situaciones estresantes y respuestas emocionales en un diario.")
    }

    private fun mostrarSugerenciasDepresionAnsiedad() {
        // Sugerir buscar apoyo profesional
        showToast("Te recomendamos buscar apoyo profesional para explorar tus sentimientos.")
        // Sugerir actividades para el bienestar emocional
        showToast("Prueba actividades que fomenten el bienestar emocional, como hablar con amigos y practicar la gratitud.")
        // Integrar agenda de apoyo
        showToast("Integra una agenda para sesiones con profesionales de la salud mental.")
    }

    private fun mostrarSugerenciasDepresionAnsiedadEstres() {
        // Sugerir buscar ayuda urgente
        showToast("Es crucial que busques ayuda profesional de inmediato.")
        // Proporcionar recursos de salud mental
        showToast("Proporciona enlaces a recursos de salud mental y líneas de ayuda de emergencia.")
        // Integrar calendario de seguimiento
        showToast("Implementa un calendario de seguimiento para programar citas con profesionales.")
    }

// ...


    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}

class PrefManager(context: Context) {
    private val prefName = "MyAppPrefs"
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)

    fun saveRespuestas(respuestas: List<String>) {
        val editor = sharedPreferences.edit()
        editor.putStringSet("respuestas", respuestas.toSet())
        editor.apply()
    }

    fun getRespuestas(): Set<String> {
        return sharedPreferences.getStringSet("respuestas", setOf()) ?: setOf()
    }
}
