package com.example.fooddonationapp.utils

import android.app.Application
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import com.example.fooddonationapp.R



import com.google.gson.GsonBuilder
import retrofit2.Response
import timber.log.Timber

open class BaseViewModel(application: Application) : AndroidViewModel(application) {
    var context = application
    @RequiresApi(Build.VERSION_CODES.M)
    fun isConnected(showMessage: Boolean = false): Boolean {
        val connectivityManager =
            getApplication<Application>().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return if (connectivityManager.activeNetwork == null) {
            showToast(showMessage, false)
            false
        } else {
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabilities =
                connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            return when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                    true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                    true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                    true
                }
                else -> {
                    showToast(showMessage, false);false
                }
            }
        }
    }

    val gson = GsonBuilder().create()
    fun <T> handleResponse(response: Response<T>): NetworkResult<T> {
        return when {
            response.message().toString().contains("timeout") -> {
                NetworkResult.Error("Timeout")
            }
            response.isSuccessful -> {

                val responseResult = response.body()
                NetworkResult.Success(responseResult!!)

            }
//            response.code() == Constant.RESPONSE_CODE.RESPONSE_400 -> {
//                gson.fromJson(
//                    response.body().toString(),
//                    BaseResponse::class.java
//                )
//
//                var data = gson.fromJson(
//                    response.errorBody()!!.string(),
//                    BaseResponse::class.java
//                )
//                Timber.e(data.message)
//                NetworkResult.Error(
//                    data.message //+ "\n" + data.errorCode
//                )
//            }

//            response.code() == Constant.RESPONSE_CODE.RESPONSE_401 -> {
//                Utils.logout()
//                val intent: Intent? =
//                    context.packageManager.getLaunchIntentForPackage(context.packageName)
//                intent?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//                intent?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
//                ContextCompat.startActivity(context, intent!!, null)
//                NetworkResult.Error(response.message())
//            }
            else -> {
                Timber.e("in else")
                Timber.e(response.message())


                NetworkResult.Error(response.message())
            }
        }
    }

    private fun showToast(showMessage: Boolean, hasInternet: Boolean) {
        if (showMessage && !hasInternet)
            context.resources.getString(R.string.no_Internet).toast(context)
    }
}