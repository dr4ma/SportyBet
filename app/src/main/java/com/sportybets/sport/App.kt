package com.sportybets.sport

import android.app.Application
import com.onesignal.OneSignal

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        OneSignal.initWithContext(this)
        OneSignal.setAppId("91efa19e-fac0-4d14-acfd-cee78573debc")
    }
}