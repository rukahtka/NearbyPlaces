package com.example.nearbyplaces

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide

class PlaceDetail : Fragment(){

    var placeName : TextView ?= null
    var placeAddress : TextView ?= null
    var placeImg : ImageView ?= null
    var placeLink: TextView ?= null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.place_detail_view_fragment, null)
        placeName = view.findViewById(R.id.txtPlaceName) as TextView
        placeImg = view.findViewById(R.id.placeImg) as ImageView
        placeAddress = view.findViewById(R.id.txtAdress) as TextView
        placeLink = view.findViewById(R.id.mapLink) as TextView


        val place = arguments?.getSerializable("place") as Place

        placeName?.text = place.placeName
        placeAddress?.text = place.placeVicinity

        if (place.photoReference != null) {
            Glide.with(context!!).load(
                "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference=" + place.photoReference.toString() + "&key="+getString(R.string.google_place_key)
            ).into(placeImg!!)
        } else {
            placeImg?.setImageResource(R.drawable.noimage)
        }

        placeLink?.setOnClickListener(View.OnClickListener {view ->

            val mapFragment = MapFragment()
            val bundle1 = Bundle()
            bundle1.putSerializable("place", place)
            mapFragment.arguments = bundle1
            this@PlaceDetail.fragmentManager!!.beginTransaction().replace(R.id.container, mapFragment).addToBackStack(null).commit()

        })

        return view

    }
}