package com.example.domain.repository

import com.example.domain.model.UserDataModel

interface UserDataRepository {

    suspend fun changeUserImage(newImage: ByteArray) : ByteArray
    suspend fun changeUserName(userName: String) : String

    suspend fun getVideoTime() : Long
    suspend fun setVideoTime(time: Long)

    suspend fun getRadioButtonText() : String
    suspend fun setRadioButtonText(text: String)

    suspend fun getUseData() : UserDataModel?
}