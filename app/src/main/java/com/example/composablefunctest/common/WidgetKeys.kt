package com.example.composablefunctest.common

import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.glance.action.ActionParameters

object WidgetKey {

    val nameIdKey = stringPreferencesKey("NameIdKey")
    val nameIdParamKey = ActionParameters.Key<String>("NameParamKey")
}