package com.zhpew.beastfightingchess

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import com.zhpew.beastfightingchess.model.CellBean

/**
 * 控制器
 */
object GameController {

    val state = mutableStateOf(UiState(initCell()))

    lateinit var onFinish: (isRedWin: Boolean) -> Unit

    fun initCell(): SnapshotStateList<SnapshotStateList<CellBean>> {
        val array = IntArray(16) {
            return@IntArray it
        }
        // 乱序
        for (index in 0 until 15) {
            // 向上取整
            val random = (Math.random() * (15 - index)).toInt() + 1
            val temp = array[index]
            array[index] = array[random]
            array[random] = temp
        }
        val pieces = ArrayList<SnapshotStateList<CellBean>>()
        for (i in 0..3) {
            val list: ArrayList<CellBean> = ArrayList()
            for (j in 0..3) {
                val cell = CellBean()
                val index = i * 4 + j
                if (array[index] > 7) {
                    // 红方
                    cell.level = array[index] - 7
                    cell.isRed = true
                } else {
                    cell.level = array[index] + 1
                    cell.isRed = false
                }
                list.add(cell)
            }
            pieces.add(list.toMutableStateList())
        }
        return pieces.toMutableStateList()
    }

    fun onItemClick(index: Int) {
        val i = index / 4
        val j = index % 4

        val lastI = state.value.selectedIndex / 4
        val lastJ = state.value.selectedIndex % 4

        val item = state.value.Pieces[i][j]
        val lastItem = state.value.Pieces[lastI][lastJ]

        if (state.value.selectedIndex != -1) {
            // 已经有选中的item了，判断是翻牌，还是往哪边走，还是无效操作
            if (state.value.selectedIndex == index) {
                if (item.isCover) {
                    // 翻
                    item.isCover = false
                    checkFinish()
                } else {
                    //取消选中态
                    state.value = state.value.copy(selectedIndex = -1)
                }
            } else {
                // 是否是上下左右
                if ((i == lastI + 1 && j == lastJ)
                    || (i == lastI - 1 && j == lastJ)
                    || (i == lastI && j == lastJ - 1)
                    || (i == lastI && j == lastJ + 1)
                ) {
                    // 有效操作,判断能不能走
                    if (item.isCover) {
                        //不能走,切换选中
                        state.value = state.value.copy(selectedIndex = index)
                    }
                    if (item.isBlock) {
                        // 空位，可走,交换位置
                        state.value.Pieces[i][j] = lastItem
                        state.value.Pieces[lastI][lastJ] = item
                    }
                    if (item.isRed == lastItem.isRed) {
                        // 同一边的棋子，不可走，切换选中
                        state.value = state.value.copy(selectedIndex = index)
                    } else {
                        if (lastItem.level >= item.level) {
                            // 可以吃掉。先交换位置，再将last置为block
                            state.value.Pieces[i][j] = lastItem
                            state.value.Pieces[lastI][lastJ] = item.apply {
                                isBlock = true
                            }
                            checkFinish()
                        } else {
                            // 非同边棋子，不可选，无效操作
                            return
                        }
                    }
                } else {
                    if (state.value.isRedTurn == lastItem.isRed) {
                        //切换选中
                        state.value = state.value.copy(selectedIndex = index)
                    }
                }
            }
        } else {
            if (!item.isBlock) {
                if (state.value.Pieces[i][j].isRed == state.value.isRedTurn) {
                    state.value = state.value.copy(selectedIndex = index)
                }
            }
        }
    }

    /**
     *     判断是否结束了
     */
    private fun checkFinish() {
        val pieces = state.value.Pieces
        var redLive = false
        var blueLive = false
        for (cellList in pieces) {
            for (cell in cellList) {
                if (cell.isRed) {
                    redLive = !cell.isBlock
                } else {
                    blueLive = !cell.isBlock
                }
            }
            if (redLive && blueLive) {
                // 双方此刻都还存活
                state.value = state.value.copy(isRedTurn = !state.value.isRedTurn)
                return
            }
        }
        // 结束了，某一方已经嗝屁了
        onFinish(redLive)
    }

}

data class UiState(
    val Pieces: SnapshotStateList<SnapshotStateList<CellBean>> = mutableStateListOf(),
    val selectedIndex: Int = -1,
    val isRedTurn: Boolean = false
)