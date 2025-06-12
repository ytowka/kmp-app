package com.example.kmpapp.android.feature.content.ui

import kotlin.math.abs

object ContentUtils {

    private const val format = "%.1f"

    fun formatMark(avg: Number): String =
        if(avg is Float){
            com.example.kmpapp.android.feature.content.ui.ContentUtils.format.format(avg)
        }else avg.toString()
}