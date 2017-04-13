package ru.ls.donkitchen.domain.storage

import rx.Single

/**
 *
 *
 * @author Lord (Kuleshov M.V.)
 * @since 02.04.17
 */
interface StorageInteractor {
    fun initialize(): Single<Unit>
}
