package com.example.domain.repository

import com.example.domain.model.NetworkResult
import kotlinx.coroutines.flow.Flow

interface ConfigurationRepository {

    suspend fun isApplicationInDarkTheme(id: Int) : Flow<NetworkResult<Boolean?>>
    suspend fun setApplicationTheme(theme: Boolean?, id: Int)

}