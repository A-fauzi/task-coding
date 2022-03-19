package com.afauzi.task_coding.consume_api.dataModel.current_weather

data class DataWeather(
    var id : Int,
    var main: String,
    var description: String,
    var icon: String
)
