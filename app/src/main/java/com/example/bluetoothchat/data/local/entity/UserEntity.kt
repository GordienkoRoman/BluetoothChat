package com.example.bluetoothchat.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "users"
)
data class UsersEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "user_id")  val userId: Int,
    @ColumnInfo("name") val userName:String,
    @ColumnInfo(name = "img_url") val imgUrl: String?
)