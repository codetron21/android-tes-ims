package com.codetron.imscodingtest.abstraction

interface AccountManager {
    fun setName(name: String)
    fun getName(): String?
    fun getProductsCart(): List<Int>
    fun addProductToCart(id: Int)
    fun reset()
}