package ru.ls.donkitchen.data.repository.storage

import android.content.Context
import org.flywaydb.core.Flyway
import org.flywaydb.core.api.FlywayException
import org.flywaydb.core.api.android.ContextHolder
import ru.ls.donkitchen.core.data.repository.StorageRepository
import ru.ls.donkitchen.data.storage.ormlite.DatabaseHelper

/**
 *
 *
 * @author Lord (Kuleshov M.V.)
 * @since 02.04.17
 */
class StorageRepositoryImpl(private val context: Context): StorageRepository {
    @Throws(FlywayException::class)
    override fun initialize() {
        // Инициализируем БД
        val db = context.openOrCreateDatabase(DatabaseHelper.DATABASE_NAME, 0, null)
        ContextHolder.setContext(context)
        val flyway = Flyway()
        flyway.setDataSource("jdbc:sqlite:" + db.path, "", "")
        flyway.isBaselineOnMigrate = true //  setInitOnMigrate(true)
        flyway.migrate()
        db.close()
    }
}