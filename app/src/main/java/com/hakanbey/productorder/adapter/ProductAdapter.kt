package com.hakanbey.productorder.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.BaseAdapter
import com.hakanbey.productorder.R
import com.hakanbey.productorder.databinding.SingleProductBinding
import com.hakanbey.productorder.extension.loadUri
import com.hakanbey.productorder.extension.toMoneyFormat
import com.hakanbey.productorder.model.ProductBilgiler
import com.hakanbey.productorder.util.Util
import com.hakanbey.productorder.view.ProductDetailsActivity
import java.util.*
import kotlin.collections.ArrayList
import kotlin.streams.toList

// Code with ❤️
//┌──────────────────────────┐
//│ Created by Hakan AKKAYA  │
//│ ──────────────────────── │
//│ contact@hakanakkaya.net  │            
//│ ──────────────────────── │
//│ 22.01.2022               │
//└──────────────────────────┘

class ProductAdapter(private val context: Context) : BaseAdapter() {
    //  Product List
    private var productList: List<ProductBilgiler> = Util.PRODUCTS.products!![0].bilgiler.toList()
    private var productListBackup: List<ProductBilgiler> = ArrayList(productList)
    private var searchingList: ArrayList<ProductBilgiler> = ArrayList(productList)
    var priceOrder: Int = 0

    fun searchProduct(search: String) {
        searchingList.clear()
        for (item in productListBackup) {
            if (item.productName.lowercase().contains(search.lowercase())) {
                searchingList.add(item)
            }
        }
        notifyDataSetChanged()
    }

    fun orderByPrice() {
        priceOrder++
        when (priceOrder % 3) {
            0 -> {
                searchingList.clear()
                searchingList.addAll(productListBackup)
                notifyDataSetChanged()
            }
            1 -> {
                searchingList = searchingList.stream().sorted { o1, o2 -> o2.price.toFloat().compareTo(o1.price.toFloat()) }.toList() as ArrayList<ProductBilgiler>
                notifyDataSetChanged()
            }
            2 -> {
                searchingList = searchingList.stream().sorted { o1, o2 -> o1.price.toFloat().compareTo(o2.price.toFloat()) }.toList() as ArrayList<ProductBilgiler>
                notifyDataSetChanged()
            }
            else -> {
                searchingList.clear()
                searchingList.addAll(productListBackup)
                notifyDataSetChanged()
            }
        }
    }

    override fun getCount(): Int {
        return searchingList.count()
    }

    override fun getItem(position: Int): Any {
        return searchingList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        //  Binding
        val binding: SingleProductBinding = if (convertView == null)
            SingleProductBinding.inflate(LayoutInflater.from(context), parent, false)
        else
            SingleProductBinding.inflate(LayoutInflater.from(context), parent, false)

        //  Current Product Item
        val product: ProductBilgiler = searchingList[position]

        //  Product Id
        binding.singleProductLblProductId.text = product.productId

        //  If Have Product Image
        if (product.image)
            binding.singleProductIv.loadUri(product.images[0].thumb)

        //  Product Name
        binding.singleProductLblTitle.text = product.productName

        //  Product Brief
        binding.singleProductLblDescription.text = product.brief

        //  Product Price
        val price: String = "${product.price.toMoneyFormat()} ₺"
        binding.singleProductLblPrice.text = price

        //  Satıra Tıklandığında
        binding.root.setOnClickListener {
            Util.PRODUCT_DETAILS = product
            val intent: Intent = Intent(context, ProductDetailsActivity::class.java)
            context.startActivity(intent)
            (context as Activity).overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
        }

        //  Row Animation
        val animation: Animation = AnimationUtils.loadAnimation(context, R.anim.listview_slide_left)
        binding.root.startAnimation(animation)

        return binding.root
    }
}