package com.example.bluetoothchat.presentation.MainScreen

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.bluetoothchat.di.viewmodelFactory.ViewModelFactory
import com.example.bluetoothchat.domain.model.Message
import com.example.bluetoothchat.presentation.ChatListScreen.ChatListScreen
import com.example.bluetoothchat.presentation.ChatScreen.ChatScreen
import com.example.bluetoothchat.presentation.Routes
import com.example.bluetoothchat.presentation.Routes.Companion.KEY_CHAT_SCREEN
import com.example.bluetoothchat.presentation.components.NavigationDrawer
import com.example.bluetoothchat.presentation.components.ScaffoldViewState
import com.example.bluetoothchat.presentation.components.TopAppBar.MainScreenTopAppBar


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    messages: State<MutableMap<Int, MutableList<Message>>>,
    viewModelFactory: ViewModelFactory
) {
    val viewModel: MainScreenViewModel = viewModel(factory = viewModelFactory)
    val state by viewModel.state.collectAsState()
    val navController: NavHostController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    val scaffoldState = remember {
        mutableStateOf(ScaffoldViewState())
    }

    val currentUser = remember {
        mutableStateOf(viewModel.userFlow)
    }
//    for(i in 1..10){
//       // viewModel.sendMessage(Message(i.toString(),"time",true),i)
//        viewModel.saveUser(i)
//    }
    Scaffold(
        contentWindowInsets = WindowInsets.safeDrawing,
        topBar = { MainScreenTopAppBar(viewModel.getName()) }
    ) { paddingValues ->
        ModalNavigationDrawer(
            drawerContent = {
                NavigationDrawer()
            }
        ) {
                NavHost(
                    modifier = Modifier.padding(paddingValues),
                    navController = navController,
                    startDestination = Routes.ChatList.route,
                    route = Routes.Home.route
                ) {
                    composable(route = Routes.ChatList.route){
                        ChatListScreen(messages, onItemClick = {
                            navController.navigate(Routes.Chat.route+"/"+it)
                        })
                    }
                    composable(route = Routes.Chat.withArgs("{$KEY_CHAT_SCREEN}")){
                        val userIdTo = it.arguments?.getString(KEY_CHAT_SCREEN) ?: ""
                        val list = messages.value[userIdTo.toInt()]!!
                        ChatScreen(userIdTo,messages.value[userIdTo.toInt()]!!,currentUser.value, onSendMessage = viewModel::sendMessage, onDeleteMessage = viewModel::deleteMessage)
                    }
                }

        }
    }

}