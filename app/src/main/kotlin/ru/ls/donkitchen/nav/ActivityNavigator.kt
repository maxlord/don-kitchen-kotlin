package ru.ls.donkitchen.nav

import android.app.Activity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.View
import org.jetbrains.anko.contentView
import ru.ls.donkitchen.navigate
import ru.ls.donkitchen.ui.Screens
import ru.ls.donkitchen.ui.categorylist.CategoryList
import ru.ls.donkitchen.ui.receiptdetail.ReceiptDetail
import ru.ls.donkitchen.ui.receiptlist.ReceiptList
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.commands.*

class ActivityNavigator(private val activity: Activity): Navigator {
    override fun applyCommand(command: Command) {
        when (command) {
            is Forward -> {
                when (command.screenKey) {
                    Screens.CATEGORIES -> activity.navigate<CategoryList>(false)
                    Screens.RECEIPTS -> activity.navigate<ReceiptList>(false, command.transitionData as Bundle?)
                    Screens.RECEIPT_DETAIL -> activity.navigate<ReceiptDetail>(false, command.transitionData as Bundle?)
                }
            }
            is Back -> {
                activity.finish()
            }
            is Replace -> {
                when (command.screenKey) {
                    Screens.CATEGORIES -> activity.navigate<CategoryList>(true)
                    Screens.RECEIPTS -> activity.navigate<ReceiptList>(true, command.transitionData as Bundle?)
                    Screens.RECEIPT_DETAIL -> activity.navigate<ReceiptDetail>(true, command.transitionData as Bundle?)
                }
            }
            is SystemMessage ->
                if (activity.contentView != null) {
                    Snackbar.make(activity.contentView as View, command.message, Snackbar.LENGTH_SHORT).show()
                }
        }
    }
}