package com.example.automaticdoorsapk.userInterface.function

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class OpenCloseDoorsViewModel : ViewModel() {

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

    // Funções para alternar o estado das portas
    fun toggleInternalDoor() {
        _isInternalDoorOpen.value = _isInternalDoorOpen.value?.not()
        // Caso a porta interna seja aberta, fechamos a externa
        if (_isInternalDoorOpen.value == true) {
            _isExternalDoorOpen.value = false
        }
    }

    fun toggleExternalDoor() {
        _isExternalDoorOpen.value = _isExternalDoorOpen.value?.not()
        // Caso a porta externa seja aberta, fechamos a interna
        if (_isExternalDoorOpen.value == true) {
            _isInternalDoorOpen.value = false
        }
    }
}
