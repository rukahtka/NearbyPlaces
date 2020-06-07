package com.example.nearbyplaces

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewAdapter (val placeList:ArrayList<Place>) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>(){


    var onRecyclerViewItemClickListener : OnRecyclerViewItemClickListener ?= null

    interface OnRecyclerViewItemClickListener{
        fun onClick(v:View?, position:Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.views_recyclerview, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return placeList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(placeList[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) , View.OnClickListener {
        fun bindView(place: Place){
            val textPlaceName = itemView.findViewById<TextView>(R.id.placeName)
            val textPlaceVicinity = itemView.findViewById<TextView>(R.id.placeVicinity)
            textPlaceName.text = place.placeName
            textPlaceVicinity.text = place.placeVicinity
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            onRecyclerViewItemClickListener!!.onClick(p0, position)
        }

    }

    fun setOnRecyclerViewClickListener(onRecyclerViewItemClickListener: OnRecyclerViewItemClickListener){
        this.onRecyclerViewItemClickListener = onRecyclerViewItemClickListener
    }




}