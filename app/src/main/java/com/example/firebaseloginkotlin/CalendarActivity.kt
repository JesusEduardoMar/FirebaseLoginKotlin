package com.example.firebaseloginkotlin

import ActividadesAdapter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firebaseloginkotlin.databinding.ActivityCalendarBinding
import java.util.*

class CalendarActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCalendarBinding
    private lateinit var adapter: ActividadesAdapter // Declarar el adaptador como variable de clase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalendarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicializar el adaptador
        adapter = ActividadesAdapter(emptyList())

        // Configurar el RecyclerView con el adaptador
        binding.activitiesRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.activitiesRecyclerView.adapter = adapter

        // Obtener respuestas del intent
        val respuestas = intent.getStringArrayListExtra("respuestas")

        // Verificar que las respuestas no sean nulas
        if (respuestas != null) {
            // Mostrar actividades en el calendario
            mostrarActividadesSegunRespuestas(respuestas)
        }


        val selectedDate = intent.getLongExtra("selectedDate", 0L)

        if (selectedDate != 0L && respuestas != null) {
            // Actualizar el adaptador con las nuevas actividades
            val actividades = obtenerActividadesPersonalizadas(selectedDate, respuestas)
            adapter.actualizarActividades(actividades)
            //mostrarActividades(selectedDate, respuestas)
        }
        // Puedes agregar más configuraciones o funcionalidades según sea necesario
    }

    private fun mostrarActividadesSegunRespuestas(respuestas: List<String>) {
        // Obtener la fecha actual o la fecha seleccionada según tu lógica
        val selectedDate = System.currentTimeMillis() // Puedes personalizar esto según tus necesidades

        // Obtener actividades personalizadas según las respuestas del usuario
        val actividades = obtenerActividadesPersonalizadas(selectedDate, respuestas)

        // Configurar el adaptador y mostrar las actividades
        adapter = ActividadesAdapter(actividades)
        binding.activitiesRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.activitiesRecyclerView.adapter = adapter
    }

    private fun obtenerActividadesPersonalizadas(selectedDate: Long, respuestas: List<String>): List<String> {
        // Lógica de ejemplo: actividades basadas en las respuestas del usuario
        val actividades = mutableListOf<String>()

        // Usa las respuestas del usuario para determinar actividades específicas
        for (respuesta in respuestas) {
            when (respuesta) {
                "Nunca" -> actividades.add("Realizar una actividad relajante")
                "Ocasionalmente" -> actividades.add("Practicar técnicas de manejo del estrés")
                "Frecuentemente" -> actividades.add("Hacer ejercicio para liberar tensiones")
                "Siempre" -> actividades.add("Buscar apoyo profesional y reflexionar sobre el bienestar emocional")
            }
        }

        // Ejemplo: Agrega actividades comunes para todos los días
        actividades.addAll(obtenerActividadesPredefinidas(selectedDate))

        return actividades
    }

    private fun obtenerActividadesPredefinidas(selectedDate: Long): List<String> {
        // Ejemplo de lógica para actividades predefinidas
        return when {
            esFechaEspecial(selectedDate) -> listOf(
                "Realizar una caminata",
                "Meditar durante 10 minutos",
                "Leer un libro inspirador"
            )
            else -> listOf(
                "Hacer ejercicio",
                "Practicar yoga",
                "Escuchar música relajante"
            )
        }
    }

    private fun esFechaEspecial(date: Long): Boolean {
        // Puedes personalizar esta lógica según tus necesidades
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = date

        // En este ejemplo, se considera una fecha especial si es un día impar
        return calendar.get(Calendar.DAY_OF_MONTH) % 2 != 0
    }
}
