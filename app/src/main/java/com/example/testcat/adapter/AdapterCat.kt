package com.example.testcat.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.testcat.data.response.Data
import com.example.testcat.databinding.ItemCatLayoutBinding
import coil.load

class AdapterCat : RecyclerView.Adapter<AdapterCat.MyViewHolder>() {

    inner class MyViewHolder(val itemCatLayoutBinding: ItemCatLayoutBinding) :
        RecyclerView.ViewHolder(itemCatLayoutBinding.root)

    private val diffCallback =
        object : DiffUtil.ItemCallback<Data>() {
            override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
                return oldItem.images == newItem.images
            }

            override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean {
                return oldItem == newItem
            }

        }

    val differ = AsyncListDiffer(this, diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ItemCatLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentList = differ.currentList[position]
        val data = currentList.images

        if (data != null) {

            for (image in data) {
                if (image.link.endsWith(".jpg")) {
                    Log.d("ccc", "link ${image.link}")
                    holder.itemCatLayoutBinding.apply {
                        imgCat.load(image.link) {

                        }
                    }
                }
            }
        }

    }

    override fun getItemCount(): Int = differ.currentList.size

}