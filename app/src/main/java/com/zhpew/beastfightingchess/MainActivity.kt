package com.zhpew.beastfightingchess

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import com.zhpew.beastfightingchess.model.CellBean
import com.zhpew.beastfightingchess.view.BoardView
import com.zhpew.beastfightingchess.view.CellView

class MainActivity : AppCompatActivity() {

    lateinit var vm: MainViewModel

    private fun initVM() {
        vm = ViewModelProvider(this)[MainViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initVM()
        setContent {
            Column(
                Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .width(240.dp)
                        .height(240.dp)
                ) {
                    BoardView()
                    Row(modifier = Modifier.fillMaxSize()) {
                        for (i in 0 until 4) {
                            Column(
                                Modifier
                                    .fillMaxHeight()
                                    .width(60.dp)
                            ) {
                                for (j in 0 until 4) {
                                    CellView(cellBean = GameController.state.value.Pieces[i][j], true)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}