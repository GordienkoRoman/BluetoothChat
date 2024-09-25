package com.example.bluetoothchat.presentation

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.bluetoothchat.BaseApplication
import com.example.bluetoothchat.di.viewmodelFactory.ViewModelFactory
import com.example.bluetoothchat.presentation.ChatScreen.ChatScreen
import com.example.bluetoothchat.presentation.DevicesListScreen.DevicesScreen
import com.example.bluetoothchat.presentation.MainScreen.MainScreen
import com.example.bluetoothchat.ui.theme.BluetoothChatTheme
import javax.inject.Inject

class MainActivity : ComponentActivity() {
    private val bluetoothManager by lazy {
        applicationContext.getSystemService(BluetoothManager::class.java)
    }
    private val bluetoothAdapter by lazy {
        bluetoothManager?.adapter
    }

    private val isBluetoothEnabled: Boolean
        get() = bluetoothAdapter?.isEnabled == true

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val component by lazy {
        (application as BaseApplication).component
    }


    private val viewModel by viewModels<BluetoothViewModel> {
        viewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component.inject(this)
        enableEdgeToEdge()
        val enableBluetoothLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {

        }
        val permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            val canEnableBluetooth = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                permissions[android.Manifest.permission.BLUETOOTH_SCAN] == true
            } else {
                true
            }
            if (canEnableBluetooth && !isBluetoothEnabled) {
                enableBluetoothLauncher.launch(
                    Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                )
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
            permissionLauncher.launch(
                arrayOf(
                    android.Manifest.permission.BLUETOOTH_SCAN,
                    android.Manifest.permission.BLUETOOTH_CONNECT
                )
            )

        setContent {
            BluetoothChatTheme {
                val state by viewModel.state.collectAsState()

                LaunchedEffect(key1 = state.isConnected) {
                    if (state.isConnected) {
                        Toast.makeText(
                            applicationContext,
                            "You're connected!",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

                Surface(
                    color = Color.LightGray
                ) {
                    MainScreen(
                        state = state,
                        onDisconnect = viewModel::disconnectFromDevice,
                        onSendMessage = viewModel::sendMessage
                    )
//                    when {
//                        state.isConnecting -> {
//                            Column(
//                                modifier = Modifier.fillMaxSize(),
//                                horizontalAlignment = Alignment.CenterHorizontally,
//                                verticalArrangement = Arrangement.Center
//                            ) {
//                                CircularProgressIndicator()
//                                Text(text = "Connecting...")
//                            }
//                        }
//                        state.isConnected -> {
//                            MainScreen(
//                                state = state,
//                                onDisconnect = viewModel::disconnectFromDevice,
//                                onSendMessage = viewModel::sendMessage
//                            )
//
//                        }
//
//                        else -> {
//                            DevicesScreen(
//                                state = state,
//                                onStartScan = viewModel::startScan,
//                                onStopScan = viewModel::stopScan,
//                                onDeviceClick = viewModel::connectToDevice,
//                                onStartServer = viewModel::waitForIncomingConnections
//                            )
//                        }
//                    }
                }
            }
        }
    }
}

