package com.example.bluetoothchat.presentation.MainScreen

import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.runtime.Composable
import com.example.bluetoothchat.presentation.BluetoothUiState
import com.example.bluetoothchat.presentation.ChatListScreen.ChatListScreen
import com.example.bluetoothchat.presentation.ChatScreen.ChatScreen
import com.example.bluetoothchat.presentation.components.NavigationDrawer

@Composable
fun MainScreen(
    state: BluetoothUiState,
    onDisconnect: () -> Unit,
    onSendMessage: (String) -> Unit
){
    ModalNavigationDrawer(
        drawerContent = {
            NavigationDrawer()
        }
    ) {
        ChatListScreen()

    }
}