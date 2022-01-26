package com.hakanbey.productorder.model

import com.google.gson.annotations.SerializedName

// Code with ❤️
//┌──────────────────────────┐
//│ Created by Hakan AKKAYA  │
//│ ──────────────────────── │
//│ contact@hakanakkaya.net  │            
//│ ──────────────────────── │
//│ 22.01.2022               │
//└──────────────────────────┘

data class UserLoginResponse(
    @SerializedName("user")
    val userLogin: List<UserLogin>? = null
)

data class UserLogin(
    @SerializedName("durum")
    val durum: Boolean,
    @SerializedName("mesaj")
    val mesaj: String,
    @SerializedName("bilgiler")
    val bilgiler: Bilgiler?
)

data class Bilgiler(
    @SerializedName("userId")
    val userId: String,
    @SerializedName("userName")
    val userName: String,
    @SerializedName("userSurname")
    val userSurname: String,
    @SerializedName("userEmail")
    val userEmail: String,
    @SerializedName("userPhone")
    val userPhone: String,
    @SerializedName("face")
    val face: String,
    @SerializedName("faceID")
    val faceID: String
)