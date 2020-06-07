package com.example.nearbyplaces

import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices

class MainActivity : AppCompatActivity(){

    var edtPlace:EditText ?= null
    var btnSearch:Button  ?= null

    var mlocation:Location ?= null
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        edtPlace = findViewById(R.id.edtPalce) as EditText
        btnSearch = findViewById(R.id.btnSearch) as Button
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location : Location? ->
                // Got last known location. In some rare situations this can be null.
                mlocation = location
            }

        btnSearch!!.setOnClickListener(View.OnClickListener {
            if (mlocation != null && edtPlace!!.text.length > 0){
                var bundleData = Bundle().apply {
                    putString("place", edtPlace!!.text.toString())
                    putDouble("lat", mlocation!!.latitude)
                    putDouble("long", mlocation!!.longitude)
                }

                var placesFragment = ShowPlacesFragment()
                placesFragment.arguments = bundleData
                supportFragmentManager.beginTransaction().add(R.id.container, placesFragment).addToBackStack(null).commit()

            }
        })



    }

}
