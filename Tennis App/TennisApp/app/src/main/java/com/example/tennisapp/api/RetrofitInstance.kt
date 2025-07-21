package com.example.tennisapp.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private const val BASE_URL = "http://192.168.4.21/TennisApp/v1/"    //IP should be your local IPv4 address which can be found by typing 'ipconfig' in terminal window


    fun getInstance(): Retrofit{
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }






}