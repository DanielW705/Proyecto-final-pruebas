package com.example.walkdog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng

class PerroViewModel : ViewModel() {
    private val _Dogs = MutableLiveData(
        mutableListOf(
            Dogs("Buddy", LatLng(37.7749, -122.4194)),
            Dogs("Max", LatLng(34.0522, -118.2437)),
            Dogs("Bella", LatLng(40.7128, -74.0060)),
            Dogs("Lucy", LatLng(51.5074, -0.1278))
        )
    )
    val ListDogs: LiveData<MutableList<Dogs>> = _Dogs

    fun addDog(Dog: Dogs) {
        _Dogs.value?.add(Dog)
        _Dogs.value = _Dogs.value
    }

    fun updateDogLocation(index: Int, newLoc: LatLng) {
        _Dogs.value?.get(index)?.loc = newLoc
        _Dogs.value = _Dogs.value
    }
}