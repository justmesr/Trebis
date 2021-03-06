package simon.trebis.data.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import simon.trebis.data.entity.Website

@Dao
interface WebsiteDao {

    @Query("SELECT * FROM website")
    fun getAll(): LiveData<List<Website>>

    @Query("SELECT * FROM website WHERE website_id = :websiteId")
    fun get(websiteId: Long): Website?

    @Update
    fun update(website: Website)

    @Insert
    fun insert(website: Website): Long?

    @Delete
    fun delete(website: Website)

}
