package com.example.randomcityapp.presentation

import android.widget.TextView
import androidx.databinding.BindingAdapter
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
    fun setToolbarCityColor(toolbar: MaterialToolbar, cityColor: String?) {
        cityColor?.let {
            val bgColor = ColorHelper.getCityColor(toolbar.context, it)
            val textColor = ColorHelper.getTextColorOnCityColor(toolbar.context, it)
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
    fun setTextCityColor(textView: TextView, cityColor: String?) {
        cityColor?.let {
            val textColor = ColorHelper.getCityColor(textView.context, cityColor)
            textView.setTextColor(textColor)
        }
    }
}