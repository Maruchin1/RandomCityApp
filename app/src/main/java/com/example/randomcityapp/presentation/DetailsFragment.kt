package com.example.randomcityapp.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.randomcityapp.R
import com.example.randomcityapp.core.view_models.MainViewModel
import com.example.randomcityapp.databinding.FragmentDetailsBinding
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import org.koin.android.viewmodel.ext.android.sharedViewModel


class DetailsFragment : Fragment(), OnMapReadyCallback {

    private val viewModel: MainViewModel by sharedViewModel()
    private var binding: FragmentDetailsBinding? = null
    private lateinit var googleMap: GoogleMap

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
        setupMap()
        observeCity()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
        viewModel.clearDetailsCity()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap
        viewModel.detailsCityLocation.observe(viewLifecycleOwner, this::updateMap)
    }

    private fun setupMap() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    private fun updateMap(location: LatLng?) {
        googleMap.clear()
        location?.let {
            googleMap.addMarker(MarkerOptions().position(it))
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 10f))
        }
    }

    private fun observeCity() {
        viewModel.detailsCity.observe(viewLifecycleOwner) { city ->
            city?.let { updateStatusBarColor(it.color) }
        }
    }

    private fun updateStatusBarColor(cityColor: String) {
        val statusBarColorId = Binder.getCityColorId(cityColor)
        requireActivity().window?.let {
            val color = ContextCompat.getColor(requireContext(), statusBarColorId)
            it.statusBarColor = color
            it.navigationBarColor = color
            if (cityColor in arrayOf("White", "Yellow")) {
                it.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
            } else {
                it.decorView.systemUiVisibility = 0
            }
        }
    }
}