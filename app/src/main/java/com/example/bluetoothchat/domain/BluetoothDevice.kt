package com.example.bluetoothchat.domain

typealias BluetoothDeviceDomain = BluetoothDevice
data class BluetoothDevice (
    val name: String?,
    val address: String
)