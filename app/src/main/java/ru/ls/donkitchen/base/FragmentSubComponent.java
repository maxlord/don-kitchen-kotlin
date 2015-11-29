package ru.ls.donkitchen.base;

import com.mobsandgeeks.saripaar.Validator;

import dagger.Subcomponent;
import ru.ls.donkitchen.activity.categorylist.CategoryListFragment;
import ru.ls.donkitchen.activity.receiptdetail.ReceiptDetailFragment;
import ru.ls.donkitchen.activity.receiptdetail.ReceiptDetailInfoFragment;
import ru.ls.donkitchen.activity.receiptdetail.ReceiptDetailReviewsFragment;
import ru.ls.donkitchen.activity.receiptlist.ReceiptListFragment;
import ru.ls.donkitchen.activity.splash.SplashFragment;
import ru.ls.donkitchen.annotation.PerFragment;

@PerFragment
@Subcomponent(modules = FragmentModule.class)
public interface FragmentSubComponent {
	Validator provideValidator();

	void inject(SplashFragment fragment);
	void inject(CategoryListFragment fragment);
	void inject(ReceiptListFragment fragment);
	void inject(ReceiptDetailFragment fragment);
	void inject(ReceiptDetailInfoFragment fragment);
	void inject(ReceiptDetailReviewsFragment fragment);
}
