package com.example.bluetoothchat.data.bluetooth

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import com.example.bluetoothchat.domain.BluetoothDeviceDomain
import com.example.bluetoothchat.domain.model.Message


@SuppressLint("MissingPermission")
fun BluetoothDevice.toBluetoothDeviceDomain():BluetoothDeviceDomain {
    return BluetoothDeviceDomain(
        name = name,
        address = address
    )
}

fun String.toBluetoothMessage(isFromLocalUser: Boolean): Message {
    val name = substringBeforeLast("#")
    val message = substringAfter("#")
    return Message(
        text = message,
        time = name,
        isFromLocalUser = isFromLocalUser
    )
}

fun Message.toByteArray(): ByteArray {
    return "$time#$text".encodeToByteArray()
}