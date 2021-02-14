package com.example.randomcityapp.presentation

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.commit
import com.example.randomcityapp.R
import com.example.randomcityapp.core.view_models.MainViewModel
import com.example.randomcityapp.databinding.ActivityMainBinding
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : FragmentActivity() {

    private val isStandardView: Boolean
        get() = binding.mainFragmentContainer != null

    private val listFragment by lazy { ListFragment() }
    private val detailsFragment by lazy { DetailsFragment() }

    private val viewModel: MainViewModel by viewModel()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this

        if (savedInstanceState == null) {
            handleInitialization()
        } else {
            handleRotation()
        }
        val displayMode = if (isStandardView) {
            MainViewModel.DisplayMode.STANDARD
        } else {
            MainViewModel.DisplayMode.SIDE_BY_SIDE
        }
        viewModel.setDisplayMode(displayMode)
    }

    private fun handleInitialization() {
        if (isStandardView) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add(R.id.mainFragmentContainer, listFragment)
            }
        } else {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add(R.id.listFragmentContainer, listFragment)
                add(R.id.detailsFragmentContainer, detailsFragment)
            }
        }
    }

    private fun handleRotation() {
        val previousDisplayMode = viewModel.getDisplayMode()
        if (isStandardView && previousDisplayMode == MainViewModel.DisplayMode.SIDE_BY_SIDE) {
            handleChangeFromSideBySideToStandard()
        } else if (!isStandardView && previousDisplayMode == MainViewModel.DisplayMode.STANDARD) {
            handleChangeFromStandardToSideBySide()
        }
    }

    private fun handleChangeFromSideBySideToStandard() {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            supportFragmentManager.fragments.forEach { remove(it) }
            add(R.id.mainFragmentContainer, listFragment)
        }
        if (viewModel.isDetailsCitySelected()) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                replace(R.id.mainFragmentContainer, detailsFragment)
                addToBackStack(null)
            }
        }
    }

    private fun handleChangeFromStandardToSideBySide() {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            supportFragmentManager.fragments.forEach { remove(it) }
            add(R.id.listFragmentContainer, listFragment)
            add(R.id.detailsFragmentContainer, detailsFragment)
        }
    }
}