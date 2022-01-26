package com.hakanbey.productorder.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SearchView
import android.widget.Toast
import com.hakanbey.productorder.R
import com.hakanbey.productorder.adapter.ProductAdapter
import com.hakanbey.productorder.databinding.ActivityProductBinding
import com.hakanbey.productorder.extension.hide
import com.hakanbey.productorder.extension.show
import com.hakanbey.productorder.model.ProductBilgiler
import com.hakanbey.productorder.model.ProductResponse
import com.hakanbey.productorder.util.Util
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.streams.toList

class ProductActivity : AppCompatActivity() {
    //  Binding
    private lateinit var binding: ActivityProductBinding

    //  Adapter
    private lateinit var adapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //  ActionBar Title
        val title: String = "Hoş geldin, ${Util.USERResponse.userLogin!![0].bilgiler?.userName}"
        binding.incToolbarProduct.toolbarProductTitle.text = title

        //  Show Loading
        showLoading()

        //  Listener
        objectListener()

        //  Get Products
        getProducts()
    }

    /**
     * Listener
     */
    private fun objectListener() {
        //  Arama Yapıldığında
        binding.incToolbarProduct.toolbarProductSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    if (query.isNotEmpty()) {
                        for (i in query) {
                            if (i == ' ') {
                                Toast.makeText(this@ProductActivity, "Lütfen boşluk kullanmayın.", Toast.LENGTH_SHORT).show()
                                return false
                            }
                        }
                        adapter.searchProduct(query)
                    }
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    if (newText.isNotEmpty()) {
                        for (i in newText) {
                            if (i == ' ') {
                                Toast.makeText(this@ProductActivity, "Lütfen boşluk kullanmayın.", Toast.LENGTH_SHORT).show()
                                return false
                            }
                        }
                        adapter.searchProduct(newText)
                    }
                }
                return true
            }

        })

        //  Arama Kutucuğu Açıldığında
        binding.incToolbarProduct.toolbarProductSearchView.setOnSearchClickListener {
            binding.incToolbarProduct.toolbarProductTitle.hide()
        }

        //  Arama Kutucuğu Kapatıldığında
        binding.incToolbarProduct.toolbarProductSearchView.setOnCloseListener {
            binding.incToolbarProduct.toolbarProductTitle.show()
            adapter.searchProduct("")
            false
        }

        //  Çıkış Yap Tıklandığında
        binding.incToolbarProduct.toolbarProductIbLogout.setOnClickListener {
            logoutShared()
            val intent: Intent = Intent(this@ProductActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
            overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right)
            Toast.makeText(this@ProductActivity, "Çıkış yapıldı.", Toast.LENGTH_SHORT).show()
        }

        //  Fiyat Sütununa Tıklanıldığında
        binding.productLblPrice.setOnClickListener {
            adapter.orderByPrice()
            when (adapter.priceOrder % 3) {
                0 -> {
                    binding.productLblPrice.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_sort_normal, 0)
                }
                1 -> {
                    binding.productLblPrice.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_sort_desc, 0)
                }
                2 -> {
                    binding.productLblPrice.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_sort_asc, 0)
                }
                else -> {
                    binding.productLblPrice.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_sort_normal, 0)
                }
            }
        }


    }

    /**
     * Get Products From API
     */
    private fun getProducts() {
        Util.getClient().getProducts().enqueue(object : Callback<ProductResponse> {
            override fun onResponse(call: Call<ProductResponse>, response: Response<ProductResponse>) {
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data != null) {
                        if (data.products?.get(0)?.durum == true) {
                            Util.PRODUCTS = data
                            loadProductList()
                        } else {
                            Toast.makeText(this@ProductActivity, data.products!![0].mesaj, Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Toast.makeText(this@ProductActivity, response.errorBody().toString(), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ProductResponse>, t: Throwable) {
                Toast.makeText(this@ProductActivity, t.toString(), Toast.LENGTH_SHORT).show()
                t.printStackTrace()
                hideLoading()
            }

        })
    }

    /**
     * Load ListView
     */
    private fun loadProductList() {
        //  Initialize Product Adapter
        adapter = ProductAdapter(this@ProductActivity)
        binding.productListView.adapter = adapter
        hideLoading()
    }

    /**
     * Logout
     */
    private fun logoutShared() {
        val shared = getSharedPreferences("login", Context.MODE_PRIVATE)
        val editor = shared.edit()
        editor.putString("email", "")
        editor.putString("password", "")
        editor.apply()
    }

    /**
     * Show Loading Effect
     */
    private fun showLoading() {
        binding.incToolbarProduct.toolbarProductProgressBar.show()
    }

    /**
     * Hide Loading Effect
     */
    private fun hideLoading() {
        binding.incToolbarProduct.toolbarProductProgressBar.hide()
    }
}