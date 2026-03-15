package com.kosciukw.services.internal.user.model.api.request

import com.google.gson.annotations.SerializedName
import pl.kosciukw.petsify.shared.utils.empty

data class LoginByPasswordRequest(
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String
) {
    override fun toString() = String.empty()
}