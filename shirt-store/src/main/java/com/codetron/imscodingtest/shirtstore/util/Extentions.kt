package com.codetron.imscodingtest.shirtstore.util

import com.codetron.imscodingtest.shirtstore.data.StoreState
import java.text.NumberFormat
import java.util.*

fun <T> StoreState<T>.getData() = (this as? StoreState.Success)?.data

fun Long.formatRupiah(): String {
    val localId = Locale("in", "ID")
    val numberFormat = NumberFormat.getCurrencyInstance(localId)
    return numberFormat.format(this)
}