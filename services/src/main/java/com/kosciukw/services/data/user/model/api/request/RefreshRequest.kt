package com.kosciukw.services.internal.user.model.api.request

import com.google.gson.annotations.SerializedName
import pl.kosciukw.petsify.shared.utils.empty

data class RefreshRequest(
    @SerializedName("refreshToken") val refreshToken: String
) {
    override fun toString() = String.empty()
}