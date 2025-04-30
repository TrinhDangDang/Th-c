package com.example.thuc

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.thuc.data.Alarm
import com.example.thuc.data.Quote
import com.example.thuc.data.ThucRepository
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ThucViewModel(private val thucRepository: ThucRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    fun selectAlarm(alarm: Alarm) {
        _uiState.value = _uiState.value.copy(currentSelectedAlarm = alarm)
    }

    val allAlarmsDataStream: StateFlow<List<Alarm>>  = thucRepository.getAlarmsStream().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
        initialValue = emptyList()
    )

    val allQuotesDataStream: StateFlow<List<Quote>> = thucRepository.getQuotesStream().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
        initialValue = emptyList()
    )

//    fun getAlarmByLabel(alarmLabel: String) {
//        viewModelScope.launch {
//            thucRepository.getAlarmByLabel(alarmLabel).collect { alarm ->
//                _uiState.value = _uiState.value.copy(currentSelectedAlarm = alarm)
//            }
//        }
//    }


    fun insertAlarm(alarm: Alarm){
        viewModelScope.launch {
            thucRepository.insertAlarm(alarm)
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



    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
        val Factory = viewModelFactory {
            initializer {
                val application =
                    checkNotNull(this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY])
                            as ThucApplication
                ThucViewModel(application.container.thucRepository)
            }
        }
    }
}

data class UiState(
    val currentSelectedAlarm: Alarm? = null,
    val alarms: List<Alarm> = emptyList(),
    val quotes: List<Quote> = emptyList(),

)
