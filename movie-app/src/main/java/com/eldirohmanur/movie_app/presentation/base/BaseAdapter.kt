package com.eldirohmanur.movie_app.presentation.base

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseAdapter<T, VB: ViewBinding>() : RecyclerView.Adapter<BaseAdapter<T, VB>.BaseViewHolder>() {
        private val listItem = ArrayList<T>()
    inner class BaseViewHolder(val binding: VB) : RecyclerView.ViewHolder(binding.root)

    abstract fun getBinding(parent: ViewGroup): VB
    abstract fun onBind(binding: VB, item: T)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val binding = getBinding(parent)
        return BaseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        onBind(holder.binding, listItem[position])
    }

    override fun getItemCount(): Int = listItem.size

    @SuppressLint("NotifyDataSetChanged")
    fun replaceAll(data: List<T>) {
        listItem.clear()
        listItem.addAll(data)
        notifyDataSetChanged()
    }
}