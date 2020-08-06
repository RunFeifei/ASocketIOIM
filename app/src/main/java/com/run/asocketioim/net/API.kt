package com.run.asocketioim.net

import com.run.asocketioim.bean.User
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * Created by PengFeifei on 2020/8/5.
 */
interface API {

    @POST("login")
    @FormUrlEncoded
    suspend fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): User
}