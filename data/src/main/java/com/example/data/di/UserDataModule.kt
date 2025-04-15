package com.example.data.di

import android.content.Context
import com.example.data.data_source.local.dao.UserDataDao
import com.example.data.data_source.local.database.UserDataDatabase
import com.example.data.repository.UserDataRepositoryImpl
import com.example.domain.repository.UserDataRepository
import com.example.domain.usecase.UserData.ChangeUserImageUseCase
import com.example.domain.usecase.UserData.ChangeUserNameUseCase
import com.example.domain.usecase.UserData.GetUserDataUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UserDataModule {

    @Provides @Singleton
    fun getUserDataDao(@ApplicationContext context: Context): UserDataDao{
        return UserDataDatabase.createDatabase(context).userDataDao
    }

    @Provides @Singleton
    fun getUserDataRepo(userDataDao: UserDataDao): UserDataRepository{
        return UserDataRepositoryImpl(userDataDao)
    }

    @Provides @Singleton
    fun getChangeUserImage(userDataRepository: UserDataRepository) : ChangeUserImageUseCase{
        return ChangeUserImageUseCase(userDataRepository)
    }

    @Provides @Singleton
    fun getChangeUserName(userDataRepository: UserDataRepository) : ChangeUserNameUseCase{
        return ChangeUserNameUseCase(userDataRepository)
    }

    @Provides @Singleton
    fun getUserData(userDataRepository: UserDataRepository) : GetUserDataUseCase{
        return GetUserDataUseCase(userDataRepository)
    }
}