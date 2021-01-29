package com.example.dicodingsubms3guvm.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dicodingsubms3guvm.*
import kotlinx.android.synthetic.main.item_user_fav.view.*

class ListFavoriteUserAdapter(private val activity: Activity) :
    RecyclerView.Adapter<ListFavoriteUserAdapter.ViewHolder>() {


    var iniListFavorite = ArrayList<GithubUser>()

    set(iniListFavorite) {
        if (iniListFavorite.size > 0) {
            this.iniListFavorite.clear()
        }
        this.iniListFavorite.addAll(iniListFavorite)

        notifyDataSetChanged()
    }

    fun addItem(githubUser: GithubUser) {
        this.iniListFavorite.add(githubUser)
        notifyItemInserted(this.iniListFavorite.size - 1)
    }

    fun removeItem(position: Int) {
        this.iniListFavorite.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, this.iniListFavorite.size)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(modelnya: GithubUser) {
            with(itemView) {
                Glide.with(this).load(modelnya.avatar).into(poto)
                txtName.text = modelnya.name
                txtId.text = modelnya.idUser
                itemView.setOnClickListener(
                    CustomOnItemClickListener(
                        adapterPosition,
                        object : CustomOnItemClickListener.OnItemClickCallback {
                            override fun onItemClicked(view: View, position: Int) {
                                val intent = Intent(activity, DetailActivity::class.java)
//                                intent.putExtra(DetailActivity.EXTRA_POSITION, position)
                                intent.putExtra(MainActivity.KEY_GU, modelnya)
//                                activity.startActivityForResult(
//                                    intent,
//                                    DetailActivity.REQUEST_UPDATE
//                                )
                                activity.startActivity(intent)
                            }
                        }
                    )
                )
            }
        }

//        var btn_delete: ImageButton = itemView.findViewById(R.id.btnDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_user_fav, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(iniListFavorite.get(position))

//        holder.btn_delete.setOnClickListener {
////            Toast.makeText(holder.itemView.context, "Button hapus", Toast.LENGTH_SHORT).show()
////            showAlertDialog(this, ALERT_DIALOG_DELETE)
//            onItemClickListener?.invoke(iniListFavorite[position])
//        }
//
//        holder.itemView.setOnClickListener {
//            onItemClickListener?.invoke(iniListFavorite[position])
//        }
    }

    override fun getItemCount(): Int {
        return iniListFavorite.size
    }
}