package com.kosciukw.services.data.user.api.provider

interface UserUrlProvider {

    fun getLoginByPasswordUrl(): String
    fun getStartOtpRegistrationUrl(): String
    fun getSignUpUrl(): String
}