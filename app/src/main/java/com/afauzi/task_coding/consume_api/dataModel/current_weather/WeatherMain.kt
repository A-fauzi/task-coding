package com.afauzi.task_coding.consume_api.dataModel.current_weather

data class WeatherMain(
    var temp: Double,
    var feels_like: Double,
    var temp_min: Double,
    var temp_max: Double,
    var pressure: Long,
    var humidity: Int
)
