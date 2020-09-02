package com.example.urpet.database.repository

import androidx.lifecycle.LiveData
import com.example.urpet.database.dao.UltimaComidaDAO
import com.example.urpet.database.entity.UltimaComidaEntity

// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO
class UltimaComidaRepository(private val comidaDAO: UltimaComidaDAO) {

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    val allComidas: List<UltimaComidaEntity> = comidaDAO.getAllComidas()

    fun insertComida(comida: UltimaComidaEntity) {
        comidaDAO.insertUltimaComida(comida)
    }
}