package com.ciro.montebravotest.models

import kotlinx.serialization.Serializable

@Serializable
class ResponseData (
    val status : Boolean = false,
    val message : MessageResponse
        )