package com.example.randomcityapp.presentation

import android.app.Activity
import android.content.Context
import android.view.View
import androidx.core.content.ContextCompat
import com.example.randomcityapp.R

object ColorHelper {

    fun getCityColor(context: Context, cityColor: String?): Int {
        val colorResId = getCityColorResId(cityColor)
        return getColor(context, colorResId)
    }

    fun getTextColorOnCityColor(context: Context, cityColor: String?): Int {
        val colorResId = getTextColorOnCityColorResId(cityColor)
        return getColor(context, colorResId)
    }

    fun setSystemBarsCityColor(activity: Activity, cityColor: String?) {
        val colorResId = getCityColorResId(cityColor)
        val mode = if (isLightCityColor(cityColor)) SystemBarsMode.LIGHT else SystemBarsMode.DARK
        setSystemBarsColor(activity, colorResId, mode)
    }

    fun setSystemBarsColor(activity: Activity, colorResId: Int, mode: SystemBarsMode) {
        activity.window?.run {
            val color = getColor(activity, colorResId)
            statusBarColor = color
            navigationBarColor = color
            decorView.systemUiVisibility = when (mode) {
                SystemBarsMode.LIGHT -> View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
                SystemBarsMode.DARK -> 0
            }
        }
    }

    private fun getCityColorResId(cityColor: String?): Int {
        return when (cityColor) {
            "Yellow" -> R.color.yellow
            "Green" -> R.color.green
            "Blue" -> R.color.blue
            "Red" -> R.color.red
            "Black" -> R.color.black
            "White" -> R.color.white
            else -> throw IllegalStateException("Invalid color: $cityColor")
        }
    }

    private fun getTextColorOnCityColorResId(cityColor: String?): Int {
        return if (isLightCityColor(cityColor)) R.color.black else R.color.white
    }

    private fun isLightCityColor(cityColor: String?): Boolean {
        return cityColor in arrayOf("Yellow", "White")
    }

    private fun getColor(context: Context, colorResId: Int): Int {
        return ContextCompat.getColor(context, colorResId)
    }

    enum class SystemBarsMode {
        LIGHT, DARK
    }
}