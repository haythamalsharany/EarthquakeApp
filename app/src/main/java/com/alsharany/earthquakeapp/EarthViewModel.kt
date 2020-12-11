package com.alsharany.earthquakeapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class EarthViewModel : ViewModel() {

    val earthquakeLiveData: LiveData<List<Earthquake>>

    init {
        earthquakeLiveData = EarthquakeFetchr().feachData()
    }

}