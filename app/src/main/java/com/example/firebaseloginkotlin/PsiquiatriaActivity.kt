package com.example.firebaseloginkotlin

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase
import java.util.*

class PsiquiatriaActivity : AppCompatActivity() {

    // Lista de doctores, con enlaces predefinidos
    val listaDoctores = mutableListOf<Doctor>(
        Doctor("Dr. Juan Pérez", true, "abc123"),
        Doctor("Dra. María González", false, "xyz456"),
    )

    private lateinit var database: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_psiquiatra)

        // Inicializar Firebase
        database = FirebaseDatabase.getInstance()

        val btnAgendarCita: Button = findViewById(R.id.btnAgendarCita)
        val etNombrePaciente: EditText = findViewById(R.id.etNombrePaciente)
        val etEmail: EditText = findViewById(R.id.etEmail)

        btnAgendarCita.setOnClickListener {
            val doctorSeleccionado = seleccionarDoctor()

            if (doctorSeleccionado != null && esHoraDeConsulta() && doctorSeleccionado.disponible) {
                val urlConsulta = generarURLConsulta(doctorSeleccionado, etNombrePaciente.text.toString())
                mostrarMensaje("Tu URL de consulta: $urlConsulta")

                // Agendar la cita en Firebase Realtime Database
                agendarCita(doctorSeleccionado, etNombrePaciente.text.toString(), etEmail.text.toString())
            } else {
                mostrarMensaje("No se puede agendar la cita. Por favor, verifica el horario y la disponibilidad del doctor.")
            }
        }
    }

    private fun seleccionarDoctor(): Doctor? {
        // Puedes personalizar la lógica para seleccionar al doctor según tus necesidades
        return listaDoctores.firstOrNull()
    }

    private fun esHoraDeConsulta(): Boolean {
        val horaActual = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        return horaActual in 9..25
    }

    private fun generarURLConsulta(doctor: Doctor, nombrePaciente: String): String {
        // Construir el enlace completo con el prefijo "https://meet.google.com/"
        return "https://meet.google.com/${doctor.enlaceReunion}?name=$nombrePaciente"
    }

    private fun agendarCita(doctor: Doctor, nombrePaciente: String, email: String) {
        // Generar un ID único para la cita
        val citaId = UUID.randomUUID().toString()

        // Crear el objeto Cita
        val cita = Cita(citaId, nombrePaciente, email /*, Agrega más campos según tus necesidades */)

        // Guardar la cita en Firebase Realtime Database
        val citasRef = database.getReference("citas")
        citasRef.child(citaId).setValue(cita)

        // Mostrar mensaje de éxito o cualquier otra acción que desees realizar
        mostrarMensaje("Cita agendada con éxito. La doctora será notificada.")
    }

    private fun mostrarMensaje(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }
}

data class Doctor(val nombre: String, val disponible: Boolean, val enlaceReunion: String)
data class Cita(val citaId: String, val nombrePaciente: String, val email: String /* Agrega más campos según tus necesidades */)
