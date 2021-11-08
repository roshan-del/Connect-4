/** Text based interface for the Connect 4 Game
*@author Paul Latkovic
*/
import java.util.Scanner;

public class Game {

	private Connect4Board board = new Connect4Board();
	private HumanPlayer player1 = new HumanPlayer('B');
	private HumanPlayer player_H = new HumanPlayer('O');
	private ComputerPlayer player_C = new ComputerPlayer('O');

	
	/** This method organizes the flow of text based gameplay.
	*/
	public void play(){

		System.out.println("WELCOME TO CONNECT FOUR!");
		Scanner input = new Scanner(System.in);

		/* User input for their name*/
		System.out.println("Player ONE, What is your name?");
		player1.setName(input.nextLine());

		
		/* Choose to play against a human or a computer */
		System.out.println("Would you like to play against a human (0), or the computer (1)?");
		int opponentNum = input.nextInt();

		
		/* Error checking*/
		while (opponentNum != 0 && opponentNum != 1){
			System.out.println("That's not a valid number.");
			System.out.println("Would you like to play against a human (0), or the computer (1)?");
			opponentNum = input.nextInt();
		}
		
		
		/* Get the name of the second player */
		if(opponentNum == 0){
			System.out.println("Player TWO, what is your name");
			Scanner getName = new Scanner(System.in);
			player_H.setName(getName.nextLine());
		}
		
		
		/* Set the difficulty of the computer player */
		if (opponentNum != 0){
			System.out.println("Choose your difficulty. Easy (1) or Hard (2). Default is Hard.");
			int difficulty = input.nextInt();
			if (difficulty == 1){
				player_C.setDifficulty(1);
			} else {
				player_C.setDifficulty(2);
			}
		}
		
		
		/* If the opponent is a HumanPlayer*/
		if(opponentNum == 0){

			while (!board.hasWon(player1.getToken()) && !board.hasWon(player_C.getToken())&& !board.isFull()){
				board.print();
				System.out.println(player1.getName() + ", it's your turn");
				board.updateBoard(player1.getMove(), player1.getToken());

				if (board.hasWon(player1.getToken())){
					System.out.println("Congratulations " + player1.getName() + ", you won!!!");
				} else {
					board.print();

					System.out.println(player_H.getName() + ", it's your turn");
					board.updateBoard(player_H.getMove(), player_H.getToken());

					if (board.hasWon(player_H.getToken())){
						board.print();
						System.out.println("Congratulations " + player_H.getName() + ", you won!!!");
					}
				}
			}
			
			
		/* If player 2 is a ComputerPlayer*/
		} else {
			while (!board.hasWon(player1.getToken()) && !board.hasWon(player_C.getToken())&& !board.isFull()){
				board.print();
				System.out.println(player1.getName() +  ", it's your turn");
				board.updateBoard(player1.getMove(), player1.getToken());

				if (board.hasWon(player1.getToken())) {
					board.print();
					System.out.println("Congratulations " + player1.getName() + ", you won!!!");
				} else {
					try {
						Thread.sleep(500);
						board.updateBoard(player_C.getMove(board.getCurrentBoard()), player_C.getToken());
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					if (board.hasWon(player_C.getToken())){
						board.print();
						System.out.println("Sorry, the computer won!!!");
					}
				}
			}
		}
		
		if(board.isFull()){
			System.out.println("Sorry, that's a tie game.");
			board.resetBoard();
		}

	}


	
	/** Main method to activate a text based version of the connect 4 logic
	*/
	public static void main(String[] args){
		String decision = "y";
		while (decision.equals("y") || decision.equals("Y")) {
			/* User enters if they want to play the game */
			Scanner x = new Scanner(System.in);
			System.out.println("Would you like to play Connect Four? (Y/N)");
			decision = x.nextLine();

			if (decision.equals("y") || decision.equals("Y")) {
				Game game = new Game();
				game.play();
			}else {
				System.out.println("Thanks for Playing!");
			}
		}
	}
}
