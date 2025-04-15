@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.composablefunctest.common

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp

@Composable
fun CustomTopAppBar(
    modifier: Modifier = Modifier,
    title: String = "Home",
    icon: ImageVector = Icons.Default.Menu,
    userImage: Bitmap? = null,
    navigationClick: () -> Unit
) {
    TopAppBar(
        modifier = modifier,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        windowInsets = WindowInsets(0,0,0,0),
        navigationIcon = {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier
                    .clickable { navigationClick() }
            )
        },
        title = {
            Text(
                text = title,
                color = Color.White
            )
        },
        actions = {
            if (userImage != null){
                Image(
                    bitmap = userImage.asImageBitmap(),
                    contentDescription = null,
                    modifier = Modifier
                        .size(55.dp)
                        .border(1.dp, Color.Black.copy(0.7f), CircleShape)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            }
        }
    )
}