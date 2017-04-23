package ru.ls.donkitchen.data.repository.receipt

import com.j256.ormlite.dao.Dao
import io.reactivex.Single
import ru.ls.donkitchen.core.data.entity.ReceiptEntity
import ru.ls.donkitchen.core.data.repository.ReceiptRepository
import ru.ls.donkitchen.data.db.table.Category
import ru.ls.donkitchen.data.db.table.Receipt
import ru.ls.donkitchen.data.rest.Api
import ru.ls.donkitchen.data.rest.request.ReceiptIncrementViews
import ru.ls.donkitchen.data.rest.response.ReceiptListResult
import ru.ls.donkitchen.data.storage.ormlite.DatabaseHelper
import timber.log.Timber
import java.sql.SQLException

class ReceiptRepositoryImpl(
        private val databaseHelper: DatabaseHelper,
        private val api: Api,
        private val converter: ReceiptEntityConverter) : ReceiptRepository {

    override fun getReceipts(categoryId: Int): Single<List<ReceiptEntity>> {
        return api.getReceipts(categoryId).map { it.receipts ?: listOf() }
                .onErrorResumeNext { Single.just(readCategoryItemsFromDatabase(categoryId)) }
                .flatMap(this::writeCategoryItemsToDatabase)
                .map {
                    it?.map { converter.convert(it) } ?: listOf()
                }
    }

    private fun readCategoryItemsFromDatabase(categoryId: Int): List<ReceiptListResult.ReceiptItem>? {
        Timber.i("Получаем рецепты из БД")
        val receiptItems = arrayListOf<ReceiptListResult.ReceiptItem>()
        try {
            val dao = databaseHelper.getDao<Dao<Receipt, Int>, Receipt>(Receipt::class.java)
            val qb = dao.queryBuilder()
            qb.where().eq(Receipt.CATEGORY_ID, categoryId)
            qb.orderBy(Receipt.RATING, false)
            qb.orderBy(Receipt.VIEWS_COUNT, false)
            qb.orderBy(Receipt.NAME, true)
            val receipts = qb.query()
            if (!receipts.isEmpty()) {
                // Выполняем перепаковку объектов
                receipts.forEach { receiptItems.add(converter.convertToReceiptItem(it)) }
            }
        } catch (e: SQLException) {
            Timber.e(e, "Ошибка при получении списка рецептов из БД")
        }

        return receiptItems
    }

    private fun writeCategoryItemsToDatabase(data: List<ReceiptListResult.ReceiptItem>): Single<List<ReceiptListResult.ReceiptItem>> {
        return Single.create<List<ReceiptListResult.ReceiptItem>> { emitter ->
            Timber.i("Сохраняем рецепты в БД")
            if (!data.isEmpty()) {
                try {
                    val categoryDao = databaseHelper.getDao<Dao<Category, Int>, Category>(Category::class.java)
                    val receiptDao = databaseHelper.getDao<Dao<Receipt, Int>, Receipt>(Receipt::class.java)

                    data.forEach { ri ->
                        // Получаем категорию
                        val receiptCategory = categoryDao.queryForId(ri.categoryId)
                        val item = Receipt()
                        item.id = ri.id
                        item.name = ri.name
                        item.ingredients = ri.ingredients
                        item.receipt = ri.receipt
                        item.imageLink = ri.imageLink
                        item.category = receiptCategory
                        item.viewsCount = ri.views
                        item.rating = ri.rating
                        receiptDao.createOrUpdate(item)
                    }
                    emitter.onSuccess(data)
                } catch (e: SQLException) {
                    Timber.e(e, "Ошибка сохранения рецептов в БД")
                    emitter.onError(e)
                }
            }
        }
    }

    override fun getReceiptDetail(receiptId: Int): Single<ReceiptEntity> {
        return Single.create { emitter ->
            try {
                val dao = databaseHelper.getDao<Dao<Receipt, Int>, Receipt>(Receipt::class.java)
                val r = dao.queryForId(receiptId)
                emitter.onSuccess(converter.convert(r))
            } catch (e: SQLException) {
                emitter.onError(e)
            }
        }
    }

    override fun incrementReceiptViews(receiptId: Int): Single<Unit> {
        return api.incrementReceiptViews(receiptId, ReceiptIncrementViews())
    }

}