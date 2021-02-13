package com.example.randomcityapp.presentation.framework

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.example.randomcityapp.BR
import com.example.randomcityapp.core.MainViewModel

fun <T : ViewDataBinding> Fragment.bindView(
    inflater: LayoutInflater,
    layoutResId: Int,
    container: ViewGroup?,
    viewModel: MainViewModel?
): T {
    val binding: T = DataBindingUtil.inflate(inflater, layoutResId, container, false)
    binding.lifecycleOwner = viewLifecycleOwner
    binding.setVariable(BR.controller, this)
    viewModel?.let { binding.setVariable(BR.viewModel, it) }
    return binding
}