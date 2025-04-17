package com.example.data.repository

import com.example.data.data_source.local.dao.UserDataDao
import com.example.data.model.UserDataModelImpl
import com.example.domain.model.UserDataModel
import com.example.domain.repository.UserDataRepository

class UserDataRepositoryImpl(
    private val userDataDao: UserDataDao
) : UserDataRepository {

    override suspend fun changeUserImage(newImage: ByteArray): ByteArray {
        createUserDatabase()
        userDataDao.upsertUserData(userDataDao.getUserData()!!.copy(userImage = newImage))

        return newImage
    }

    override suspend fun changeUserName(userName: String): String {
        createUserDatabase()
        userDataDao.upsertUserData(userDataDao.getUserData()!!.copy(userName = userName))

        return userName
    }

    override suspend fun getVideoTime(): Long {
        createUserDatabase()

        return userDataDao.getUserData()?.videoTime ?: 0L
    }

    override suspend fun setVideoTime(time: Long) {
        createUserDatabase()

        userDataDao.upsertUserData(
            userDataDao.getUserData()!!
                .copy(videoTime = time)
        )
    }

    override suspend fun getUseData(): UserDataModel? {
        createUserDatabase()

        return userDataDao.getUserData()
    }

    private fun createUserDatabase(id: Int? = 0) {
        if (userDataDao.getUserData(id) == null){
            userDataDao.createUserDatabase(UserDataModelImpl(id))
        }
    }

    override suspend fun getRadioButtonText(): String {
        createUserDatabase()

        return userDataDao.getUserData()?.radioButtonText ?: ""
    }

    override suspend fun setRadioButtonText(text: String) {
        createUserDatabase()

        userDataDao.upsertUserData(
            userDataDao.getUserData()!!
                .copy(radioButtonText = text)
        )
    }
}