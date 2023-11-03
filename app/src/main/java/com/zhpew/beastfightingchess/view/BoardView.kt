package com.zhpew.beastfightingchess.view

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.unit.dp
import com.zhpew.beastfightingchess.px

@Composable
fun BoardView() {
    Canvas(
        modifier = Modifier
            .width(240.dp)
            .height(240.dp)
    ) {
        drawIntoCanvas {
            val paint = Paint()
            paint.color = Color.Blue
            paint.style = PaintingStyle.Stroke
            val rect = Rect(0f, 0f, 240.px, 240.px)
            it.drawRect(rect, paint)
            it.drawLine(Offset(0f, 60.px), Offset(240.px, 60.px), paint)
            it.drawLine(Offset(0f, 120.px), Offset(240.px, 120.px), paint)
            it.drawLine(Offset(0f, 180.px), Offset(240.px, 180.px), paint)

            it.drawLine(Offset(60.px, 0f), Offset(60.px, 240.px), paint)
            it.drawLine(Offset(120.px, 0f), Offset(120.px, 240.px), paint)
            it.drawLine(Offset(180.px, 0f), Offset(180.px, 240.px), paint)
        }
    }
}