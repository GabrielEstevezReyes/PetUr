package com.example.urpet.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.urpet.repository.UltimaComidaRepository
import java.lang.IllegalArgumentException

/**
 * Created by Carlos Elliot Frias Mercado (Maku) on 08/09/2020.
 */
class UltimaComidaViewModelFactory(private val repository: UltimaComidaRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(UltimaComidaViewModel::class.java)){
            return UltimaComidaViewModel(repository) as T
        }
        throw IllegalArgumentException("View Model Desconocido")
    }

}