package com.alsharany.earthquakeapp.models


import com.google.gson.annotations.SerializedName

data class EarthquakeResponse(
    @SerializedName("features")
    var erthR: List<Earthquake>
)
