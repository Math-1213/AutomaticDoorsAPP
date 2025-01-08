package com.example.automaticdoorsapk.userInterface.function

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.automaticdoorsapk.userInterface.function.data.Tag

class TagsViewModel : ViewModel() {

    // Lista de tags registradas
    private val _tags = MutableLiveData<List<Tag>>(listOf())
    val tags: LiveData<List<Tag>> get() = _tags

    // Seleção de tag para edição
    private val _selectedTag = MutableLiveData<Tag?>()
    val selectedTag: LiveData<Tag?> get() = _selectedTag

    // Carregar tags (simulação ou integração com banco de dados)
    fun loadTags() {
        // Substituir por carregamento real de banco ou API
        _tags.value = listOf(
            Tag("1", "Tag 1", "Descrição 1"),
            Tag("2", "Tag 2", "Descrição 2"),
            Tag("3", "Tag 3", "Descrição 3")
        )
    }

    // Selecionar uma tag para edição
    fun selectTag(tag: Tag) {
        _selectedTag.value = tag
    }

    // Atualizar tag
    fun updateTag(updatedTag: Tag) {
        _tags.value = _tags.value?.map { if (it.id == updatedTag.id) updatedTag else it }
    }
}
