package com.codetron.imscodingtest.shirtstore.store

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.codetron.imscodingtest.shirtstore.R
import com.codetron.imscodingtest.shirtstore.data.Category

class CategoriesAdapter : RecyclerView.Adapter<CategoriesAdapter.CategoryViewHolder>() {

    private val data = mutableListOf<Category>()
    private var clickListener: ((Int) -> Unit)? = null

    fun setOnClickListener(clickListener: ((Int) -> Unit)? = null) {
        this.clickListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val textView = inflater.inflate(R.layout.item_category, parent, false) as TextView
        return CategoryViewHolder(textView)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = data[position]
        holder.textView.isSelected = category.isSelected
        holder.textView.text = category.name
        holder.textView.backgroundTintList = ColorStateList.valueOf(
            Color.parseColor(category.colorHex ?: "#000000")
        )
        holder.textView.setOnClickListener {
            clickListener?.invoke(category.id)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(data: List<Category>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    inner class CategoryViewHolder(
        val textView: TextView
    ) : RecyclerView.ViewHolder(textView)

}