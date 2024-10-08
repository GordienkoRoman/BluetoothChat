package com.example.bluetoothchat.presentation

sealed class Routes(val route: String) {
    data object Home : Routes(route = "home_screen")
    data object ChatList : Routes(route = "chat_list_screen")
    data object Chat : Routes(route = "chat_screen")
    data object FeedSearch : Routes(route = "feed_search_screen")
    data object FeedItem : Routes(route = "feed_item_screen")
    data object Login : Routes(route = "login_screen")
    data object Support : Routes(route = "support_screen")
    data object ContactSupport : Routes(route = "contact_support_screen")
    data object SupportMain : Routes(route = "support_main_screen")
    data object Profile : Routes(route = "profile_screen")
    data object Friends : Routes(route = "friends_screen")

    companion object {
        const val KEY_AUTH_USER = "auth_user"
        const val KEY_FEED_ITEM_INDEX = "feed_item_index"
        const val KEY_FEED_SEARCH_INDEX = "feed_search_index"
    }

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach {
                append("/$it")
            }
        }
    }
}