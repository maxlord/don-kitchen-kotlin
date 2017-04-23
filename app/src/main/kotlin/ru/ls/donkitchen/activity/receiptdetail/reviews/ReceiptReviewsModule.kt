package ru.ls.donkitchen.activity.receiptdetail.reviews

import dagger.Module
import dagger.Provides
import ru.ls.donkitchen.annotation.PerScreen
import ru.ls.donkitchen.core.converter.ReceiptConverter
import ru.ls.donkitchen.core.converter.ReviewConverter
import ru.ls.donkitchen.core.converter.ReviewConverterImpl
import ru.ls.donkitchen.core.data.repository.ReceiptRepository
import ru.ls.donkitchen.core.data.repository.ReviewRepository
import ru.ls.donkitchen.data.repository.review.ReviewEntityConverter
import ru.ls.donkitchen.data.repository.review.ReviewEntityConverterImpl
import ru.ls.donkitchen.data.repository.review.ReviewRepositoryImpl
import ru.ls.donkitchen.data.rest.Api
import ru.ls.donkitchen.domain.receipt.ReceiptInteractor
import ru.ls.donkitchen.domain.receipt.ReceiptInteractorImpl
import ru.ls.donkitchen.domain.review.ReviewInteractor
import ru.ls.donkitchen.domain.review.ReviewInteractorImpl

@Module
class ReceiptReviewsModule {
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
