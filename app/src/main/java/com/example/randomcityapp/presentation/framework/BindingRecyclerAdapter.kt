package com.example.randomcityapp.presentation.framework

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.randomcityapp.BR

open class BindingRecyclerAdapter<T : Any>(
    protected val controller: Any,
    protected val lifecycleOwner: LifecycleOwner,
    private val layoutResId: Int
) : RecyclerView.Adapter<BindingViewHolder>() {

    constructor(fragment: Fragment, layoutResId: Int) : this(
        fragment,
        fragment.viewLifecycleOwner,
        layoutResId
    )

    protected val itemsList = mutableListOf<T>()

    private var diffCallback: CustomDiffCallback<T>? = null

    fun setSource(source: LiveData<List<T>>) {
        source.observe(lifecycleOwner) {
            Log.d("Adapter", it.toString())
            updateItems(it)
        }
    }

    fun setItemComparator(compareBy: (item: T) -> Any) {
        diffCallback = CustomDiffCallback(compareBy)
    }

    open fun updateItems(newList: List<T>?) {
        if (diffCallback == null) {
            itemsList.clear()
            newList?.let { itemsList.addAll(it) }
            notifyDataSetChanged()
        } else {
            diffCallback!!.setLists(oldList = itemsList, newList = newList ?: emptyList())
            val diffResult = DiffUtil.calculateDiff(diffCallback!!)
            itemsList.clear()
            newList?.let { itemsList.addAll(it) }
            diffResult.dispatchUpdatesTo(this)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ViewDataBinding = DataBindingUtil.inflate(inflater, layoutResId, parent, false)
        return BindingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BindingViewHolder, position: Int) {
        val item = itemsList[position]
        holder.bind(item = item, controller = controller)
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }
}

class BindingViewHolder(private val binding: ViewDataBinding) :
    RecyclerView.ViewHolder(binding.root) {
    val view = binding.root

    fun bind(item: Any, controller: Any) {
        binding.apply {
            setVariable(BR.item, item)
            setVariable(BR.controller, controller)
            binding.executePendingBindings()
        }
    }
}

private class CustomDiffCallback<T>(
    private val compareBy: (item: T) -> Any
) : DiffUtil.Callback() {

    private var oldList: List<T> = emptyList()
    private var newList: List<T> = emptyList()

    fun setLists(oldList: List<T>, newList: List<T>) {
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
        return compareBy(oldItem) == compareBy(newItem)
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}