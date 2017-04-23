package ru.ls.donkitchen.fragment.base

import dagger.Subcomponent
import ru.ls.donkitchen.annotation.PerFragment
import ru.ls.donkitchen.ui.categorylist.CategoryListModule
import ru.ls.donkitchen.ui.categorylist.CategoryListSubComponent
import ru.ls.donkitchen.ui.receiptlist.ReceiptListModule
import ru.ls.donkitchen.ui.receiptlist.ReceiptListSubComponent


/**
 *
 *
 * @author Lord (Kuleshov M.V.)
 * @since 11.01.16
 */
@PerFragment
@Subcomponent(modules = arrayOf(FragmentModule::class))
interface FragmentSubComponent {
//    operator fun plus(module: CategoryListModule): CategoryListSubComponent
//    operator fun plus(module: ReceiptListModule): ReceiptListSubComponent

//    fun inject(fragment: CategoryListFragment)
//    fun inject(fragment: ReceiptListFragment)
//    fun inject(fragment: ReceiptDetailFragment)
//    fun inject(fragment: ReceiptDetailInfoFragment)
//    fun inject(fragment: ReceiptDetailReviewsFragment)
}
