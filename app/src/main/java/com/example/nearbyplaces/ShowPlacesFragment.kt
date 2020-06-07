package com.example.nearbyplaces

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.nearbyplaces.RecyclerViewAdapter.OnRecyclerViewItemClickListener

class ShowPlacesFragment : Fragment() {

    var recyclerView:RecyclerView ?= null
    var placeArrayList : ArrayList<Place> ?= null
    var requestQueue : RequestQueue ?= null
    var url : String ?= null
    var recyclerViewAdapter:RecyclerViewAdapter ?= null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var view = inflater.inflate(R.layout.fragment_showplaces, null)
        view.setOnClickListener(View.OnClickListener {view ->


        })
        recyclerView = view.findViewById(R.id.recyclerView) as RecyclerView
        val placeTo = arguments?.getString("place")
        val lat = arguments?.getDouble("lat")
        val long = arguments?.getDouble("long")
        recyclerView!!.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        placeArrayList = ArrayList()
        recyclerViewAdapter = RecyclerViewAdapter(placeArrayList!!)
        recyclerView!!.adapter = recyclerViewAdapter
        requestQueue = Volley.newRequestQueue(context)

        url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="+lat+","+long+"&radius=1500&keyword="+placeTo+"&key="+getString(R.string.google_place_key)
        Log.d("URL", url)
        getPlaceList(url!!)

        recyclerViewAdapter!!.setOnRecyclerViewClickListener(Listener())


        return view
    }

    fun getPlaceList(URL:String){
        var jsonObjectRequest = JsonObjectRequest(Request.Method.GET, URL, null, Response.Listener {response ->
            var arr = response.getJSONArray("results")
            for( i in 0..arr.length()-1){
                var obj = arr.getJSONObject(i)
                var photoReference:String ?= null
                if(obj.has("photos")){
                    photoReference = obj.getJSONArray("photos").getJSONObject(0).getString("photo_reference")
                }
                val place = Place(
                    obj.getJSONObject("geometry").getJSONObject("location").getDouble("lat"),
                    obj.getJSONObject("geometry").getJSONObject("location").getDouble("lng"),
                    photoReference,
                    obj.getString("place_id"),
                    obj.getString("name"),
                    obj.getString("vicinity")
                )
                placeArrayList?.add(place)
                recyclerViewAdapter?.notifyDataSetChanged()
            }
        }, Response.ErrorListener { error ->
            Log.d("Unsuccessful", "Value Not Parsed "+error.toString())
        })

        requestQueue?.add(jsonObjectRequest)
        jsonObjectRequest.retryPolicy = DefaultRetryPolicy(15000, 0, 1.0f)
    }

    inner class Listener : OnRecyclerViewItemClickListener{
        override fun onClick(v: View?, position: Int) {
            var bundle = Bundle().apply {
                    putSerializable("place", placeArrayList?.get(position))
            }

            var frag_details = PlaceDetail()
            frag_details.arguments = bundle

            this@ShowPlacesFragment.fragmentManager!!.beginTransaction().replace(R.id.container, frag_details).addToBackStack(null).commit()
        }

    }
}