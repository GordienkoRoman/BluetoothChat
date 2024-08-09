package com.example.bluetoothchat.data

import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.pm.PackageManager
import com.example.bluetoothchat.domain.BluetoothController
import com.example.bluetoothchat.domain.BluetoothDevice
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AndroidBluetoothController(
    private val context: Context
) : BluetoothController {

    private val bluetoothManager by lazy{
        context.getSystemService(BluetoothManager::class.java)
    }
    private val bluetoothAdapter by lazy {
        bluetoothManager?.adapter
    }
    private val _scannedDevices = MutableStateFlow<List<BluetoothDevice>>(emptyList())
    private val _pairedDevices = MutableStateFlow<List<BluetoothDevice>>(emptyList())
    override val scannedDevices: StateFlow<List<BluetoothDevice>>
        get() = _scannedDevices.asStateFlow()

    override val pairedDevices: StateFlow<List<BluetoothDevice>>
        get() = _pairedDevices.asStateFlow()

    override fun startDiscovery() {
        TODO("Not yet implemented")
    }

    override fun stopDiscovery() {
        TODO("Not yet implemented")
    }

    override fun release() {
        TODO("Not yet implemented")
    }
    private fun hasPermission(permission:String):Boolean{
        return context.checkSelfPermission(permission)==PackageManager.PERMISSION_GRANTED
    }
}