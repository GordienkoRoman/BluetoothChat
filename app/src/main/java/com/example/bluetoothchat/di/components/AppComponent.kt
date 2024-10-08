package com.example.bluetoothchat.di.components

import android.content.Context
import androidx.compose.material3.ExperimentalMaterial3Api
import com.example.bluetoothchat.di.modules.AppModule
import com.example.bluetoothchat.di.modules.DatabaseModule
import com.example.bluetoothchat.di.modules.ViewModelModule
import com.example.bluetoothchat.presentation.MainActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Scope

@Scope
annotation class AppScope

@AppScope
@Component(
    modules = [AppModule::class,
        ViewModelModule::class,
        DatabaseModule::class]
)
interface AppComponent {
    @OptIn(ExperimentalMaterial3Api::class)
    fun inject(mainActivity: MainActivity)

    @Component.Factory
    interface Factory {

        fun create(
            @BindsInstance context: Context
        ): AppComponent
    }
}