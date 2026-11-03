package com.example.myapplication.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

import androidx.annotation.Keep

/** Dữ liệu trả về từ API: GET /v2/product */
@Keep
data class Product(
    val id: String,
    val name: String,
    val des: String,
    val price: Double,
    val imgURL: String
)

interface ProductApi {
    @GET("v2/product")
    suspend fun getProduct(): Product
}

object RetrofitClient {
    private const val BASE_URL = "https://mock.apidog.com/m1/890655-872447-default/"

    val api: ProductApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ProductApi::class.java)
    }
}
