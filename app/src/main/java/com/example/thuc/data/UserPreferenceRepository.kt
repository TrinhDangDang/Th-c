package com.example.thuc.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreferenceRepository(private val dataStore: DataStore<Preferences>) {

    private companion object {
      //  val DARK_THEME = booleanPreferencesKey("dark_theme")
        val DARK_THEME = booleanPreferencesKey("dark_theme")
    }

    val darkTheme: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[DARK_THEME] ?: false  // default to light theme
    }


    suspend fun saveDarkThemeEnabled(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[DARK_THEME] = enabled
        }
    }
}
