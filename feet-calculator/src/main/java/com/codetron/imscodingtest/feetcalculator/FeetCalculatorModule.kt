package com.codetron.imscodingtest.feetcalculator

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object FeetCalculatorModule {

    fun getModules() = listOf(common, viewModelModule)

    private val common = module {
        factory { NumberValidator() }
        single { FeetCalculator }
    }

    private val viewModelModule = module {
        viewModel { FeetCalculatorViewModel(get(), get()) }
    }

}