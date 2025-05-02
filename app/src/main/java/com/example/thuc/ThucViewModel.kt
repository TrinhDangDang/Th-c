package com.example.thuc

import android.os.Build
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.thuc.data.Alarm
import com.example.thuc.data.AlarmScheduler
import com.example.thuc.data.Quote
import com.example.thuc.data.ThucRepository
import com.example.thuc.data.UserPreferenceRepository
import com.example.thuc.data.getNextAlarmTimeInMillis
import com.example.thuc.network.AIRepository
import com.example.thuc.network.TogetherAIRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ThucViewModel(private val thucRepository: ThucRepository, private val userPreferenceRepository: UserPreferenceRepository, private val alarmScheduler: AlarmScheduler, private val togetherAIRepository: AIRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _toastMessage = MutableSharedFlow<String>()
    val toastMessage = _toastMessage


    fun selectAlarm(alarm: Alarm) {
        _uiState.value = _uiState.value.copy(currentSelectedAlarm = alarm)
    }

    val allAlarmsDataStream: StateFlow<List<Alarm>>  = thucRepository.getAlarmsStream().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
        initialValue = emptyList()
    )

    suspend fun isLabelAvailable(label: String): Boolean {
        return withContext(Dispatchers.IO) {
            thucRepository.getAlarmByLabel(label) == null
        }
    }

    val allQuotesDataStream: StateFlow<List<Quote>> = thucRepository.getQuotesStream().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
        initialValue = emptyList()
    )


    init {
        viewModelScope.launch {
            userPreferenceRepository.darkTheme.collect { darkTheme ->
                _uiState.value = _uiState.value.copy(darkTheme = darkTheme)
            }
        }
        viewModelScope.launch {
            allAlarmsDataStream.collect { alarms ->
                _uiState.value = _uiState.value.copy(alarms = alarms)
                val alarmManager = alarmScheduler.getSystemAlarmManager()
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S || alarmManager.canScheduleExactAlarms()) {
                    alarms.filter { it.isEnabled }.forEach { alarm ->
                        val timeInMillis = getNextAlarmTimeInMillis(alarm)
                        alarmScheduler.scheduleExactAlarm(timeInMillis, alarm.id)
                    }
                }

            }
        }
        viewModelScope.launch {
            allQuotesDataStream.collect {
                quotes ->
                _uiState.value = _uiState.value.copy(quotes = quotes)
            }
        }
    }

    fun insertAlarm(alarm: Alarm){
        viewModelScope.launch {
            val available = isLabelAvailable(alarm.label)
            if (available) {
                thucRepository.insertAlarm(alarm)
            } else {
                _toastMessage.emit("Alarm label '${alarm.label}' is already taken.")
            }
        }
    }

    fun insertQuote(quote: Quote){
        viewModelScope.launch {
            thucRepository.insertQuote(quote)
        }
    }

    fun updateAlarm(alarm: Alarm){
        viewModelScope.launch {
            thucRepository.updateAlarm(alarm)
        }
    }

    fun deleteAlarm(alarm: Alarm) {
        viewModelScope.launch {
            thucRepository.deleteAlarm(alarm)
        }
    }

    fun toggleDarkTheme(darkTheme: Boolean){
        viewModelScope.launch {
            userPreferenceRepository.saveDarkThemeEnabled(darkTheme)
        }
    }

    fun toggleAlarm(alarm: Alarm) {
        viewModelScope.launch {
            val updatedAlarm = alarm.copy(isEnabled = !alarm.isEnabled)
            thucRepository.updateAlarm(updatedAlarm)

            if (updatedAlarm.isEnabled) {
                val timeInMillis = getNextAlarmTimeInMillis(updatedAlarm)
                alarmScheduler.scheduleExactAlarm(timeInMillis, alarm.id)
            } else {
                alarmScheduler.cancelAlarm(alarm.id)
            }
        }
    }

    fun getMotivationalQuote() {
        viewModelScope.launch {
            val quote = togetherAIRepository.getQuote("Give me a motivational quote. make it short")
            _uiState.value = _uiState.value.copy(quote = quote)
            //insertQuote(Quote(text = quote))
        }
    }


    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
        val Factory = viewModelFactory {
            initializer {
                val application =
                    checkNotNull(this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY])
                            as ThucApplication
                ThucViewModel(application.container.thucRepository, application.container.userPreferenceRepository,
                    AlarmScheduler(application.applicationContext), application.container.togetherAIRepository)
            }
        }
    }
}

data class UiState(
    val quote: String = "",
    val darkTheme: Boolean = true,
    val currentSelectedAlarm: Alarm? = null,
    val alarms: List<Alarm> = emptyList(),
    val quotes: List<Quote> = emptyList(),

)
