package com.example.dicodingsubms3guvm

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dicodingsubms3guvm.adapter.ListUserAdapter
import kotlinx.android.synthetic.main.activity_main.*
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.IOException

class MainActivity : AppCompatActivity() {
    private lateinit var adapter : ListUserAdapter
    private lateinit var mainViewModel: MainViewModel

    companion object {
        const val KEY_GU = "keygithubuser"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.setTitle(R.string.bar_mainactivity)

        showImageSearch(true)

        adapter = ListUserAdapter()
        idListLayout.adapter = adapter
        idListLayout.layoutManager = LinearLayoutManager(this@MainActivity)
        idListLayout.addItemDecoration(
            DividerItemDecoration(
                this@MainActivity,
                LinearLayout.VERTICAL
            )
        )

        adapter.onItemClickListener = {
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra(KEY_GU, it)
            startActivity(intent)
        }

        mainViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(
            MainViewModel::class.java
        )

        mainViewModel.getAPISearchUser().observe(this, Observer { searchItems ->
            if (searchItems != null) {

                adapter.setData(searchItems)
                showLoading(false)
            }
        })
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
        }
    }

    private fun showImageSearch(state: Boolean) {
        if (state) {
            imgSearchBack.visibility = View.VISIBLE
        } else {
            imgSearchBack.visibility = View.GONE
        }
    }

    // Menu bar search
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_item, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu?.findItem(R.id.idMenuSearch)?.actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean {
                showImageSearch(false)
                showLoading(true)
                mainViewModel.setAPISearchUser(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.idMenuFavorite -> {
                val intentFavorite = Intent(this, FavoriteActivity::class.java)
                this.startActivity(intentFavorite)
                return true
            }
            R.id.idMenuSetting -> {
                val intentSetting = Intent(this, SettingActivity::class.java)
                this.startActivity(intentSetting)
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }
}