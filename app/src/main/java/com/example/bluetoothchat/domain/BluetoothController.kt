package com.example.bluetoothchat.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface BluetoothController {
    val isConnected:StateFlow<Boolean>
    val scannedDevices: StateFlow<List<BluetoothDevice>>
    val pairedDevices: StateFlow<List<BluetoothDevice>>

    fun startDiscovery()
    fun stopDiscovery()
    suspend fun trySendMessage(message: String): BluetoothMessage?

    fun startBluetoothServer(): Flow<ConnectionResult>
    fun connectToDevice(device: BluetoothDevice):Flow<ConnectionResult>
    fun closeConnection()

    fun release()
}