@file:Suppress("NOTHING_TO_INLINE")

package ru.ls.test

import org.hamcrest.CoreMatchers
import org.mockito.Mockito
import org.mockito.verification.VerificationMode

inline fun <reified T : Any> any(instance: T) =
        Mockito.any(T::class.java) ?: instance

inline fun <reified T : Any> eq(instance: T) =
        Mockito.eq(instance) ?: instance

inline fun <reified T : Any?> eqOpt(instance: T?) =
        Mockito.eq(instance) ?: instance

inline fun <reified T: Any> isA(instance: T) =
        Mockito.isA(T::class.java) ?: instance

fun <T> same(value: T): T = Mockito.same(value) ?: value

@Suppress("HasPlatformType") // unspecified return type is required to allow compiler to infer type in caller code
inline fun <reified T: Any> instanceOf() = CoreMatchers.instanceOf<Any>(T::class.java)

inline fun onlyOnce(): VerificationMode = Mockito.times(1)

fun anyLongOrNull(): Long = Mockito.any() ?: 0L