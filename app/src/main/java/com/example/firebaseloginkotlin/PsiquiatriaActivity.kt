package com.example.firebaseloginkotlin

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class PsiquiatriaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_psiquiatria)

        val buttonDoctor1: Button = findViewById(R.id.buttonDoctor1)
        val buttonDoctor2: Button = findViewById(R.id.buttonDoctor2)
        val buttonDoctor3: Button = findViewById(R.id.buttonDoctor3)

        // Asigna un listener al botón de agendar cita del primer doctor
        buttonDoctor1.setOnClickListener {
            // Código para abrir la actividad de agendar cita y pasar los datos necesarios
            val intent = Intent(this@PsiquiatriaActivity, AgendarCitaActivity::class.java)
            intent.putExtra("doctorName", "Dr. Juan Carlos") // Ejemplo de dato a pasar (nombre del doctor)
            // Agrega más datos necesarios según tus requerimientos
            startActivity(intent)
        }
        buttonDoctor2.setOnClickListener {
            // Código para abrir la actividad de agendar cita y pasar los datos necesarios
            val intent = Intent(this@PsiquiatriaActivity, AgendarCitaActivity::class.java)
            intent.putExtra("doctorName", "Dr. Juan Carlos") // Ejemplo de dato a pasar (nombre del doctor)
            // Agrega más datos necesarios según tus requerimientos
            startActivity(intent)
        }
        buttonDoctor3.setOnClickListener {
            // Código para abrir la actividad de agendar cita y pasar los datos necesarios
            val intent = Intent(this@PsiquiatriaActivity, AgendarCitaActivity::class.java)
            intent.putExtra("doctorName", "Dr. Juan Carlos") // Ejemplo de dato a pasar (nombre del doctor)
            // Agrega más datos necesarios según tus requerimientos
            startActivity(intent)
        }
    }
}

