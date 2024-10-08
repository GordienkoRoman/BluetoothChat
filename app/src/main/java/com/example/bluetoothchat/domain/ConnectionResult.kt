package com.example.bluetoothchat.domain

import com.example.bluetoothchat.domain.model.Message

sealed interface ConnectionResult {
    object ConnectionEstablished:ConnectionResult
    data class TransferSucceeded(val message: Message): ConnectionResult
    data class Error(val message:String):ConnectionResult
}