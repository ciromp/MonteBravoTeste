package com.ciro.montebravotest.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class MetaDataResponse (
    @SerialName("1. Information")
    val information : String? = null,
    @SerialName("2. Symbol")
    val symbol : String? = null,
    @SerialName("3. Last Refreshed")
    val last_refreshed : String? = null,
    @SerialName("4. Output Size")
    val output_size : String? = null,
    @SerialName("5. Time Zone")
    val time_zone : String? = null
    )
