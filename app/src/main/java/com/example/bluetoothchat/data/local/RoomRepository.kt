package com.example.bluetoothchat.data.local

import android.content.Context
import com.example.bluetoothchat.data.local.dao.MessageDao
import com.example.bluetoothchat.data.local.dao.UsersDao
import com.example.bluetoothchat.data.local.entity.MessageEntity
import com.example.bluetoothchat.data.local.entity.UsersEntity
import com.example.bluetoothchat.domain.model.Message
import com.example.bluetoothchat.domain.model.User
import javax.inject.Inject
import kotlin.random.Random

class RoomRepository @Inject constructor(
    private val usersDao: UsersDao,
    private val messageDao: MessageDao,
    val context: Context
){
    suspend fun saveMessage(message: Message,userId: Int) {
        messageDao.saveMessage(MessageEntity(0,userId,message.text,message.time))
    }

    suspend fun getMessages(userId:Int) :List<Message> {
        return messageDao.getMessages(userId).map {
            it.toMessage()
        }
    }

    suspend fun saveUser(id:Int)
    {
        usersDao.upsertUser(UsersEntity(id,"tt","tt"))
    }
    suspend fun getUsers():List<User>{
        return usersDao.getUsers().map {
            it.toUser()
        }
    }
}