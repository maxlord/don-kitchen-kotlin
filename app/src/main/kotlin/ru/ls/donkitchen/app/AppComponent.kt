package ru.ls.donkitchen.app

import dagger.Component
import ru.ls.donkitchen.activity.base.ActivityModule
import ru.ls.donkitchen.activity.base.ActivitySubComponent
import ru.ls.donkitchen.service.base.ServiceModule
import ru.ls.donkitchen.service.base.ServiceSubComponent
import ru.ls.donkitchen.ui.categorylist.CategoryListModule
import ru.ls.donkitchen.ui.categorylist.CategoryListSubComponent
import ru.ls.donkitchen.ui.receiptdetail.ReceiptDetailModule
import ru.ls.donkitchen.ui.receiptdetail.ReceiptDetailSubComponent
import ru.ls.donkitchen.ui.receiptdetail.review.ReviewSubComponent
import ru.ls.donkitchen.ui.receiptdetail.reviews.ReceiptReviewsModule
import ru.ls.donkitchen.ui.receiptdetail.reviews.ReceiptReviewsSubComponent
import ru.ls.donkitchen.ui.receiptdetail.reviews.ReviewModule
import ru.ls.donkitchen.ui.receiptlist.ReceiptListModule
import ru.ls.donkitchen.ui.receiptlist.ReceiptListSubComponent
import ru.ls.donkitchen.ui.splash.SplashModule
import ru.ls.donkitchen.ui.splash.SplashSubComponent
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class)) interface AppComponent {
    fun inject(application: DonKitchenApplication)

    operator fun plus(module: ActivityModule): ActivitySubComponent
    operator fun plus(module: ServiceModule): ServiceSubComponent
    operator fun plus(module: SplashModule): SplashSubComponent
    operator fun plus(module: CategoryListModule): CategoryListSubComponent
    operator fun plus(module: ReceiptListModule): ReceiptListSubComponent
    operator fun plus(module: ReceiptDetailModule): ReceiptDetailSubComponent
    operator fun plus(module: ReceiptReviewsModule): ReceiptReviewsSubComponent
    operator fun plus(module: ReviewModule): ReviewSubComponent
}
