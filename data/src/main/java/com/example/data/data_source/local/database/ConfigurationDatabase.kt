package com.example.data.data_source.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.data.data_source.local.dao.ConfigurationDao
import com.example.data.model.ConfigurationModelImpl

@Database(
    [ConfigurationModelImpl::class],
    version = 1
)
abstract class ConfigurationDatabase : RoomDatabase() {

    abstract val confDao: ConfigurationDao

    companion object {
        fun createDatabase(context: Context): ConfigurationDatabase {
            return Room.databaseBuilder(context, ConfigurationDatabase::class.java, "conf.db")
                .build()
        }
    }
}