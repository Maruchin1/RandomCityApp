package com.example.randomcityapp.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import com.example.randomcityapp.R
import com.example.randomcityapp.core.view_models.MainViewModel
import com.example.randomcityapp.databinding.FragmentDetailsBinding
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import org.koin.android.viewmodel.ext.android.sharedViewModel


class DetailsFragment : Fragment() {

    private val viewModel: MainViewModel by sharedViewModel()
    private var binding: FragmentDetailsBinding? = null
    private var googleMap: GoogleMap? = null
    private var onMapReadyCallback: OnMapReadyCallback? = null

    fun close() {
        parentFragmentManager.popBackStack()
    }

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("MyDebug", "Details - onViewCreated")
        setupMapAsync()
        observeCity()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("MyDebug", "Details - onDestroyView")
        binding = null
        googleMap = null
        onMapReadyCallback = null
    }

    private fun setupMapAsync() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync {
            if (isResumed) setupMap(it)
        }
    }

    private fun setupMap(googleMap: GoogleMap) {
        this.googleMap = googleMap
        viewModel.detailsCityLocation.observe(viewLifecycleOwner, this::updateMap)
    }

    private fun updateMap(location: LatLng?) {
        googleMap?.clear()
        location?.let {
            googleMap?.addMarker(MarkerOptions().position(it))
            googleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 10f))
        }
    }

    private fun observeCity() {
        viewModel.detailsCity.observe(viewLifecycleOwner) {
            if (it == null) {
                ColorHelper.setDefaultSystemBars(requireActivity())
            } else {
                ColorHelper.setSystemBarsCityColor(requireActivity(), it.color)
            }
        }
    }
}