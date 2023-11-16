package com.example.firebaseloginkotlin

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.content.Intent
import androidx.cardview.widget.CardView
import android.widget.RelativeLayout

class ApoyoActivity : AppCompatActivity() {
    private lateinit var apoyo: CardView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_apoyo)

        apoyo = findViewById(R.id.apoyo)
        apoyo.setOnClickListener {
            val intent = Intent(this, PsiquiatriaActivity::class.java)
            startActivity(intent)
        }

    }
}



