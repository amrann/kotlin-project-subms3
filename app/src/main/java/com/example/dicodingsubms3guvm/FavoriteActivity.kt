package com.example.dicodingsubms3guvm

import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dicodingsubms3guvm.adapter.ListFavoriteUserAdapter
import com.example.dicodingsubms3guvm.db.GithubUserHelper
import com.example.dicodingsubms3guvm.helper.MappingHelper
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_favorite.*
import kotlinx.android.synthetic.main.item_user_fav.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


class FavoriteActivity : AppCompatActivity() {

    private lateinit var listFavoriteAdapter: ListFavoriteUserAdapter
    private lateinit var githubUserHelper: GithubUserHelper
    private var githubUser: GithubUser? = null

    private var position: Int = 0

    companion object {
        private const val EXTRA_STATE = "EXTRA_STATE"
        const val ALERT_DIALOG_CLOSE = 10
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        idListFavoriteLayout.layoutManager = LinearLayoutManager(this)
        idListFavoriteLayout.setHasFixedSize(true)
        listFavoriteAdapter = ListFavoriteUserAdapter(this)
        idListFavoriteLayout.adapter = listFavoriteAdapter

        githubUserHelper = GithubUserHelper.getInstance(applicationContext)
        githubUserHelper.open()


        if (savedInstanceState == null) {
            // proses ambil data
            loadGithubUserAsync()
        } else {
            val list = savedInstanceState.getParcelableArrayList<GithubUser>(EXTRA_STATE)
            if (list != null) {
                listFavoriteAdapter.iniListFavorite = list
            }
        }

//        listFavoriteAdapter.onItemClickListener = { btnDelete ->
//            showAlertDialog(ALERT_DIALOG_DELETE)
//        }
//
//        listFavoriteAdapter.onItemClickListener = {
////            Toast.makeText(this, "Gagal mengupdate data", Toast.LENGTH_SHORT).show()
//            val intent = Intent(this, DetailActivity::class.java)
//            intent.putExtra("iniKey", it)
//            startActivity(intent)
//        }

    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        if (data != null) {
//            when (requestCode) {
//                DetailActivity.REQUEST_ADD -> if (resultCode == DetailActivity.RESULT_ADD) {
//                    val githubUser = data.getParcelableExtra<GithubUser>(MainActivity.KEY_GU)
//                    githubUser?.let { listFavoriteAdapter.addItem(it) }
//                    idListFavoriteLayout.smoothScrollToPosition(listFavoriteAdapter.itemCount - 1)
////                    rv_notes.smoothScrollToPosition(adapter.itemCount - 1)
////                    showSnackbarMessage("Satu item berhasil ditambahkan")
//                }
//                DetailActivity.REQUEST_UPDATE ->
//                    when (resultCode) {
//                        /* Akan dipanggil jika result codenya UPDATE
//                           Semua data di load kembali dari awal */
////                        DetailActivity.RESULT_UPDATE -> {
////                            val githubUser = data.getParcelableExtra<GithubUser>(MainActivity.KEY_GU)
////                            val position = data.getIntExtra(DetailActivity.EXTRA_POSITION, 0)
////                            githubUser?.let { listFavoriteAdapter.updateItem(position, it) }
//////                            rv_notes.smoothScrollToPosition(position)
//////                            showSnackbarMessage("Satu item berhasil diubah")
////                        }
//                        /* Akan dipanggil jika result codenya DELETE
//                           Delete akan menghapus data dari list berdasarkan dari position */
//                        DetailActivity.RESULT_DELETE -> {
//                            val position = data.getIntExtra(DetailActivity.EXTRA_POSITION, 0)
//                            listFavoriteAdapter.removeItem(position)
////                            showSnackbarMessage("Satu item berhasil dihapus")
//                        }
//                    }
//            }
//        }
//    }


    override fun onDestroy() {
        super.onDestroy()
        githubUserHelper.close()
    }

    private fun loadGithubUserAsync() {
        GlobalScope.launch(Dispatchers.Main) {
            progressBarFav.visibility = View.VISIBLE
            val deferredGithubUser = async(Dispatchers.IO) {
                val cursor = githubUserHelper.queryAll()
                MappingHelper.mapCursorToArrayList(cursor)
            }
            progressBarFav.visibility = View.INVISIBLE
            val gu = deferredGithubUser.await()
            Log.d("CHECK MAPPING FAV : ", gu.toString())
            if (gu.size > 0) {
                listFavoriteAdapter.iniListFavorite = gu
            } else {
                listFavoriteAdapter.iniListFavorite = ArrayList()
                showSnackbarMessage("Tidak ada data saat ini")
            }
        }
    }

    private fun showSnackbarMessage(message: String) {
        Snackbar.make(idListFavoriteLayout, message, Snackbar.LENGTH_SHORT).show()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(EXTRA_STATE, listFavoriteAdapter.iniListFavorite)
    }


}