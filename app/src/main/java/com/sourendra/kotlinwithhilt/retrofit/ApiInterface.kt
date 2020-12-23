package com.sourendra.kotlinwithhilt.retrofit

import retrofit2.http.GET

interface ApiInterface {

    @GET("blogs")
    suspend fun getBlogs(): List<BlogNetworkEntity>
}