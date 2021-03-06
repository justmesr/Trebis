package simon.trebis.data.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.support.annotation.NonNull
import org.jetbrains.annotations.NotNull
import java.util.*

@Entity(tableName = "website")
class Website(
        @NotNull
        @ColumnInfo(name = "name")
        var name: String = "",

        @ColumnInfo(name = "favicon", typeAffinity = ColumnInfo.BLOB)
        var favicon: ByteArray? = null,

        @NotNull
        @ColumnInfo(name = "time_created")
        var date: Date = Date(),

        @NotNull
        @ColumnInfo(name = "url")
        var url: String = "",

        @PrimaryKey(autoGenerate = true)
        @NonNull
        @ColumnInfo(name = "website_id")
        var id: Long? = null
)
