package com.example.thuc

import android.app.Application
import com.example.thuc.data.AppContainer
import com.example.thuc.data.AppDataContainer


class ThucApplication: Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}