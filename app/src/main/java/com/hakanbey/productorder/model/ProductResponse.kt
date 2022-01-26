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

data class ProductResponse(
    @SerializedName("Products")
    val products: List<Product>? = null
)

data class Product(
    @SerializedName("durum")
    val durum: Boolean,
    @SerializedName("mesaj")
    val mesaj: String,
    @SerializedName("total")
    val total: String,
    @SerializedName("bilgiler")
    val bilgiler: ArrayList<ProductBilgiler>
)

data class ProductBilgiler(
    @SerializedName("productId")
    val productId: String,
    @SerializedName("productName")
    val productName: String,
    @SerializedName("brief")
    val brief: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("price")
    val price: String,
    @SerializedName("saleInformation")
    val saleInformation: SaleInformation,
    @SerializedName("campaign")
    val campaign: Campaign,
    @SerializedName("campaignTitle")
    val campaignTitle: String,
    @SerializedName("campaignDescription")
    val campaignDescription: String,
    @SerializedName("categories")
    val categories: List<Categories>,
    @SerializedName("image")
    val image: Boolean,
    @SerializedName("images")
    val images: List<Images>,
    @SerializedName("likes")
    val likes: Likes
)

data class SaleInformation(
    @SerializedName("saleTypeId")
    val saleTypeId: String,
    @SerializedName("saleType")
    val saleType: String
)

data class Campaign(
    @SerializedName("campaignTypeId")
    val campaignTypeId: String
)

data class Categories(
    @SerializedName("categoryId")
    val categoryId: String,
    @SerializedName("categoryName")
    val categoryName: String
)

data class Images(
    @SerializedName("normal")
    val normal: String,
    @SerializedName("thumb")
    val thumb: String
)

data class Likes(
    @SerializedName("like")
    val like: Like,
    @SerializedName("dislike")
    val dislike: Int
)

data class Like(
    @SerializedName("oy_toplam")
    val oy_toplam: String,
    @SerializedName("ortalama")
    val ortalama: String
)