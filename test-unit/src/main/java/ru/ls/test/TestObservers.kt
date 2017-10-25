package ru.ls.test

import io.reactivex.observers.TestObserver
import org.junit.Assert.assertThat

fun <T> TestObserver<T>.getFirstEvent(): T = values()[0]

inline fun <reified T: Any> TestObserver<*>.assertEmittedCount(times: Int) {
    assertThat(values().count { it is T }, Is(times))
}

inline fun <reified T: Any> TestObserver<*>.assertEmittedOnce() {
    assertEmittedCount<T>(times = 1)
}