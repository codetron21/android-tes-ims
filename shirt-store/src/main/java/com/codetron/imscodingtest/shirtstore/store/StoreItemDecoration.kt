package com.codetron.imscodingtest.shirtstore.store

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.codetron.imscodingtest.resources.util.dp

class StoreItemDecoration : ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.top = 4F.dp().toInt()
        outRect.bottom = 4F.dp().toInt()
        outRect.right = 4F.dp().toInt()
        outRect.left = 4F.dp().toInt()
    }

}