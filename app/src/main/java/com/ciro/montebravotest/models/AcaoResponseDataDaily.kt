package com.ciro.montebravotest.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.HashMap

@Serializable
class AcaoResponseDataDaily (
    @SerialName("Meta Data")
    val meta_data : MetaDataResponse,
    @SerialName("Time Series (Daily)")
    val data_chart_resuts : HashMap<String, ChartDataResults>
)