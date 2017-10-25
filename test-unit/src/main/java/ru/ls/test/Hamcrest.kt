package ru.ls.test

import org.hamcrest.Matcher
import org.hamcrest.core.Is

@Suppress("HasPlatformType") // unspecified return type is required to allow compiler to infer type in caller code
fun <T> Is(matcher: Matcher<T>) = Is.`is`(matcher)

@Suppress("HasPlatformType") // unspecified return type is required to allow compiler to infer type in caller code
fun <T> Is(value: T) = Is.`is`(value)