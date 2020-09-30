package com.example.urpet.data.local.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = UltimaComidaEntity.nombreTabla)
data class UltimaComidaEntity(
        @PrimaryKey(autoGenerate = true) var id:Int?,
        var nombre: String?,
        var descripcion: String?,
        var fecha: String?,
        var presentacion: String?

) {

   // constructor(nombre: String?, descripcion: String?, fecha: String?, presentacion: String?): this(nombre, descripcion, fecha, presentacion)

    companion object{
        const val nombreTabla:String = "UltimaComida"
    }

}