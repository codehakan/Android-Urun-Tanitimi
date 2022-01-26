package com.hakanbey.productorder.util

import com.hakanbey.productorder.model.ProductBilgiler
import com.hakanbey.productorder.model.ProductResponse
import com.hakanbey.productorder.model.UserLoginResponse
import com.hakanbey.productorder.service.ProductService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// Code with ❤️
//┌──────────────────────────┐
//│ Created by Hakan AKKAYA  │
//│ ──────────────────────── │
//│ contact@hakanakkaya.net  │            
//│ ──────────────────────── │
//│ 22.01.2022               │
//└──────────────────────────┘

object Util {
    /**
     * Product List
     */
    var PRODUCTS: ProductResponse = ProductResponse()

    /**
     * Product Details
     */
    lateinit var PRODUCT_DETAILS: ProductBilgiler

    /**
     * User Information
     */
    var USERResponse: UserLoginResponse = UserLoginResponse()

    /**
     * API Attribute and Functions
     */
    private const val BASE_URL: String = "https://www.jsonbulut.com/json/"
    private var retrofit: Retrofit? = null

    const val ref: String = "c7c2de28d81d3da4a386fc8444d574f2"

    fun getClient(): ProductService {
        if (retrofit == null) {
            getRetrofit()
        }
        return retrofit!!.create(ProductService::class.java)
    }

    private fun getRetrofit(): Retrofit {
        retrofit = Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit!!
    }

}