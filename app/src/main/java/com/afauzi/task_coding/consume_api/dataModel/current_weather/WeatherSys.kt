package com.afauzi.task_coding.consume_api.dataModel.current_weather

data class WeatherSys(
    var type: Int,
    var id: Long,
    var country: String = "ID",
    var sunrise: Long,
    var sunset: Long
)
