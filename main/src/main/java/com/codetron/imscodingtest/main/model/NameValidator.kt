package com.codetron.imscodingtest.main.model

import com.codetron.imscodingtest.main.R

class NameValidator {

    fun validate(name: String?): List<Pair<Int, Int>> {
        val listValidations = mutableListOf<Pair<Int, Int>>()

        checkName(name)?.let(listValidations::add)

        return listValidations
    }

    private fun checkName(name: String?): Pair<Int, Int>? {
        if (name?.trim().isNullOrEmpty()) {
            return NAME_INDEX to R.string.error_name
        }
        return null
    }

    companion object {
        const val NAME_INDEX = 0
    }

}