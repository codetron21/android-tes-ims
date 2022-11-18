package com.codetron.imscodingtest.splash

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.codetron.imscodingtest.abstraction.ActivityRouter
import org.koin.android.ext.android.inject

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity(R.layout.activity_splash) {

    private lateinit var viewModel: SplashViewModel

    private val router by inject<ActivityRouter>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)[SplashViewModel::class.java]

        if (savedInstanceState == null) {
            viewModel.makeDelay()
        }

        viewModel.doNavigate().observe(this) { navigate ->
            if (navigate) {
                router.navigateToMain(this@SplashActivity).run {
                    startActivity(this)
                    finish()
                }
            }
        }
    }

}