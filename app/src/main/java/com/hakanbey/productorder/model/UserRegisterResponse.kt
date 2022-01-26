package com.hakanbey.productorder.model

import com.google.gson.annotations.SerializedName

// Code with ❤️
//┌──────────────────────────┐
//│ Created by Hakan AKKAYA  │
//│ ──────────────────────── │
//│ contact@hakanakkaya.net  │            
//│ ──────────────────────── │
//│ 23.01.2022               │
//└──────────────────────────┘

data class UserRegisterResponse(
    @SerializedName("user")
    val user: ArrayList<UserRegister>
)

data class UserRegister(
    @SerializedName("durum")
    val durum: Boolean,
    @SerializedName("mesaj")
    val mesaj: String,
    @SerializedName("kullaniciId")
    val kullaniciId: String
)