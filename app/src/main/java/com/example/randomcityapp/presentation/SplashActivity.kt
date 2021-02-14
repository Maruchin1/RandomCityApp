package com.example.randomcityapp.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.example.randomcityapp.R
import com.example.randomcityapp.core.logic.RandomCityProducer
import com.example.randomcityapp.databinding.ActivitySplashBinding
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class SplashActivity : AppCompatActivity() {

    private val randomCityProducer: RandomCityProducer by inject()
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
        ColorHelper.setDefaultSystemBars(this)
        openMainWhenFirstEmitted()
    }

    private fun openMainWhenFirstEmitted() = lifecycleScope.launch {
        randomCityProducer.waitForFirstEmitted()
        val intent = Intent(this@SplashActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}