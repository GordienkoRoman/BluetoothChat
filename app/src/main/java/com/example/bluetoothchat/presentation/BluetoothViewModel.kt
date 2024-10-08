package com.example.bluetoothchat.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bluetoothchat.data.local.RoomRepository
import com.example.bluetoothchat.domain.model.Message
import com.example.bluetoothchat.domain.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class BluetoothViewModel @Inject constructor(
    private val roomRepository: RoomRepository
) : ViewModel() {

    private val _messagesList = MutableStateFlow(listOf(Message("", "", true)))
    val messagesList
        get() = _messagesList.asStateFlow()
    private val _usersList = MutableStateFlow(listOf(User(0, "", "")))
    val usersList
        get() = _usersList.asStateFlow()
    private val loadingMutableStateFlow = MutableStateFlow(true)
    val loadingFlow
        get() = loadingMutableStateFlow.asStateFlow()

    fun saveUser(id: Int) {
        viewModelScope.launch {
            roomRepository.saveUser(id)
        }
    }

//    fun getUsers(): List<User> {
//        viewModelScope.launch {
//            loadingMutableStateFlow.value = true
//            runCatching {
//                roomRepository.getUsers()
//            }.onSuccess {
//                _usersList.value = it
//                it.forEach { user: User ->
//                    roomRepository.getMessages(user.id)
//                }
//                loadingMutableStateFlow.value = false
//            }.onFailure {
//                loadingMutableStateFlow.value = false
//            }
//        }
//    }

    fun getMessages(userId: Int) {
        viewModelScope.launch {
            loadingMutableStateFlow.value = true
            runCatching {
                roomRepository.getMessages(userId)
            }.onSuccess {
                _messagesList.value = it
                loadingMutableStateFlow.value = false
            }.onFailure {
                loadingMutableStateFlow.value = false
            }
        }
    }
}