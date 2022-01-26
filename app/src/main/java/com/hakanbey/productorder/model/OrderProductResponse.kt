package com.hakanbey.productorder.model

import com.google.gson.annotations.SerializedName

// Code with ❤️
//┌──────────────────────────┐
//│ Created by Hakan AKKAYA  │
//│ ──────────────────────── │
//│ contact@hakanakkaya.net  │            
//│ ──────────────────────── │
//│ 25.01.2022               │
//└──────────────────────────┘

data class OrderProductResponse(
    @SerializedName("order")
    val order: List<Order>
)

data class Order(
    @SerializedName("durum")
    val durum: Boolean,
    @SerializedName("mesaj")
    val mesaj: String
)