package com.codetron.imscodingtest.main.main

import android.os.Bundle
import android.view.View
import android.view.ViewPropertyAnimator
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import com.codetron.imscodingtest.abstraction.AccountManager
import com.codetron.imscodingtest.abstraction.ActivityRouter
import com.codetron.imscodingtest.main.databinding.ActivityMainBinding
import com.codetron.imscodingtest.main.model.MainViewState
import com.codetron.imscodingtest.main.model.NameValidator
import com.codetron.imscodingtest.resources.R
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel by viewModel<MainViewModel>()
    private val router by inject<ActivityRouter>()
    private val accountManager by inject<AccountManager>()

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        actionListeners()
    }

    override fun onResume() {
        super.onResume()
        animateCircleDecoration()
        animateForm()
        observeViewModel()
    }

    private fun observeViewModel() = with(binding) {
        viewModel.getViewState().observe(this@MainActivity) { state ->
            progressBar.isVisible = state is MainViewState.Loading
            textError.isVisible = state is MainViewState.Error

            if (state is MainViewState.Success) {
                accountManager.setName(state.data)
                startActivity(router.navigateToMenu(this@MainActivity))
                overridePendingTransition(
                    R.anim.anim_slide_right_in,
                    R.anim.anim_slide_left_out
                )
            }
        }

        viewModel.getListValidations().observe(this@MainActivity) {
            clearError()
            it.forEach { pair ->
                if (pair.first == NameValidator.NAME_INDEX) {
                    inputName.error = getString(pair.second)
                    textError.text = getString(pair.second)
                }
            }
        }
    }

    private fun actionListeners() = with(binding) {
        inputName.doOnTextChanged { _, _, _, _ ->
            clearError()
        }

        buttonSubmit.setOnClickListener {
            val name = inputName.text?.toString()
            viewModel.submitName(name)
        }
    }

    private fun clearError() = with(binding) {
        inputName.error = null
        textError.isVisible = false
    }

    private fun animateForm() = with(binding) {
        inputName.alpha = 0F
        textLabelName.alpha = 0F
        textTitle.alpha = 0F
        buttonSubmit.alpha = 0F

        val actionButtonAnimate = Runnable {
            getAnimatorTranslation(buttonSubmit).start()
        }

        val actionInputAnimate = Runnable {
            getAnimatorTranslation(textLabelName).start()
            getAnimatorTranslation(inputName)
                .withEndAction(actionButtonAnimate)
                .start()
        }

        getAnimatorTranslation(textTitle)
            .withEndAction(actionInputAnimate)
            .start()
    }

    private fun getAnimatorTranslation(
        view: View,
        isEnabled: Boolean = true
    ): ViewPropertyAnimator {
        val posTranslate = 200F

        view.isEnabled = isEnabled
        view.alpha = 0F
        view.translationY = posTranslate

        return view.animate()
            .translationYBy(-posTranslate)
            .alpha(1F)
            .setDuration(500L)
            .withEndAction { view.isEnabled = true }
            .setInterpolator(DecelerateInterpolator())
    }

    private fun animateCircleDecoration() = with(binding) {
        animateScaling(viewBlue1)
        animateScaling(viewBlue2)
        animateScaling(viewRed1)
        animateScaling(viewRed2)
    }

    private fun animateScaling(view: View) {
        view.scaleX = 0F
        view.scaleY = 0F
        view.alpha = 0F

        view.animate()
            .scaleX(1F)
            .scaleY(1F)
            .alpha(1F)
            .setDuration(500L)
            .setInterpolator(AccelerateInterpolator())
            .start()
    }

}