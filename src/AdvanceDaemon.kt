package com.oliverco

import kotlin.concurrent.thread

class AdvanceDaemon(updateDelaySeconds: Int, private val ngramPosition: NgramPosition) {
    private val delay = (updateDelaySeconds * 1000).toLong()

    fun run() : Thread {
        return thread(start = true, isDaemon = true) {
            while(true) {
                Thread.sleep(delay)
                ngramPosition.advance()
            }
        }
    }
}