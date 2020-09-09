package com.example.urpet.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.urpet.database.entity.UltimaComidaEntity
import com.example.urpet.repository.UltimaComidaRepository
import kotlinx.coroutines.launch

/**
 * Created by Carlos Elliot Frias Mercado (Maku) on 08/09/2020.
 */

class UltimaComidaViewModel(private val repository: UltimaComidaRepository) : ViewModel(){

    val ultimaComida = repository.ultimaComida

    val comidas = repository.comidas

    fun insert(ultimaComidaEntity: UltimaComidaEntity){
        viewModelScope.launch {
            repository.insert(ultimaComidaEntity)
        }
    }


}