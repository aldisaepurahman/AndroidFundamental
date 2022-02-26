package com.app.restaurantreview

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    companion object {
        private const val TAG = "MainViewModel"
        private const val RESTAURANT_ID = "uewq1zg2zlskfw1e867"
    }

    /*ambil data restoran*/
    private val _restaurant = MutableLiveData<Restaurant>()
    val restaurant: LiveData<Restaurant> = _restaurant

    /*ambil data review*/
    private val _listReview = MutableLiveData<List<CustomerReviewsItem>>()
    val listReview: LiveData<List<CustomerReviewsItem>> = _listReview

    /*data progress bar*/
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    /*data snackbar single event*/
    private val _snackBarText = MutableLiveData<Event<String>>()
    val snackBarText: LiveData<Event<String>> = _snackBarText

    /*ketika diinstansiasi dan dipanggil, data restoran dan reviewnya akan diambil*/
    init {
        findRestaurant()
    }

    private fun findRestaurant() {
        _isLoading.value = true

        /*cara get data dengan retrofit, panggil fungsi dari interface nya*/
        val client = ApiConfig.getApiService().getRestaurant(RESTAURANT_ID)
        client.enqueue(object: Callback<RestaurantResponse> {
            /*ketika fungsi berhasil dipanggil*/
            override fun onResponse(
                call: Call<RestaurantResponse>,
                response: Response<RestaurantResponse>
            ) {
                _isLoading.value = false

                /*jika hasilnya data berhasil diambil*/
                if (response.isSuccessful) {
                    _restaurant.value = response.body()?.restaurant
                    _listReview.value = response.body()?.restaurant?.customerReviews
                }
                /*jika gagal diambil*/
                else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<RestaurantResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun postReview(review: String) {
        _isLoading.value = true

        /*cara post data dengan retrofit*/
        val client = ApiConfig.getApiService().postReview(RESTAURANT_ID,
            "Dicoding", review)
        client.enqueue(object : Callback<PostReviewResponse> {
            /*jika fungsi berhasil dipanggil*/
            override fun onResponse(
                call: Call<PostReviewResponse>,
                response: Response<PostReviewResponse>
            ) {
                _isLoading.value = false
                /*ambil data yang akan dipost*/
                val responseBody = response.body()
                /*jika data yang dipost tidak kosong dan berhasil di post, post reviewnya*/
                if (response.isSuccessful) {
                    _listReview.value = responseBody?.customerReviews
                    _snackBarText.value = Event(responseBody?.message.toString())
                } else {
                    /*jika gagal*/
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<PostReviewResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
}