package com.zhpew.beastfightingchess.model

data class CellBean(
    var isCover: Boolean = false, // 是否是覆盖状态
    var isBlock: Boolean = false,   // 是否是空位
    var isRed:Boolean = false, // 红方还是蓝方
    var level:Int = 0   // 象，狮，虎，豹，犬，狼，狗，猫，鼠，从8 - 1
)