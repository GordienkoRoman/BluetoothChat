package com.example.bluetoothchat.presentation.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable

data class ScaffoldViewState(
    val title: @Composable () -> Unit = {},
    val navigationIcon: @Composable () -> Unit = {},
    val actions:  @Composable RowScope.() -> Unit = {},
    val floatingActionButton: @Composable () -> Unit = {}
)