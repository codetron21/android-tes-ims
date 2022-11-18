package com.codetron.imscodingtest.feetcalculator

import org.junit.Assert
import org.junit.Test

class NumberValidatorTest {

    private val validator = NumberValidator()

    @Test
    fun test_number_valid() {
        val number = "1.0"

        val result = validator.validate(number)

        Assert.assertNotNull(result)
        Assert.assertTrue(result.isEmpty())
    }

    @Test
    fun test_number_invalid() {
        val number = "null"

        val result = validator.validate(number)

        Assert.assertNotNull(result)
        Assert.assertTrue(result.isNotEmpty())
        Assert.assertEquals(result[0].first, NumberValidator.NUMBER_INDEX)
        Assert.assertEquals(result[0].second, R.string.error_number_invalid)
    }

    @Test
    fun test_number_null() {
        val number = null

        val result = validator.validate(number)

        Assert.assertNotNull(result)
        Assert.assertTrue(result.isNotEmpty())
        Assert.assertEquals(result[0].first, NumberValidator.NUMBER_INDEX)
        Assert.assertEquals(result[0].second, R.string.error_number_empty)
    }

}