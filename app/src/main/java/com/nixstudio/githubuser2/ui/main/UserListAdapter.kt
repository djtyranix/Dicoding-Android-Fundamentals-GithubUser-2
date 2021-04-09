package com.nixstudio.githubuser2.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.nixstudio.githubuser2.R
import com.nixstudio.githubuser2.model.UsersItem
import de.hdodenhof.circleimageview.CircleImageView

class UserListAdapter() : RecyclerView.Adapter<UserListAdapter.ViewHolder>() {
    private lateinit var onItemClickCallback : OnItemClickCallback
    private val listUser : ArrayList<UsersItem> = ArrayList()

    interface OnItemClickCallback {
        fun onItemClicked(data: UsersItem)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        var imgPhoto : CircleImageView = itemView.findViewById(R.id.img_user_photo)
        var tvUsername : TextView = itemView.findViewById(R.id.tv_user_name)
    }

    fun setData(items: ArrayList<UsersItem>) {
        listUser.clear()
        listUser.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
    ): ViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.item_row_user, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(
            holder: ViewHolder,
            position: Int
    ) {
        val user = listUser[position]

        Glide.with(holder.itemView.context)
                .load(user.avatarUrl)
                .apply(RequestOptions().override(550, 550))
                .into(holder.imgPhoto)

        holder.tvUsername.text = user.login

        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(listUser[holder.absoluteAdapterPosition])
        }
    }

    override fun getItemCount(): Int = listUser.size
}