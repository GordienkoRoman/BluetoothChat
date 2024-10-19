package com.example.bluetoothchat.presentation.ChatScreen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bluetoothchat.domain.model.Message
import com.example.bluetoothchat.domain.model.User
import kotlinx.coroutines.flow.StateFlow
import kotlin.reflect.KFunction3

@Composable
fun ChatScreen(
    userId: String,
    messages: List<Message>,
    onDisconnect: StateFlow<User>,
    onSendMessage: KFunction3<Message, Int, Int, Unit>,
    onDeleteMessage: (Int)->Unit
) {
    val tmpMessages = rememberSaveable {
        mutableStateOf(messages)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .background(Color.DarkGray)
    ) {
        Chat(messages = tmpMessages, userId = userId, onSendMessage = onSendMessage, onDisconnect = onDisconnect, onDeleteMessage = onDeleteMessage)

    }
}

@Composable
fun Chat(
    userId: String,
    messages: MutableState<List<Message>>,
    onDisconnect: StateFlow<User>,
    onSendMessage: KFunction3<Message, Int, Int, Unit>,
    onDeleteMessage: (Int)->Unit
)
{
    val tmpMessage = rememberSaveable {
        mutableStateOf("")
    }

    val keyboardController = LocalSoftwareKeyboardController.current
    Box(modifier = Modifier.fillMaxSize())
    {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(messages.value) { message ->
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    ChatMessage(
                        message = message,
                        modifier = Modifier.align(if (message.isFromLocalUser) Alignment.Start else Alignment.End),
                        onDeleteMessage = onDeleteMessage,
                        messages = messages
                    )
                }
            }
        }
        Column(modifier = Modifier.align(Alignment.BottomCenter)) {
            ChatTextField(tmpMessage, onSendMessage, messages, userId, keyboardController)

        }
    }
}

@Composable
fun ChatTextField(
    tmpMessage: MutableState<String>,
    onSendMessage: (Message, Int, Int) -> Unit,
    stateMessages: MutableState<List<Message>>,
    userId: String,
    keyboardController: SoftwareKeyboardController?
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            readOnly = false,
            minLines = 1,
            value = tmpMessage.value,
            onValueChange = { tmpMessage.value = it },
            modifier = Modifier.weight(1f),
            placeholder = {
                Text(text = "Message")
            }
        )
        IconButton(onClick = {
            val mes = Message(tmpMessage.value,"time",true)
         //   onSendMessage(mes,1,userId.toInt())
            val list = stateMessages.value.plus(mes)
            stateMessages.value = list
            tmpMessage.value = ""
            keyboardController?.hide()
        }) {
            Icon(
                imageVector = Icons.Default.Send,
                contentDescription = "Send message"
            )
        }
    }
}
@Composable
fun ChatMessage(
    modifier: Modifier = Modifier,
    messages: MutableState<List<Message>>,
    message: Message,
    onDeleteMessage: (Int) -> Unit
) {
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }
    val color = colorResource(id = com.example.bluetoothchat.R.color.dark_blue)
    Column(
        modifier = modifier
            .clip(
                RoundedCornerShape(
                    topStart = if (message.isFromLocalUser) 15.dp else 0.dp,
                    topEnd = 15.dp,
                    bottomStart = 15.dp,
                    bottomEnd = if (message.isFromLocalUser) 0.dp else 15.dp
                )
            )
            .background(
                if (!message.isFromLocalUser) color else Color.Gray
            )
            .padding(16.dp)
            .clickable { expanded = !expanded },
        horizontalAlignment = Alignment.End
    ) {
        Text(
            text = message.time,
            fontSize = 10.sp,
            color = Color.White
        )
        Text(
            text = message.text,
            color = Color.White,
            modifier = Modifier.widthIn(max = 250.dp)
        )
    }
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false }
    ) {
        DropdownMenuItem(
            text = { Text("DELETE") },
            onClick = {
                onDeleteMessage(message.id)
                messages.value = messages.value.minus(message)
                expanded = false
            }
        )
        DropdownMenuItem(
            text = { Text("Save") },
            onClick = { Toast.makeText(context, "Save", Toast.LENGTH_SHORT).show() }
        )
    }
}