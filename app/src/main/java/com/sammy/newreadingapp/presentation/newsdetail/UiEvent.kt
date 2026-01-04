package com.sammy.newreadingapp.presentation.newsdetail

sealed class UiEvent {
    data class ShowMessage(val message: String) : UiEvent()
}