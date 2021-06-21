package com.szy.javapoemtest

import android.app.Application
import android.util.Log

/**
 * Created by devov on 2021/3/12.
 */
class MyApplication : Application() {

    init {
        Log.i("RRORE","myApplication!!")
    }

    override fun onCreate() {
        super.onCreate()
        getSharedPreferences("java",0).edit().putString("xiba","java poem babanana").apply()
    }
}