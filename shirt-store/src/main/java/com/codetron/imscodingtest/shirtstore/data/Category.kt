package com.codetron.imscodingtest.shirtstore.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Category(
    @SerializedName("id") val id: Int = -1,
    @SerializedName("name") val name: String? = null,
    @SerializedName("color_hex") val colorHex: String? = null,
    var isSelected: Boolean = false,
):Parcelable