package com.codetron.imscodingtest.feetcalculator


import org.junit.Assert
import org.junit.Test

class FeetCalculatorTest {

    @Test
    fun test_calculator() {
        val number = 1.0

        val result = FeetCalculator.calculate(number)

        println(result)

        Assert.assertEquals(number.div(FeetCalculator.DIVIDER), result, 0.0)
    }

    @Test
    fun test_format() {
        val number = 0.30478512648582745

        val result = FeetCalculator.format(number)

        println(result)

        Assert.assertEquals("0.3048", result)
    }

}