package com.codetron.imscodingtest.splash

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SplashViewModelTest {

    @get:Rule
    val executor = InstantTaskExecutorRule()

    @MockK
    private lateinit var navigateObserver: Observer<Boolean>

    private lateinit var viewModel: SplashViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        viewModel = SplashViewModel()
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun tierDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun splash_test() = runTest {
        val values = slot<Boolean>()

        every {
            navigateObserver.onChanged(capture(values))
        } just runs

        viewModel.doNavigate().observeForever(navigateObserver)

        val before = values.captured // should false
        viewModel.makeDelay(0L)
        val after = values.captured // should true

        verifyOrder {
            navigateObserver.onChanged(false)
            navigateObserver.onChanged(true)
        }

        println("$before $after")
        Assert.assertTrue(before.not())
        Assert.assertTrue(after)
    }

}