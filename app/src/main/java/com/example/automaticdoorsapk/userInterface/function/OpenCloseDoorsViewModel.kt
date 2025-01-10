package com.example.automaticdoorsapk.userInterface.function

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
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

    private fun publishDoorState(topic: String, msg: String) {
        if (mqttManager.isConnected()) {
            mqttManager.publish(topic, msg)
        } else {
            println("MQTT Não Inicializado")
        }
    }

    // Funções para abrir/fechar a porta interna
    private fun openInternalDoor() {
        publishDoorState("home/doors/openIN", "1")
        _isInternalDoorOpen.value = true // Correto
    }

    private fun closeInternalDoor() {
        publishDoorState("home/doors/closeIN", "1")
        _isInternalDoorOpen.value = false // Corrigido para 'false'
    }

    // Funções para abrir/fechar a porta externa
    private fun openExternalDoor() {
        publishDoorState("home/doors/openOUT", "1")
        _isExternalDoorOpen.value = true // Correto
    }

    private fun closeExternalDoor() {
        publishDoorState("home/doors/closeOUT", "1")
        _isExternalDoorOpen.value = false // Corrigido para 'false'
    }

    // Funções para alternar o estado das portas
    fun toggleInternalDoor() {
        if (_isInternalDoorOpen.value == true) {
            closeInternalDoor()
        } else {
            // Caso a porta interna seja aberta, fechar a externa
            if (_isExternalDoorOpen.value == true) closeExternalDoor()
            openInternalDoor()
        }
    }

    fun toggleExternalDoor() {
        if (_isExternalDoorOpen.value == true) {
            closeExternalDoor()
        } else {
            // Caso a porta externa seja aberta, fechar a interna
            if (_isInternalDoorOpen.value == true) closeInternalDoor()
            openExternalDoor()
        }
    }
}

class OpenCloseDoorsViewModelFactory(private val mqttManager: MqttManager) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OpenCloseDoorsViewModel::class.java)) {
            return OpenCloseDoorsViewModel(mqttManager) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}