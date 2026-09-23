package com.contactrapide.app

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.contactrapide.app.databinding.ActivityServicesBinding

class ServicesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityServicesBinding
    private val whatsappNumber = "221761301330"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityServicesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Bouton retour
        binding.btnBack.setOnClickListener { finish() }

        // Chaque service → ouvre WhatsApp avec un message pré-rempli
        binding.cardMenage.setOnClickListener {
            openWhatsApp("Bonjour, je souhaite un service de Ménage & Cuisine.")
        }
        binding.cardNounou.setOnClickListener {
            openWhatsApp("Bonjour, je souhaite un service de Garde d'enfants / Nounou.")
        }
        binding.cardChauffeur.setOnClickListener {
            openWhatsApp("Bonjour, je souhaite un service de Chauffeur & Sécurité.")
        }
        binding.cardPolyvalent.setOnClickListener {
            openWhatsApp("Bonjour, je souhaite un service de Personnel polyvalent.")
        }
    }

    private fun openWhatsApp(message: String) {
        try {
            val url = "https://wa.me/$whatsappNumber?text=" + Uri.encode(message)
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
        } catch (e: Exception) {
            Toast.makeText(this, "WhatsApp non installé", Toast.LENGTH_SHORT).show()
        }
    }
}
