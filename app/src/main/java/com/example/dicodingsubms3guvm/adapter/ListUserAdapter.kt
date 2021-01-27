package com.example.dicodingsubms3guvm.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dicodingsubms3guvm.GithubUser
import com.example.dicodingsubms3guvm.R
import kotlinx.android.synthetic.main.item_user.view.*

class ListUserAdapter : RecyclerView.Adapter<ListUserAdapter.ViewHolder>()  {

    private val iniList = ArrayList<GithubUser>()

    var onItemClickListener: ((GithubUser) -> Unit)? = null

    fun setData(items: ArrayList<GithubUser>) {
        iniList.clear()
        iniList.addAll(items)
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bindItems(modelnya: GithubUser) {
            with(itemView) {
                Glide.with(this).load(modelnya.avatar).into(poto)
                txtName.text = modelnya.name
                txtId.text = modelnya.idUser.toString()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(iniList.get(position))

        holder.itemView.setOnClickListener{
            onItemClickListener?.invoke(iniList[position])
        }
    }

    override fun getItemCount(): Int {
        return iniList.size
    }
}