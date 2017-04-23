package ru.ls.donkitchen.activity.receiptdetail

import dagger.Module
import dagger.Provides
import ru.ls.donkitchen.activity.categorylist.ReceiptViewItemConverter
import ru.ls.donkitchen.activity.categorylist.ReceiptViewItemConverterImpl
import ru.ls.donkitchen.annotation.PerScreen
import ru.ls.donkitchen.core.converter.ReceiptConverter
import ru.ls.donkitchen.core.converter.ReceiptConverterImpl
import ru.ls.donkitchen.core.data.repository.ReceiptRepository
import ru.ls.donkitchen.data.repository.receipt.ReceiptEntityConverter
import ru.ls.donkitchen.data.repository.receipt.ReceiptEntityConverterImpl
import ru.ls.donkitchen.data.repository.receipt.ReceiptRepositoryImpl
import ru.ls.donkitchen.data.rest.Api
import ru.ls.donkitchen.data.storage.ormlite.DatabaseHelper
import ru.ls.donkitchen.domain.receipt.ReceiptInteractor
import ru.ls.donkitchen.domain.receipt.ReceiptInteractorImpl

@Module
class ReceiptDetailModule {
    @PerScreen
    @Provides
    fun provideReceiptInteractor(repository: ReceiptRepository, converter: ReceiptConverter): ReceiptInteractor {
        return ReceiptInteractorImpl(repository, converter)
    }

    @PerScreen
    @Provides
    fun provideReceiptRepository(databaseHelper: DatabaseHelper, api: Api, converter: ReceiptEntityConverter): ReceiptRepository {
        return ReceiptRepositoryImpl(databaseHelper, api, converter)
    }

    @PerScreen
    @Provides
    fun provideReceiptEntityConverter(): ReceiptEntityConverter {
        return ReceiptEntityConverterImpl()
    }

    @PerScreen
    @Provides
    fun provideReceiptConverter(): ReceiptConverter {
        return ReceiptConverterImpl()
    }

    @PerScreen
    @Provides
    fun provideReceiptViewItemConverter(): ReceiptViewItemConverter {
        return ReceiptViewItemConverterImpl()
    }
}
