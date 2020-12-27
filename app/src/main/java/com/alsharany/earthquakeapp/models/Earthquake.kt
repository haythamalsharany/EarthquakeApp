package com.alsharany.earthquakeapp.models


import com.google.gson.annotations.SerializedName

data class Earthquake(
    @SerializedName("properties")
    var property: Property = Property(),

    @SerializedName("geometry")
    var geometric: Geometric = Geometric()
)