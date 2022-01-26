package com.hakanbey.productorder.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hakanbey.productorder.databinding.SingleProductDetailsImageBinding
import com.hakanbey.productorder.model.Images

// Code with ❤️
//┌──────────────────────────┐
//│ Created by Hakan AKKAYA  │
//│ ──────────────────────── │
//│ contact@hakanakkaya.net  │            
//│ ──────────────────────── │
//│ 24.01.2022               │
//└──────────────────────────┘

class ProductDetailImagesAdapter(
    private val imageList: List<Images>
) : RecyclerView.Adapter<ProductDetailImagesAdapter.MyViewHolder>() {


    class MyViewHolder(val binding: SingleProductDetailsImageBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding: SingleProductDetailsImageBinding = SingleProductDetailsImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem: Images = imageList[position]

        holder.binding.apply {
            Glide.with(root).load(currentItem.normal).into(singleProductDetailsImageView)
        }

    }

    override fun getItemCount(): Int {
        return imageList.count()
    }
}