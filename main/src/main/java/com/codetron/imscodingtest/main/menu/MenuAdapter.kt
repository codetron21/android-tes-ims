package com.codetron.imscodingtest.main.menu

import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.RecyclerView

class MenuAdapter(
    private val data: List<MenuModel>,
    private val clickListener: (Int) -> Unit
) : RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val button = AppCompatButton(parent.context, null, androidx.appcompat.R.attr.buttonStyle)
        button.height = 200
        return MenuViewHolder(button)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        val context = holder.view.context
        holder.view.text = context.getString(data[position].text)
        holder.view.setOnClickListener {
            clickListener(data[position].id)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class MenuViewHolder(
        val view: AppCompatButton
    ) : RecyclerView.ViewHolder(view)

}