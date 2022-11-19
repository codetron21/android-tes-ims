package com.codetron.imscodingtest.shirtstore.di

import com.codetron.imscodingtest.shirtstore.data.DataSources
import com.codetron.imscodingtest.shirtstore.data.StoreDataSources
import com.codetron.imscodingtest.shirtstore.store.StoreViewModel
import com.google.gson.Gson
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object StoreModule {

    fun getModules() = listOf(common, data, viewModelModule)

    private val common = module {
        factory { Gson() }
    }

    private val data = module {
        single<DataSources> { StoreDataSources(get(), get()) }
    }

    private val viewModelModule = module {
        viewModel { StoreViewModel(get()) }
    }

}