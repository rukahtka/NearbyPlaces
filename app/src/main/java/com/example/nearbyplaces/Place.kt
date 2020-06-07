package com.example.nearbyplaces

import java.io.Serializable

data class Place (var lat:Double, var long:Double, var photoReference:String?,
                  var placeId:String, var placeName:String, var placeVicinity:String) : Serializable