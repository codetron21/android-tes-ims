package com.codetron.imscodingtest.feetcalculator

class NumberValidator {

    fun validate(number: String?): List<Pair<Int, Int>> {
        val listValidation = mutableListOf<Pair<Int, Int>>()
        checkNumber(number)?.let(listValidation::add)
        return listValidation
    }

    private fun checkNumber(number: String?): Pair<Int, Int>? {
        val numberCandidate = number?.trim() ?: return (NUMBER_INDEX to R.string.error_number_empty)

        if (numberCandidate.isEmpty()) {
            return NUMBER_INDEX to R.string.error_number_empty
        }

        numberCandidate.toDoubleOrNull() ?: return NUMBER_INDEX to R.string.error_number_invalid
        return null
    }

    companion object {
        const val NUMBER_INDEX = 0
    }

}