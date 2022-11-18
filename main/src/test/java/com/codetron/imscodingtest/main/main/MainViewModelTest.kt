package com.codetron.imscodingtest.main.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.codetron.imscodingtest.main.model.MainViewState
import com.codetron.imscodingtest.main.model.NameValidator
import io.mockk.*
import io.mockk.impl.annotations.MockK
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MainViewModelTest {

    @get:Rule
    val executor = InstantTaskExecutorRule()

    @MockK
    private lateinit var validator: NameValidator

    @MockK
    private lateinit var validationsObserver: Observer<List<Pair<Int, Int>>>

    @MockK
    private lateinit var stateObserver: Observer<MainViewState<String>>

    private lateinit var viewModel: MainViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        viewModel = MainViewModel(validator)
    }

    @Test
    fun test_name_valid() {
        val name = "ada"
        val emptyList = emptyList<Pair<Int, Int>>()
        val nameCapture = slot<String>()
        val stateCapture = slot<MainViewState<String>>()
        val validationCapture = slot<List<Pair<Int, Int>>>()

        every { validator.validate(capture(nameCapture)) } returns emptyList

        every {
            stateObserver.onChanged(capture(stateCapture))
            validationsObserver.onChanged(capture(validationCapture))
        } just runs

        viewModel.getListValidations().observeForever(validationsObserver)
        viewModel.getViewState().observeForever(stateObserver)

        viewModel.submitName(name)

        verifyOrder {
            stateObserver.onChanged(MainViewState.Loading)
            validator.validate(name)
            validationsObserver.onChanged(emptyList)
            stateObserver.onChanged(MainViewState.Success(name))
        }

        Assert.assertEquals(nameCapture.captured, name)
        Assert.assertTrue(stateCapture.captured is MainViewState.Success)
        Assert.assertTrue(validationCapture.captured.isEmpty())

    }

    @Test
    fun test_name_empty_invalid() {
        val name = ""
        val validationList = listOf(NameValidator.NAME_INDEX to 1)
        val nameCapture = slot<String>()
        val stateCapture = slot<MainViewState<String>>()
        val validationCapture = slot<List<Pair<Int, Int>>>()

        every { validator.validate(capture(nameCapture)) } returns validationList

        every {
            stateObserver.onChanged(capture(stateCapture))
            validationsObserver.onChanged(capture(validationCapture))
        } just runs

        viewModel.getListValidations().observeForever(validationsObserver)
        viewModel.getViewState().observeForever(stateObserver)

        viewModel.submitName(name)

        verifyOrder {
            stateObserver.onChanged(MainViewState.Loading)
            validator.validate(name)
            validationsObserver.onChanged(validationList)
            stateObserver.onChanged(capture(stateCapture))
        }

        Assert.assertNotNull(nameCapture.captured)
        Assert.assertTrue(stateCapture.captured is MainViewState.Error)
        Assert.assertTrue(validationCapture.captured.isNotEmpty())
        Assert.assertTrue((stateCapture.captured as MainViewState.Error).data is IllegalStateException)
        Assert.assertEquals(validationList[0].first, validationCapture.captured[0].first)
        Assert.assertEquals(validationList[0].second, validationCapture.captured[0].second)
    }

    @Test
    fun test_name_null_invalid() {
        val name: String? = null
        val validationList = listOf(NameValidator.NAME_INDEX to 1)
        val stateCapture = slot<MainViewState<String>>()
        val validationCapture = slot<List<Pair<Int, Int>>>()

        every { validator.validate(name) } returns validationList

        every {
            stateObserver.onChanged(capture(stateCapture))
            validationsObserver.onChanged(capture(validationCapture))
        } just runs

        viewModel.getListValidations().observeForever(validationsObserver)
        viewModel.getViewState().observeForever(stateObserver)

        viewModel.submitName(name)

        verifyOrder {
            stateObserver.onChanged(MainViewState.Loading)
            validator.validate(name)
            validationsObserver.onChanged(validationList)
            stateObserver.onChanged(capture(stateCapture))
        }

        Assert.assertTrue(stateCapture.captured is MainViewState.Error)
        Assert.assertTrue(validationCapture.captured.isNotEmpty())
        Assert.assertTrue((stateCapture.captured as MainViewState.Error).data is IllegalStateException)
        Assert.assertEquals(validationList[0].first, validationCapture.captured[0].first)
        Assert.assertEquals(validationList[0].second, validationCapture.captured[0].second)
    }

}