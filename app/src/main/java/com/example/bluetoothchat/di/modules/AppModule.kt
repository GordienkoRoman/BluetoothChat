package com.example.bluetoothchat.di.modules

import android.content.Context
import com.example.bluetoothchat.data.bluetooth.AndroidBluetoothController
import com.example.bluetoothchat.di.components.AppScope
import com.example.bluetoothchat.domain.BluetoothController
import dagger.Module
import dagger.Provides

@Module
class AppModule {
    @Provides
    @AppScope
    fun provideBluetoothController(context: Context): BluetoothController {
        return AndroidBluetoothController(context)
    }
}
