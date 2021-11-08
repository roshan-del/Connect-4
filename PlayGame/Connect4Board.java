/** Class for the game board object in the connect 4 game
*@author Tim Dees
*/
public class Connect4Board{
    private char[][] grid = {{'.','.','.','.','.','.','.'},
						 	 {'.','.','.','.','.','.','.'},
							 {'.','.','.','.','.','.','.'},
							 {'.','.','.','.','.','.','.'},
							 {'.','.','.','.','.','.','.'},
							 {'.','.','.','.','.','.','.'}};

	
	/** This constructor allows the creation of a new game board with no initial values.
	*/
    public Connect4Board(){
	}
	
	
	/** This constructor allows the creation of a new game board with pre-determined grid layout.
	* @param gridToCopy - an initial value for the current state of a board
	*/
	public Connect4Board(char[][] gridToCopy){
		for (int row = 0; row < 6; row++) {
			for (int column = 0; column < 7; column++) {
				grid[row][column] = gridToCopy[row][column];
			}
		}
	}

	
	/** This method checks if a given move is valid.
	* @param rowOfMove - the row value of the move to check validity on.
	* @param colOfMove - the column value of the move to check validity on.
	* @param currentGameboard - the current state of the gameboard on which to determine validity.
	* @return returns true is the attempted move is valid.
	*/
	public boolean isValid(int movecol){
		int moverow = findRow(movecol);
		
		if (grid[moverow][movecol] != '.'){
			return false;
		}
		//Check the row exists
		if (moverow < 0 || moverow > 6){
			return false;
		}
		//Check the column exists
		if (movecol < 0 || movecol > 7){
			return false;
		}
		//Check if the whole column is full
		boolean columnIsFull = true;
		for (int row = 0; row < 6; row++){
			if (grid[row][movecol] == '.'){
				columnIsFull = false;
			}		
		}
		if (columnIsFull){
			return true;
		}
		return true;
	}

	
	/** Retrieves the current version of the game board 
	@return retrieves the current game state as a 2D char array.
	*/
    public char[][] getCurrentBoard(){
        return grid;
	}
	

	/** This method prints the current game board to the console for text based versions of the game
	*/
	public void print(){
		for (int row = 0; row < 6; row++){
			for (int col = 0; col < 7; col++){
				System.out.print(grid[row][col]);
			}	
			System.out.println();
		}
	}
	
	
	/** This method resets the board to it's default.
	*/
	public void resetBoard(){
		for (int row = 0; row < 6; row++){
			for (int col = 0; col < 7; col++){
				grid[row][col] = '.';
			}
		}
	}
	
	
	/** This method checks if someone has won.
	* @param token - the token of which to look for winning combinations.
	* @return returns true if that token has a winning configuration.
	*/
    public boolean hasWon(char token){
		//Four in a row horizontally
		for (int row = 0; row < 6; row++){
			for (int col = 0; col < 4; col++){
				if (token == grid[row][col] &&
					token == grid[row][col+1] &&
					token == grid[row][col+2] &&
					token == grid[row][col+3]){
						return true;
				}	
			}
		}
		//Four in a row vertically
		for (int row = 0; row < 3; row++){
			for (int col = 0; col < 7; col++){
				if (token == grid[row][col] &&
					token == grid[row+1][col] &&
					token == grid[row+2][col] &&
					token == grid[row+3][col]){
						return true;
				}	
			}
		}
		//Four in a row diagonally (down-right)
		for (int row = 0; row < 3; row++){
			for (int col = 0; col < 4; col++){
				if (token == grid[row][col] &&
					token == grid[row+1][col+1] &&
					token == grid[row+2][col+2] &&
					token == grid[row+3][col+3]){
						return true;
				}	
			}
		}
		//Four in a row diagonally (down-left)
		for (int row = 0; row < 3; row++){
			for (int col = 3; col < 7; col++){
				if (token == grid[row][col] &&
					token == grid[row+1][col-1] &&
					token == grid[row+2][col-2] &&
					token == grid[row+3][col-3]){
						return true;
				}	
			}
		}
		return false;
	}
	
	
	/** This method checks if the board is full.
	* @return returns true if the board is totally full.
	*/
    public boolean isFull() {
		for (int row = 0; row < 6; row++){
			for (int column = 0; column < 7 ; column++){
				if (grid[row][column] == '.') {
					return false;
				}
			}
		}
		return true;
	}
	
	
	/** This method determines the row of a move if given a column.
	* @param colOfMove - the column to determine the corresponding row from.
	* @param gameState - the current state of the board to examine.
	* @return returns an integer value of the appropriate row.
	*/
	public int findRow(int colOfMove){
		int rowToPlace = 0;
		
		for (int row = 0; row < 6; row++){
			if (grid[row][colOfMove] == '.'){
				rowToPlace = row;
			}
		}
		
		return rowToPlace;
	}

	
	/** This method allows a piece to be placed on the board.
	* @param colOfMove - the column of the move you want to make.
	* @param token - the appropriate token you want to place on the board
	*/
    public void updateBoard(int colOfMove, char token){
		if (isValid(colOfMove)){
			int rowOfMove = findRow(colOfMove);
			grid[rowOfMove][colOfMove] = token;
		}
	}
	
	
	/** This method removes a piece from the board in a specific column.
	* @param colOfMove - the column from which you want to remove the highest piece.
	*/
	public void resetPiece(int colOfMove){
		if (colOfMove >= 0 && colOfMove <= 6){
			int rowToPlace = 5;
			
			for (int row = 5; row > -1; row--){
				if (grid[row][colOfMove] != '.'){
					rowToPlace = row;
				}
			}
			
			grid[rowToPlace][colOfMove] = '.';
		}
	}
}