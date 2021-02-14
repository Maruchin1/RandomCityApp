package com.example.randomcityapp.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.example.randomcityapp.R
import com.example.randomcityapp.core.models.RandomCity
import com.example.randomcityapp.core.view_models.MainViewModel
import com.example.randomcityapp.databinding.FragmentListBinding
import org.koin.android.viewmodel.ext.android.sharedViewModel

class ListFragment : Fragment() {

    private val viewModel: MainViewModel by sharedViewModel()
    private var binding: FragmentListBinding? = null
    private lateinit var adapter: ListAdapter

    fun openDetails(randomCity: RandomCity) {
        viewModel.selectDetailsCity(randomCity)
        if (isStandardDisplayMode()) {
            showDetailsFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListBinding.inflate(inflater, container, false).also {
            it.lifecycleOwner = viewLifecycleOwner
            it.controller = this
            it.viewModel = viewModel
        }
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ColorHelper.setDefaultSystemBars(requireActivity())
        adapter = ListAdapter(this, viewModel)
        binding?.run {
            citiesRecycler.adapter = adapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun isStandardDisplayMode(): Boolean {
        return viewModel.getDisplayMode() == MainViewModel.DisplayMode.STANDARD
    }

    private fun showDetailsFragment() {
        parentFragmentManager.commit {
            setReorderingAllowed(true)
            setCustomAnimations(R.anim.slide_in, R.anim.fade_out, R.anim.fade_in, R.anim.slide_out)
            replace<DetailsFragment>(R.id.mainFragmentContainer)
            addToBackStack(null)
        }
    }
}