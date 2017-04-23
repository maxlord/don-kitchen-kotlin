package ru.ls.donkitchen.fragment.base

import dagger.Subcomponent
import ru.ls.donkitchen.activity.categorylist.CategoryListFragment
import ru.ls.donkitchen.activity.categorylist.CategoryListModule
import ru.ls.donkitchen.activity.categorylist.CategoryListSubComponent
import ru.ls.donkitchen.activity.receiptdetail.ReceiptDetailFragment
import ru.ls.donkitchen.activity.receiptdetail.info.ReceiptDetailInfoFragment
import ru.ls.donkitchen.activity.receiptdetail.reviews.ReceiptDetailReviewsFragment
import ru.ls.donkitchen.activity.receiptlist.ReceiptListFragment
import ru.ls.donkitchen.activity.receiptlist.ReceiptListModule
import ru.ls.donkitchen.activity.receiptlist.ReceiptListSubComponent
import ru.ls.donkitchen.annotation.PerFragment


/**
 *
 *
 * @author Lord (Kuleshov M.V.)
 * @since 11.01.16
 */
@PerFragment
@Subcomponent(modules = arrayOf(FragmentModule::class))
interface FragmentSubComponent {
    operator fun plus(module: CategoryListModule): CategoryListSubComponent
    operator fun plus(module: ReceiptListModule): ReceiptListSubComponent

//    fun inject(fragment: CategoryListFragment)
//    fun inject(fragment: ReceiptListFragment)
    fun inject(fragment: ReceiptDetailFragment)
    fun inject(fragment: ReceiptDetailInfoFragment)
    fun inject(fragment: ReceiptDetailReviewsFragment)
}
