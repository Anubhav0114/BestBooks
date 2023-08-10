package com.example.bestbooks

import com.google.gson.annotations.SerializedName

data class BestSellerBook (


    @SerializedName("rank")
    val rank : String? = null,

    @SerializedName("title")
    val title : String? = null,

    @SerializedName("book_image")
    val coverImg : String? = null ,

    @SerializedName("author")
    val author : String? = null,

    @SerializedName("description")
    val description : String? = null,

    @SerializedName("amazon_product_url")
    val purchaseLink : String? = null
)