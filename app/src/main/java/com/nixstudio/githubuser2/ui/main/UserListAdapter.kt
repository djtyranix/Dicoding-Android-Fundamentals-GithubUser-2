package com.nixstudio.githubuser2.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.nixstudio.githubuser2.R
import de.hdodenhof.circleimageview.CircleImageView

class UserListAdapter(private val listUser : ArrayList<Unit>, private val context : Context) : RecyclerView.Adapter<UserListAdapter.CardViewViewHolder>() {
    private lateinit var onItemClickCallback : OnItemClickCallback

    interface OnItemClickCallback {
        fun onItemClicked(data: Unit)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    class CardViewViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        var imgPhoto : CircleImageView = itemView.findViewById(R.id.img_user_photo)
        var tvUsername : TextView = itemView.findViewById(R.id.tv_user_name)
    }

    override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
    ): CardViewViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.item_cardview_user, parent, false)
        return CardViewViewHolder(view)
    }

    override fun onBindViewHolder(
            holder: CardViewViewHolder,
            position: Int
    ) {
        val user = listUser[position]

        val userPhotoId = context.resources.getIdentifier("willDoLater", "drawable", context.packageName)

        Glide.with(holder.itemView.context)
                .load(userPhotoId)
                .apply(RequestOptions().override(550, 550))
                .into(holder.imgPhoto)

        holder.tvUsername.text = "username"

        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(listUser[holder.adapterPosition])
        }
    }

    override fun getItemCount(): Int {
        return listUser.size
    }
}