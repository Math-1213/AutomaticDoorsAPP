package com.example.automaticdoorsapk.userInterface.function

import android.Manifest
import android.content.Context
import android.location.Geocoder
import android.util.Log
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import java.io.File
import java.util.Locale

class LocationHelper(
    private val context: Context,
    private val fusedLocationClient: FusedLocationProviderClient
) {

    fun fetchLocation() {
        // Verifique se as permissões estão concedidas
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != android.content.pm.PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != android.content.pm.PackageManager.PERMISSION_GRANTED
        ) {
            Log.e("LocationLogApp", "Permission not granted!")
            return
        }

        // Tente obter a localização
        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                // Use uma thread separada para evitar problemas com o Geocoder
                Thread {
                    try {
                        val geocoder = Geocoder(context, Locale.getDefault())
                        val addresses =
                            geocoder.getFromLocation(location.latitude, location.longitude, 1)
                        if (!addresses.isNullOrEmpty()) {
                            val address = addresses[0].getAddressLine(0)
                            saveLogToFile("Current Address: $address")
                            Log.i("LocationLogApp", "Current Address: $address")
                        } else {
                            Log.e("LocationLogApp", "No address found!")
                        }
                    } catch (e: Exception) {
                        Log.e("LocationLogApp", "Geocoder error: ${e.message}")
                    }
                }.start()
            } else {
                Log.e("LocationLogApp", "Location is null!")
            }
        }.addOnFailureListener { exception ->
            Log.e("LocationLogApp", "Error fetching location: ${exception.message}")
        }
    }

    private fun saveLogToFile(message: String) {
        try {
            val logFile = File(context.getExternalFilesDir(null), "location_log.txt")
            logFile.appendText("$message\n")
            Log.i("LocationLogApp", "Log saved to ${logFile.absolutePath}")
        } catch (e: Exception) {
            Log.e("LocationLogApp", "Failed to save log: ${e.message}")
        }
    }
}