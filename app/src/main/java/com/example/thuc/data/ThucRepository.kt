package com.example.thuc.data

import kotlinx.coroutines.flow.Flow

interface ThucRepository {

    // Alarms
    fun getAlarmsStream(): Flow<List<Alarm>>

//    fun getAlarmByLabel(alarmLabel: String): Flow<Alarm>

    suspend fun insertAlarm(alarm: Alarm)

    suspend fun updateAlarm(alarm: Alarm)

    suspend fun deleteAlarm(alarm: Alarm)

    // Quotes
    fun getQuotesStream(): Flow<List<Quote>>

    suspend fun insertQuote(quote: Quote)

    suspend fun deleteQuote(quote: Quote)
}
