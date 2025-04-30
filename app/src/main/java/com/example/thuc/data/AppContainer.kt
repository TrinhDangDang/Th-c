package com.example.thuc.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

interface AppContainer {
    val thucRepository: ThucRepository
    val userPreferenceRepository: UserPreferenceRepository
}

class AppDataContainer(private val context: Context, private val dataStore: DataStore<Preferences>) : AppContainer{
    override val thucRepository: ThucRepository by lazy {
        OfflineThucRepository(ThucDatabase.getDatabase(context).alarmDao(), ThucDatabase.getDatabase(context).quoteDao())
    }
    override val userPreferenceRepository: UserPreferenceRepository by lazy {
        UserPreferenceRepository(dataStore)
    }
}