package com.codetron.imscodingtest.feetcalculator

import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
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
            onBackPressed()
        }
    }

    private fun ActivityFeetCalculatorBinding.printResult(number: String, result: String?) {
        textResult.isVisible = result != null
        result ?: return
        val formatResult = getString(R.string.result_format, number, result)
        val span = SpannableString(formatResult)

        val redColor = ContextCompat.getColor(
            root.context,
            com.codetron.imscodingtest.resources.R.color.secondary
        )

        val blueColor = ContextCompat.getColor(
            root.context,
            com.codetron.imscodingtest.resources.R.color.primary
        )

        val startValueF = 0
        val endValueF = number.length
        span.changeColor(redColor, startValueF, endValueF)
        span.makeBold(startValueF, endValueF)

        val startUnitF = number.length + 1
        val endUnitF = number.length + 1 + 4
        span.changeColor(blueColor, startUnitF, endUnitF)

        val startValueM = formatResult.length - 5 - 1 - result.length
        val endValueM = formatResult.length - 5 - 1
        span.changeColor(redColor, startValueM, endValueM)
        span.makeBold(startValueM, endValueM)

        val startUnitM = formatResult.length - 5
        val endUnitM = formatResult.length
        span.changeColor(blueColor, startUnitM, endUnitM)

        textResult.text = span
    }

    private fun SpannableString.makeBold(start: Int, end: Int) {
        setSpan(
            StyleSpan(Typeface.BOLD),
            start, end,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }

    private fun SpannableString.changeColor(color: Int, start: Int, end: Int) {
        setSpan(
            ForegroundColorSpan(color),
            start,
            end,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(
            com.codetron.imscodingtest.resources.R.anim.anim_slide_left_in,
            com.codetron.imscodingtest.resources.R.anim.anim_slide_right_out
        )
    }

}