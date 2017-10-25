@file:JvmName("RandomUtils")

package ru.ls.test

import java.util.*

private val random = Random(System.currentTimeMillis())

/**
 * Returns a pseudo-random uniformly distributed int in the half-open range [1, Integer.MAX_VALUE)
 */
fun randomInt(): Int {
    return 1 + random.nextInt(Integer.MAX_VALUE - 1)
}

fun randomIntAsString(): String {
    return randomInt().toString()
}

fun randomIntAsString(maxValue: Int): String {
    return randomInt(maxValue).toString()
}

/**
 * Delegate of [Random.nextInt] (int)}
 */
fun randomInt(maxValue: Int): Int {
    return random.nextInt(maxValue)
}

/**
 * Delegate of [Random.nextDouble] ()}
 */
fun randomDouble(): Double {
    return random.nextDouble()
}

/**
 * Delegate of [Random.nextFloat] ()}
 */
fun randomFloat(): Float {
    return random.nextFloat()
}

/**
 * Delegate of [Random.nextLong] ()}
 */
fun randomLong(): Long {
    return random.nextLong()
}

/**
 * Delegate of [Random.nextBoolean] ()}
 */
fun randomBoolean(): Boolean {
    return random.nextBoolean()
}

fun randomString(): String {
    return "test_" + randomInt()
}

fun randomUrlString(): String {
    return "https://example.com/" + randomString()
}

fun randomStringList(): List<String> {
    val capacity = randomInt(10)
    val list = ArrayList<String>(capacity)
    for (i in 0..capacity - 1) {
        list.add(randomString())
    }
    return list
}

fun randomLongArray(): LongArray {
    val array = LongArray(randomInt(10))
    for (i in array.indices) {
        array[i] = randomInt().toLong()
    }
    return array
}

sealed class Characters(val chars: List<Char>) {
    object Alphabetic : Characters(
            chars = ('a'..'z').toList() + ('A'..'Z').toList()
    )

    object Digits : Characters(
            chars = ('0'..'9').toList()
    )

    object Punctuation : Characters(
            chars = listOf('.', ',', '!', '?', ';', ':', '-', '(', ')', '"').toList()
    )

    object Special : Characters(
            chars = listOf('@', '#', '$', '%', '^', '&', '*', '{', '}', '[', ']', '/', '\\', '|')
    )

    object Alphanumeric : Characters(
            chars = Alphabetic.chars + Digits.chars
    )

    object Text : Characters(
            chars = Alphabetic.chars + Digits.chars + Punctuation.chars
    )

    class SingleChar(char: Char) : Characters(listOf(char))

    object All : Characters(
            chars = Alphabetic.chars + Digits.chars + Punctuation.chars + Special.chars
    )
}

fun randomString(length: Int, symbols: Characters = Characters.Alphanumeric): String {
    val random = Random()
    val builder = StringBuilder()
    repeat(length) {
        val index = random.nextInt(symbols.chars.size)
        builder.append(symbols.chars[index])
    }
    return builder.toString()
}

fun randomIntArray(): IntArray = IntArray(randomInt(10)).map { randomInt() }.toIntArray()

inline fun <reified T: Any> List<T>.randomElement(): T = this[randomInt(this.size)]

inline fun <reified T: Any> Array<T>.randomElement(): T = this[randomInt(this.size)]

fun randomPassword(charLength: Int): String {
    return randomString(charLength, Characters.Alphabetic) + randomInt(10)
}

fun randomEmail(): String {
    return randomString(6, Characters.Alphabetic) + "@" + randomString(4, Characters.Alphabetic) + "." + randomString(2,
            Characters.Alphabetic)
}
