package com.example.thuc.data

import kotlinx.coroutines.flow.Flow

class OfflineThucRepository(private val alarmDao: AlarmDao, private val quoteDao: QuoteDao) : ThucRepository{
    // Alarms
    override fun getAlarmsStream(): Flow<List<Alarm>> = alarmDao.getAllAlarms()

    override suspend fun getAlarmByLabel(alarmLabel: String): Alarm? = alarmDao.getAlarmByLabel(alarmLabel)

    override suspend fun insertAlarm(alarm: Alarm) = alarmDao.insertAlarm(alarm)

    override suspend fun updateAlarm(alarm: Alarm) = alarmDao.updateAlarm(alarm)

    override suspend fun deleteAlarm(alarm: Alarm) = alarmDao.deleteAlarm(alarm)

    // Quotes
    override fun getQuotesStream(): Flow<List<Quote>> = quoteDao.getAllQuotes()

    override suspend fun getQuoteByText(text: String): Quote? = quoteDao.getQuoteByText(text)

    override suspend fun insertQuote(quote: Quote) = quoteDao.insertQuote(quote)

    override suspend fun deleteQuote(quote: Quote) = quoteDao.deleteQuote(quote)
}