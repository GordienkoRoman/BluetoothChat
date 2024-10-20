package com.example.bluetoothchat.data.SharedPreferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject


class DataStoreRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {

    // private val dataStore = context.dataStore

    companion object {
        // private val Context.dataStore by preferencesDataStore(name = "settings")
        val userKey = stringPreferencesKey("USER_KEY")

    }

    val loginFlow = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            val login = preferences[userKey] ?: false
            login
        }

    suspend fun setUser(login: String) {
        dataStore.edit { preferences ->
            preferences[userKey] = login
        }
    }
}