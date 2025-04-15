package com.example.data.data_source.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.data.data_source.local.dao.UserDataDao
import com.example.data.model.UserDataModelImpl

@Database(entities = [UserDataModelImpl::class], version = 1)
abstract class UserDataDatabase : RoomDatabase() {

    abstract val userDataDao: UserDataDao

    companion object {
        fun createDatabase(context: Context): UserDataDatabase {
            return Room.databaseBuilder(context, UserDataDatabase::class.java, "userData.db")
                .build()
        }
    }
}