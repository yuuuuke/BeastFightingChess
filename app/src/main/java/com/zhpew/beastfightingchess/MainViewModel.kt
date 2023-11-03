package com.zhpew.beastfightingchess

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import com.zhpew.beastfightingchess.GameController.initCell
import com.zhpew.beastfightingchess.model.CellBean

class MainViewModel : ViewModel() {
    val state = GameController.state
}

