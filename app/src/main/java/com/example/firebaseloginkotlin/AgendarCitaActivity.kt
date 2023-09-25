package com.example.firebaseloginkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import android.widget.Toast

class AgendarCitaActivity : AppCompatActivity() {
    private lateinit var datePicker: DatePicker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agendar_cita)

        datePicker = findViewById(R.id.datePicker)

        val buttonConfirmar: Button = findViewById(R.id.buttonConfirm)
        buttonConfirmar.setOnClickListener {
            val selectedDate = getSelectedDate()
            // Realiza las acciones necesarias con la fecha seleccionada
            Toast.makeText(this, "Fecha seleccionada: $selectedDate", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getSelectedDate(): String {
        val day = datePicker.dayOfMonth
        val month = datePicker.month
        val year = datePicker.year

        // Formatea la fecha como desees (por ejemplo, "dd/MM/yyyy")
        val formattedDate = String.format("%02d/%02d/%04d", day, month + 1, year)

        return formattedDate
    }
}
