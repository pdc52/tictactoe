package com.example.tictactoe

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tictactoe.data.Event
import com.example.tictactoe.data.PlayResponse
import com.example.tictactoe.data.SquareState
import com.example.tictactoe.data.TicTacToeBoard

class TicTacToeViewModel : ViewModel() {

    private val size: Int = 4
    private val ticTacToeBoard = TicTacToeBoard(size)
    private var _board = mutableStateOf(ticTacToeBoard.board)
    val board: Array<Array<SquareState>> = _board.value
    private var win = false

    private var currentPlayer = SquareState.PLAYED_X
    var currentPlayerText by mutableStateOf("Current Player: X")
        private set
    private var countMoves = 0
    var countMovesText by mutableStateOf("Total Moves: 0")
        private set

    private val statusMessage = MutableLiveData<Event<String>>()
    val message : LiveData<Event<String>>
        get() = statusMessage

    fun playMove(row: Int, column: Int) {
        if (!win) {
            val response = ticTacToeBoard.playSquare(row, column, currentPlayer)
            when (response) {
                PlayResponse.WIN -> {
                    incrementMoves()
                    sendWinMessage()
                    win = true
                }

                PlayResponse.VALID -> {
                    incrementMoves()
                    updateCurrentPlayer()
                }

                else -> {
                    statusMessage.value = Event("Invalid move. Please select empty square.")
                }
            }
        } else {
            statusMessage.value = Event("Game already won. Please select new game.")
        }
    }

    fun resetBoard() {
        for (row in 0 until size) {
            for (column in 0 until size) {
                ticTacToeBoard.setSquareState(row, column, SquareState.UNPLAYED)
            }
        }
        win = false
        countMoves = 0
        countMovesText = "Total Moves: 0"
        currentPlayer = SquareState.PLAYED_X
        currentPlayerText = "Current Player: X"
    }

    private fun incrementMoves() {
        countMoves++
        countMovesText = "Total Moves: $countMoves"
    }

    private fun updateCurrentPlayer() {
        if (currentPlayer == SquareState.PLAYED_X) {
            currentPlayer = SquareState.PLAYED_O
            currentPlayerText = "Current Player: O"
        } else {
            currentPlayer = SquareState.PLAYED_X
            currentPlayerText = "Current Player: X"
        }
    }

    private fun sendWinMessage() {
        val winningPlayer = if (currentPlayer == SquareState.PLAYED_O) {
            "O"
        } else {
            "X"
        }
        statusMessage.value = Event("Player $winningPlayer wins!")
    }
}