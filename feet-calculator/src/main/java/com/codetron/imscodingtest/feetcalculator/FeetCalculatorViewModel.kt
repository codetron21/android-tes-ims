package com.codetron.imscodingtest.feetcalculator

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.codetron.imscodingtest.resources.util.SingleLiveEvent

class FeetCalculatorViewModel(
    private val validator: NumberValidator,
    private val calculator: FeetCalculator,
) : ViewModel() {

    private val _result = SingleLiveEvent<Pair<String?,String?>>()
    private val _listValidation = SingleLiveEvent<List<Pair<Int, Int>>>()

    fun calculate(number: String?) {
        _result.value = number to null
        _listValidation.value = validator.validate(number)
        val resultValidations = _listValidation.value ?: emptyList()
        if (resultValidations.isNotEmpty()) return
        val resultMeter = calculator.calculate(number!!.toDouble())
        _result.value = number to calculator.format(resultMeter)
    }

    fun getListValidation():LiveData<List<Pair<Int,Int>>> = _listValidation
    fun getResult():LiveData<Pair<String?,String?>> = _result

}