package com.codetron.imscodingtest.feetcalculator

import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import com.codetron.imscodingtest.feetcalculator.databinding.ActivityFeetCalculatorBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class FeetCalculatorActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFeetCalculatorBinding

    private val viewModel: FeetCalculatorViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFeetCalculatorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.getResult().observe(this) { pair ->
            if (pair.first == null) return@observe
            binding.printResult(pair.first!!, pair.second)
        }

        viewModel.getListValidation().observe(this) { list ->
            clearError()
            list.forEach { pair ->
                when (pair.first) {
                    NumberValidator.NUMBER_INDEX -> {
                        setError(pair.second)
                    }
                }
            }
        }
    }

    private fun clearError() {
        binding.textError.isVisible = false
    }

    private fun setError(@StringRes textRes: Int) {
        binding.textError.isVisible = true
        binding.textError.text = getString(textRes)
    }

    private fun setupView() = with(binding) {
        inputFeet.requestFocus()

        inputFeet.doOnTextChanged { _, _, _, _ ->
            clearError()
        }

        buttonSubmit.setOnClickListener {
            val number = inputFeet.text?.toString()
            viewModel.calculate(number)
        }

        buttonBack.setOnClickListener {
            finish()
        }
    }

    private fun ActivityFeetCalculatorBinding.printResult(number: String, result: String?) {
        textResult.isVisible = result != null
        result ?: return
        textResult.text = getString(R.string.result_format, number, result)
    }

}