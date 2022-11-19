package com.codetron.imscodingtest

import android.app.Application
import com.codetron.imscodingtest.feetcalculator.FeetCalculatorModule
import com.codetron.imscodingtest.main.di.MainModule
import com.codetron.imscodingtest.shirtstore.di.StoreModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class IMSApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@IMSApplication)
            modules(
                AppModules.getModules()
                        + MainModule.getModules()
                        + FeetCalculatorModule.getModules()
                        + StoreModule.getModules()
            )
        }
    }
}