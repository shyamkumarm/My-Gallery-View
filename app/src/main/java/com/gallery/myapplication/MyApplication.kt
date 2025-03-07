package com.gallery.myapplication

import android.app.Application
import com.gallery.myapplication.di.dataModules
import com.gallery.myapplication.di.domainModules
import com.gallery.myapplication.di.presentationModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger

import org.koin.core.context.startKoin

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@MyApplication)
            modules(presentationModules + domainModules + dataModules)
        }
    }
}