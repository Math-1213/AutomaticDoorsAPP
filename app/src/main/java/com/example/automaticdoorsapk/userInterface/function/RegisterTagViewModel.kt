package com.example.automaticdoorsapk.userInterface.function

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.automaticdoorsapk.network.MqttManager
import com.example.automaticdoorsapk.userInterface.function.data.Tag

class RegisterTagViewModel(private val mqttManager: MqttManager) : ViewModel() {

    // Estado do modo de registro
    private val _isRegisterMode = MutableLiveData(false)
    val isRegisterMode: LiveData<Boolean> get() = _isRegisterMode

    // Estado do ID da tag detectada
    private val _tagId = MutableLiveData<String>()
    val tagId: LiveData<String> get() = _tagId

    // Estado do nome do proprietário da tag
    private val _tagName = MutableLiveData<String>()
    val tagName: LiveData<String> get() = _tagName

    // Estado da descrição da tag
    private val _tagDescription = MutableLiveData<String>()
    val tagDescription: LiveData<String> get() = _tagDescription

    // Estado da validade da tag
    private val _isTagValid = MutableLiveData<Boolean>()
    val isTagValid: LiveData<Boolean> get() = _isTagValid

    // Estado da Conexão MQTT
    private val _isConnected = MutableLiveData<Boolean>(false) // Status de conexão MQTT
    val isConnected: LiveData<Boolean> get() = _isConnected

    fun setConnectionStatus(isConnected: Boolean) {
        _isConnected.value = isConnected
    }

    init {
        validateTag()
    }

    private fun clearTagData() {
        _tagId.value = ""
        _tagName.value = ""
        _tagDescription.value = ""
        _isTagValid.value = false
    }

    // Alternar modo entre Registro e Funcionamento
    fun toggleMode() {
        if (_isRegisterMode.value == true) {
            // Envia a mensagem MQTT para parar o registro
            mqttManager.publish("home/doors/stopRegisterTag", "1")
            clearTagData()
        } else {
            // Envia a mensagem MQTT para iniciar o registro
            mqttManager.publish("home/doors/registerTag", "1")
        }
        _isRegisterMode.value = !_isRegisterMode.value!!
    }

    // Atualizar o modo de registro
    fun setRegisterMode(isInRegMode: Boolean) {
        _isRegisterMode.value = isInRegMode
    }

    // Atualizar o ID da tag detectada
    fun setTagId(id: String) {
        _tagId.value = id
        validateTag()  // Validar após definir o ID da tag
    }

    // Atualizar o nome do proprietário da tag
    fun setTagName(name: String) {
        _tagName.value = name
        validateTag()  // Validar após alterar o nome
    }

    // Atualizar a descrição da tag
    fun setTagDescription(description: String) {
        _tagDescription.value = description
        validateTag()  // Validar após alterar a descrição
    }

    // Validar se os campos da tag estão preenchidos
    private fun validateTag() {
        // A tag é válida se o ID, nome e descrição não estiverem vazios
        _isTagValid.value = !(_tagId.value.isNullOrEmpty() || _tagName.value.isNullOrEmpty() || _tagDescription.value.isNullOrEmpty())
    }

    // Salvar a tag registrada
    fun saveTag() {
        if (_isRegisterMode.value == true && _isTagValid.value == true) {
            val tag = Tag(
                id = _tagId.value.orEmpty(),
                name = _tagName.value.orEmpty(),
                description = _tagDescription.value.orEmpty()
            )
            println("Tag salva: $tag")
            mqttManager.publish("home/doors/Registro", tag.toString())
            toggleMode()
        }
    }
}
