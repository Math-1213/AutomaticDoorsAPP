package com.example.automaticdoorsapk.userInterface.function

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.automaticdoorsapk.network.MqttManager

class OpenCloseDoorsViewModel(private val mqttManager: MqttManager) : ViewModel() {

    // Estado do nome do usuário
    private val _userName = MutableLiveData<String>()
    val userName: LiveData<String> get() = _userName

    // Estado das portas
    private val _isInternalDoorOpen = MutableLiveData<Boolean>(false)
    val isInternalDoorOpen: LiveData<Boolean> get() = _isInternalDoorOpen

    private val _isExternalDoorOpen = MutableLiveData<Boolean>(false)
    val isExternalDoorOpen: LiveData<Boolean> get() = _isExternalDoorOpen

    // Função para definir o nome do usuário
    fun setUserName(name: String) {
        _userName.value = name
    }

    // Funções para abrir e fechar portas via MQTT
    fun openInternalDoor() {
        mqttManager.publish("home/doors/openIN", "")
        _isInternalDoorOpen.value = true
        _isExternalDoorOpen.value = false // Garante que a outra porta seja fechada
    }

    fun closeInternalDoor() {
        mqttManager.publish("home/doors/closeIN", "")
        _isInternalDoorOpen.value = false
    }

    fun openExternalDoor() {
        mqttManager.publish("home/doors/openOUT", "")
        _isExternalDoorOpen.value = true
        _isInternalDoorOpen.value = false // Garante que a outra porta seja fechada
    }

    fun closeExternalDoor() {
        mqttManager.publish("home/doors/closeOUT", "")
        _isExternalDoorOpen.value = false
    }

    // Funções para alternar o estado das portas
    fun toggleInternalDoor() {
        if (_isInternalDoorOpen.value == true) {
            closeInternalDoor()
        } else {
            openInternalDoor()
        }
    }

    fun toggleExternalDoor() {
        if (_isExternalDoorOpen.value == true) {
            closeExternalDoor()
        } else {
            openExternalDoor()
        }
    }
}
