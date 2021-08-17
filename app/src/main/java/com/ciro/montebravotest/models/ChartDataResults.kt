package com.ciro.montebravotest.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class ChartDataResults (
    @SerialName("1. open")
    val open : Float,
    @SerialName("2. high")
    val high : Float,
    @SerialName("3. low")
    val low : Float,
    @SerialName("4. close")
    val close : Float,
    @SerialName("5. volume")
    val volume : Float
        )
