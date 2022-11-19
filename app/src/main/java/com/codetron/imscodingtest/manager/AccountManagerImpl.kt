package com.codetron.imscodingtest.manager

import com.codetron.imscodingtest.abstraction.AccountManager

class AccountManagerImpl : AccountManager {

    private var name: String? = null
    private val ids = mutableListOf<Int>()

    override fun setName(name: String) {
        this.name = name
    }

    override fun getName(): String? {
        return name
    }

    override fun getProductsCart(): List<Int> {
        return ids
    }

    override fun addProductToCart(id: Int) {
        ids.add(id)
    }

    override fun reset() {
        name = ""
        ids.clear()
    }
}