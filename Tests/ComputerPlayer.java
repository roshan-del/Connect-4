/** Creates a new computer player with it's own token and which is capable of making moves if given a gameboard
*@author Tim Dees
*/
public class ComputerPlayer extends Player{
	/*private Connect4Board currentGameState;*/
	/*Difficulty rating, 1 is easy, 2 is hard.*/
	private int difficulty = 2;

	
	/** This constructor allows the creation of a computer player with no initial values.
	*/
	public ComputerPlayer(){
	}
	
	
	/** This constructor allows the creation of a computer player with pre-determined Difficulty.
	* @param newToken - the token color to initialize for the new ComputerPlayer
	*/
	public ComputerPlayer(int newDifficulty){
		this.setDifficulty(newDifficulty);
	}
	
	
	/** This constructor allows the creation of a computer player with pre-determined token.
	* @param newToken - the token color to initialize for the new ComputerPlayer
	*/
	public ComputerPlayer(char newToken){
		super(newToken);
	}
	
	
	/** This constructor allows the creation of a computer player with pre-determined token and pre-determined difficulty.
	* @param newToken - the token color to initialize for the new ComputerPlayer
	*/
	public ComputerPlayer(char newToken, int newDifficulty){
		super.setToken(newToken);
		this.setDifficulty(newDifficulty);
	}
	
	
	/** This method sets the difficulty for the computer player
	* @param newDifficulty - the difficulty to which to set the computer (1-easy, 2-medium, 3-hard)
	*/
	public void setDifficulty(int newDifficulty){
		if (newDifficulty == 1){
			difficulty = newDifficulty;
		} else {
			difficulty = 2;
		}
	}
	
	
	/** This method retrieves the difficulty setting for the computer player
	* @return the difficulty of the computer player
	*/
	public int getDifficulty(){
		return difficulty;
	}

	
	/** This method returns a move for the computer to make
	* @param currentGameState - the current status of a game board grid
	* @return returns a column location on the board for the computer to make their next move.
	*/
	public int getMove(char[][] currentGameBoard){
		Connect4Board currentGameState = new Connect4Board(currentGameBoard);
		char goodToken = token;
		char resetToken = '.';
		char badToken = ',';
		
		
		//Determine the token of the opposing player.
		for (int row = 0; row < 6; row++){
			for (int col = 0; col < 7; col++){
				if (currentGameBoard[row][col] != '.' && currentGameBoard[row][col] != token){
					badToken = currentGameBoard[row][col];
				}
			}
		}
		
		int[] columnScores = {0,0,1,2,1,0,0};
		
		/*Looks for any winning moves and makes them
		Level 1*/
		for (int i = 0; i < 7; i ++){
			//int row_i = currentGameState.findRow(i);
			if (currentGameState.isValid(i)){
				currentGameState.updateBoard(i, goodToken);
				if (currentGameState.hasWon(goodToken)){
					currentGameState.resetPiece(i);
					return i;
				} 
				
				if (difficulty == 2){
				
				/*Looks at all possible responses that could be made to first level moves and assigns value to different options
				Level 2*/
				for (int j = 0; j < 7; j++) {
					//int row_j = findRow(j, currentGameState);
					if (currentGameState.isValid(j)){
						currentGameState.updateBoard(j, badToken);
						if (currentGameState.hasWon(badToken)){
							columnScores[i] -= 100000;
						} 
					
						/* Looks at possible moves and assigns value
						Level 3*/
						for (int k = 0; k < 7; k++) {
							//int row_k = findRow(k, currentGameState);
							if (currentGameState.isValid(k)){
								currentGameState.updateBoard(k, goodToken);
								if (currentGameState.hasWon(goodToken)){
									columnScores[i] += 50;
								}
								
								/* Looks at possible moves and assigns value
								Level 4*/
								for (int l = 0; l < 7; l++) {
									//int row_l = findRow(l, currentGameState);
									if (currentGameState.isValid(l)){
										currentGameState.updateBoard(l, badToken);
										if (currentGameState.hasWon(badToken)){
											columnScores[i] -= 20;
										}
										
										/* Looks at possible moves and assigns value
										Level 5*/
										for (int m = 0; m < 7; m++) {
											//int row_m = findRow(m, currentGameState);
											if (currentGameState.isValid(m)){
												currentGameState.updateBoard(m, goodToken);
												if (currentGameState.hasWon(goodToken)){
													columnScores[i] += 10;
												}
												
												/* Looks at possible moves and assigns value
												Level 6*/
												for (int n = 0; n < 7; n++) {
													//int row_n = findRow(n, currentGameState);
													if (currentGameState.isValid(n)){
														currentGameState.updateBoard(n, badToken);
														if (currentGameState.hasWon(badToken)){
															columnScores[i] -= 1;
														}
														/*Removes the piece just placed by reseting the pieces to their null value after 
														  checking possible moves and their potential value*/
														currentGameState.resetPiece(n);
													}
												}
												currentGameState.resetPiece(m);
											}
										}
										currentGameState.resetPiece(l);
									}
								}
								currentGameState.resetPiece(k);
							}
						}	
						currentGameState.resetPiece(j);
					}
				}
				}
				currentGameState.resetPiece(i);
			} else {
				columnScores[i] -= 900000000;
			}
		}
		
		/*Looks through the values given by the above moves and determines the highest value move*/
		int indexOfHighestValue = 0;
		int testValue = -1000000;
		for (int m = 0; m < 7; m++) {
			if (columnScores[m] > testValue){
				indexOfHighestValue = m;
				testValue = columnScores[m];
			}
		}
		
		return indexOfHighestValue;
		
	}
}
