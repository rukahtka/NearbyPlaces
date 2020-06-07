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
                Toast.makeText(this, "got location "+location.toString(), Toast.LENGTH_LONG).show()
                Log.d("Got IT", "got location "+location.toString())
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
/*
    override fun onStart() {
        super.onStart()

        if(ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED)
        {
            getLocation()
        }
        else
        {
            requestPermission()
        }
    }


    fun getLocation() {
        fusedLocationClient!!.lastLocation.addOnCompleteListener(OnCompleteListener {task ->

            if(task.isSuccessful && task.result != null){
                location = task.result
                Toast.makeText(this, "got location "+location.toString(), Toast.LENGTH_LONG).show()
                Log.d("Got IT", "got location "+location.toString())
            }
        })
    }

    private fun showSnackbar(mainTextStringId: String, actionStringId: Int,
                             listener: View.OnClickListener) {

        Toast.makeText(this@MainActivity, mainTextStringId, Toast.LENGTH_LONG).show()
    }

    fun requestPermission(){
        val providerational = ActivityCompat.shouldShowRequestPermissionRationale(this,
                              Manifest.permission.ACCESS_COARSE_LOCATION)
        Log.d("permission", "requesting persmission")
        if(providerational){
            showSnackbar("Location permission is required.", android.R.string.ok,
            View.OnClickListener {
                ActivityCompat.requestPermissions(this@MainActivity,
                    arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
                    REQUEST_PERMISSION_REQ_CODE)
            })
        }
        else{
            Log.d("inside else", "Requesting Permission")
            ActivityCompat.requestPermissions(this@MainActivity,
                arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
                REQUEST_PERMISSION_REQ_CODE)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {

        if(requestCode == REQUEST_PERMISSION_REQ_CODE){
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getLocation()
            }
            else{
                Toast.makeText(MainActivity@this, "Location access denied.", Toast.LENGTH_LONG).show()
            }
        }
    }
*/
/*/

inner class MyLocationListener : LocationListener {
    override fun onLocationChanged(location: Location?) {
        Log.d("test123", "Location:$location")
        if (location != null) {
            this@MainActivity.location = location
            Toast.makeText(this@MainActivity, "Location got from LocationListener", Toast.LENGTH_SHORT).show()
        }
    }
}

 override fun onConnected( bundle: Bundle?): Unit {
    if (ContextCompat.checkSelfPermission(
            this,
            "android.permission.ACCESS_FINE_LOCATION"
        ) === 0 || ContextCompat.checkSelfPermission(
            this,
            "android.permission.ACCESS_COARSE_LOCATION"
        ) === 0
    ) {
        Log.d("got123", "got Location")
        this.mLastLocation =
            LocationServices.FusedLocationApi.getLastLocation(this.mGoogleApiClient)
        if (this.mLastLocation != null) {
            Log.d("test123", "Lat:" + this.mLastLocation?.getLatitude())
            Log.d("test123", "Log:" + this.mLastLocation?.getLongitude())
            this@MainActivity.location = this.mLastLocation
            Toast.makeText(this, "Location got from client", 0).show()
        }
        startLocationUpdate()
    }
}

    override fun onConnectionSuspended(p0: Int) {

    }

    fun startLocationUpdate() {
        try {
            this.myLocationLisner = MyLocationListener()
            if (ContextCompat.checkSelfPermission(
                    this,
                    "android.permission.ACCESS_FINE_LOCATION"
                ) === 0 || ContextCompat.checkSelfPermission(
                    this,
                    "android.permission.ACCESS_COARSE_LOCATION"
                ) === 0
            ) {
                LocationServices.FusedLocationApi.requestLocationUpdates(
                    this.mGoogleApiClient,
                    this.mLocationRequest,
                    this.myLocationLisner
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onResume() {
        super.onResume()
        if (!this.isGoogleClientInitilize) {
            this.isGoogleClientInitilize = true
            this.mGoogleApiClient = GoogleApiClient.Builder(this).addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).addApi(LocationServices.API).build()
            this.mGoogleApiClient?.connect()
        }
    }

    override fun onStop() {
        super.onStop()
        this.mGoogleApiClient?.disconnect()
    }

    override fun onConnectionFailed(p0: ConnectionResult) {

    }

*/



}
