package com.example.tennisapp.api

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface RetrofitAPI {

    /*
    A Retrofit object can call these functions. These functions execute php code found in C:\xampp\htdocs\TennisApp\v1
    */


    @FormUrlEncoded
    @POST("registerUsers.php")
    fun createUser(
        @Field("email") email : String,
        @Field("username") username : String,
        @Field("password") password : String,
        @Field("securityquestion") securityquestion : String,
        @Field("answer") answer : String
    ): Call<ResponseBody>

    @FormUrlEncoded
    @POST("userLogin.php")
    fun userLogin(
        @Field("username") username: String,
        @Field("password") password: String
    ) : Call <ResponseBody>

}