package com.example.automaticdoorsapk.userInterface.function

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.automaticdoorsapk.network.MqttManager


class RegisterTagViewModelFactory(
    private val mqttManager: MqttManager
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegisterTagViewModel::class.java)) {
            return RegisterTagViewModel(mqttManager) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
