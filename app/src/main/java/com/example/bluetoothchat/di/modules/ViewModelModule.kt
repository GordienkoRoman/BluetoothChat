package com.example.bluetoothchat.di.modules

import androidx.lifecycle.ViewModel
import com.example.bluetoothchat.di.viewmodelFactory.ViewModelKey
import com.example.bluetoothchat.presentation.BluetoothViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module
interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(BluetoothViewModel::class)
    fun bindLoginViewModel(viewModel: BluetoothViewModel): ViewModel

}