package com.example.randomcityapp.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.randomcityapp.core.MainViewModel
import com.example.randomcityapp.databinding.FragmentRandomCitiesListBinding
import org.koin.android.viewmodel.ext.android.sharedViewModel

class RandomCitiesListFragment : Fragment() {

    private val viewModel: MainViewModel by sharedViewModel()
    private lateinit var binding: FragmentRandomCitiesListBinding
    private lateinit var adapter: RandomCitiesListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRandomCitiesListBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.controller = this
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = RandomCitiesListAdapter(this, viewModel.randomCities)
        binding.citiesRecycler.adapter = adapter
    }
}