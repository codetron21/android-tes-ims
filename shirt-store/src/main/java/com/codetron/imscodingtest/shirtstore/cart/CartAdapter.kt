package com.codetron.imscodingtest.shirtstore.cart

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.codetron.imscodingtest.shirtstore.data.Product
import com.codetron.imscodingtest.shirtstore.databinding.ItemCartBinding

class CartAdapter : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    private val data = mutableListOf<Product>()
    private var clickListener: ((Int) -> Unit)? = null

    @SuppressLint("NotifyDataSetChanged")
    fun submitData(data: List<Product>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    fun setDeleteOnClickListener(clickListener: ((Int) -> Unit)? = null) {
        this.clickListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        return CartViewHolder(
            ItemCartBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bindData(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class CartViewHolder(
        private val binding: ItemCartBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindData(data: Product) {
            binding.run {
                textName.text = data.name
                imageProduct.load(data.photoUrl) {
                    crossfade(true)
                    placeholder(com.codetron.imscodingtest.resources.R.color.blue_light)
                    error(com.codetron.imscodingtest.resources.R.color.grey)
                }
                buttonDelete.setOnClickListener {
                    clickListener?.invoke(data.id)
                }
            }
        }

    }

}