package com.example.randomcityapp.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.example.randomcityapp.R
import com.example.randomcityapp.core.view_models.MainViewModel
import com.example.randomcityapp.databinding.ActivityMainBinding
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModel()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this

        if (isStandardView()) {
            showInitialList()
            observeDetailsCity()
        }
    }

    private fun isStandardView(): Boolean {
        return binding.mainFragmentContainer != null
    }

    private fun showInitialList() {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add<ListFragment>(R.id.mainFragmentContainer)
        }
    }

    private fun observeDetailsCity() {
        viewModel.detailsCity.observe(this) {
            if (it != null) changeToDetails()
        }
    }

    private fun changeToDetails() {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            setCustomAnimations(R.anim.slide_in, R.anim.fade_out, R.anim.fade_in, R.anim.slide_out)
            replace<DetailsFragment>(R.id.mainFragmentContainer)
            addToBackStack(null)
        }
    }
}