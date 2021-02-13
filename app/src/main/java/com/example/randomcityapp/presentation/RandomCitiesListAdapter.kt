package com.example.randomcityapp.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.randomcityapp.core.RandomCity
import com.example.randomcityapp.databinding.ItemRandomCityBinding

class RandomCitiesListAdapter(
    private val fragment: Fragment,
    private val source: LiveData<List<RandomCity>>
) : RecyclerView.Adapter<RandomCitiesListAdapter.BindingViewHolder>() {

    private val itemsList = mutableListOf<RandomCity>()
    private val diffCallback = CitiesDiffCallback()

    init {
        source.observe(fragment.viewLifecycleOwner) { updateItems(it) }
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemRandomCityBinding.inflate(inflater, parent, false)
        return BindingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BindingViewHolder, position: Int) {
        val item = itemsList[position]
        holder.bind(item, fragment)
    }

    private fun updateItems(newList: List<RandomCity>) {
        diffCallback.setLists(itemsList, newList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        itemsList.clear()
        itemsList.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }

    class BindingViewHolder(
        private val binding: ItemRandomCityBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: RandomCity, controller: Fragment) = binding.run {
            setVariable(BR.item, item)
            setVariable(BR.controller, controller)
            executePendingBindings()
        }
    }

    private class CitiesDiffCallback : DiffUtil.Callback() {

        private var oldList: List<RandomCity> = emptyList()
        private var newList: List<RandomCity> = emptyList()

        fun setLists(oldList: List<RandomCity>, newList: List<RandomCity>) {
            this.oldList = oldList
            this.newList = newList
        }

        override fun getOldListSize(): Int {
            return oldList.size
        }

        override fun getNewListSize(): Int {
            return newList.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldItem = oldList[oldItemPosition]
            val newItem = newList[newItemPosition]
            return oldItem.emissionDateTime == newItem.emissionDateTime
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldItem = oldList[oldItemPosition]
            val newItem = newList[newItemPosition]
            return oldItem == newItem
        }

    }
}