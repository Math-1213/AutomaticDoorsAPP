package com.example.automaticdoorsapk.userInterface.function

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.automaticdoorsapk.network.MqttManager
import com.example.automaticdoorsapk.userInterface.function.data.Tag


class RegisterTagViewModel(private val mqttManager: MqttManager) : ViewModel() {

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

    // Estado do modo atual (Funcionamento ou Registro)
    private val _isRegisterMode = MutableLiveData<Boolean>(false)
    val isRegisterMode: LiveData<Boolean> get() = _isRegisterMode

    init {
        validateTag()
    }

    // Alternar modo entre Registro e Funcionamento
    fun toggleMode() {
        if (_isRegisterMode.value == true) {
            mqttManager.publish("home/doors/stopRegisterTag", "")
        } else {
            mqttManager.publish("home/doors/registerTag", "")
        }
        _isRegisterMode.value = !_isRegisterMode.value!!
    }

    // Atualizar o ID da tag detectada
    fun setTagId(id: String) {
        _tagId.value = id
    }

    // Atualizar o nome do proprietário da tag
    fun setTagName(name: String) {
        _tagName.value = name
        validateTag()
    }

    // Atualizar a descrição da tag
    fun setTagDescription(description: String) {
        _tagDescription.value = description
        validateTag()
    }

    // Validar se os campos da tag estão preenchidos
    private fun validateTag() {
        _isTagValid.value = !(_tagName.value.isNullOrEmpty() || _tagDescription.value.isNullOrEmpty())
    }

    // Salvar a tag registrada
    fun saveTag() {
        if (_isRegisterMode.value == true) {
            val tag = Tag(
                id = _tagId.value.orEmpty(),
                name = _tagName.value.orEmpty(),
                description = _tagDescription.value.orEmpty()
            )
            // Enviar a tag para o repositório ou banco de dados
            println("Tag salva: $tag")

            // Retornar para o modo de funcionamento
            toggleMode()
        }
    }
}