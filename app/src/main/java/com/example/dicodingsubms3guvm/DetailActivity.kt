package com.example.dicodingsubms3guvm

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.dicodingsubms3guvm.adapter.PagerAdapter
import com.example.dicodingsubms3guvm.db.DatabaseContract
import com.example.dicodingsubms3guvm.db.GithubUserHelper
import com.example.dicodingsubms3guvm.helper.MappingHelper
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_detail_2.*
import kotlinx.android.synthetic.main.activity_detail_2.idBio
import kotlinx.android.synthetic.main.activity_detail_2.idLoc
import kotlinx.android.synthetic.main.activity_detail_2.idName
import kotlinx.android.synthetic.main.activity_detail_2.idPoto
import kotlinx.android.synthetic.main.activity_detail_2.idTabLayout
import kotlinx.android.synthetic.main.activity_detail_2.idViewPager
import kotlinx.android.synthetic.main.activity_detail_2.progressBar

class DetailActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mainViewModel: MainViewModel
    private var githubUser: GithubUser? = null
    private var position: Int = 0
    private var statusFavorite: Boolean = false
    private var idUser: String? = null
    private var nama: String? = null
    private var ava: String? = null
    private lateinit var githubUserHelper: GithubUserHelper

    companion object {
        const val RESULT_ADD = 101
        const val EXTRA_GU = "extra_githubuser"
        const val EXTRA_POSITION = "extra_position"
        const val REQUEST_ADD = 100
        const val REQUEST_UPDATE = 200
        const val RESULT_UPDATE = 201
        const val RESULT_DELETE = 30
        const val ALERT_DIALOG_CLOSE = 10
        const val ALERT_DIALOG_DELETE = 20
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        githubUserHelper = GithubUserHelper.getInstance(applicationContext)
        githubUserHelper.open()

        val gguser = intent.getParcelableExtra<GithubUser>(MainActivity.KEY_GU)
        idUser = gguser?.idUser
        nama = gguser?.name
        ava = gguser?.avatar
        Glide.with(this@DetailActivity).load(ava).into(idPoto)

        mainViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(
            MainViewModel::class.java
        )

        showLoading(true)
        nama?.let { mainViewModel.setAPIDetailUser(it) }

        mainViewModel.getAPIDetailUser().observe(this, Observer {
            if (it != null) {
                idName.text = mainViewModel.listString.value?.get(0)
                idBio.text = mainViewModel.listString.value?.get(1)
                idLoc.text = mainViewModel.listString.value?.get(2)
                showLoading(false)
            }
        })

        val pagerAdapter = PagerAdapter(this, supportFragmentManager)
        pagerAdapter.usernama = gguser?.name
        idViewPager.adapter = pagerAdapter
        idTabLayout.setupWithViewPager(idViewPager)

        githubUser = GithubUser()

        val cursor = githubUserHelper.queryById(idUser.toString())
        val checkIdUser = MappingHelper.mapCursorToArrayList(cursor)
//        val getIdUser = checkIdUser.map { it -> it.idUser}
//        Log.d("CHECK MAPPING DETAIL : ", checkIdUser.size.toString())
//        Log.d("CHECK MAPPING DETAIL : ", getIdUser.toString())

        when (checkIdUser.size) {
            0 -> statusFavorite = false
            1 -> statusFavorite = true
        }


        // ADD FAVORITE USER
        val fav: FloatingActionButton = findViewById(R.id.floatFavorite)
        setStatusFavorite(statusFavorite)
        fav.setOnClickListener(this)

    }

    override fun onClick(view: View) {
        var cekStatus = statusFavorite
        if (cekStatus == true) {
            val isDialogClose = ALERT_DIALOG_CLOSE
            val dialogTitle: String
            val dialogMessage: String

            dialogMessage = "Apakah anda yakin ingin menghapus item ini?"
            dialogTitle = "Hapus Favorite"

            val alertDialogBuilder = AlertDialog.Builder(this)
            alertDialogBuilder.setTitle(dialogTitle)
            alertDialogBuilder
                .setMessage(dialogMessage)
                .setCancelable(false)
                .setPositiveButton("Ya") { dialog, id ->
//                    githubUserHelper.deleteById(idUser.toString()).toLong()
//                    cekStatus = false
//                    setStatusFavorite(cekStatus)
//                    val intent = getIntent()
//                    setResult(RESULT_DELETE, intent)
//                    Toast.makeText(this, "Data berhasil terhapus", Toast.LENGTH_SHORT).show()
//                    startActivity(intent);

                    val result = githubUserHelper.deleteById(idUser.toString()).toLong()
                    if (result > 0) {
                        cekStatus = false
                        setStatusFavorite(cekStatus)
                        val intent = Intent()
                        intent.putExtra(EXTRA_POSITION, position)
                        setResult(RESULT_DELETE, intent)
//                        Toast.makeText(this, "Data berhasil terhapus", Toast.LENGTH_SHORT).show()
                        finish();
                    } else {
                        Toast.makeText(this, "Gagal Menghapus Data", Toast.LENGTH_SHORT).show()
                    }
                }
                .setNegativeButton("Tidak") { dialog, id -> dialog.cancel() }
            var alertDialog = alertDialogBuilder.create()
            alertDialog.show()

        } else {

            val intent = Intent(this, FavoriteActivity::class.java)
            intent.putExtra(EXTRA_GU, githubUser)


            val values = ContentValues()
            values.put(DatabaseContract.GithubUserColumns.COLUMN_ID_USER, idUser)
            values.put(DatabaseContract.GithubUserColumns.COLUMN_USERNAME, nama)
            values.put(DatabaseContract.GithubUserColumns.COLUMN_AVATAR_URL, ava)

            var result = githubUserHelper.insert(values)

            if (result > 0) {
                githubUser?.id = result.toInt()
                setResult(RESULT_ADD, intent)
                cekStatus = true
                setStatusFavorite(cekStatus)
//                finish()
                startActivity(intent)
//                Toast.makeText(this, "Berhasil menambah data", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Gagal menambah data", Toast.LENGTH_SHORT).show()
//                showAlertDialog(ALERT_DIALOG_DELETE)
            }
        }
    }

    private fun setStatusFavorite(statusFavorite: Boolean) {
        if (statusFavorite) {
            floatFavorite.setImageResource(R.drawable.ic_baseline_favorite_24);
        } else {
            floatFavorite.setImageResource(R.drawable.ic_baseline_favorite_border_24);
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
        }
    }

    private fun showAlertDialog(type: Int) {
        val isDialogClose = type == ALERT_DIALOG_CLOSE
        val dialogTitle: String
        val dialogMessage: String

        dialogMessage = "Apakah anda yakin ingin menghapus item ini?"
        dialogTitle = "Hapus Favorite"

        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle(dialogTitle)
        alertDialogBuilder
            .setMessage(dialogMessage)
            .setCancelable(false)
            .setPositiveButton("Ya") { dialog, id ->
//                val intent = Intent()
//                val gg = intent.getParcelableExtra<GithubUser>(DetailActivity.EXTRA_GU)
//                Toast.makeText(this, "Belum berfungsi " + gg?.name.toString(), Toast.LENGTH_SHORT).show()
                if (isDialogClose) {
                    finish()
                } else {
                    val alrd = githubUserHelper.queryById(id.toString())
                    val iddel = MappingHelper.mapCursorToArrayList(alrd)
                    Log.d("CHECK ALERDIALOG ID : ", iddel.toString())

                    val result = githubUserHelper.deleteById(githubUser?.id.toString()).toLong()

                    if (result > 0) {
                        val intent = Intent()
                        intent.putExtra(EXTRA_POSITION, position)
                        setResult(RESULT_DELETE, intent)
//                        finish()
                    } else {
                        Toast.makeText(this, "Gagal menghapus data", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            .setNegativeButton("Tidak") { dialog, id -> dialog.cancel() }
        var alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }
}