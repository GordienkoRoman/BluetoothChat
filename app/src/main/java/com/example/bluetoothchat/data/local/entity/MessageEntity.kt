package com.example.bluetoothchat.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.bluetoothchat.domain.model.Message

@Entity(
    tableName = "messages"
)
data class MessageEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "message_id")  val messageId: Int,
    @ColumnInfo(name = "from_user_id") val fromUserId:Int,
    @ColumnInfo(name = "to_user_id") val toUserId:Int,
    @ColumnInfo("text") val text:String,
    @ColumnInfo("time") val time:String
){
        fun toMessage(): Message =Message(text = text, time = time, isFromLocalUser = true)
}