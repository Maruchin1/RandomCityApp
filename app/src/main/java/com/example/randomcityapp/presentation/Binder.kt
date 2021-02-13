package com.example.randomcityapp.presentation

import android.widget.TextView
import androidx.databinding.BindingAdapter
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
}