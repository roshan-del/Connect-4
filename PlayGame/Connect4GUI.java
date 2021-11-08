/** The GUI interface for the connect4 game.
*@author Tim Dees
*/
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.*;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.event.*;
import javafx.scene.input.*;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.geometry.Insets;
import javafx.geometry.Pos;



public class Connect4GUI extends Application{
	Stage window;
	
	Connect4Board gameBoard = new Connect4Board();
	HumanPlayer player1 = new HumanPlayer();
	HumanPlayer player_H = new HumanPlayer();
	ComputerPlayer player_C = new ComputerPlayer();
	int player2Identity = 1;
	int playerTurnIdentity = 1;
	
	/* All items used for the user to select whether player 2 is a human or a computer*/
	Label playerSelectLabel = new Label("Would you like to play against a Human or a computer:");
	Button selectHumanPlayerButton = new Button("Human");
	Button selectComputerPlayerButton = new Button("Computer");
	Label playerSelectInfoLabel = new Label("Please make a selection");
	
	/* Player 2 selection (human or computer) items organized for display */
	HBox playerSelectionButtons = new HBox(selectHumanPlayerButton, selectComputerPlayerButton);
	VBox playerSelectionItems = new VBox(playerSelectLabel, playerSelectionButtons, playerSelectInfoLabel);

	
	/* All items used for the user to select whether the computer player is set to easy or hard*/
	Label difficultySelectLabel = new Label("Please select difficulty:");
	Button selectEasyDifficulty = new Button("Easy");
	Button selectHardDifficulty = new Button("Hard");
	Label difficultySelectInfoLabel = new Label("Please make a Selection");
	
	/* Computer difficulty selection items organized for display */
	HBox difficultySelectionButtons = new HBox(selectEasyDifficulty, selectHardDifficulty);
	VBox difficultySelectionItems = new VBox(difficultySelectLabel, difficultySelectionButtons, difficultySelectInfoLabel);
	
	/* Organization of all menu items for display */	
	Button playGameButton = new Button("Play");
	VBox menuBox = new VBox(playerSelectionItems, difficultySelectionItems, playGameButton);
	BorderPane gamePane = new BorderPane();
	
	/* All items used on the game scene for display board, giving player information and retrieving moves from the player */
	Label informationLabel = new Label("^Please make a move by selecting a column from above.^");
	Label highscoreLabel = new Label("The all time highscore is: " + player1.getHighscore());
	Label currentScoreLabel = new Label("Your current score is: " + player1.getScore());
	Button resetHighscore = new Button("Reset Highscore");
	Button returnToMenuButton = new Button("Main Menu");
	Button restartGameButton = new Button("New Game");
	Button hintButton = new Button("Hint");
	TextField[][] gridButtons = new TextField[6][7];
	Button[] listOfColumnButtons = new Button[7];
	
	
	/* Game Scene items organized for display */
	HBox bottomButtons = new HBox(restartGameButton, returnToMenuButton, hintButton);
	HBox scoreDisplayItems = new HBox(highscoreLabel, currentScoreLabel);
	VBox bottomItems = new VBox(scoreDisplayItems, bottomButtons, resetHighscore);
	HBox columnButtons = new HBox();
	
	
	/* Create the scenes Menu scene and Game scene*/
	Scene menuScene = new Scene(menuBox, 400,350);
	Scene gameScene = new Scene(gamePane, 400,350);
	
	/** This method takes the char version from the Connect4Board and turns it into a colored GUI grid.
	*/
	public void displayGrid(){
		char player1Token = player1.getToken();
		char player2Token;
		
		/* Determine Player 2 Token */
		if (player2Identity == 1){
			player2Token = player_H.getToken();
		} else{
			player2Token = player_C.getToken();
		}
		
		/* For each spot on the board look which player's token is there and display it as a color. */
		char[][] gridToDisplay = gameBoard.getCurrentBoard();
		for (int row = 0; row < 6; row++) {
			for (int column = 0; column < 7; column++){
				if (gridToDisplay[row][column] == player1Token){
					gridButtons[row][column].setStyle("-fx-background-color: Blue");
				} 
				if (gridToDisplay[row][column] == player2Token){
					gridButtons[row][column].setStyle("-fx-background-color: Yellow");
				}if (gridToDisplay[row][column] == '.'){
					gridButtons[row][column].setStyle("-fx-background-color: White");
				}
			}
		}
	}
	
	
	/** This method is the main method that allows the GUI to launch.
	*/
	public static void main(String [] args){
		launch(args);
	}
	
	
	
	/** This method is the main location for all GUI code.
	*/
	@Override
	public void start(Stage primaryStage) {
		window = primaryStage;
		
		player1.setToken('O');
		player_C.setToken('B');
		player_H.setToken('B');
		
		//Set all menu related button Actions
		selectComputerPlayerButton.setOnAction(new HandleMenuButtonClick());
		selectHumanPlayerButton.setOnAction(new HandleMenuButtonClick());
		selectEasyDifficulty.setOnAction(new HandleMenuButtonClick());
		selectHardDifficulty.setOnAction(new HandleMenuButtonClick());
		playGameButton.setOnAction(new HandleMenuButtonClick());
		returnToMenuButton.setOnAction(new HandleMenuButtonClick());
		restartGameButton.setOnAction(new HandleMenuButtonClick());
		hintButton.setOnAction(new HandleMenuButtonClick());
		resetHighscore.setOnAction(new HandleMenuButtonClick());
		
		GridPane grid = new GridPane();
		
		
		/*Create a grid of TextField boxes that will represent the Game Board*/
		for (int row = 0; row < 6; row++) {
			for (int column = 0; column < 7; column++){
				TextField b = new TextField();
				b.setMinWidth(50);
				b.setMaxWidth(50);
				b.setDisable(true);
				gridButtons[row][column] = b;
				grid.add(gridButtons[row][column], column, row);				
			}
		}
		
		
		/*Create a row of Buttons that will be used to select columns of move*/
		for (int i = 0; i < 7; i++){
			Button button = new Button();
			button.setText(Integer.toString(i + 1));
			button.setMinWidth(50);
			button.setOnAction(new HandleColumnButtonClick(i));
			listOfColumnButtons[i] = button;
			columnButtons.getChildren().add(listOfColumnButtons[i]);	
		}
		
		
		VBox displayAndText = new VBox(columnButtons, informationLabel);
		menuBox.setSpacing(50);
		
		displayAndText.setAlignment(Pos.CENTER);
		gamePane.setTop(displayAndText);
		gamePane.setCenter(grid);
		gamePane.setBottom(bottomItems);
		
		/* Make everything centered, rescalable with window and positioned well relative to each other */
		bottomButtons.setSpacing(40);
		bottomButtons.setAlignment(Pos.CENTER);
		scoreDisplayItems.setSpacing(40);
		scoreDisplayItems.setAlignment(Pos.CENTER);
		bottomItems.setSpacing(15);
		playerSelectionItems.setSpacing(10);
		difficultySelectionItems.setSpacing(10);
		bottomItems.setAlignment(Pos.CENTER);
		playerSelectionButtons.setAlignment(Pos.CENTER);
		difficultySelectionButtons.setAlignment(Pos.CENTER);
		playerSelectionItems.setAlignment(Pos.CENTER);
		difficultySelectionItems.setAlignment(Pos.CENTER);
		menuBox.setAlignment(Pos.CENTER);
		menuBox.setStyle("-fx-background-color: Grey");
		gamePane.setStyle("-fx-background-color: Grey");
		gamePane.setAlignment(grid, Pos.CENTER);
		gamePane.setMargin(grid, new Insets(12,0,0,25));
		gamePane.setMargin(displayAndText, new Insets(0,0,0,25));
		gamePane.setMargin(bottomButtons, new Insets(0,0,40,60));
		
		
		displayGrid();
		
		
		window.setTitle("Connect 4");
		window.setScene(menuScene);
		window.show();
	}
	
	

	
	/** This class is responsible for all button clicks pertaining to MENU and PREFERENCE SELECTION items
	*/
	public class HandleMenuButtonClick implements EventHandler<ActionEvent>{
		
		public void handle(ActionEvent event){
			if(event.getSource() == returnToMenuButton){
				window.setScene(menuScene);
			}
			if(event.getSource() == selectEasyDifficulty){
				player_C.setDifficulty(1);
				difficultySelectInfoLabel.setText("Easy");
			}
			if(event.getSource() == selectHardDifficulty){
				player_C.setDifficulty(2);
				difficultySelectInfoLabel.setText("Hard");
			}
			if(event.getSource() == selectHumanPlayerButton){
				player2Identity = 1;
				highscoreLabel.setText("");
				currentScoreLabel.setText("");
				playerSelectInfoLabel.setText("Human");
				
			}
			if(event.getSource() == selectComputerPlayerButton){
				player2Identity = 2;
				playerSelectInfoLabel.setText("Computer");
			}
			if(event.getSource() == playGameButton){
				gameBoard.resetBoard();
				displayGrid();
				window.setScene(gameScene);
			}
			if(event.getSource() == restartGameButton){
				gameBoard.resetBoard();
				displayGrid();
				informationLabel.setText("New Game. Player 1. It's your Turn!");
			}
			if(event.getSource() == hintButton){
				ComputerPlayer hintPlayer = new ComputerPlayer();
				hintPlayer.setDifficulty(2);
				int recommendedColumn = 3;
				if (playerTurnIdentity == 1){
					hintPlayer.setToken(player1.getToken());
					recommendedColumn = hintPlayer.getMove(gameBoard.getCurrentBoard());
				} else {
					hintPlayer.setToken(player_H.getToken());
					recommendedColumn = hintPlayer.getMove(gameBoard.getCurrentBoard());
				}
				informationLabel.setText("I recommend column " + (recommendedColumn + 1));
			}
			if(event.getSource() == resetHighscore){
				player1.resetHighscore();
				highscoreLabel.setText("The all time highscore is: 0");
			}
		}
	}
	
	/** This class is responsible for all button clicks pertaining to GAME and MOVE SELECTION items
	*/
	public class HandleColumnButtonClick implements EventHandler<ActionEvent>{
		private int columnSelected;
		private char playerToken;
	
		public HandleColumnButtonClick(int aColumn){
			columnSelected = aColumn;
		}
	
		public void handle(ActionEvent event){
			
			/* If the game has been won then the buttons just start a new game */
			if (gameBoard.hasWon(player1.getToken()) || gameBoard.hasWon(player_C.getToken()) || gameBoard.hasWon(player_H.getToken()) || gameBoard.isFull()) {
				gameBoard.resetBoard();
				displayGrid();
				playerTurnIdentity = 1;
				informationLabel.setText("New Game. Player 1. It's your Turn!");
			} else {
				
				/* If Player2 is a human */
				if (player2Identity == 1){
					if (gameBoard.isValid(columnSelected)){
						if (playerTurnIdentity == 1){
							informationLabel.setText("Player 2. It's your turn!");
							gameBoard.updateBoard(columnSelected, player1.getToken());
							playerTurnIdentity = 2;
						} else {
							informationLabel.setText("Player 1. It's your turn!");
							gameBoard.updateBoard(columnSelected, player_H.getToken());
							playerTurnIdentity = 1;
						}
					} else {
						informationLabel.setText("Invalid Move, please select a different column.");
					}
					displayGrid();
					
					
					/* See if either player has won */
					if (gameBoard.hasWon(player1.getToken())) {
						informationLabel.setText("Congratulations Player 1 you've won!");
					}
					if (gameBoard.hasWon(player_H.getToken())) {
						informationLabel.setText("Congratulations Player 2 you've won!");
					}
					if (gameBoard.isFull()) {
						informationLabel.setText("Game Over. It's a tie.");
					}
				}
				
				
				/* If Player2 is a computer */
				if (player2Identity == 2){
					/* Get a valid move from the player */
					if (gameBoard.isValid(columnSelected)){
						informationLabel.setText("Player. It's your Turn!");
						gameBoard.updateBoard(columnSelected, player1.getToken());
						/* See if they won and if not ask the computer for a move. */
						if (gameBoard.hasWon(player1.getToken())){
							player1.incrementScore();
							informationLabel.setText("Congratulations! You Beat the Computer!.");
							displayGrid();
						} else {
							gameBoard.updateBoard(player_C.getMove(gameBoard.getCurrentBoard()), player_C.getToken());
							displayGrid();
							if (gameBoard.hasWon(player_C.getToken())){
								informationLabel.setText("I'm sorry but the computer won.");
								player1.resetScore();
							}
							if (gameBoard.isFull()){
								informationLabel.setText("Game Over. It's a tie.");
								player1.resetScore();
							}
						}
						
						
					/* error checking */
					} else {
						informationLabel.setText("Invalid Move, please select a different column.");
					}
					highscoreLabel.setText("The all time highscore is: " + player1.getHighscore());
					currentScoreLabel.setText("Your current score is: " + player1.getScore());
				}
			}
		}
	}


}