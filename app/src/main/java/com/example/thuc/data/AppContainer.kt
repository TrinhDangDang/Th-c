package com.example.thuc.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.thuc.network.AIRepository
import com.example.thuc.network.TogetherAIRepository
import com.example.thuc.network.TogetherAiApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import kotlin.getValue


interface AppContainer {
    val thucRepository: ThucRepository
    val userPreferenceRepository: UserPreferenceRepository
    val togetherAIRepository: AIRepository
}

class AppDataContainer(private val context: Context, private val dataStore: DataStore<Preferences>) : AppContainer{
    override val thucRepository: ThucRepository by lazy {
        OfflineThucRepository(ThucDatabase.getDatabase(context).alarmDao(), ThucDatabase.getDatabase(context).quoteDao())
    }
    override val userPreferenceRepository: UserPreferenceRepository by lazy {
        UserPreferenceRepository(dataStore)
    }

    val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .connectTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
        .readTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
        .writeTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
        .build()

    val json = Json {
        ignoreUnknownKeys = true
        encodeDefaults = true
    }

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://api.together.xyz/v1/")
        .client(client)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()

    val apiService: TogetherAiApiService = retrofit.create(TogetherAiApiService::class.java)

    override val togetherAIRepository by lazy {
       TogetherAIRepository(apiService)
    }

}