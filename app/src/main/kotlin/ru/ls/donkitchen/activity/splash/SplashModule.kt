package ru.ls.donkitchen.activity.splash

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.ls.donkitchen.annotation.PerScreen
import ru.ls.donkitchen.core.data.repository.StorageRepository
import ru.ls.donkitchen.data.repository.storage.StorageRepositoryImpl
import ru.ls.donkitchen.domain.storage.StorageInteractor
import ru.ls.donkitchen.domain.storage.StorageInteractorImpl

/**
 *
 *
 * @author Lord (Kuleshov M.V.)
 * @since 02.04.17
 */
@Module
class SplashModule(private val context: Context) {
    @PerScreen
    @Provides
    fun provideStorageInteractor(repository: StorageRepository): StorageInteractor {
        return StorageInteractorImpl(repository)
    }

    @PerScreen
    @Provides
    fun provideStorageRepository(): StorageRepository {
        return StorageRepositoryImpl(context)
    }
}
