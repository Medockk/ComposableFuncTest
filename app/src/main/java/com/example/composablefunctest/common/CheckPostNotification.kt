package com.example.composablefunctest.common

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.RequiresApi

fun checkPostNotification(activity: Activity, context: Context): Boolean {

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        if (!checkPermission(context)) {
            requestPermissions(activity)
        }

        return checkPermission(context)
    }

    return true
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
private fun requestPermissions(activity: Activity) {
    activity.requestPermissions(arrayOf(Manifest.permission.POST_NOTIFICATIONS), 1)

}

/**
 * Return true, if permission is accept
 */
@RequiresApi(Build.VERSION_CODES.TIRAMISU)
private fun checkPermission(context: Context): Boolean =
    context.checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) ==
            PackageManager.PERMISSION_GRANTED
