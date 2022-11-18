package com.codetron.imscodingtest.abstraction

import android.content.Context
import android.content.Intent

interface ActivityRouter {

    fun navigateToMain(context: Context): Intent

    fun navigateToMenu(context: Context): Intent

    fun navigateToFeetCalculator(context: Context): Intent

}