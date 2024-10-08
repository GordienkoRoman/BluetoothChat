package com.example.bluetoothchat.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Message(
    val text: String,
    val time: String,
    val isFromLocalUser: Boolean
):Parcelable