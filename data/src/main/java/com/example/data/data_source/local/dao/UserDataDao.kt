package com.example.data.data_source.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.model.UserDataModelImpl

@Dao
interface UserDataDao {

    @Insert
    fun createUserDatabase(userDataModelImpl: UserDataModelImpl)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsertUserData(userDataModelImpl: UserDataModelImpl)

    @Query("SELECT * FROM UserDataModelImpl WHERE id = :id")
    fun getUserData(id: Int? = 0) : UserDataModelImpl?
}