package com.contactrapide.app

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.contactrapide.app.databinding.ActivityAboutBinding

class AboutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAboutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener { finish() }

        // Clic sur l'adresse → ouvre la carte
        binding.tvAddress.setOnClickListener {
            startActivity(Intent(this, MapActivity::class.java))
        }

        // Clic sur le téléphone → appel
        binding.tvPhone.setOnClickListener {
            startActivity(Intent(Intent.ACTION_DIAL, Uri.parse("tel:761301330")))
        }

        // Clic sur WhatsApp
        binding.tvWhatsapp.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://wa.me/221761301330")))
        }
    }
}
