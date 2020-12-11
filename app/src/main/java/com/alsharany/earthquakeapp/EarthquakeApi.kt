package com.alsharany.earthquakeapp

import retrofit2.Call
import retrofit2.http.GET

interface EarthquakeApi {

    @GET("/fdsnws/event/1/query?format=geojson&starttime=2014-01-01&endtime=2014-01-02")
    fun fetchContents(): Call<ErthResponse>

}