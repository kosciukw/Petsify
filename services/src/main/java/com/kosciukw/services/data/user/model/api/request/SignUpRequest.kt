package com.kosciukw.services.data.user.model.api.request

import com.google.gson.annotations.SerializedName
import pl.kosciukw.petsify.shared.utils.empty

data class SignUpRequest(
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String,
    @SerializedName("name") val name: String,
    @SerializedName("termsAccepted") val termsAccepted: Boolean,
    @SerializedName("marketingAccepted") val marketingAccepted: Boolean
) {
    override fun toString() = String.empty()
}