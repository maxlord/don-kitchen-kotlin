package ru.ls.donkitchen.fragment.base

import dagger.Subcomponent
import ru.ls.donkitchen.activity.categorylist.CategoryListFragment
import ru.ls.donkitchen.activity.receiptdetail.ReceiptDetailFragment
import ru.ls.donkitchen.activity.receiptdetail.ReceiptDetailInfoFragment
import ru.ls.donkitchen.activity.receiptdetail.ReceiptDetailReviewsFragment
import ru.ls.donkitchen.activity.receiptlist.ReceiptListFragment
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
    fun inject(fragment: CategoryListFragment)
    fun inject(fragment: ReceiptListFragment)
    fun inject(fragment: ReceiptDetailFragment)
    fun inject(fragment: ReceiptDetailInfoFragment)
    fun inject(fragment: ReceiptDetailReviewsFragment)
}
