package com.example.bluetoothchat.presentation

import com.example.bluetoothchat.domain.BluetoothDevice
import com.example.bluetoothchat.domain.BluetoothMessage

data class BluetoothUiState(
    val isConnected:Boolean = false,
    val isConnecting:Boolean = false,
    val scannedDevices: List<BluetoothDevice> = emptyList(),
    val pairedDevices: List<BluetoothDevice> = emptyList(),
    val messages: List<BluetoothMessage> = emptyList()
) {
}