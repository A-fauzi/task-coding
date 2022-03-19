package com.afauzi.task_coding.consume_api.dataModel.current_weather

data class ModelCurrentWeather(
    var coord: LatLang,
    var weather: List<DataWeather>,
    var base: String,
    var main: WeatherMain,
    var visibility: Long,
    var wind: WeatherWind,
    var clouds: WeatherClouds,
    var dt: Long,
    var sys: WeatherSys,
    var timezone: Long,
    var id: Long,
    var name: String,
    var cod: Int,
)
