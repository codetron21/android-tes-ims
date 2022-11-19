package com.codetron.imscodingtest.router

import android.content.Context
import android.content.Intent
import com.codetron.imscodingtest.abstraction.ActivityRouter
import com.codetron.imscodingtest.feetcalculator.FeetCalculatorActivity
import com.codetron.imscodingtest.main.main.MainActivity
import com.codetron.imscodingtest.main.menu.MenuActivity
import com.codetron.imscodingtest.shirtstore.store.StoreActivity

class ActivityRouterImpl : ActivityRouter {

    override fun navigateToMain(context: Context): Intent {
        return Intent(context, MainActivity::class.java)
    }

    override fun navigateToMenu(context: Context): Intent {
        return Intent(context, MenuActivity::class.java)
    }

    override fun navigateToFeetCalculator(context: Context): Intent {
        return Intent(context, FeetCalculatorActivity::class.java)
    }

    override fun navigateToStore(context: Context): Intent {
        return Intent(context, StoreActivity::class.java)
    }
}