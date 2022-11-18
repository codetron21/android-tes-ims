package com.codetron.imscodingtest.main.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.codetron.imscodingtest.main.model.MainViewState
import com.codetron.imscodingtest.main.model.NameValidator
import com.codetron.imscodingtest.resources.util.SingleLiveEvent

class MainViewModel(
    private val validator: NameValidator
) : ViewModel() {

    private val _viewState =
        SingleLiveEvent<MainViewState<String>>()
    private val _listValidations =
        SingleLiveEvent<List<Pair<Int, Int>>>()

    fun submitName(name: String?) {
        _viewState.value = MainViewState.Loading
        _listValidations.value = validator.validate(name)
        _listValidations.value?.isNotEmpty()?.run {
            if (this) {
                _viewState.value = MainViewState.Error(IllegalStateException())
                return
            }
        }
        _viewState.value = MainViewState.Success(name!!)
    }

    fun getListValidations(): LiveData<List<Pair<Int, Int>>> = _listValidations

    fun getViewState(): LiveData<MainViewState<String>> = _viewState

}