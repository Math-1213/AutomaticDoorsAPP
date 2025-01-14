package com.example.automaticdoorsapk.userInterface.function

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.automaticdoorsapk.network.MqttManager
import com.example.automaticdoorsapk.userInterface.function.data.room.LogEntry
import com.example.automaticdoorsapk.userInterface.function.data.room.LogViewModel
import com.google.android.gms.location.FusedLocationProviderClient


class OpenCloseDoorsViewModel(
    private val mqttManager: MqttManager,
    private val logViewModel: LogViewModel,
    private val context: Context,
    private val fusedLocationClient: FusedLocationProviderClient) : ViewModel() {

    private val _userName = MutableLiveData<String>()
    val userName: LiveData<String> get() = _userName

    private val _isInternalDoorOpen = MutableLiveData<Boolean>(false)
    val isInternalDoorOpen: LiveData<Boolean> get() = _isInternalDoorOpen

    private val _isExternalDoorOpen = MutableLiveData<Boolean>(false)
    val isExternalDoorOpen: LiveData<Boolean> get() = _isExternalDoorOpen

    private val _isConnected = MutableLiveData<Boolean>(false) // Estado de conexão MQTT
    val isConnected: LiveData<Boolean> get() = _isConnected

    fun setUserName(name: String) {
        _userName.value = name
    }

    fun setConnectionStatus(isConnected: Boolean) {
        _isConnected.value = isConnected
    }

    private fun publishDoorState(topic: String, msg: String) {
        if (mqttManager.isConnected()) {
            mqttManager.publish(topic, msg)
        } else {
            println("MQTT Não Inicializado")
        }
    }

    // Função para registrar um log
    private fun logDoorAction(doorName: String, action: String) {
        val locationHelper = LocationHelper(context, fusedLocationClient)

        locationHelper.getLocationAsString { address ->
            val locationInfo = address ?: "Localização não disponível"

            val logEntry = LogEntry(
                date = System.currentTimeMillis(),
                loginName = _userName.value ?: "unknown",
                methodOfLogin = "MQTT",
                additionalInfo = "Porta $doorName foi $action na localização: $locationInfo"
            )

            logViewModel.insertLog(logEntry)
        }
    }

    // Funções para abrir/fechar a porta interna
    private fun openInternalDoor() {
        publishDoorState("home/doors/openIN", "1")
        _isInternalDoorOpen.value = true
        logDoorAction("Interna", "aberta")  // Registrar log
    }

    private fun closeInternalDoor() {
        publishDoorState("home/doors/closeIN", "1")
        _isInternalDoorOpen.value = false
        logDoorAction("Interna", "fechada")  // Registrar log
    }

    // Funções para abrir/fechar a porta externa
    private fun openExternalDoor() {
        publishDoorState("home/doors/openOUT", "1")
        _isExternalDoorOpen.value = true
        logDoorAction("Externa", "aberta")  // Registrar log
    }

    private fun closeExternalDoor() {
        publishDoorState("home/doors/closeOUT", "1")
        _isExternalDoorOpen.value = false
        logDoorAction("Externa", "fechada")  // Registrar log
    }

    // Funções para alternar o estado das portas
    fun toggleInternalDoor() {
        if (_isInternalDoorOpen.value == true) {
            closeInternalDoor()
        } else {
            if (_isExternalDoorOpen.value == true) closeExternalDoor()
            openInternalDoor()
        }
    }

    fun toggleExternalDoor() {
        if (_isExternalDoorOpen.value == true) {
            closeExternalDoor()
        } else {
            if (_isInternalDoorOpen.value == true) closeInternalDoor()
            openExternalDoor()
        }
    }
}

class OpenCloseDoorsViewModelFactory(
    private val mqttManager: MqttManager,
    private val logViewModel: LogViewModel,
    private val context: Context,
    private val fusedLocationClient: FusedLocationProviderClient
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OpenCloseDoorsViewModel::class.java)) {
            return OpenCloseDoorsViewModel(mqttManager, logViewModel, context, fusedLocationClient) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

