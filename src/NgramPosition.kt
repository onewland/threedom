package com.oliverco

import java.util.concurrent.atomic.AtomicInteger

class NgramPosition(private var position: Int) {
    private val counter = AtomicInteger(position)

    fun advance() = counter.incrementAndGet()
    fun get() = counter.get()
}