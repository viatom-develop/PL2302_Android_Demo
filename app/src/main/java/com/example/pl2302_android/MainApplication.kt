package com.example.pl2302_android

import android.app.Application

class MainApplication:Application() {
    override fun onCreate() {

        super.onCreate()
        instance=this
    }

    companion object{
        lateinit var instance:MainApplication
    }
}