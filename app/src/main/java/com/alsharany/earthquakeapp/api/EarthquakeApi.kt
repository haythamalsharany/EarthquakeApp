package com.alsharany.earthquakeapp.api

import com.alsharany.earthquakeapp.models.EarthquakeResponse
import retrofit2.Call
import retrofit2.http.GET

interface EarthquakeApi {

    @GET("query?format=geojson&limit=50")
    fun fetchContents(): Call<EarthquakeResponse>

}