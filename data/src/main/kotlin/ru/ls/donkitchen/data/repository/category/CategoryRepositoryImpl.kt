package ru.ls.donkitchen.data.repository.category

import com.j256.ormlite.dao.Dao
import io.reactivex.Single
import ru.ls.donkitchen.core.data.entity.CategoryEntity
import ru.ls.donkitchen.core.data.repository.CategoryRepository
import ru.ls.donkitchen.data.db.table.Category
import ru.ls.donkitchen.data.rest.Api
import ru.ls.donkitchen.data.rest.response.CategoryListResult
import ru.ls.donkitchen.data.storage.ormlite.DatabaseHelper
import timber.log.Timber
import java.sql.SQLException

/**
 *
 *
 * @author Lord (Kuleshov M.V.)
 * @since 08.04.17
 */
class CategoryRepositoryImpl(
        private val databaseHelper: DatabaseHelper,
        private val api: Api,
        private val converter: CategoryEntityConverter) : CategoryRepository {

    override fun getCategories(): Single<List<CategoryEntity>> {
        return api.getCategories().map { it.categories ?: listOf() }
                .onErrorResumeNext { Single.just(readCategoryItemsFromDatabase()) }
                .flatMap(this::writeCategoryItemsToDatabase)
                .map {
                    it.map { converter.convert(it) }
                }
    }

    private fun readCategoryItemsFromDatabase(): List<CategoryListResult.CategoryItem> {
        Timber.i("Получаем категории из БД")
        val categoryItems = arrayListOf<CategoryListResult.CategoryItem>()
        try {
            val dao = databaseHelper.getDao<Dao<Category, Int>, Category>(Category::class.java)
            val qb = dao.queryBuilder()
            qb.orderBy(Category.PRIORITY, true)
            val categories = qb.query()

            if (!categories.isEmpty()) {
                // Выполняем перепаковку объектов
                categories.forEach { c ->
                    val item = CategoryListResult.CategoryItem()
                    item.id = c.id
                    item.name = c.name
                    item.imageLink = c.imageLink
                    item.priority = c.priority
                    item.receiptCount = c.receiptCount

                    categoryItems.add(item)
                }
            }
        } catch (e: SQLException) {
            Timber.e(e, "Ошибка при получении категорий из БД")
        }

        return categoryItems
    }

    private fun writeCategoryItemsToDatabase(data: List<CategoryListResult.CategoryItem>): Single<List<CategoryListResult.CategoryItem>> {
        return Single.create<List<CategoryListResult.CategoryItem>> { emitter ->
            Timber.i("Сохраняем категории в БД")
            if (data.isNotEmpty()) {
                try {
                    val dao = databaseHelper.getDao<Dao<Category, Int>, Category>(Category::class.java)

                    data.forEach { ci ->
                        val item = Category()
                        item.id = ci.id
                        item.name = ci.name
                        item.imageLink = ci.imageLink
                        item.priority = ci.priority
                        item.receiptCount = ci.receiptCount

                        dao.createOrUpdate(item)
                    }
                    emitter.onSuccess(data)
                } catch (e: SQLException) {
                    emitter.onError(e)
                }
            }
        }
    }
}