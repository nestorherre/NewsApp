package com.example.newsapp.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

object InternetConnection {

    fun isActive(context: Context?, hasInternet: Boolean ): Boolean{
        if (context != null){
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
            return activeNetwork?.isConnectedOrConnecting == true
        }
        return hasInternet
    }

}