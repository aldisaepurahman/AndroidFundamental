package com.app.myrecyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.app.myrecyclerview.databinding.ItemRowHeroBinding
import com.bumptech.glide.Glide

class ListHeroAdapter(private val listHero: ArrayList<Hero>)
    : RecyclerView.Adapter<ListHeroAdapter.ListViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    interface OnItemClickCallback {
        fun onItemClicked(data: Hero)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    class ListViewHolder(var binding: ItemRowHeroBinding) : RecyclerView.ViewHolder(binding.root)
//        var imgPhoto: ImageView = itemView.findViewById(R.id.img_item_photo)
//        var tvName : TextView = itemView.findViewById(R.id.tv_item_name)
//        var tvDescription: TextView = itemView.findViewById(R.id.tv_item_description)
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemRowHeroBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (name, description, photo) = listHero[position]
        with(holder){
            with(binding){
                tvItemName.text = name
                tvItemDescription.text = description
            }
            Glide.with(itemView.context)
                .load(photo)
                .circleCrop()
                .into(binding.imgItemPhoto)
            itemView.setOnClickListener {
                onItemClickCallback.onItemClicked(listHero[adapterPosition])
            }
        }
    }

    override fun getItemCount(): Int = listHero.size
}