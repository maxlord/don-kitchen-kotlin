package ru.ls.donkitchen.data.repository.category

import com.j256.ormlite.dao.Dao
import ru.ls.donkitchen.core.data.entity.CategoryEntity
import ru.ls.donkitchen.core.data.repository.CategoryRepository
import ru.ls.donkitchen.data.db.table.Category
import ru.ls.donkitchen.data.rest.Api
import ru.ls.donkitchen.data.rest.response.CategoryListResult
import ru.ls.donkitchen.data.storage.ormlite.DatabaseHelper
import rx.Single
import java.sql.SQLException

/**
 *
 *
 * @author Lord (Kuleshov M.V.)
 * @since 08.04.17
 */
class CategoryRepositoryImpl(
        private val databaseHelper: DatabaseHelper,
        private val api: Api): CategoryRepository {

    override fun getCategories(): Single<List<CategoryEntity>> {
        val converter: CategoryEntityConverter = CategoryEntityConverterImpl()
        // сеть
        val network = api.getCategories().map(CategoryListResult::categories)
        // сохранение/обновление в БД из сети
        val networkWithSave = network.doOnSuccess { data ->
//            Timber.i("Сохраняем категории в БД")
            if (data != null && !data.isEmpty()) {
                try {
                    val dao = databaseHelper.getDao<Dao<Category, Int>, Category>(Category::class.java)

                    for (ci in data) {
                        val item = Category()
                        item.id = ci.id
                        item.name = ci.name
                        item.imageLink = ci.imageLink
                        item.priority = ci.priority
                        item.receiptCount = ci.receiptCount

                        dao.createOrUpdate(item)
                    }
                } catch (e: SQLException) {
//                    Timber.e(e, "Ошибка сохранения категорий в БД")
                }
            }
        }
        // БД
        val db = Single.create<List<CategoryListResult.CategoryItem>> { s ->
//            Timber.i("Получаем категории из БД")

            try {
                val dao = databaseHelper.getDao<Dao<Category, Int>, Category>(Category::class.java)
                val qb = dao.queryBuilder()
                qb.orderBy(Category.PRIORITY, true)
                val categories = qb.query()
                val categoryItems = ArrayList<CategoryListResult.CategoryItem>()
                if (!categories.isEmpty()) {
                    // Выполняем перепаковку объектов
                    for (c in categories) {
                        val item = CategoryListResult.CategoryItem()
                        item.id = c.id
                        item.name = c.name
                        item.imageLink = c.imageLink
                        item.priority = c.priority
                        item.receiptCount = c.receiptCount

                        categoryItems.add(item)
                    }
                }

                s.onSuccess(categoryItems)
            } catch (e: SQLException) {
                s.onError(e)
            }
        }

        // Выполняем все запросы
        return Single.concat(db, networkWithSave)
                .map {
                    it?.map { converter.convert(it) } ?: arrayListOf()
                }.toSingle()
    }
}
