package com.example.urpet.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.urpet.database.entity.UltimaComidaEntity

@Dao
interface UltimaComidaDAO{
    @Insert
    suspend fun insertUltimaComida(comida: UltimaComidaEntity)

    @Query("SELECT * FROM " + UltimaComidaEntity.nombreTabla)
    fun getAllComidas(): LiveData<List<UltimaComidaEntity>>

    @Query("SELECT * FROM " + UltimaComidaEntity.nombreTabla + " ORDER BY ID DESC LIMIT 1")
    fun getUltimaComida(): LiveData<UltimaComidaEntity>

    @Query("DELETE FROM " + UltimaComidaEntity.nombreTabla)
    suspend fun clearTable()
}