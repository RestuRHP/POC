package com.learn.pocmobile.lib

import android.content.Context
import android.content.Context.WIFI_SERVICE
import android.net.wifi.WifiManager

class Wifiku {
    fun getWifiSSID(context: Context): String {
        val wifiManager = context.getSystemService(WIFI_SERVICE) as WifiManager
        val wifiInfo = wifiManager.connectionInfo
        return wifiInfo.ssid
    }

    fun getWifiBSSID(context: Context): String {
        val wifiManager = context.getSystemService(WIFI_SERVICE) as WifiManager
        val wifiInfo = wifiManager.connectionInfo
        return wifiInfo.bssid
    }

    fun getWifiMacAddress(context: Context): String {
        val wifiManager = context.getSystemService(WIFI_SERVICE) as WifiManager
        val wifiInfo = wifiManager.connectionInfo
        return wifiInfo.macAddress
    }
}