package ru.ls.donkitchen.annotation

import javax.inject.Qualifier

import kotlin.annotation.AnnotationRetention.RUNTIME

@Qualifier
@MustBeDocumented
@Retention(RUNTIME)
annotation class CachePrefs
