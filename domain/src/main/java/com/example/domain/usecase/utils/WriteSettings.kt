package com.example.domain.usecase.utils

import android.content.Context
import android.content.Intent
import android.provider.Settings
import androidx.core.net.toUri
import kotlin.math.roundToInt

class WriteSettings(
    private val context: Context
) {

    /**
     * Check, can application change system settings
     */
    operator fun invoke() {
        with(context) {
            if (Settings.System.canWrite(this)) return

            startActivity(
                Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS)
                    .setData("package:$packageName".toUri())
            )
        }
    }

    /**
     * Set system brightness
     * @param value The value of screen brightness
     */
    operator fun invoke(value: Float){
        Settings.System.putInt(
            context.contentResolver,
            Settings.System.SCREEN_BRIGHTNESS,
            value.roundToInt() / 5
        )
    }
}