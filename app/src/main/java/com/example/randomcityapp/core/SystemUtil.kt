package com.example.randomcityapp.core

import java.time.LocalDateTime
import java.util.*

class SystemUtil {

    fun getCurrentDateTime(): LocalDateTime {
        return LocalDateTime.now()
    }

    fun getRandomInt(bound: Int): Int {
        val random = Random()
        return random.nextInt(bound)
    }
}