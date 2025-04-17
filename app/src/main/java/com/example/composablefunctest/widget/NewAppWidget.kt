package com.example.composablefunctest.widget

import android.appwidget.AppWidgetManager
import android.content.Context
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.unit.dp
import androidx.glance.Button
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.action.ActionParameters
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.action.ActionCallback
import androidx.glance.appwidget.action.actionRunCallback
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.padding
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import com.example.domain.usecase.UserData.GetUserDataUseCase
import javax.inject.Inject

class CustomWidget @Inject constructor(
    private val getUserDataUseCase: GetUserDataUseCase? = null
) : GlanceAppWidget() {

    init {
        actionRunCallback<UpdateAppWidget>()
    }

    override suspend fun provideGlance(context: Context, id: GlanceId) {

        val data = getUserDataUseCase?.let { it() }
        provideContent {

            Column(
                modifier = GlanceModifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(5.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "qwe",
                    style = TextStyle(
                        color = ColorProvider(MaterialTheme.colorScheme.onPrimary)
                    )
                )
                Button(
                    text = "qweqwe",
                    onClick = actionRunCallback<GetUserDataAction>()
                )

                if (data != null){
                    Text(
                        text = data.radioButtonText
                    )
                }
            }
        }
    }


}

class WidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget
        get() = CustomWidget()

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)

        actionRunCallback<UpdateAppWidget>()
    }
}

class UpdateAppWidget : ActionCallback {
    override suspend fun onAction(
        context: Context,
        glanceId: GlanceId,
        parameters: ActionParameters
    ) {
        CustomWidget().update(context, glanceId)
    }
}

class GetUserDataAction : ActionCallback {
    override suspend fun onAction(
        context: Context,
        glanceId: GlanceId,
        parameters: ActionParameters
    ) {
        CustomWidget()
    }
}