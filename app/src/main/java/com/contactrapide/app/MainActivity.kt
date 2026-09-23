package com.contactrapide.app

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.contactrapide.app.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    // Numéros officiels ContactRapide
    private val phoneNumber = "761301330"
    private val whatsappNumber = "221761301330"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupButtons()
    }

    private fun setupButtons() {

        // Bouton APPELER
        binding.btnCall.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:$phoneNumber")
            }
            startActivity(intent)
        }

        // Bouton WHATSAPP
        binding.btnWhatsapp.setOnClickListener {
            try {
                val intent = Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.parse("https://wa.me/$whatsappNumber")
                }
                startActivity(intent)
            } catch (e: Exception) {
                Toast.makeText(this, "WhatsApp non installé", Toast.LENGTH_SHORT).show()
            }
        }

        // Bouton LOCALISATION
        binding.btnLocation.setOnClickListener {
            startActivity(Intent(this, MapActivity::class.java))
        }

        // Bouton SERVICES
        binding.btnServices.setOnClickListener {
            startActivity(Intent(this, ServicesActivity::class.java))
        }

        // Bouton À PROPOS
        binding.btnAbout.setOnClickListener {
            startActivity(Intent(this, AboutActivity::class.java))
        }
    }
}
