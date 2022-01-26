package com.hakanbey.productorder.service

import com.hakanbey.productorder.model.OrderProductResponse
import com.hakanbey.productorder.model.ProductResponse
import com.hakanbey.productorder.model.UserLoginResponse
import com.hakanbey.productorder.model.UserRegisterResponse
import com.hakanbey.productorder.util.Util
import retrofit2.Call
import retrofit2.http.*

// Code with ❤️
//┌──────────────────────────┐
//│ Created by Hakan AKKAYA  │
//│ ──────────────────────── │
//│ contact@hakanakkaya.net  │            
//│ ──────────────────────── │
//│ 22.01.2022               │
//└──────────────────────────┘

interface ProductService {

    /**
     * User Login Service
     */
    @GET("userLogin.php")
    fun userLogin(
        @Query("userEmail") userEmail: String,
        @Query("userPass") userPass: String,
        @Query("ref") ref: String = Util.ref,
        @Query("face") face: String = "no"
    ): Call<UserLoginResponse>


    /**
     * Product List Service
     */
    @GET("product.php")
    fun getProducts(
        @Query("ref") ref: String = Util.ref,
        @Query("start") start: String = "0"
    ): Call<ProductResponse>

    /**
     * User Register Service
     */
    @GET("userRegister.php")
    fun userRegister(
        @Query("userMail") userMail: String,
        @Query("userName") userName: String,
        @Query("userSurname") userSurname: String,
        @Query("userPhone") userPhone: String,
        @Query("userPass") userPass: String,
        @Query("ref") ref: String = Util.ref
    ): Call<UserRegisterResponse>

    /**
     * Order Product
     */
    @GET("orderForm.php")
    fun orderProduct(
        @Query("customerId") customerId: String,
        @Query("productId") productId: String,
        @Query("ref") ref: String = Util.ref,
        @Query("html") html: String = "12"
    ): Call<OrderProductResponse>
}