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
        val products = readProductsJson()
        products.forEach { product ->
            product.category = product.categoryId?.let { categoryId ->
                getCategoryById(categoryId)
            }
        }
        return products
    }

    override fun filterProductsByCategory(categoryId: Int): List<Product> {
        val result = getAllProducts().filter {
            it.categoryId == categoryId
        }

        if (result.isEmpty()) {
            return getAllProducts()
        }

        return result
    }

    override fun getAllCategories(): List<Category> {
        return readCategoriesJson()
    }

    override fun getProductById(id: Int): Product? {
        val products = getAllProducts()
        return products.find { it.id == id }
    }

    override fun getCategoryById(id: Int): Category? {
        val categories = getAllCategories()
        return categories.find { it.id == id }
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