package com.ciro.montebravotest.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.HashMap

@Serializable
class AcaoResponseDataMonthly (
    @SerialName("Meta Data")
    val meta_data : MetaDataResponse,
    @SerialName("Monthly Time Series")
    val data_chart_resuts : HashMap<String, ChartDataResults>
        )