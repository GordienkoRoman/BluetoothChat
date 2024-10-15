package com.example.bluetoothchat.domain

import com.example.bluetoothchat.domain.model.Message

data class BluetoothUiState(
    val isConnected:Boolean = false,
    val isConnecting:Boolean = false,
    val scannedDevices: List<BluetoothDevice> = emptyList(),
    val pairedDevices: List<BluetoothDevice> = emptyList(),
    val messages: List<Message> = emptyList()
) {
}