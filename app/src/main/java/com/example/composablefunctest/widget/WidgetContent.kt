package com.example.composablefunctest.widget

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.Preferences
import androidx.glance.GlanceModifier
import androidx.glance.action.actionStartActivity
import androidx.glance.action.clickable
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import androidx.glance.text.Text
import androidx.glance.text.TextAlign
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import com.example.composablefunctest.Main.MainActivity
import com.example.composablefunctest.common.WidgetKey

private const val notRegisteredText = "You aren't registered!"

@Composable
fun WidgetContent(prefs: Preferences) {

    val nameID = if ((prefs[WidgetKey.nameIdKey] ?: notRegisteredText).isBlank()) {
        notRegisteredText
    } else {
        prefs[WidgetKey.nameIdKey] ?: notRegisteredText
    }


    Column(
        modifier = GlanceModifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
            .clickable(actionStartActivity<MainActivity>()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = nameID,
            style = TextStyle(
                color = ColorProvider(MaterialTheme.colorScheme.onPrimary),
                fontSize = 32.sp,
                textAlign = TextAlign.Center
            )
        )
    }
}