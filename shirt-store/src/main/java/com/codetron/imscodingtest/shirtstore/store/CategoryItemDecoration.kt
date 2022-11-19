package com.codetron.imscodingtest.shirtstore.store

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.codetron.imscodingtest.resources.util.dp

class CategoryItemDecoration : ItemDecoration() {

    private val borderPaint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.STROKE
            strokeWidth = 4F.dp()
            strokeCap = Paint.Cap.ROUND
        }
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)

        parent.children.forEach {
            val viewHolder = parent.getChildViewHolder(it) as CategoriesAdapter.CategoryViewHolder
            val textView = viewHolder.textView
            val isSelected = textView.isSelected

            if (isSelected) {
                borderPaint.apply {
                    this.color = ContextCompat.getColor(
                        textView.context,
                        com.codetron.imscodingtest.resources.R.color.blue_light
                    )
                }

                c.drawRoundRect(
                    textView.left.toFloat(),
                    textView.top.toFloat(),
                    textView.right.toFloat(),
                    textView.bottom.toFloat(),
                    50F.dp(),
                    50F.dp(),
                    borderPaint
                )

                return
            }
        }
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.top = 8F.dp().toInt()
        outRect.bottom = 8F.dp().toInt()
        outRect.right = 4F.dp().toInt()
        outRect.left = 4F.dp().toInt()
    }

}