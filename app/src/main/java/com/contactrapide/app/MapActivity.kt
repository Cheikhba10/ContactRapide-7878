package com.contactrapide.app

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.preference.PreferenceManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.contactrapide.app.databinding.ActivityMapBinding
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.compass.CompassOverlay
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay

class MapActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMapBinding
    private lateinit var myLocationOverlay: MyLocationNewOverlay

    // Coordonnées exactes de l'agence ContactRapide
    private val agencyLat = 14.7551443
    private val agencyLon = -17.4306665
    private val agencyPoint by lazy { GeoPoint(agencyLat, agencyLon) }

    companion object {
        private const val REQ_LOCATION = 1001
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Configuration osmdroid (obligatoire)
        Configuration.getInstance().load(
            applicationContext,
            PreferenceManager.getDefaultSharedPreferences(applicationContext)
        )
        Configuration.getInstance().userAgentValue = packageName

        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupMap()
        setupButtons()
    }

    private fun setupMap() {
        binding.map.setTileSource(TileSourceFactory.MAPNIK)
        binding.map.setMultiTouchControls(true)
        binding.map.controller.setZoom(17.0)
        binding.map.controller.setCenter(agencyPoint)

        // Boussole
        val compass = CompassOverlay(this, binding.map)
        compass.enableCompass()
        binding.map.overlays.add(compass)

        // Marqueur de l'agence
        val marker = Marker(binding.map).apply {
            position = agencyPoint
            title = "ContactRapide — Agence des Bonnes"
            snippet = getString(R.string.address_short)
            setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
        }
        binding.map.overlays.add(marker)
        binding.map.invalidate()

        // Overlay de ma position (activé au clic)
        val gpsProvider = GpsMyLocationProvider(this)
        myLocationOverlay = MyLocationNewOverlay(gpsProvider, binding.map)
        myLocationOverlay.enableMyLocation()
        binding.map.overlays.add(myLocationOverlay)
    }

    private fun setupButtons() {

        binding.btnBack.setOnClickListener { finish() }

        // Bouton "Ma position"
        binding.btnMyLocation.setOnClickListener {
            if (hasLocationPermission()) {
                centerOnMyLocation()
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ),
                    REQ_LOCATION
                )
            }
        }

        // Bouton "Google Maps" (ouvre l'app Google Maps ou le navigateur)
        binding.btnGoogleMaps.setOnClickListener {
            val uri = Uri.parse("geo:$agencyLat,$agencyLon?q=$agencyLat,$agencyLon(ContactRapide)")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            intent.setPackage("com.google.android.apps.maps")
            try {
                startActivity(intent)
            } catch (e: Exception) {
                // Fallback navigateur
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://www.google.com/maps/search/?api=1&query=$agencyLat,$agencyLon")
                    )
                )
            }
        }

        // Bouton "Itinéraire"
        binding.btnRoute.setOnClickListener {
            val uri = Uri.parse("google.navigation:q=$agencyLat,$agencyLon&mode=d")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            intent.setPackage("com.google.android.apps.maps")
            try {
                startActivity(intent)
            } catch (e: Exception) {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://www.google.com/maps/dir/?api=1&destination=$agencyLat,$agencyLon")
                    )
                )
            }
        }
    }

    private fun hasLocationPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            this, Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun centerOnMyLocation() {
        val myLocation = myLocationOverlay.myLocation
        if (myLocation != null) {
            binding.map.controller.animateTo(myLocation)
            binding.map.controller.setZoom(17.0)
        } else {
            Toast.makeText(this, "Position en cours d'acquisition…", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQ_LOCATION && grantResults.isNotEmpty()
            && grantResults[0] == PackageManager.PERMISSION_GRANTED
        ) {
            centerOnMyLocation()
        } else {
            Toast.makeText(this, "Permission refusée", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onResume() {
        super.onResume()
        binding.map.onResume()
        myLocationOverlay.enableMyLocation()
    }

    override fun onPause() {
        super.onPause()
        binding.map.onPause()
        myLocationOverlay.disableMyLocation()
    }
}
