package ru.ls.donkitchen.ui.categorylist

import org.junit.Before
import org.junit.Rule
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import ru.ls.donkitchen.domain.category.CategoryInteractor
import ru.ls.donkitchen.domain.category.CategoryInteractorImpl

@Suppress("IllegalIdentifier")
class CategoryListPresenterTest {

    @Rule
    @JvmField val rule: MockitoRule = MockitoJUnit.rule()

    private lateinit var interactor: CategoryInteractor

    @Before
    fun setUp() {
        interactor = buildInteractor()
    }

    private fun buildInteractor(): CategoryInteractor = CategoryInteractorImpl() {
        MyAdvertDetailsInteractorImpl(
                api,
                schedulers,
                throwableConverter,
                state
        )
    }

}