package com.codetron.imscodingtest.feetcalculator

object FeetCalculator {
    const val DIVIDER = 3.281
    fun calculate(number: Double): Double {
        return number / DIVIDER
    }

    fun format(number: Double): String {
        return String.format("%.4f", number)
    }
}