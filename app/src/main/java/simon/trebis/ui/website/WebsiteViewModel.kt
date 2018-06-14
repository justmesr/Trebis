package simon.trebis.ui.website

import android.arch.lifecycle.ViewModel
import simon.trebis.data.entity.Entry
import java.util.*

class WebsiteViewModel : ViewModel() {

    var websiteId: Long? = null
    var expanded: Boolean = true
    var selectedDay: Date = Date()
    var entries: List<Entry> = ArrayList()

    fun toggleExpanded() {
        expanded = !expanded
    }


}
