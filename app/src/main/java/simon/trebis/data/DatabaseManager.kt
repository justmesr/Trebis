package simon.trebis.data

import android.arch.lifecycle.LiveData
import android.content.Context
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import simon.trebis.data.dao.EntryDao
import simon.trebis.data.dao.WebsiteDao
import simon.trebis.data.dao.WorkDao
import simon.trebis.data.entity.Entry
import simon.trebis.data.entity.Website
import simon.trebis.data.entity.Work
import java.util.*

class DatabaseManager private constructor(context: Context) {

    companion object {

        @Volatile
        private var INSTANCE: DatabaseManager? = null

        internal fun instance(context: Context): DatabaseManager {
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: DatabaseManager(context).also {
                    INSTANCE = it
                }
            }

            return INSTANCE!!
        }

    }

    private val websiteDao: WebsiteDao
    private val entryDao: EntryDao
    private val workDao: WorkDao

    init {
        val database = TrebisDatabase.getDatabase(context)

        websiteDao = database.websiteDao()
        entryDao = database.entryDao()
        workDao = database.workDao()
    }

    fun createWork(websiteId: Long, workId: UUID): Deferred<Long?> {
        return async { workDao.insert(Work(websiteId, workId)) }
    }

    fun getWork(websiteId: Long): Deferred<Work?> {
        return async { workDao.get(websiteId) }
    }

    fun updateWork(work: Work) {
        launch { workDao.update(work) }
    }

    fun deleteWork(work: Work) {
        launch { workDao.delete(work) }
    }

    fun deleteWork(websiteId: Long) {
        launch { workDao.delete(websiteId) }
    }

    fun createEntry(websiteId: Long, snapshot: ByteArray): Deferred<Long?> {
        return async { entryDao.insert(Entry(websiteId, snapshot)) }
    }

    fun getEntries(websiteId: Long): Deferred<LiveData<List<Entry>>> {
        return async { entryDao.getAll(websiteId) }
    }

    fun updateEntry(entry: Entry) {
        launch { entryDao.update(entry) }
    }

    fun deleteEntry(entry: Entry) {
        launch { entryDao.delete(entry) }
    }

    fun createWebsite(website: Website): Deferred<Long?> {
        return async { websiteDao.insert(website) }
    }

    fun getWebsites(): Deferred<LiveData<List<Website>>> {
        return async { websiteDao.getAll() }
    }

    fun getWebsite(websiteId: Long): Deferred<Website?> {
        return async { websiteDao.get(websiteId) }
    }

    fun updateWebsite(website: Website) {
        launch { websiteDao.update(website) }
    }

    fun deleteWebsite(website: Website) {
        launch { websiteDao.delete(website) }
    }

}
