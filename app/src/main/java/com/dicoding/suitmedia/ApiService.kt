package com.dicoding.suitmedia.network

import com.dicoding.suitmedia.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

data class UserResponse(
    val page: Int,
    val per_page: Int,
    val total: Int,
    val total_pages: Int,
    val data: List<User>
)

interface ApiService {
    @GET("users")
    fun getUsers(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): Call<UserResponse>

    companion object {
        fun create(): ApiService {
            val retrofit = retrofit2.Retrofit.Builder()
                .baseUrl("https://reqres.in/api/")
                .addConverterFactory(retrofit2.converter.gson.GsonConverterFactory.create())
                .client(okhttp3.OkHttpClient.Builder()
                    .addInterceptor(okhttp3.logging.HttpLoggingInterceptor().apply {
                        level = okhttp3.logging.HttpLoggingInterceptor.Level.BODY
                    }).build())
                .build()

            return retrofit.create(ApiService::class.java)
        }
    }
}
