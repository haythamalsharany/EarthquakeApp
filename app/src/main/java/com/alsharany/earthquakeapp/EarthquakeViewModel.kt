package com.alsharany.earthquakeapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.alsharany.earthquakeapp.models.Earthquake

class EarthquakeViewModel : ViewModel() {

    val earthquakeLiveData: LiveData<List<Earthquake>>

    init {
        earthquakeLiveData = EarthquakeRepositry().feachData()
    }

}