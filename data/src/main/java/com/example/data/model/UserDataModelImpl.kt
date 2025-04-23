package com.example.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.domain.model.UserDataModel

@Entity
data class UserDataModelImpl(
    @PrimaryKey override val id: Int? = 0,
    @ColumnInfo(defaultValue = "") override val userName: String = "",
    override val userImage: ByteArray? = null,
    override val videoTime: Long = 0L,
    override val radioButtonText: String = "",
    override val carouselItem: Int = 0,


    ) : UserDataModel
