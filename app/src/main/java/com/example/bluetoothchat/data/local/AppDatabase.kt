package com.example.bluetoothchat.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.bluetoothchat.data.local.dao.MessageDao
import com.example.bluetoothchat.data.local.dao.UsersDao
import com.example.bluetoothchat.data.local.entity.MessageEntity
import com.example.bluetoothchat.data.local.entity.UsersEntity

@Database(
    entities = [UsersEntity::class,MessageEntity::class],
    version = 3
)
abstract class AppDataBase : RoomDatabase() {
    abstract val usersDao: UsersDao
    abstract val messageDao: MessageDao
}