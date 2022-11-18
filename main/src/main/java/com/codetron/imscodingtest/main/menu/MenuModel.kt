package com.codetron.imscodingtest.main.menu

import androidx.annotation.StringRes
import com.codetron.imscodingtest.main.R

data class MenuModel(
    val id: Int,
    @StringRes val text: Int,
) {
    companion object {
        fun getData() = listOf(
            MenuModel(0, R.string.button_feet_calculator),
            MenuModel(1, R.string.button_store)
        )
    }
}