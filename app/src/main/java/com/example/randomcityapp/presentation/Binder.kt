package com.example.randomcityapp.presentation

import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.example.randomcityapp.R
import com.google.android.material.appbar.MaterialToolbar
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object Binder {
    private val dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

    @BindingAdapter("android:text")
    @JvmStatic
    fun setDateTime(textView: TextView, dateTime: LocalDateTime?) {
        dateTime?.let {
            val text = dateTime.format(dateTimeFormatter)
            textView.text = text
        }
    }

    @BindingAdapter("app:toolbarCityColor")
    @JvmStatic
    fun setToolbarCityColor(toolbar: MaterialToolbar, color: String?) {
        val colorId = getCityColorId(color)
        val textColorId = when (color) {
            "Yellow", "White" -> R.color.black
            else -> R.color.white
        }
        toolbar.setBackgroundColor(ContextCompat.getColor(toolbar.context, colorId))
        toolbar.setTitleTextColor(ContextCompat.getColor(toolbar.context, textColorId))
    }

    @BindingAdapter("app:textCityColor")
    @JvmStatic
    fun setTextCityColor(textView: TextView, color: String?) {
        val textColorId = getCityColorId(color)
        textView.setTextColor(ContextCompat.getColor(textView.context, textColorId))
    }

    private fun getCityColorId(color: String?): Int {
        return when (color) {
            "Yellow" -> R.color.yellow
            "Green" -> R.color.green
            "Blue" -> R.color.blue
            "Red" -> R.color.red
            "Black" -> R.color.black
            "White" -> R.color.white
            else -> throw IllegalStateException("Invalid color: $color")
        }
    }
}