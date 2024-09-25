package com.example.bluetoothchat.domain

import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BluetoothMessage(
    val message: String,
    val senderName: String,
    val isFromLocalUser: Boolean
):Parcelable