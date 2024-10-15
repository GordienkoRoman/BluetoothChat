package com.example.bluetoothchat.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.bluetoothchat.data.local.entity.MessageEntity

@Dao
interface MessageDao {
    @Insert(entity = MessageEntity::class,onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveMessage(messageEntity: MessageEntity)

    @Query("SELECT * FROM messages WHERE to_user_id = :userId OR from_user_id = :userId")
    suspend fun getMessages(userId: Int): List<MessageEntity>

    @Query("SELECT * FROM messages")
    suspend fun getAllMessages(): List<MessageEntity>
}