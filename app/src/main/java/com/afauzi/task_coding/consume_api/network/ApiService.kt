package com.afauzi.task_coding.consume_api.network

import com.afauzi.task_coding.consume_api.dataModel.current_weather.ModelCurrentWeather
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("weather")
    fun getCurrentWeather(

        @Query("id")
        idLang: String,

        @Query("lang")
        lang: String,

        @Query("lat")
        latitude: String,

        @Query("lon")
        longtitude: String,

        @Query("appid")
        appid: String,

        @Query("units")
        units: String,

        ): Call<ModelCurrentWeather>

    @GET("forecast?id=524901&lang=id&lat=-6.226831&lon=106.899936&appid=5a6f585765c828ed2b2ec4426d9f1325&units=metric")
    fun getListWeather()

    @GET("forecast/daily?&units=metric&lat=-6.226831&lon=106.899936&cnt=7&appid=064129b86c99c35c42d531db251b99e3&units=metric")
    fun getDailyWeather()

}