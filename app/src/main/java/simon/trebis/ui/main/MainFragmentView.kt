package simon.trebis.ui.main

import android.annotation.SuppressLint
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.CardView
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import simon.trebis.R
import simon.trebis.data.entity.Website
import simon.trebis.ui.SortType
import simon.trebis.ui.UnscrollableLayoutManager

class MainFragmentView(val view: View) {

    private val sortButton: CardView = view.findViewById(R.id.layout_sort_button)
    private val recyclerView: RecyclerView = view.findViewById(R.id.layout_list)
    private val fab: FloatingActionButton = view.findViewById(R.id.mainFragment_fab)
    private val counter: TextView = view.findViewById(R.id.registered_count)

    var changeSortMethod: () -> Unit = {}
    var createWebsite: () -> Unit = {}
    var goToWebsite: (Website) -> Unit = {}
    var goToEditWebsite: (Website) -> Unit = {}
    var deleteWebsite: (Website) -> Unit = {}

    init {
        setupSortButton()
        setupFab()
        setupLayoutManager()
    }

    private fun setupSortButton() {
        sortButton.setOnClickListener { changeSortMethod() }
    }

    private fun setupFab() {
        fab.setOnClickListener { createWebsite() }
    }

    private fun setupLayoutManager() {
        UnscrollableLayoutManager(view.context, LinearLayoutManager.VERTICAL).let {
            it.setScrollEnabled(false)
            recyclerView.layoutManager = it
        }
    }

    fun setLayouts(layouts: ArrayList<Website>, sortType: SortType, filter: String) {
        val displayed = layouts
                .filter { matchesFilter(filter, it) }
                .sortedWith(sortType.comparator)

        recyclerView.adapter = LayoutAdapter(displayed, view.context).also {
            it.goToWebsite = goToWebsite
            it.goToEditWebsite = goToEditWebsite
            it.deleteWebsite = deleteWebsite
        }

        setCounter(displayed.size)
    }

    private fun matchesFilter(filter: String, it: Website): Boolean {
        return filter.isBlank() ||
                it.name.toLowerCase().contains(filter.toLowerCase()) ||
                it.url.contains(filter)
    }

    @SuppressLint("SetTextI18n")
    private fun setCounter(count: Int) {
        counter.text = "($count)"
    }

}
