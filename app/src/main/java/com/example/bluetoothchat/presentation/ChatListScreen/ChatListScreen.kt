package com.example.bluetoothchat.presentation.ChatListScreen

import android.media.Image
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.R
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bluetoothchat.domain.model.Message
import com.example.bluetoothchat.presentation.ChatScreen.ChatScreen

@Composable
fun ChatListScreen(
    messages: State<List<Message>>,
    onItemClick: (Int) -> Unit
){
    LazyColumn {
        items(10){
            ChatListItem(onItemClick)
        }
    }
}

@Preview
@Composable
fun ChatListItem(onClick:(Int)->Unit={}){
    Row(modifier = Modifier.clickable { onClick(1) })
    {
        Image(
            painterResource(id = com.example.bluetoothchat.R.drawable.images),
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape),
            contentDescription = "god_beauty",
            contentScale = ContentScale.Crop)
        Column( modifier = Modifier
            .weight(1f)) {
            Text(text = "Hi,")
            //     CustomText(text = "Hi,", textColor = TextFieldLabelColor)
            Text(
                "3r4tyuiopihugytdgf",
                fontSize = 25.sp,
                color = Color.White,
                )
        }

    }
}