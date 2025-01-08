package com.example.automaticdoorsapk.userInterface.function.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.automaticdoorsapk.userInterface.function.data.Tag

class RegisterTagViewModel : ViewModel() {
    private val _tagName = MutableLiveData<String>()
    val tagName: LiveData<String> get() = _tagName

    private val _tagDescription = MutableLiveData<String>()
    val tagDescription: LiveData<String> get() = _tagDescription

    private val _isTagValid = MutableLiveData<Boolean>()
    val isTagValid: LiveData<Boolean> get() = _isTagValid

    fun setTagName(name: String) {
        _tagName.value = name
        validateTag()
    }

    fun setTagDescription(description: String) {
        _tagDescription.value = description
        validateTag()
    }

    private fun validateTag() {
        _isTagValid.value = !(_tagName.value.isNullOrEmpty() || _tagDescription.value.isNullOrEmpty())
    }

    fun saveTag() {
        val tag = Tag(id = "123456789", name = _tagName.value.orEmpty(), description = _tagDescription.value.orEmpty())
        // Salvar a tag no repositório (API, banco de dados, etc)
        // Exemplo: tagRepository.save(tag)
        println("Tag salva: $tag")
    }
}
