package com.example.thuc

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.thuc.data.AppContainer
import com.example.thuc.data.AppDataContainer

private const val KEYWORD_DATASTORE_NAME = "user_preference_data_store"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = KEYWORD_DATASTORE_NAME
)

class ThucApplication: Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this, this.dataStore)
    }
}