package com.codetron.imscodingtest.feetcalculator

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.text.InputFilter
import android.text.InputType
import android.util.AttributeSet
import android.util.TypedValue
import android.view.inputmethod.EditorInfo
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.codetron.imscodingtest.resources.util.dp

class FeetInputView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : AppCompatEditText(context, attrs) {

    private val MAX_LENGTH = 10
    private val MAX_CHANGE_LENGTH = 5
    private val UNIT_FEET = "Ft"

    private val feetTextSize = 14F
    private val largeTextSize = 90F
    private val smallTextSize = largeTextSize / 2F
    private val greyTextColor by lazy {
        ContextCompat.getColor(
            context,
            com.codetron.imscodingtest.resources.R.color.grey
        )
    }
    private val normalTextColor by lazy {
        ContextCompat.getColor(
            context,
            com.codetron.imscodingtest.resources.R.color.black
        )
    }
    private val errorTextColor by lazy {
        ContextCompat.getColor(
            context,
            com.codetron.imscodingtest.resources.R.color.secondary
        )
    }

    private val textFeetPaint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            this.textSize = feetTextSize.dp()
            this.style = Paint.Style.FILL
            this.isFakeBoldText = true
            this.color = normalTextColor
            this.textAlign = Paint.Align.LEFT
        }
    }

    private val textPlaceholderPaint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            this.textSize = largeTextSize.dp()
            this.textAlign = Paint.Align.CENTER
            this.color = greyTextColor
            this.style = Paint.Style.FILL
        }
    }

    private val textBounds by lazy { Rect() }

    init {
        initView()
    }

    override fun onDraw(canvas: Canvas?) {
        val posX = if (length() < MAX_CHANGE_LENGTH) {
            measuredWidth
        } else {
            (measuredWidth - textBounds.width())
        }.toFloat()

        val textSize = if (length() < MAX_CHANGE_LENGTH) {
            largeTextSize
        } else {
            smallTextSize
        }

        val textColor = if (length() < MAX_LENGTH) {
            normalTextColor
        } else {
            errorTextColor
        }

        updateText(textSize)
        setTextColor(textColor)

        textFeetPaint.getTextBounds(UNIT_FEET, 0, UNIT_FEET.length, textBounds)

        canvas?.drawText(
            UNIT_FEET,
            0,
            UNIT_FEET.length,
            posX,
            0F,
            textFeetPaint.apply { this.color = textColor }
        )

        if (text.isNullOrEmpty()) {
            canvas?.drawText(
                "0",
                0,
                1,
                measuredWidth / 2F,
                measuredHeight * 0.75f,
                textPlaceholderPaint
            )
        }
        super.onDraw(canvas)
    }

    private fun initView() {
        background = null
        maxLines = 1
        imeOptions = EditorInfo.IME_ACTION_DONE
        textAlignment = TEXT_ALIGNMENT_CENTER
        clipToOutline = false
        minWidth = largeTextSize.toInt()
        filters = arrayOf<InputFilter>(InputFilter.LengthFilter(MAX_LENGTH))
        setRawInputType(InputType.TYPE_CLASS_NUMBER)
        updateText(largeTextSize)
    }

    private fun updateText(textSize: Float) {
        setTextSize(TypedValue.COMPLEX_UNIT_DIP, textSize)
    }
}