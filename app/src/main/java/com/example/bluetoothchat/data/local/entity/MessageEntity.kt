package com.example.bluetoothchat.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "users"
)
data class MessageEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "message_id")  val messageId: Int,
    @ColumnInfo(name = "user_id") val userId:Int,
    @ColumnInfo("text") val text:String,
    @ColumnInfo("time") val time:String
)