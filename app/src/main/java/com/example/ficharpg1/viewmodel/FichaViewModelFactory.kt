package com.example.ficharpg1.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.ficharpg1.model.dao.FichaDao


class FichaViewModelFactory(
    private val fichaDao: FichaDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FichaViewModel::class.java)) {
            return FichaViewModel(fichaDao) as T
        }
        throw IllegalArgumentException("Classe ViewModel desconhecida")
    }
}