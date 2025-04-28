package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.domain.model.ConfigurationModel

@Entity
data class ConfigurationModelImpl(
    @PrimaryKey(true) override val id: Int? = 0,
    override val theme: Boolean?
) : ConfigurationModel
