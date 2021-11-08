/** Series of automated JUnit tests for methods, variable and encapsulation contained in ComputerPlayer and Connect4Board.
*@author Tim Dees
*/
import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import org.junit.Test;


public class Connect4Test {
	
	private ComputerPlayer player = new ComputerPlayer('O');
	
	private char[][] grid = {{'.','.','.','.','.','.','.'},
							 {'.','.','.','.','.','.','.'},
						 	 {'.','.','.','O','.','.','.'},
							 {'.','.','O','B','.','.','.'},
							 {'.','O','B','O','.','.','.'},
							 {'O','B','O','B','.','.','.'}};
							
	private char[][] fullGrid = {{'B','B','B','O','O','O','B'},
								 {'B','O','B','O','B','O','B'},
								 {'B','B','B','O','O','O','B'},
								 {'O','O','O','B','B','B','O'},
								 {'O','B','O','B','O','B','O'},
								 {'O','O','O','B','B','B','O'}};
	
	
	/** Test 1 - Confirm the isFull method recognizes a full board 
	*/
	@Test
	public void testRecognizeFullBoard() {
					
		Connect4Board board = new Connect4Board();
		
		/* Fill up the Board*/
		for (int row = 0; row < 6; row++){
			for (int col = 0; col < 7; col++){
				board.updateBoard(col, 'O');
			}
		}
		
		assertEquals("Test isFull method with full board", true, board.isFull());
	}
	
	
	/** Test 2 - Confirm the ifFull method recognizes when the board is NOT full
	*/
	@Test
	public void testIsRecognizeEmptyBoard() {
					
		Connect4Board board = new Connect4Board();
		
		assertEquals("Test isFull method with empty board", false, board.isFull());
	}
	
	
	/** Test 3 - Confirm the hasWon method recognizes a DIAGONAL win condition on the board
	*/
	@Test
	public void testWinningBoardDiagonal() {
					
		Connect4Board board = new Connect4Board(grid);
		
		assertEquals("Test hasWon method with diagonal winning condition on board", true, board.hasWon('O'));
	}
	
	
	/** Test 4 - Confirm the hasWon method recognizes a HORIZONTAL win condition on the board
	*/
	@Test
	public void testWinningBoardHorizontal() {
					
		Connect4Board board = new Connect4Board();
		
		for (int col = 0; col < 4; col++){
			board.updateBoard(col, 'O');
		}
		
		assertEquals("Test hasWon method with Horizontal winning condition on board", true, board.hasWon('O'));
	}

	
	/** Test 5 - Confirm the hasWon method recognizes a VERTICAL win condition on the board
	*/
	@Test
	public void testWinningBoardVertical() {
					
		Connect4Board board = new Connect4Board();
		
		for (int i = 0; i < 4; i++){
			board.updateBoard(3, 'O');
		}
		
		assertEquals("Test hasWon method with Vertical winning condition on board", true, board.hasWon('O'));
		
	}
	
	/** Test 6 - Confirm the hasWon method recognizes that there are NO WIN CONDITIONS on the board
	*/
	@Test
	public void testNoWinningCondition() {
					
		Connect4Board board = new Connect4Board(fullGrid);
		
		assertEquals("Test hasWon method with No winning conditions on board", false, board.hasWon('O'));
	}
	
	
	/** Test 7 - Confirm the findRow method can accurately determine to correct row to place a piece on
	*/
	@Test
	public void testFindRow(){
					
		Connect4Board board = new Connect4Board(grid);
		
		assertEquals("Test findRow method on column with 3 pieces in it already", 2, board.findRow(2));
	}
	
	
	/** Test 8 - Confirm the resetBoard method can reset a board to blank
	*/
	@Test
	public void testResetBoard(){
					
		Connect4Board emptyBoard = new Connect4Board();
		Connect4Board board = new Connect4Board();
		board.updateBoard(3, 'O');
		board.resetBoard();
		
		assertEquals("Test resetBoard method after adding a piece to the board.", emptyBoard.getCurrentBoard(), board.getCurrentBoard());
	}
	
	
	/** Test 9 - Confirm the resetPiece method will remove only a single piece from the requested column
	*/
	@Test
	public void testResetPieceNormal(){
						 
		char[][] expectedGrid = {{'.','.','.','.','.','.','.'},
								 {'.','.','.','.','.','.','.'},
								 {'.','.','.','.','.','.','.'},
								 {'.','.','O','B','.','.','.'},
								 {'.','O','B','O','.','.','.'},
								 {'O','B','O','B','.','.','.'}};
					
		Connect4Board board = new Connect4Board(grid);
		board.resetPiece(3);
		
		assertEquals("Test resetPiece method on column with 4 pieces in it already", expectedGrid, board.getCurrentBoard());
	}
	
	
	/** Test 10 - Confirm the resetPiece method has no privacy leaks when operating on a column that contains no pieces
	*/
	@Test
	public void testResetPieceNoPieceToRemove(){
					
		Connect4Board board = new Connect4Board(grid);
		board.resetPiece(5);
		
		assertEquals("Test resetPiece method on column with 0 pieces in it already", grid, board.getCurrentBoard());
	}
	
	
	/** Test 11 - Confirm the resetPiece method with an out of bounds condition
	*/
	@Test
	public void testResetPieceOutOfBounds(){
					
		Connect4Board board = new Connect4Board(grid);
		board.resetPiece(8);
		
		assertEquals("Test resetPiece method with out of bounds input", grid, board.getCurrentBoard());
	}
	
	
	/** Test 12 - Confirm the updateBoard method add the correct piece to the correct location
	*/
	@Test
	public void testUpdateBoardNormal(){
		
		char[][] expectedGrid = {{'.','.','.','.','.','.','.'},
								 {'.','.','.','B','.','.','.'},
								 {'.','.','.','O','.','.','.'},
								 {'.','.','O','B','.','.','.'},
								 {'.','O','B','O','.','.','.'},
								 {'O','B','O','B','.','.','.'}};
					
		Connect4Board board = new Connect4Board(grid);
		board.updateBoard(3, 'B');
		
		assertEquals("Test updateBoard on column with 4 pieces in it already", expectedGrid, board.getCurrentBoard());
	}
	
	
	/** Test 13 - Confirm the updateBoard method doesn't have privacy leaks when trying to place a piece on a full board
	*/
	@Test
	public void testUpdateBoardFullColumn(){
					
		Connect4Board board = new Connect4Board(fullGrid);
		board.updateBoard(3, 'B');
		
		assertEquals("Test updateBoard with full column", fullGrid, board.getCurrentBoard());
	}
	
	
	/** Test 14 - Confirm the getMove method (ComputerPlayer) by giving it only one possible move and confirming it chooses that
	*/
	@Test
	public void testComputerMoveOnlyOption() {

					
		Connect4Board board = new Connect4Board(fullGrid);
		board.resetPiece(6);
		
		assertEquals("Test getMove with only one option", 6, player.getMove(board.getCurrentBoard()));
	}
	
	
	/** Test 15 - Confirm the getMove method (ComputerPlayer) by giving it multiple options but one best move (blocking an opponent from winning)
	*/
	@Test
	public void testComputerMoveBlockingOptionOne() {
		
		char[][] grid = {{'.','.','.','.','.','.','.'},
						 {'O','O','B','O','B','O','B'},
						 {'B','B','B','O','O','O','B'},
						 {'B','O','O','B','B','B','O'},
						 {'O','B','O','B','O','B','O'},
						 {'O','O','O','B','B','B','O'}};
		
		assertEquals("Test getMove with one best option - blocking", 3, player.getMove(grid));
	}
	
	
	/** Test 16 - Confirm the getMove method (ComputerPlayer) by giving it multiple options but one best move (blocking an opponent from winning)
	*/
	@Test
	public void testComputerMoveBlockingOptionTwo() {
		
		char[][] grid = {{'.','.','B','B','.','.','.'},
						 {'.','.','O','O','B','.','.'},
						 {'.','B','B','O','B','.','.'},
						 {'.','O','O','B','B','.','.'},
						 {'.','B','O','B','O','.','.'},
						 {'.','O','B','B','O','.','.'}};
		
		assertEquals("Test getMove with one best option - blocking", 4, player.getMove(grid));
	}
	
	
	/** Test 17 - Confirm the getMove method (ComputerPlayer) by giving it multiple options but one best move (Immediate victory)
	*/
	@Test
	public void testComputerMoveWinningOption() {
		
		char[][] grid = {{'.','.','.','.','.','.','.'},
						 {'B','O','B','O','B','O','B'},
						 {'B','B','O','B','O','O','B'},
						 {'O','O','O','B','B','B','O'},
						 {'B','B','O','B','O','B','O'},
						 {'O','O','B','O','B','B','O'}};
		
		assertEquals("Test getMove with one best option - winning", 4, player.getMove(grid));	
	}
	
}