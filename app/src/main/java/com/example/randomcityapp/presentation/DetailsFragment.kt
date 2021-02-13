package com.example.randomcityapp.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.randomcityapp.R
import com.example.randomcityapp.core.MainViewModel
import com.example.randomcityapp.databinding.FragmentDetailsBinding
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.sharedViewModel


class DetailsFragment : Fragment(), OnMapReadyCallback {

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun onMapReady(googleMap: GoogleMap) {
        lifecycleScope.launch {
            viewModel.getDetailsCityLocation()?.let { city ->
                googleMap.addMarker(MarkerOptions().position(city))
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(city))
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(city, 10.0f))
            }
        }
    }
}