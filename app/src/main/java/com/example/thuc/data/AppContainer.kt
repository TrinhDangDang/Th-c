package com.example.thuc.data

import android.content.Context

interface AppContainer {
    val thucRepository: ThucRepository
}

class AppDataContainer(private val context: Context) : AppContainer{
    override val thucRepository: ThucRepository by lazy {
        OfflineThucRepository(ThucDatabase.getDatabase(context).alarmDao(), ThucDatabase.getDatabase(context).quoteDao())
    }
}