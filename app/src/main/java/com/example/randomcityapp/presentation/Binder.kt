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
        color?.let {
            val bgColorId = getCityColorId(it)
            val textColorId = when (it) {
                "Yellow", "White" -> R.color.black
                else -> R.color.white
            }
            val bgColor = ContextCompat.getColor(toolbar.context, bgColorId)
            val textColor = ContextCompat.getColor(toolbar.context, textColorId)
            toolbar.setBackgroundColor(bgColor)
            toolbar.setTitleTextColor(textColor)
            toolbar.setNavigationIconTint(textColor)
        }
    }

    @BindingAdapter("app:onNavigationClick")
    @JvmStatic
    fun setOnNavigationClick(toolbar: MaterialToolbar, action: (() -> Unit)?) {
        action?.let {
            toolbar.setNavigationOnClickListener { action() }
        }
    }

    @BindingAdapter("app:textCityColor")
    @JvmStatic
    fun setTextCityColor(textView: TextView, color: String?) {
        color?.let {
            val textColorId = getCityColorId(it)
            val textColor = ContextCompat.getColor(textView.context, textColorId)
            textView.setTextColor(textColor)
        }
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