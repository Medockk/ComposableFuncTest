package com.example.composablefunctest.SegmentButton

sealed class SegmentButtonEvent  {

    data class SetSingleSegmentButtonIndex(val value: Int) : SegmentButtonEvent()
    data class SetMultiSegmentButton(val index: Int, val value: Boolean) : SegmentButtonEvent()
}