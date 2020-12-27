package com.alsharany.earthquakeapp.models

import com.google.gson.annotations.SerializedName

data class Geometric(
    @SerializedName("coordinates")
    var geos: List<Double> = emptyList()
)