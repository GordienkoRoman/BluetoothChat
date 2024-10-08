package com.example.bluetoothchat.presentation.MainScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bluetoothchat.data.local.RoomRepository
import com.example.bluetoothchat.domain.BluetoothChatRepository
import com.example.bluetoothchat.domain.BluetoothDeviceDomain
import com.example.bluetoothchat.domain.ConnectionResult
import com.example.bluetoothchat.domain.model.Message
import com.example.bluetoothchat.domain.model.User
import com.example.bluetoothchat.presentation.BluetoothUiState
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainScreenViewModel @Inject constructor(
    private val roomRepository: RoomRepository,
    private val bluetoothChatRepository: BluetoothChatRepository
) : ViewModel() {
    private var deviceConnectionJob: Job? = null
    private val userMutableFlow  = MutableStateFlow(User(0,"",""))
    val userFlow
        get() = userMutableFlow.asStateFlow()

    private val _state = MutableStateFlow(BluetoothUiState())
    val state
        get() = combine(
            bluetoothChatRepository.scannedDevices,
            bluetoothChatRepository.pairedDevices,
            _state
        ) { scannedDevices, pairedDevices, state ->
            state.copy(scannedDevices = scannedDevices,
                pairedDevices = pairedDevices,
                messages = if(state.isConnected) state.messages else emptyList())
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000),_state.value)

    init {
        bluetoothChatRepository.isConnected.onEach { isConnected->
            _state.update { it.copy(isConnected = isConnected) }
        }.launchIn(viewModelScope)
    }
    fun startScan(){
        bluetoothChatRepository.startDiscovery()
    }
    fun stopScan(){
        bluetoothChatRepository.stopDiscovery()
    }

    fun connectToDevice(device: BluetoothDeviceDomain) {
        _state.update { it.copy(isConnecting = true) }
        deviceConnectionJob = bluetoothChatRepository
            .connectToDevice(device)
            .listen()
    }
    fun sendMessage(message: Message,userId:Int) {
        viewModelScope.launch {
            val bluetoothMessage = bluetoothChatRepository.trySendMessage(message.text)
            if(bluetoothMessage != null) {
                _state.update { it.copy(
                    messages = it.messages + bluetoothMessage
                ) }
            }
            roomRepository.saveMessage(message,userId)
        }
    }


    fun disconnectFromDevice() {
        deviceConnectionJob?.cancel()
        bluetoothChatRepository.closeConnection()
        _state.update { it.copy(
            isConnecting = false,
            isConnected = false
        ) }
    }

    fun waitForIncomingConnections() {
        _state.update { it.copy(isConnecting = true) }
        deviceConnectionJob = bluetoothChatRepository
            .startBluetoothServer()
            .listen()
    }

    private fun Flow<ConnectionResult>.listen(): Job {
        return onEach { result ->
            when(result) {
                ConnectionResult.ConnectionEstablished -> {
                    _state.update { it.copy(
                        isConnected = true,
                        isConnecting = false,
                    ) }
                }
                is ConnectionResult.Error -> {
                    _state.update { it.copy(
                        isConnected = false,
                        isConnecting = false,
                    ) }
                }

                is ConnectionResult.TransferSucceeded -> {
                    _state.update { it.copy(
                        messages = it.messages + result.message
                    ) }
                }
            }
        }
            .catch { throwable ->
                bluetoothChatRepository.closeConnection()
                _state.update { it.copy(
                    isConnected = false,
                    isConnecting = false,
                ) }
            }
            .launchIn(viewModelScope)
    }


    override fun onCleared() {
        bluetoothChatRepository.release()
        super.onCleared()
    }
}