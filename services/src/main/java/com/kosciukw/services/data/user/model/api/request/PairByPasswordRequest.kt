package com.kosciukw.services.data.user.model.api.request

import com.google.gson.annotations.SerializedName
import pl.kosciukw.petsify.shared.utils.empty

data class PairByPasswordRequest(
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String
) {
    override fun toString() = String.empty()
}