package com.example.tictactoe

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Observer
import com.example.tictactoe.data.SquareState

class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<TicTacToeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.message.observe(this, Observer {
            it.getContentIfNotHandled()?.let {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }
        })

        setContent {
            Surface(
                color = Color.White,
                modifier = Modifier.padding(32.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = viewModel.countMovesText,
                        color = Color.Black,
                        fontSize = 30.sp,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    Text(
                        text = viewModel.currentPlayerText,
                        color = Color.Black,
                        fontSize = 30.sp,
                        modifier = Modifier.padding(bottom = 32.dp)
                    )
                    TicTacToeGame(viewModel.board, viewModel::playMove)
                    Button(
                        onClick = viewModel::resetBoard,
                        modifier = Modifier.padding(top = 32.dp)
                    ) {
                        Text(text = "NEW GAME")
                    }
                }
            }
        }
    }
}

@Composable
fun TicTacToeGame(board: Array<Array<SquareState>>, onClick: (Int, Int) -> Unit) {
    for (row in board.indices) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            for (column in 0 until board[0].size) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(0.dp)
                        .aspectRatio(1f)
                        .border(width = 1.dp, color = Color.Black)
                ) {
                    TicTacToeSquare(
                        symbol = board[row][column],
                        onClick = { onClick(row, column) }
                    )
                }
            }
        }
    }
}

@Composable
fun TicTacToeSquare(symbol: SquareState, onClick: () -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .clickable { onClick() },
        color = Color.White
    ) {
        Box(
            modifier = Modifier
                .border(width = 2.dp, color = Color.Black) // Border for the cell
        ) {
            val text = when (symbol) {
                SquareState.PLAYED_X -> {
                    "X"
                }
                SquareState.PLAYED_O -> {
                    "O"
                }
                else -> {
                    ""
                }
            }
            Text(
                text = text,
                color = Color.Black,
                fontSize = 60.sp,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}