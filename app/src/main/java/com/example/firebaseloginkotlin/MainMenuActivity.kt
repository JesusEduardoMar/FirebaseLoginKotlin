package com.example.firebaseloginkotlin

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import androidx.cardview.widget.CardView
import android.widget.Button

class MainMenuActivity : AppCompatActivity() {
    private lateinit var clothingCard: CardView
    private lateinit var messageCard: CardView
    private lateinit var doCard: CardView
    private lateinit var ayudaCard: CardView

    private lateinit var aboutUsButton: Button
    private lateinit var newUserButton: Button
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)

                clothingCard = findViewById(R.id.clothingCard)
                clothingCard.setOnClickListener {
                    val intent = Intent(this@MainMenuActivity, ApoyoActivity::class.java)
                    startActivity(intent)
                }

                    messageCard = findViewById(R.id.messageCard)
                    messageCard.setOnClickListener {
                        val intent = Intent(this@MainMenuActivity, MensajesActivity::class.java)
                        startActivity(intent)
                }

        doCard = findViewById(R.id.doCard)
        doCard.setOnClickListener {
            val intent = Intent(this@MainMenuActivity, HacerActivity::class.java)
            startActivity(intent)
        }

        ayudaCard = findViewById(R.id.ayudaCard)
        ayudaCard.setOnClickListener {
            val intent = Intent(this@MainMenuActivity, AyudaActivity::class.java)
            startActivity(intent)
        }
        aboutUsButton = findViewById(R.id.aboutUsButton)
        aboutUsButton.setOnClickListener {
            val intent = Intent(this@MainMenuActivity, AboutUsActivity::class.java)
            startActivity(intent)
        }

        newUserButton = findViewById(R.id.newUserButton)
        newUserButton.setOnClickListener {
            val intent = Intent(this@MainMenuActivity, CuestionarioActivity::class.java)
            startActivity(intent)
        }
            }

        }


