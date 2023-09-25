package com.example.firebaseloginkotlin.pantalla

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.firebaseloginkotlin.MenuActivity
import com.example.firebaseloginkotlin.R
import com.example.firebaseloginkotlin.SignInActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            val intent = Intent(this@SplashActivity, MenuActivity::class.java)
            startActivity(intent)
            finish()
        }, 5000L)
    }
}
