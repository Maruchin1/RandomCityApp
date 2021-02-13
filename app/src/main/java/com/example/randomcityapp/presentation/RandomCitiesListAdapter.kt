package com.example.randomcityapp.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.randomcityapp.core.RandomCity
import com.example.randomcityapp.databinding.ItemRandomCityBinding
import com.example.randomcityapp.presentation.framework.BindingViewHolder

class RandomCitiesListAdapter(
    private val fragment: Fragment,
    private val source: LiveData<List<RandomCity>>
) : RecyclerView.Adapter<BindingViewHolder>() {

    private val itemsList = mutableListOf<RandomCity>()

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
        itemsList.clear()
        itemsList.addAll(newList)
        notifyDataSetChanged()
    }
}