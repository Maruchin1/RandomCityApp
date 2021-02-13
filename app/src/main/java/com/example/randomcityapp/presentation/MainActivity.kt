package com.example.randomcityapp.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.example.randomcityapp.R
import com.example.randomcityapp.core.MainViewModel
import com.example.randomcityapp.core.RandomCity
import com.example.randomcityapp.databinding.ActivityMainBinding
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModel()
    private lateinit var binding: ActivityMainBinding
    private var currentPage = CurrentPage.LIST

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this

        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add<ListFragment>(R.id.mainFragmentContainer)
        }
        observeDetailsCity()
    }

    private fun observeDetailsCity() {
        viewModel.detailsCity.observe(this) {
            if (needChangeToList(it)) {
                changeToList()
            } else if (needChangeToDetails(it)) {
                changeToDetails()
            }
        }
    }

    private fun needChangeToList(detailsCity: RandomCity?): Boolean {
        return detailsCity == null && currentPage == CurrentPage.DETAILS
    }

    private fun needChangeToDetails(detailsCity: RandomCity?): Boolean {
        return detailsCity != null && currentPage == CurrentPage.LIST
    }

    private fun changeToList() {
        currentPage = CurrentPage.LIST
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace<ListFragment>(R.id.mainFragmentContainer)
        }
    }

    private fun changeToDetails() {
        currentPage = CurrentPage.DETAILS
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace<DetailsFragment>(R.id.mainFragmentContainer)
        }
    }

    override fun onBackPressed() {
        if (currentPage == CurrentPage.LIST) {
            super.onBackPressed()
        } else {
            changeToList()
        }
    }

    private enum class CurrentPage {
        LIST, DETAILS
    }
}