package com.isetr.tp5app.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.isetr.tp5app.model.Etudiant

class EtudiantViewModel : ViewModel() {

    private val _etudiants= MutableLiveData<MutableList<Etudiant>>()
    val etudiants: LiveData<MutableList<Etudiant>> = _etudiants
    init{
        _etudiants.value = mutableListOf(
            Etudiant(101, "M1 Info", "ali@isetr.tn"),
            Etudiant(102, "M2 Info", "Nadia@isetr.tn"),
            Etudiant(103, "L1 Info", "Amel@isetr.tn"),
            Etudiant(104, "L3 Info", "Adel@isetr.tn") )
    }
    fun addEtudiant(etudiant : Etudiant){
        val currentList= _etudiants.value ?:mutableListOf()
        currentList.add(etudiant)
        _etudiants.value=currentList
    }

    private val _etudiantSelectionne = MutableLiveData<Etudiant?>()
    val etudiantSelectionne: LiveData<Etudiant?> = _etudiantSelectionne

    fun selectEtudiant(etudiant: Etudiant) {
        _etudiantSelectionne.value = etudiant
    }


}