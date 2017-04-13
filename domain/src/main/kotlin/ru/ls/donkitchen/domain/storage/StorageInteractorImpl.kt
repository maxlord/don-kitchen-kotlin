package ru.ls.donkitchen.domain.storage

import ru.ls.donkitchen.core.data.repository.StorageRepository
import rx.Single

/**
 *
 *
 * @author Lord (Kuleshov M.V.)
 * @since 02.04.17
 */
class StorageInteractorImpl(private val repository: StorageRepository): StorageInteractor {
    override fun initialize(): Single<Unit> {
        return Single.create { emitter ->
            try {
                repository.initialize()

                emitter.onSuccess(Unit)
            } catch (e: Throwable) {
                emitter.onError(e)
            }
        }
    }
}
