package com.example.composablefunctest.SegmentButton

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList

data class SegmentButtonState(
    val singleButtonIndex: Int = 0,
    val multiSegmentState: SnapshotStateList<Boolean> = mutableStateListOf(false,false,false, false),
    val multiSegmentTitle: MutableList<String> = mutableListOf("First","Second","Third","Fourth"),
)
