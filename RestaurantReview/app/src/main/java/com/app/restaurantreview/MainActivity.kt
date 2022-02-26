package com.app.restaurantreview

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.restaurantreview.databinding.ActivityMainBinding
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "MainActivity"
        private const val RESTAURANT_ID = "uewq1zg2zlskfw1e867"
    }

    private lateinit var binding: ActivityMainBinding
    /*jika menggunakan library activity-ktx, deklarasi secara singkat disini*/
    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        /*panggil viewmodel dan livedata
        *
        * jika tidak menggunakan library activity-ktx, instansiasi viewmodel manual
        val mainViewModel = ViewModelProvider(
            this, ViewModelProvider
                .NewInstanceFactory()
        )[MainViewModel::class.java]*/
        mainViewModel.restaurant.observe(this) { restaurant ->
            setRestaurantData(restaurant)
        }
        mainViewModel.listReview.observe(this) { consumerReviews ->
            setReviewData(consumerReviews)
        }
        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }
        mainViewModel.snackBarText.observe(this) {
            it.getContentIfNotHandled()?.let { snackBarText ->
                Snackbar.make(
                    window.decorView.rootView,
                    snackBarText,
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }

        val layoutManager = LinearLayoutManager(this)
        binding.rvReview.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvReview.addItemDecoration(itemDecoration)

        /*jika tidak menggunakan livedata dan viewmodel, fungsi find dilakukan manual di activity
        findRestaurant()*/

        binding.btnSend.setOnClickListener { view ->
            /* jika tidak menggunakan livedata dan viewmodel, post review dilakukan manual
            postReview(binding.edReview.text.toString())*/
            /*post review jika dilakukan di viewmodel*/
            mainViewModel.postReview(binding.edReview.text.toString())
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    private fun setReviewData(customerReviews: List<CustomerReviewsItem>) {
        val listReview = customerReviews.map {
            "${it.review}\n- ${it.name}"
        }

        val adapter = ReviewAdapter(listReview)
        binding.rvReview.adapter = adapter
        binding.edReview.setText("")
    }

    private fun setRestaurantData(restaurant: Restaurant) {
        binding.tvTitle.text = restaurant.name
        binding.tvDescription.text = restaurant.description
        Glide.with(this@MainActivity)
            .load("https://restaurant-api.dicoding.dev/images/large/${restaurant.pictureId}")
            .into(binding.ivPicture)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    /*jika tidak menggunakan livedata dan viewmodel, post data dilakukan disini*/
    /*private fun postReview(review: String) {
        showLoading(true)

        *//*cara post data dengan retrofit*//*
        val client = ApiConfig.getApiService().postReview(RESTAURANT_ID,
            "Dicoding", review)
        client.enqueue(object : Callback<PostReviewResponse> {
            *//*jika fungsi berhasil dipanggil*//*
            override fun onResponse(
                call: Call<PostReviewResponse>,
                response: Response<PostReviewResponse>
            ) {
                showLoading(false)
                *//*ambil data yang akan dipost*//*
                val responseBody = response.body()
                *//*jika data yang dipost tidak kosong dan berhasil di post, post reviewnya*//*
                if (response.isSuccessful && responseBody != null) {
                    setReviewData(responseBody.customerReviews)
                } else {
                    *//*jika gagal*//*
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<PostReviewResponse>, t: Throwable) {
                showLoading(false)
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }*/

    /*jika tidak menggunakan livedata dan viewmodel, gunakan fungsi ini dan 3 fungsi berikutnya*/
    /*private fun findRestaurant() {
        showLoading(true)

        *//*cara get data dengan retrofit, panggil fungsi dari interface nya*//*
        val client = ApiConfig.getApiService().getRestaurant(RESTAURANT_ID)
        client.enqueue(object: Callback<RestaurantResponse> {
            *//*ketika fungsi berhasil dipanggil*//*
            override fun onResponse(
                call: Call<RestaurantResponse>,
                response: Response<RestaurantResponse>
            ) {
                showLoading(false)

                *//*jika hasilnya data berhasil diambil*//*
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    *//*jika datanya ada*//*
                    if (responseBody != null) {
                        setRestaurantData(responseBody.restaurant)
                        setReviewData(responseBody.restaurant.customerReviews)
                    }
                }
                *//*jika gagal diambil*//*
                else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<RestaurantResponse>, t: Throwable) {
                showLoading(false)
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }*/
}