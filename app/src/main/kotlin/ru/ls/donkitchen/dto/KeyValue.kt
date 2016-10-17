package ru.ls.donkitchen.dto

/**
 * Описывает пару: ключ - значение
 * Используется в выпадающих списках
 *
 * @author Lord (Kuleshov M.V.)
 * @since 16.10.16
 */
data class KeyValue<T>(val key: T, val value: String) {
    override fun toString(): String {
        return value
    }
}
