package com.example.walkdog

import android.location.Location
import com.google.android.gms.maps.model.LatLng

class Dogs( val name: String, var loc: LatLng) {
    var id: Int = 0
    companion object{
        var i: Int = 0
    }
    init {
        i++
        id = i
    }
}