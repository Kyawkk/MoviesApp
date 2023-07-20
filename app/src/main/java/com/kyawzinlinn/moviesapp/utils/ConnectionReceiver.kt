package com.kyawzinlinn.moviesapp.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager

class ConnectionReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context?, p1: Intent?) {
        val connectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        val isConnected = networkInfo != null && networkInfo.isConnected

        listener?.onConnectionChanged(isConnected)
    }

    companion object{
        var listener: ConnectionReceiverListener? = null
    }

    interface ConnectionReceiverListener{
        fun onConnectionChanged(isConnected: Boolean)
    }
}