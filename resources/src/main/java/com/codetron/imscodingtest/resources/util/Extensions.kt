package com.codetron.imscodingtest.resources.util

import android.content.res.Resources
import android.util.TypedValue

fun Float.dp(): Float {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this,
        Resources.getSystem().displayMetrics
    )
}