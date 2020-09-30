package com.example.urpet.repository

import com.example.urpet.data.local.database.dao.UltimaComidaDAO
import com.example.urpet.data.local.database.entity.UltimaComidaEntity

/**
 * Created by Carlos Elliot Frias Mercado (Maku) on 08/09/2020.
 */

class UltimaComidaRepository(private val dao: UltimaComidaDAO){

    val comidas = dao.getAllComidas()

    val ultimaComida = dao.getUltimaComida()

    suspend fun insert(comidaEntity: UltimaComidaEntity){
        dao.insertUltimaComida(comidaEntity)
    }

}