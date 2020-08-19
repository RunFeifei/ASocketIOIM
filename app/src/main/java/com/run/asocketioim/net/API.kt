package com.run.asocketioim.net

import com.run.asocketioim.bean.User
import retrofit2.http.*

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


    @POST("register")
    @FormUrlEncoded
    suspend fun register(
        @Field("username") username: String,
        @Field("password") password: String
    ): Any


    @GET("users_paginate")
    suspend fun getUsers(@Query("page") page: Int, @Query("per_page") per_page: Int): List<User>


    @GET("online_users")
    suspend fun getOnlineUsers(): List<User>


    @GET("/")
    suspend fun check0(): String

    @POST("/")
    @FormUrlEncoded
    suspend fun check1(): String
}