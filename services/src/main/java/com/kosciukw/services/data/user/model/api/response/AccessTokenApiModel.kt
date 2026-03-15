package com.kosciukw.services.internal.user.model.api.response

import com.google.gson.annotations.SerializedName
import pl.kosciukw.petsify.shared.utils.empty

data class AccessTokenApiModel(
    @SerializedName("accessToken") val accessToken: String,
    @SerializedName("refreshToken") val refreshToken: String?
) {
    override fun toString() = String.empty()
}