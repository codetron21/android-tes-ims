package com.codetron.imscodingtest.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashViewModel : ViewModel() {

    private val _navigate = MutableLiveData(false)

    fun makeDelay(time:Long = 500L) {
        viewModelScope.launch {
           delay(time)
            _navigate.value = true
        }
    }

    fun doNavigate(): LiveData<Boolean> {
        return _navigate
    }

}