package com.codetron.imscodingtest.manager

import com.codetron.imscodingtest.abstraction.AccountManager

class AccountManagerImpl : AccountManager {

    private var name: String? = null

    override fun setName(name: String) {
        this.name = name
    }

    override fun getName(): String? {
        return name
    }
}