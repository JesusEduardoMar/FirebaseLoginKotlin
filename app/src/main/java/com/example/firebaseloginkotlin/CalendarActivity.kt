package com.example.firebaseloginkotlin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firebaseloginkotlin.databinding.ActivityCalendarBinding
import java.util.*

class CalendarActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCalendarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalendarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val selectedDate = intent.getLongExtra("selectedDate", 0L)

        if (selectedDate != 0L) {
            mostrarActividades(selectedDate)
        }
        val respuestas = intent.getStringArrayListExtra("respuestas")
        // Puedes agregar más configuraciones o funcionalidades según sea necesario
    }

    private fun mostrarActividades(selectedDate: Long) {
        val actividades = obtenerActividadesPredefinidas(selectedDate)

        val adapter = ActividadesAdapter(actividades)
        binding.activitiesRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.activitiesRecyclerView.adapter = adapter
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
