package com.example.bluetoothchat

import android.app.Application
import com.example.bluetoothchat.di.components.AppComponent
import com.example.bluetoothchat.di.components.DaggerAppComponent

class BaseApplication  : Application() {
    val component: AppComponent by lazy {
        DaggerAppComponent.factory()
            .create(this)
    }

}