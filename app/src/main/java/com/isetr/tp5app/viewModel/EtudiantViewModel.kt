package com.isetr.tp5app.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.isetr.tp5app.data.model.Etudiant
import com.isetr.tp5app.data.repository.EtudiantRepository
import kotlinx.coroutines.launch

class EtudiantViewModel(private val repository: EtudiantRepository) : ViewModel() {

    val etudiants: LiveData<List<Etudiant>> = repository.allEtudiants

    private val _selectedEtudiant = MutableLiveData<Etudiant?>()
    val selectedEtudiant: LiveData<Etudiant?> = _selectedEtudiant
    private val _etudiantsSearchResults = MutableLiveData<List<Etudiant>>()
    val etudiantsSearchResults: LiveData<List<Etudiant>> = _etudiantsSearchResults

    fun setSearchQuery(query: String) {
        viewModelScope.launch {
            _etudiantsSearchResults.value = repository.searchByClasse(query)
        }
    }

    fun selectEtudiant(etudiant: Etudiant) {
        _selectedEtudiant.value = etudiant
    }
    fun addEtudiant(etudiant: Etudiant) {
        viewModelScope.launch { repository.insert(etudiant) }
    }

    fun updateEtudiant(etudiant: Etudiant) {
        viewModelScope.launch { repository.update(etudiant) }
    }

    fun deleteEtudiant(etudiant: Etudiant) {
        viewModelScope.launch { repository.delete(etudiant) }
    }



}