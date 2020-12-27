package com.alsharany.earthquakeapp

import androidx.lifecycle.MutableLiveData
import com.alsharany.earthquakeapp.api.EarthquakeApi
import com.alsharany.earthquakeapp.models.Earthquake
import com.alsharany.earthquakeapp.models.EarthquakeResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class EarthquakeRepositry {
    var eathquakapi: EarthquakeApi


    init {
        val retrofit: Retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://earthquake.usgs.gov/fdsnws/event/1/")
            .build()
        eathquakapi = retrofit.create(EarthquakeApi::class.java)
    }


    fun feachData(): MutableLiveData<List<Earthquake>> {
        val responseLiveData: MutableLiveData<List<Earthquake>> = MutableLiveData()
        val eathquakHomePageRequest: Call<EarthquakeResponse> = eathquakapi.fetchContents()
        eathquakHomePageRequest.enqueue(object : Callback<EarthquakeResponse> {
            override fun onResponse(
                call: Call<EarthquakeResponse>,
                response: Response<EarthquakeResponse>
            ) {
                var erthResponse = response.body()
                var eathquakes = erthResponse?.erthResponse
                    ?: mutableListOf()
                responseLiveData.value = eathquakes
            }

            override fun onFailure(call: Call<EarthquakeResponse>, t: Throwable) {

            }

        })

        return responseLiveData

    }


}