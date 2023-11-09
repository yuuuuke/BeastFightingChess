package com.zhpew.beastfightingchess.view

import android.util.Log
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zhpew.beastfightingchess.GameController
import com.zhpew.beastfightingchess.R
import com.zhpew.beastfightingchess.model.CellBean
import com.zhpew.beastfightingchess.px

@Composable
fun CellView(cellBean: CellBean, index: Int) {

    val isSelected = index == GameController.state.value.selectedIndex

    var modifier: Modifier = Modifier
        .width(55.dp)
        .height(55.dp)
    modifier = if (cellBean.isCover) {
        // 覆盖状态
        modifier.border(1.dp, Color.Black, RoundedCornerShape(20))
    } else {
        if (cellBean.isRed) {
            modifier.border(1.dp, Color.Red, RoundedCornerShape(20))
        } else {
            modifier.border(1.dp, Color.Blue, RoundedCornerShape(20))
        }
    }
    val level = when (cellBean.level) {
        1 -> "鼠"
        2 -> "猫"
        3 -> "狗"
        4 -> "狼"
        5 -> "豹"
        6 -> "虎"
        7 -> "狮"
        8 -> "象"
        else -> "ERROR"
    }

    Column(
        modifier = Modifier
            .width(60.dp)
            .height(60.dp)
            .clickable {
                GameController.onItemClick(index)
            },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (cellBean.isBlock) {
            Box(modifier = Modifier
                .width(55.dp)
                .height(55.dp))
        }else{
            if (cellBean.isCover) {
                Column(
                    modifier = modifier,
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(painter = painterResource(id = R.drawable.unknow), contentDescription = "")
                }
            } else {
                val rotate = remember {
                    mutableStateOf(false)
                }
                val animator = animateIntAsState(
                    targetValue = if (rotate.value) 135 else 0,
                    animationSpec = TweenSpec(durationMillis = 100, easing = LinearEasing),
                    finishedListener = {
                        rotate.value = !rotate.value
                    })
                Box(modifier = Modifier
                    .height(55.dp)
                    .width(55.dp)) {
                    Column(
                        modifier = modifier,
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(
                            text = level, style = TextStyle(
                                color = if (cellBean.isRed) Color.Red else Color.Blue,
                                fontSize = 15.sp
                            )
                        )
                    }
                    if (isSelected) {

                        LaunchedEffect(isSelected){
                            rotate.value = true
                        }

                        val paint = remember {
                            Paint().apply {
                                style = PaintingStyle.Stroke
                                strokeWidth = 10f
                                color = Color.Black
                                strokeCap = StrokeCap.Round
                            }
                        }
                        val rect = remember {
                            Rect(Offset(27.px, 27.px), 15.px)
                        }
                        Canvas(modifier = Modifier.fillMaxSize()) {
                            drawIntoCanvas {
                                it.drawArc(
                                    rect,
                                    animator.value.toFloat(),
                                    45f,
                                    false,
                                    paint
                                )
                            }
                            drawIntoCanvas {
                                it.drawArc(
                                    rect,
                                    360 - animator.value.toFloat() - 45,
                                    45f,
                                    false,
                                    paint
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}