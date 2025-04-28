package com.example.data.repository

import com.example.data.data_source.local.dao.ConfigurationDao
import com.example.data.model.ConfigurationModelImpl
import com.example.domain.model.NetworkResult
import com.example.domain.repository.ConfigurationRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class ConfigurationRepositoryImpl(
    private val confDao: ConfigurationDao
) : ConfigurationRepository {

    override suspend fun isApplicationInDarkTheme(id: Int) = flow<NetworkResult<Boolean?>> {

        emit(NetworkResult.Loading())
        createDatabase()

        emit(NetworkResult.Success(confDao.getConfiguration(id)?.theme))
    }.catch {
        emit(NetworkResult.Error(it.localizedMessage))
    }

    override suspend fun setApplicationTheme(theme: Boolean?, id: Int) {
        createDatabase(id)

        confDao.upsertConfiguration(
            confDao.getConfiguration(id)!!.copy(
                theme = theme
            )
        )
    }

    private fun createDatabase(id: Int = 0) {
        if (confDao.getConfiguration(id) == null){
            confDao.createConfigurationDatabase(
                ConfigurationModelImpl(id, null)
            )
        }
    }
}