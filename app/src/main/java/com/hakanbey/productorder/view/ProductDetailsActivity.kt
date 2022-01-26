package com.hakanbey.productorder.view

import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.hakanbey.productorder.R
import com.hakanbey.productorder.databinding.ActivityProductDetailsBinding
import com.hakanbey.productorder.util.Util
import android.graphics.BitmapFactory

import android.graphics.Bitmap
import android.widget.Toast
import androidx.core.view.drawToBitmap
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.hakanbey.productorder.adapter.ProductDetailImagesAdapter
import com.hakanbey.productorder.extension.hide
import com.hakanbey.productorder.extension.show
import com.hakanbey.productorder.extension.toMoneyFormat
import com.hakanbey.productorder.model.OrderProductResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.InputStream
import java.net.URL


class ProductDetailsActivity : AppCompatActivity() {
    //  Binding
    private lateinit var binding: ActivityProductDetailsBinding

    //  Images Adapter
    private lateinit var imagesAdapter: ProductDetailImagesAdapter

    //  Images RecyclerView Position
    private var imagesAdapterPosition: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //  Images Adapter
        imagesAdapter = ProductDetailImagesAdapter(Util.PRODUCT_DETAILS.images)
        binding.productDetailsRecyclerView.adapter = imagesAdapter
        binding.productDetailsRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        binding.productDetailsArIndicator.attachTo(binding.productDetailsRecyclerView, false)
        binding.productDetailsArIndicator.numberOfIndicators = Util.PRODUCT_DETAILS.images.size

        //  Kaydırmalı RecyclerView
        val snapHelper: SnapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(binding.productDetailsRecyclerView)


        //  Listener
        objectListener()

        //  Load Product Details
        loadDetails()
    }

    /**
     * Listener
     */
    private fun objectListener() {
        //  Geri Butonu
        binding.incToolbarProductDetails.toolbarProductDetailsToolBar.setNavigationOnClickListener {
            finish()
            overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right)
        }

        //  Önceki Resim Butonu
        binding.productDetailsCvPrevious.setOnClickListener {
            if (imagesAdapterPosition > 0)
                binding.productDetailsRecyclerView.smoothScrollToPosition(--imagesAdapterPosition)
        }

        //  Sonraki Resim Butonu
        binding.productDetailsCvNext.setOnClickListener {
            if (imagesAdapterPosition <= imagesAdapter.itemCount)
                binding.productDetailsRecyclerView.smoothScrollToPosition(++imagesAdapterPosition)
        }

        //  Fab Buy Button
        binding.productDetailsFabBuy.setOnClickListener {
            val productId: String = Util.PRODUCT_DETAILS.productId
            val customerId = Util.USERResponse.userLogin!![0].bilgiler?.userId
            buyProduct(customerId!!, productId)
        }

        //  RecyclerView Showing Item Position
        binding.productDetailsRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val offset: Int = binding.productDetailsRecyclerView.computeHorizontalScrollOffset()
                val myCellWidth: Int = binding.productDetailsRecyclerView.getChildAt(0).measuredWidth
                if (offset % myCellWidth == 0) {
                    //  Calculate Position
                    imagesAdapterPosition = offset / myCellWidth

                    if (imagesAdapter.itemCount == 1) {
                        binding.productDetailsCvNext.hide()
                        binding.productDetailsCvPrevious.hide()
                    } else if (imagesAdapterPosition == 0) {
                        binding.productDetailsCvPrevious.hide()
                        binding.productDetailsCvNext.show()
                    } else if (imagesAdapterPosition == imagesAdapter.itemCount - 1) {
                        binding.productDetailsCvPrevious.show()
                        binding.productDetailsCvNext.hide()
                    } else {
                        binding.productDetailsCvNext.show()
                        binding.productDetailsCvPrevious.show()
                    }
                }

            }
        })
    }

    /**
     * Load Product Details
     */
    private fun loadDetails() {
        //  Title
        binding.productDetailsLblName.text = Util.PRODUCT_DETAILS.productName

        //  Price
        val price: String = "${Util.PRODUCT_DETAILS.price.toMoneyFormat()} ₺"
        binding.productDetailsBtnPrice.text = price

        //  Description
        binding.productDetailsLblDescription.text = Util.PRODUCT_DETAILS.description

        //  SaleInformation
        if (Util.PRODUCT_DETAILS.saleInformation.saleTypeId == "0")
            binding.productDetailsBtnRent.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.line_green, theme))
        else
            binding.productDetailsBtnSale.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.line_green, theme))

        //
    }

    /**
     * Order Product
     */
    private fun buyProduct(customerId: String, productId: String) {
        Util.getClient().orderProduct(customerId, productId).enqueue(object : Callback<OrderProductResponse> {
            override fun onResponse(call: Call<OrderProductResponse>, response: Response<OrderProductResponse>) {
                if (response.isSuccessful) {
                    if (response.body()!!.order[0].durum) {
                        Toast.makeText(this@ProductDetailsActivity, response.body()!!.order[0].mesaj, Toast.LENGTH_SHORT).show()
                        finish()
                        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right)
                    } else {
                        Toast.makeText(this@ProductDetailsActivity, response.body()!!.order[0].mesaj, Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@ProductDetailsActivity, response.errorBody().toString(), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<OrderProductResponse>, t: Throwable) {
                Toast.makeText(this@ProductDetailsActivity, t.toString(), Toast.LENGTH_SHORT).show()
                t.printStackTrace()
            }

        })
    }
}