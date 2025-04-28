package com.example.data.data_source.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.model.ConfigurationModelImpl

@Dao
interface ConfigurationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsertConfiguration(configurationModelImpl: ConfigurationModelImpl)

    @Query("select * from ConfigurationModelImpl where id =:id")
    fun getConfiguration(id: Int) : ConfigurationModelImpl?

    @Insert
    fun createConfigurationDatabase(configurationModelImpl: ConfigurationModelImpl)
}