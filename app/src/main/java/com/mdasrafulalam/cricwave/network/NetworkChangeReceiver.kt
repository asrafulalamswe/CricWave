package com.mdasrafulalam.cricwave.network

import android.annotation.SuppressLint
import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Build
import android.provider.Settings
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat.startActivityForResult
import com.mdasrafulalam.cricwave.R
import com.mdasrafulalam.cricwave.viewmomdel.CricketViewModel
import org.aviran.cookiebar2.CookieBar


class NetworkChangeReceiver(private val viewmodel:CricketViewModel): BroadcastReceiver() {
    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("MissingPermission", "ResourceType")
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == ConnectivityManager.CONNECTIVITY_ACTION) {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
            val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true
            if (isConnected) {
                // Handle connected
                if (viewmodel.getAllFixtures().value.isNullOrEmpty()) {
                    viewmodel.fetchAndInsertAllDataFromApi()
                }
                CookieBar.build(context as Activity?)
                    .setTitle("Network Connected")
                    .setIcon(R.drawable.success)
                    .setTitleColor(R.color.white)
                    .setBackgroundColor(R.color.swipe_color_1)
                    .setCookiePosition(CookieBar.TOP)
                    .setDuration(3000) // 3 seconds
                    .show()
            } else {
                // Handle disconnected
                CookieBar.build(context as Activity?)
                    .setTitle("Network Disconnected")
                    .setIcon(R.drawable.alert)
                    .setBackgroundColor(R.color.grey)
                    .setMessage("Open Network Setting")
                    .setCookiePosition(CookieBar.BOTTOM)
                    .setMessageColor(R.color.white)
                    .setAction("Open") {
                        // Action code - Do something
                        val callGPSSettingIntent = Intent(Settings.ACTION_WIFI_SETTINGS)
                        startActivityForResult(context,callGPSSettingIntent,1,null)
                    }
                    .setDuration(3000) // 3 seconds
                    .show()
                Log.d("NetworkChange", "Disconnected")
            }
        }
    }
}
