package com.codetron.imscodingtest.main.di

import com.codetron.imscodingtest.main.main.MainViewModel
import com.codetron.imscodingtest.main.model.NameValidator
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object MainModule {

    fun getModules() = listOf(common, viewModelModule)

    private val common = module {
        factory { NameValidator() }
    }

    private val viewModelModule = module {
        viewModel { MainViewModel(get()) }
    }

}