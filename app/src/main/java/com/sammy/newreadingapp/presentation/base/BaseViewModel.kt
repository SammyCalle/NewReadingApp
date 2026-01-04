package com.sammy.newreadingapp.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

open class BaseViewModel : ViewModel(){

    private val _events = MutableSharedFlow<String>()
    val events: SharedFlow<String> = _events

    protected val errorHandler = CoroutineExceptionHandler { _, throwable ->
        viewModelScope.launch {
            _events.emit(throwable.message ?: "Unknown error")
        }
    }

    protected fun launchSafe(block: suspend () -> Unit) {
        viewModelScope.launch(errorHandler) {
            block()
        }
    }
}