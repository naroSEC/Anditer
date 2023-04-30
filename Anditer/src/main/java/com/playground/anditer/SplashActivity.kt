package com.playground.anditer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.playground.anditer.databinding.MainLogoViewBinding

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = MainLogoViewBinding.inflate(layoutInflater)
        val fadeOutParticleView = binding.fadeOutParticle
        setContentView(binding.root)

        fadeOutParticleView.startAnimation()

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        },DURATION)
    }

    companion object {
        private const val DURATION : Long = 3500
    }
}