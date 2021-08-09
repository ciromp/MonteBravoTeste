package com.ciro.montebravotest.models

import com.ciro.montebravotest.models.Acao
import kotlinx.serialization.Serializable

@Serializable
class MessageResponse (
    val status : Boolean = false,
    val result : List<Acao>
        )