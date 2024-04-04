import com.example.tictactoe.data.PlayResponse
import com.example.tictactoe.data.SquareState
import com.example.tictactoe.data.TicTacToeBoard
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class TicTacToeBoardParameterizedTest(private val size: Int) {

    companion object {
        @JvmStatic
        @Parameterized.Parameters(name = "Board size = {0}")
        fun data(): List<Int> {
            return listOf(3, 4, 5, 10)
        }
    }

    private lateinit var ticTacToeBoard: TicTacToeBoard

    @Before
    fun setup() {
        ticTacToeBoard = TicTacToeBoard(size)
    }

    @Test
    fun testPlaySquare_ValidMove() {
        assertEquals(PlayResponse.VALID, ticTacToeBoard.playSquare(0, 0, SquareState.PLAYED_X))
    }

    @Test
    fun testPlaySquare_InvalidPlayAlreadyPlayedSquare() {
        assertEquals(PlayResponse.VALID, ticTacToeBoard.playSquare(0, 0, SquareState.PLAYED_X))
        assertEquals(PlayResponse.INVALID, ticTacToeBoard.playSquare(0, 0, SquareState.PLAYED_O))
    }

    @Test
    fun testPlaySquare_InvalidPlayUnplayed() {
        assertEquals(PlayResponse.INVALID, ticTacToeBoard.playSquare(0, 0, SquareState.UNPLAYED))
    }

    @Test
    fun testPlaySquare_InvalidPlayOutsideBoard() {
        assertEquals(PlayResponse.INVALID, ticTacToeBoard.playSquare(size, size, SquareState.PLAYED_X))
    }

    @Test
    fun testPlaySquare_WinRow() {
        for (col in 0 until size - 1) {
            assertEquals(PlayResponse.VALID, ticTacToeBoard.playSquare(0, col, SquareState.PLAYED_X))
        }
        assertEquals(PlayResponse.WIN, ticTacToeBoard.playSquare(0, size - 1, SquareState.PLAYED_X))
    }

    @Test
    fun testPlaySquare_WinColumn() {
        for (row in 0 until size - 1) {
            assertEquals(PlayResponse.VALID, ticTacToeBoard.playSquare(row, 0, SquareState.PLAYED_O))
        }
        assertEquals(PlayResponse.WIN, ticTacToeBoard.playSquare(size - 1, 0, SquareState.PLAYED_O))
    }

    @Test
    fun testPlaySquare_WinDiagonalTopLeftBottomRight() {
        for (row in 0 until size - 1) {
            assertEquals(PlayResponse.VALID, ticTacToeBoard.playSquare(row, row, SquareState.PLAYED_X))
        }
        assertEquals(PlayResponse.WIN, ticTacToeBoard.playSquare(size - 1, size - 1, SquareState.PLAYED_X))
    }

    @Test
    fun testPlaySquare_WinDiagonalTopRightBottomLeft() {
        for (col in 0 until size - 1) {
            assertEquals(PlayResponse.VALID, ticTacToeBoard.playSquare(size - 1 - col, col, SquareState.PLAYED_X))
        }
        assertEquals(PlayResponse.WIN, ticTacToeBoard.playSquare(0, size - 1, SquareState.PLAYED_X))
    }

    // Check box wins by finishing in different locations
    @Test
    fun testPlaySquare_WinBoxBottomRight() {
        assertEquals(PlayResponse.VALID, ticTacToeBoard.playSquare(0, 0, SquareState.PLAYED_O))
        assertEquals(PlayResponse.VALID, ticTacToeBoard.playSquare(0, 1, SquareState.PLAYED_O))
        assertEquals(PlayResponse.VALID, ticTacToeBoard.playSquare(1, 0, SquareState.PLAYED_O))
        assertEquals(PlayResponse.WIN, ticTacToeBoard.playSquare(1, 1, SquareState.PLAYED_O))
    }

    @Test
    fun testPlaySquare_WinBoxBottomLeft() {
        assertEquals(PlayResponse.VALID, ticTacToeBoard.playSquare(0, 0, SquareState.PLAYED_O))
        assertEquals(PlayResponse.VALID, ticTacToeBoard.playSquare(0, 1, SquareState.PLAYED_O))
        assertEquals(PlayResponse.VALID, ticTacToeBoard.playSquare(1, 1, SquareState.PLAYED_O))
        assertEquals(PlayResponse.WIN, ticTacToeBoard.playSquare(1, 0, SquareState.PLAYED_O))
    }

    @Test
    fun testPlaySquare_WinBoxTopLeft() {
        assertEquals(PlayResponse.VALID, ticTacToeBoard.playSquare(1, 0, SquareState.PLAYED_O))
        assertEquals(PlayResponse.VALID, ticTacToeBoard.playSquare(0, 1, SquareState.PLAYED_O))
        assertEquals(PlayResponse.VALID, ticTacToeBoard.playSquare(1, 1, SquareState.PLAYED_O))
        assertEquals(PlayResponse.WIN, ticTacToeBoard.playSquare(0, 0, SquareState.PLAYED_O))
    }

    @Test
    fun testPlaySquare_WinBoxTopRight() {
        assertEquals(PlayResponse.VALID, ticTacToeBoard.playSquare(0, 0, SquareState.PLAYED_O))
        assertEquals(PlayResponse.VALID, ticTacToeBoard.playSquare(1, 0, SquareState.PLAYED_O))
        assertEquals(PlayResponse.VALID, ticTacToeBoard.playSquare(1, 1, SquareState.PLAYED_O))
        assertEquals(PlayResponse.WIN, ticTacToeBoard.playSquare(0, 1, SquareState.PLAYED_O))
    }

    @Test
    fun testPlaySquare_WinCorners() {
        assertEquals(PlayResponse.VALID, ticTacToeBoard.playSquare(0, 0, SquareState.PLAYED_X))
        assertEquals(PlayResponse.VALID, ticTacToeBoard.playSquare(0, size - 1, SquareState.PLAYED_X))
        assertEquals(PlayResponse.VALID, ticTacToeBoard.playSquare(size - 1, 0, SquareState.PLAYED_X))
        assertEquals(PlayResponse.WIN, ticTacToeBoard.playSquare(size - 1, size - 1, SquareState.PLAYED_X))
    }
}