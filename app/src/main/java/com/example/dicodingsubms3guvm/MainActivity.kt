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

        // CARA BACA FILE #1
//        val folder: File = File(
//            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
//                .toString() + "/token.txt/"
//        )
////        val cc = folder.readText()
////        val bufferedReader: BufferedReader = File(folder.toString()).bufferedReader()
////        val inputString = bufferedReader.use { it.readText() }
//        Log.d("DATA FILE TOKEN.TXT : ", folder.toString())


        // CARA BACA FILE #2
//        val sdcard = Environment.getExternalStorageDirectory()
//        //Get the text file
//        val file = File(sdcard, "token.txt")
//
//        //Read text from file
//        val text = StringBuilder()
//
//        try {
//            val br = BufferedReader(FileReader(file))
//            var line: String?
//            while (br.readLine().also { line = it } != null) {
//                text.append(line)
//                text.append('\n')
//            }
//            Log.d("DATA FILE TOKEN.TXT : ", text.toString())
//            br.close()
//        } catch (e: IOException) {
//            //You'll need to add proper error handling here
//            e.printStackTrace()
//            Log.d("Exception WOIII: ", e.message.toString())
//        }



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
//            intent.putExtra("iniKeyGithubUser", it)
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