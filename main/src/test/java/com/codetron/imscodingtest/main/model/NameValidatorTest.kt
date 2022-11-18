package com.codetron.imscodingtest.main.model

import com.codetron.imscodingtest.main.R
import org.junit.Assert
import org.junit.Test

class NameValidatorTest {

    private val validator = NameValidator()

    @Test
    fun test_name_filled_should_valid(){
        val name = "ada"
        val resultSize = 0

       val result = validator.validate(name)

        Assert.assertNotNull(result)
        Assert.assertTrue(result.isEmpty())
        Assert.assertEquals(resultSize,result.size)
    }

    @Test
    fun test_name_empty_should_invalid(){
        val name = ""
        val resultSize = 1

        val result = validator.validate(name)

        Assert.assertNotNull(result)
        Assert.assertTrue(result.isNotEmpty())
        Assert.assertEquals(resultSize,result.size)
        Assert.assertEquals(NameValidator.NAME_INDEX, result[0].first)
        Assert.assertEquals(R.string.error_name, result[0].second)
    }

    @Test
    fun test_name_null_should_invalid(){
        val name = null
        val resultSize = 1

        val result = validator.validate(name)

        Assert.assertNotNull(result)
        Assert.assertTrue(result.isNotEmpty())
        Assert.assertEquals(resultSize,result.size)
        Assert.assertEquals(NameValidator.NAME_INDEX, result[0].first)
        Assert.assertEquals(R.string.error_name, result[0].second)
    }

}