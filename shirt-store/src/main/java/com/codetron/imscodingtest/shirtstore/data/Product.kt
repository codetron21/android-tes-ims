package com.codetron.imscodingtest.shirtstore.data

import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("id") val id: Int = -1,
    @SerializedName("name") val name: String?,
    @SerializedName("price") val price: Long?,
    @SerializedName("description") val description: String?,
    @SerializedName("quantity") val quantity: Int,
    @SerializedName("category_id") val categoryId: Int? = -1,
    @SerializedName("photo_url") val photoUrl:String? =null,
    var isAddToCart: Boolean = false,
)