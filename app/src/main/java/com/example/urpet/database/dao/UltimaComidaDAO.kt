package com.example.urpet.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.urpet.database.entity.UltimaComidaEntity

@Dao
interface UltimaComidaDAO{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUltimaComida(comida: UltimaComidaEntity)

    @Query("SELECT * FROM " + UltimaComidaEntity.nombreTabla)
    fun getAllComidas(): List<UltimaComidaEntity>

    @Query("SELECT * FROM " + UltimaComidaEntity.nombreTabla + " ORDER BY ID DESC LIMIT 1")
    fun getUltimaComida(): UltimaComidaEntity

    @Query("DELETE FROM " + UltimaComidaEntity.nombreTabla)
    fun clearTable()
}