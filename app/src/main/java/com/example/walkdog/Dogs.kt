package com.example.walkdog

import com.google.android.gms.maps.model.LatLng


data class Dogs( val name: String, var loc: LatLng) {
    var id: Int = 0
    companion object{
        var i: Int = 0
    }
    init {
        i++
        id = i
    }
}

