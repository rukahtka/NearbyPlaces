package com.example.nearbyplaces

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapFragment : Fragment(), OnMapReadyCallback {
    var mGoogleMap: GoogleMap? = null
    var mMap: MapView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.map_fragment, null)
        mMap = view.findViewById<View>(R.id.map) as MapView
        mMap?.onCreate(savedInstanceState)
        mMap?.onResume()
        try {
            MapsInitializer.initialize(context)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        mMap?.getMapAsync(this)
        return view
    }

    override fun onMapReady(p0: GoogleMap?) {
        mGoogleMap = p0
        val place: Place = arguments?.getSerializable("place") as Place
        mGoogleMap?.addMarker(
            MarkerOptions().position(
                LatLng(
                    place.lat,
                    place.long
                )
            ).title(place.placeName).flat(true)
        )
        if (ContextCompat.checkSelfPermission(context!!,"android.permission.ACCESS_FINE_LOCATION") === 0 || ContextCompat.checkSelfPermission(
                context!!,"android.permission.ACCESS_COARSE_LOCATION") === 0
        ) {
            mGoogleMap?.setMyLocationEnabled(true)
            mGoogleMap?.animateCamera(
                CameraUpdateFactory.newCameraPosition(
                    CameraPosition.builder().target(LatLng(place.lat, place.long))
                        .zoom(16.0f).build()
                ), 5000, null
            )
        }
    }

}