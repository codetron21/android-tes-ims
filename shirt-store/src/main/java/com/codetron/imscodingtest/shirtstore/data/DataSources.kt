package com.codetron.imscodingtest.shirtstore.data

interface DataSources {
    fun getAllProducts(): List<Product>
    fun getAllCategories(): List<Category>
    fun getProductById(id: Int): Product?
    fun getCategoryById(id: Int): Category?
    fun filterProductsByCategory(categoryId: Int): List<Product>
}