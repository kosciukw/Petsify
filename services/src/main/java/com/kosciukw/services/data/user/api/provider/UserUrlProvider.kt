package com.kosciukw.services.data.user.api.provider

interface UserUrlProvider {

    fun getPairByPasswordUrl(): String
}