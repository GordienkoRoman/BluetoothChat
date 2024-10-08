package com.example.bluetoothchat.di.modules

import android.content.Context
import androidx.room.Room
import com.example.bluetoothchat.data.bluetooth.AndroidBluetoothChatRepositoryImpl
import com.example.bluetoothchat.data.local.AppDataBase
import com.example.bluetoothchat.data.local.RoomRepository
import com.example.bluetoothchat.data.local.dao.MessageDao
import com.example.bluetoothchat.data.local.dao.UsersDao
import com.example.bluetoothchat.di.components.AppScope
import com.example.bluetoothchat.domain.BluetoothChatRepository
import dagger.Module
import dagger.Provides

@Module
class DatabaseModule {

    @AppScope
    @Provides
    fun provideAppDatabase(context: Context): AppDataBase {
        return Room.databaseBuilder(context, AppDataBase::class.java, "database.db")
            .fallbackToDestructiveMigration()
            .build()

    }
    @AppScope
    @Provides
    fun provideUsersDao(appDataBase: AppDataBase): UsersDao = appDataBase.usersDao

    @AppScope
    @Provides
    fun provideMessageDao(appDataBase: AppDataBase): MessageDao = appDataBase.messageDao

    @AppScope
    @Provides
    fun provideBluetoothChatRepository(context: Context,usersDao: UsersDao,messageDao: MessageDao): BluetoothChatRepository {
        return AndroidBluetoothChatRepositoryImpl(context,usersDao,messageDao)
    }

    @AppScope
    @Provides
    fun provideRoomRepository(usersDao: UsersDao,messageDao: MessageDao, context: Context):RoomRepository {
        return RoomRepository(usersDao = usersDao, messageDao = messageDao, context = context)
    }

}