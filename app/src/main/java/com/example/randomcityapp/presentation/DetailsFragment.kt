package com.example.randomcityapp.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.randomcityapp.core.MainViewModel
import com.example.randomcityapp.databinding.FragmentDetailsBinding
import org.koin.android.viewmodel.ext.android.sharedViewModel

class DetailsFragment : Fragment() {

    private val viewModel: MainViewModel by sharedViewModel()
    private var binding: FragmentDetailsBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsBinding.inflate(inflater, container, false).also {
            it.lifecycleOwner = viewLifecycleOwner
            it.controller = this
            it.viewModel = viewModel
        }
        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}