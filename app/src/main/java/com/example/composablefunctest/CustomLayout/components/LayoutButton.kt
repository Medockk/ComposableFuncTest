package com.example.composablefunctest.CustomLayout.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.unit.dp
import com.example.composablefunctest.CustomLayout.LayoutIDs

@Composable
fun LayoutButton(modifier: Modifier = Modifier) {
    Layout(
        modifier = modifier,
        content = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(55.dp)
                    .padding(horizontal = 20.dp)
                    .background(MaterialTheme.colorScheme.onPrimary, RoundedCornerShape(10.dp)),
            )
            Box(
                modifier = Modifier
                    .layoutId(LayoutIDs.ButtonId)
                    .padding(3.dp)
                    .width(50.dp)
                    .height(50.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(MaterialTheme.colorScheme.primary),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.KeyboardArrowRight,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier
                )
            }

            Box(
                Modifier
                    .layoutId(LayoutIDs.LayoutEndId)
            )
        },
        measurePolicy = { measurables: List<Measurable>, constraint ->

            val place = measurables.map { it.measure(constraint) }

            layout(constraint.maxWidth, constraint.maxHeight) {

                place.forEach {
                    it.placeRelative(0,0)
                }
            }
        }
    )
}