package com.example.composablefunctest.common

import android.content.Context
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.state.updateAppWidgetState
import com.example.composablefunctest.widget.CustomWidget

suspend fun updateWidgetState(context: Context, value: String) {

    GlanceAppWidgetManager(context).getGlanceIds(CustomWidget::class.java).forEach { id ->

        updateAppWidgetState(context, id){
            it[WidgetKey.nameIdKey] = value
        }

        CustomWidget().update(context, id)
    }

}