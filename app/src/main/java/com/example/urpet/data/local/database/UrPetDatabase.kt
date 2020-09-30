package com.example.urpet.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.urpet.data.local.database.dao.UltimaComidaDAO
import com.example.urpet.data.local.database.entity.UltimaComidaEntity


@Database(entities = [UltimaComidaEntity::class], version = 1, exportSchema = false)
abstract class UrPetDatabase : RoomDatabase() {

    companion object {
        private const val DB_NAME: String = "UrPet_db"

        @Volatile
        private var mInstanciaBD: UrPetDatabase? = null

        fun getInstance(context: Context): UrPetDatabase? {
            val tempDB = mInstanciaBD
            if(tempDB != null){
                return tempDB
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                        context.applicationContext,
                        UrPetDatabase::class.java,
                        DB_NAME
                ).build()
                mInstanciaBD = instance
                return mInstanciaBD
            }

        }
    }

    abstract fun mUltimaComidaDao(): UltimaComidaDAO?
}
