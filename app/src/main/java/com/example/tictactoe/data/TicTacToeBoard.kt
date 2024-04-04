package com.example.tictactoe.data

enum class SquareState {
    PLAYED_X,
    PLAYED_O,
    UNPLAYED
}

enum class PlayResponse {
    VALID,
    INVALID,
    WIN
}

// Could be modified to be row x col instead of n x n,
// But then would need to rethink diagonal win behavior
class TicTacToeBoard(val size: Int) {
    val board:Array<Array<SquareState>> = Array(size) { Array(size) { SquareState.UNPLAYED } }

    fun getSquareState(row: Int, column: Int): SquareState {
        return board[row][column]
    }

    fun setSquareState(row: Int, column: Int, state: SquareState) {
        board[row][column] = state
    }

    // Return one of PlayResponse INVALID, VALID, or WIN based on current state
    fun playSquare(row: Int, column: Int, state: SquareState): PlayResponse {
        if (row < 0 || row >= size || column < 0 || column >= size) {
            return PlayResponse.INVALID
        }
        if (state == SquareState.UNPLAYED || getSquareState(row, column) != SquareState.UNPLAYED) {
            return PlayResponse.INVALID
        }
        setSquareState(row, column, state)
        return if (checkWin(row, column)) {
            PlayResponse.WIN
        } else {
            PlayResponse.VALID
        }
    }

    // Return true if this square has caused a win
    private fun checkWin(row: Int, column: Int): Boolean {
        return checkWinLine(row, column) || checkWinCorners(row, column) || checkWinBox(row, column)
    }

    private fun checkWinLine(row: Int, column: Int): Boolean {
        return checkWinRow(row) || checkWinColumn(column) || checkWinDiagonals(row, column)
    }

    private fun checkWinRow(row: Int): Boolean {
        val first = getSquareState(row, 0)
        return (1 until size).all { board[row][it] == first }
    }

    private fun checkWinColumn(column: Int): Boolean {
        val first = getSquareState(0, column)
        return (1 until size).all { board[it][column] == first }
    }

    private fun checkWinDiagonals(row: Int, column: Int): Boolean {
        return if (row == column) {
            // Top left to bottom right
            val first = getSquareState(0, 0)
            (1 until size).all { board[it][it] == first }
        } else if (row + column == size - 1) {
            // Top right to bottom left
            val first = getSquareState(0, size - 1)
            (1 until size).all { board[it][size - 1 - it] == first }
        } else {
            // Square not on diagonal
            false
        }
    }

    private fun checkWinCorners(row: Int, column: Int): Boolean {
        val first = getSquareState(0, 0)
        return (first != SquareState.UNPLAYED &&
                first == getSquareState(0, size - 1) &&
                first == getSquareState(size - 1, 0) &&
                first == getSquareState(size - 1, size - 1))
    }

    // If extending beyond 2x2 boxes, refactor using loops to iterate through
    // possible boxes. With 2x2, it's a bit more readable to walk through each possibility
    // 4 possible 2x2 boxes that contain the played square
    // (Some possible boxes invalid due to played square being on the edge)
    // (R-1, C-1)  (R-1, C)  (R-1, C+1)
    // (R, C-1)    (R, C)    (R, C+1)
    // (R+1, C-1)  (R+1, C)  (R+1, C+1)
    private fun checkWinBox(row: Int, column: Int): Boolean {
        val currentPlayed = getSquareState(row, column)
        if (currentPlayed == SquareState.UNPLAYED) {
            return false
        }
        // Check square above
        if (row - 1 >= 0 && currentPlayed == getSquareState(row - 1, column)) {
            // Check remaining top left squares
            if (column - 1 >= 0 && currentPlayed == getSquareState(row - 1, column - 1) &&
                currentPlayed == getSquareState(row, column - 1)) {
                return true
            }
            // Check remaining top right squares
            if (column + 1 < size && currentPlayed == getSquareState(row - 1, column + 1) &&
                currentPlayed == getSquareState(row, column + 1)) {
                return true
            }
        }
        // Check square below
        if (row + 1 < size && currentPlayed == getSquareState(row + 1, column)) {
            // Check remaining bottom left squares
            if (column - 1 >= 0 && currentPlayed == getSquareState(row, column - 1) &&
                currentPlayed == getSquareState(row + 1, column - 1)) {
                return true
            }
            // Check remaining bottom right squares
            if (column + 1 < size && currentPlayed == getSquareState(row, column + 1) &&
                currentPlayed == getSquareState(row + 1, column + 1)) {
                return true
            }
        }
        return false
    }
}