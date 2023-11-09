package com.zhpew.beastfightingchess

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import com.zhpew.beastfightingchess.model.CellBean
import com.zhpew.beastfightingchess.view.BoardView
import com.zhpew.beastfightingchess.view.CellView
import java.util.*

class MainActivity : AppCompatActivity() {

    lateinit var vm: MainViewModel

    private fun initVM() {
        vm = ViewModelProvider(this)[MainViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initVM()
        GameController.onFinish = {
            Toast.makeText(this, if (it) "红方胜利！" else "蓝方胜利！", Toast.LENGTH_LONG).show()
            GameController.refresh()
        }
        setContent {
            Column(
                Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = if (GameController.state.value.isRedTurn) "红方回合" else "蓝方回合",
                    modifier = Modifier.padding(bottom = 40.dp),
                    style = TextStyle(
                        fontSize = 20.sp,
                        color = if (GameController.state.value.isRedTurn) Color.Red else Color.Blue
                    )
                )

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
                                    CellView(
                                        cellBean = GameController.state.value.Pieces[i][j],
                                        i * 4 + j
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}