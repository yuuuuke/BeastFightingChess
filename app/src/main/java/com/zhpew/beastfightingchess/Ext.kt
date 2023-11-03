package com.zhpew.beastfightingchess

import android.util.TypedValue

inline val Int.px
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(),
        MyApplication.instance.resources.displayMetrics
    );