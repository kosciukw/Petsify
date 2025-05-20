package com.kosciukw.services.data.user.api

import com.kosciukw.services.data.user.model.api.request.PairByPasswordRequest
import com.kosciukw.services.data.user.model.api.response.AccessTokenApiModel
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Url

interface UserApi {

    @POST
    suspend fun pairByPassword(
        @Url url: String,
        @Body pairByPasswordRequest: PairByPasswordRequest
    ): AccessTokenApiModel
}