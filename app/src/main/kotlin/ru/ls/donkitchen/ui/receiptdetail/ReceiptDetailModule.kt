package ru.ls.donkitchen.ui.receiptdetail

import dagger.Module
import dagger.Provides
import ru.ls.donkitchen.annotation.PerScreen
import ru.ls.donkitchen.core.converter.ReceiptConverter
import ru.ls.donkitchen.core.converter.ReceiptConverterImpl
import ru.ls.donkitchen.core.converter.ReviewConverter
import ru.ls.donkitchen.core.converter.ReviewConverterImpl
import ru.ls.donkitchen.core.data.repository.ReceiptRepository
import ru.ls.donkitchen.core.data.repository.ReviewRepository
import ru.ls.donkitchen.data.repository.receipt.ReceiptEntityConverter
import ru.ls.donkitchen.data.repository.receipt.ReceiptEntityConverterImpl
import ru.ls.donkitchen.data.repository.receipt.ReceiptRepositoryImpl
import ru.ls.donkitchen.data.repository.review.ReviewEntityConverter
import ru.ls.donkitchen.data.repository.review.ReviewEntityConverterImpl
import ru.ls.donkitchen.data.repository.review.ReviewRepositoryImpl
import ru.ls.donkitchen.data.rest.Api
import ru.ls.donkitchen.data.storage.ormlite.DatabaseHelper
import ru.ls.donkitchen.domain.receipt.ReceiptInteractor
import ru.ls.donkitchen.domain.receipt.ReceiptInteractorImpl
import ru.ls.donkitchen.domain.review.ReviewInteractor
import ru.ls.donkitchen.domain.review.ReviewInteractorImpl
import ru.ls.donkitchen.ui.categorylist.ReceiptViewItemConverter
import ru.ls.donkitchen.ui.categorylist.ReceiptViewItemConverterImpl
import ru.ls.donkitchen.ui.receiptdetail.reviews.ReviewViewItemConverter
import ru.ls.donkitchen.ui.receiptdetail.reviews.ReviewViewItemConverterImpl

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

    @PerScreen
    @Provides
    fun provideReviewInteractor(repository: ReviewRepository, converter: ReviewConverter): ReviewInteractor {
        return ReviewInteractorImpl(repository, converter)
    }

    @PerScreen
    @Provides
    fun provideReviewRepository(api: Api, converter: ReviewEntityConverter): ReviewRepository {
        return ReviewRepositoryImpl(api, converter)
    }

    @PerScreen
    @Provides
    fun provideReviewEntityConverter(): ReviewEntityConverter {
        return ReviewEntityConverterImpl()
    }

    @PerScreen
    @Provides
    fun provideReviewConverter(): ReviewConverter {
        return ReviewConverterImpl()
    }

    @PerScreen
    @Provides
    fun provideReviewViewItemConverter(): ReviewViewItemConverter {
        return ReviewViewItemConverterImpl()
    }

}