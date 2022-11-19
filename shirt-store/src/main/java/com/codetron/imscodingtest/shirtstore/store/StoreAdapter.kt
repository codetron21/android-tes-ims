package com.codetron.imscodingtest.shirtstore.store

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.codetron.imscodingtest.shirtstore.R
import com.codetron.imscodingtest.shirtstore.data.Product
import com.codetron.imscodingtest.shirtstore.databinding.ItemProductBinding
import com.codetron.imscodingtest.shirtstore.util.formatRupiah

class StoreAdapter : RecyclerView.Adapter<StoreAdapter.StoreViewHolder>() {

    private val data = mutableListOf<Product>()
    private var clickListener: ((Product) -> Unit)? = null

    fun setOnClickListener(clickListener: ((Product) -> Unit)? = null) {
        this.clickListener = clickListener
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitData(data: List<Product>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreViewHolder {
        return StoreViewHolder(ItemProductBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: StoreViewHolder, position: Int) {
        val data = data[position]
        holder.bindData(data)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class StoreViewHolder(
        private val binding: ItemProductBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindData(data: Product) {
            binding.textName.text = data.name

            binding.textPrice.text = data.price?.formatRupiah()

            binding.imageProduct.load(data.photoUrl) {
                crossfade(true)
                placeholder(com.codetron.imscodingtest.resources.R.color.blue_light)
            }

            binding.textCategory.text = data.category?.name
            binding.textCategory.backgroundTintList = ColorStateList.valueOf(
                Color.parseColor(data.category?.colorHex ?: "#000000")
            )

            binding.root.setOnClickListener {
                clickListener?.invoke(data)
            }
        }

    }

}