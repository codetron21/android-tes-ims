package com.codetron.imscodingtest.feetcalculator

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import io.mockk.*
import io.mockk.impl.annotations.MockK
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class FeetCalculatorViewModelTest {

    @get:Rule
    val executor = InstantTaskExecutorRule()

    private lateinit var validator: NumberValidator
    private lateinit var calculator: FeetCalculator
    private lateinit var viewModel: FeetCalculatorViewModel

    @MockK
    private lateinit var validationsObserver: Observer<List<Pair<Int, Int>>>

    @MockK
    private lateinit var resultObserver: Observer<Pair<String?, String?>>

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        validator = NumberValidator()
        calculator = FeetCalculator
        viewModel = FeetCalculatorViewModel(validator, calculator)
    }

    @Test
    fun test_calculate_with_valid_number_should_success() {
        val number = "10.0"
        val resultInString = calculator.format(calculator.calculate(number.toDouble()))
        val slotValidations = slot<List<Pair<Int, Int>>>()
        val listResult = mutableListOf<Pair<String?, String?>>()

        every {
            validationsObserver.onChanged(capture(slotValidations))
            resultObserver.onChanged(capture(listResult))
        } just runs

        viewModel.getResult().observeForever(resultObserver)
        viewModel.getListValidation().observeForever(validationsObserver)

        viewModel.calculate(number)

        verifyOrder {
            resultObserver.onChanged(listResult[0])
            validationsObserver.onChanged(slotValidations.captured)
            resultObserver.onChanged(listResult[1])
        }

        println(listResult)

        Assert.assertTrue(slotValidations.captured.isEmpty())
        Assert.assertTrue(listResult.isNotEmpty())
        Assert.assertNotNull(listResult[0].first)
        Assert.assertNull(listResult[0].second)
        Assert.assertNotNull(listResult[1].first)
        Assert.assertNotNull(listResult[1].second)
        Assert.assertEquals(number, listResult[1].first)
        Assert.assertEquals(resultInString, listResult[1].second)
    }

    @Test
    fun test_calculate_with_invalid_number_should_fail() {
        val number = ""
        val slotValidations = slot<List<Pair<Int, Int>>>()
        val listResult = mutableListOf<Pair<String?, String?>>()

        every {
            validationsObserver.onChanged(capture(slotValidations))
            resultObserver.onChanged(capture(listResult))
        } just runs

        viewModel.getResult().observeForever(resultObserver)
        viewModel.getListValidation().observeForever(validationsObserver)

        viewModel.calculate(number)

        verifyOrder {
            resultObserver.onChanged(listResult[0])
            validationsObserver.onChanged(slotValidations.captured)
        }

        println(listResult)

        Assert.assertTrue(slotValidations.captured.isNotEmpty())
        Assert.assertTrue(listResult.isNotEmpty())
        Assert.assertNotNull(listResult[0].first)
        Assert.assertNull(listResult[0].second)
        Assert.assertEquals(NumberValidator.NUMBER_INDEX,slotValidations.captured[0].first)
        Assert.assertEquals(R.string.error_number_empty,slotValidations.captured[0].second)
    }

    @Test
    fun test_calculate_with_null_number_should_fail() {
        val number = null
        val slotValidations = slot<List<Pair<Int, Int>>>()
        val listResult = mutableListOf<Pair<String?, String?>>()

        every {
            validationsObserver.onChanged(capture(slotValidations))
            resultObserver.onChanged(capture(listResult))
        } just runs

        viewModel.getResult().observeForever(resultObserver)
        viewModel.getListValidation().observeForever(validationsObserver)

        viewModel.calculate(number)

        verifyOrder {
            resultObserver.onChanged(listResult[0])
            validationsObserver.onChanged(slotValidations.captured)
        }

        println(listResult)

        Assert.assertTrue(slotValidations.captured.isNotEmpty())
        Assert.assertTrue(listResult.isNotEmpty())
        Assert.assertNull(listResult[0].first)
        Assert.assertNull(listResult[0].second)
        Assert.assertEquals(NumberValidator.NUMBER_INDEX,slotValidations.captured[0].first)
        Assert.assertEquals(R.string.error_number_empty,slotValidations.captured[0].second)
    }

    @Test
    fun test_calculate_with_invalid_input_should_fail() {
        val number = "ada"
        val slotValidations = slot<List<Pair<Int, Int>>>()
        val listResult = mutableListOf<Pair<String?, String?>>()

        every {
            validationsObserver.onChanged(capture(slotValidations))
            resultObserver.onChanged(capture(listResult))
        } just runs

        viewModel.getResult().observeForever(resultObserver)
        viewModel.getListValidation().observeForever(validationsObserver)

        viewModel.calculate(number)

        verifyOrder {
            resultObserver.onChanged(listResult[0])
            validationsObserver.onChanged(slotValidations.captured)
        }

        println(listResult)

        Assert.assertTrue(slotValidations.captured.isNotEmpty())
        Assert.assertTrue(listResult.isNotEmpty())
        Assert.assertNotNull(listResult[0].first)
        Assert.assertNull(listResult[0].second)
        Assert.assertEquals(NumberValidator.NUMBER_INDEX,slotValidations.captured[0].first)
        Assert.assertEquals(R.string.error_number_invalid,slotValidations.captured[0].second)
    }

}