package com.codetron.imscodingtest.shirtstore.data

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

class StoreDataSources(
    private val context: Context,
    private val gson: Gson
) : DataSources {

    override fun getAllProducts(): List<Product> {
        return readProductsJson()
    }

    override fun filterProductsByCategory(categoryId: Int): List<Product> {
        return emptyList()
    }

    override fun getAllCategories(): List<Category> {
        return readCategoriesJson()
    }

    override fun getProductById(id: Int): Product? {
        return null
    }

    override fun getCategoryById(id: Int): Category? {
        return null
    }

    private fun readCategoriesJson(): List<Category> {
        val i: InputStream = context.assets.open("categories.json")
        val br = BufferedReader(InputStreamReader(i))
        val typeToken = object : TypeToken<List<Category>>() {}.type
        return gson.fromJson(br, typeToken)
    }

    private fun readProductsJson(): List<Product> {
        val i: InputStream = context.assets.open("products.json")
        val br = BufferedReader(InputStreamReader(i))
        val typeToken = object : TypeToken<List<Product>>() {}.type
        return gson.fromJson(br, typeToken)
    }

}