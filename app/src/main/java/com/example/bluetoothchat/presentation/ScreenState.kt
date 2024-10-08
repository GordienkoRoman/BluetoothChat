package com.example.bluetoothchat.presentation

import com.example.bluetoothchat.domain.model.Message

sealed class ScreenState {
    class Loaded(messages:List<Message>)
}