package ru.ls.test

import com.nhaarman.mockito_kotlin.KArgumentCaptor
import com.nhaarman.mockito_kotlin.argumentCaptor

inline fun <reified T : Any> capture(captureBlock: (KArgumentCaptor<T>) -> Unit): T =
        with(argumentCaptor<T>()) {
            captureBlock(this)
            value
        }

inline fun <reified T : Any> captureAll(captureBlock: (KArgumentCaptor<T>) -> Unit): List<T> =
        with(argumentCaptor<T>()) {
            captureBlock(this)
            allValues
        }