package com.codetron.imscodingtest.shirtstore.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    @SerializedName("id") val id: Int = -1,
    @SerializedName("name") val name: String?,
    @SerializedName("price") val price: Long?,
    @SerializedName("description") val description: String?,
    @SerializedName("category_id") val categoryId: Int? = -1,
    @SerializedName("photo_url") val photoUrl: String? = null,
    var category: Category? = null,
) : Parcelable