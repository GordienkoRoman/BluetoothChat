package com.example.bluetoothchat.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.bluetoothchat.data.local.entity.UsersEntity

@Dao
interface UsersDao {
    @Upsert(entity = UsersEntity::class)
    suspend fun upsertUser(userEntity: UsersEntity)

    @Query("SELECT * FROM users")
    suspend fun getUsers():List<UsersEntity>
}