package com.cyrillrx.tracker.utils

import java.util.concurrent.TimeUnit
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

/**
 * @author Cyril Leroux
 *         Created on 23/04/2016.
 */

fun waitFor(timeDuration: Long, timeUnit: TimeUnit) {
    try {
        timeUnit.sleep(timeDuration)
    } catch (e: InterruptedException) { // TODO log error
    }
}

/**
 * Throws an [IllegalArgumentException] with the result of calling [lazyMessage]
 * if the [value] is null. Otherwise
 * returns the not null value.
 */
@UseExperimental(ExperimentalContracts::class)
inline fun requireNotBlank(value: String?, lazyMessage: () -> Any): String {
    contract {
        returns() implies (value != null)
    }

    return if (value.isNullOrBlank()) {
        val message = lazyMessage()
        throw IllegalArgumentException(message.toString())
    } else {
        value
    }
}