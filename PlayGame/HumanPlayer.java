/** Class that holds information to do with Human Players and their moves
*@author Vaughan Lacharite
*/
import java.io.*;
import java.lang.*;
import java.util.Scanner;

public class HumanPlayer extends Player{
	private String name;
	private int highscore;	
	private int score;
	
	/** This constructor allows the creation of a human player with no initial values.
	*/
	public HumanPlayer(){
		loadHighscore();
	}
	
	/** This constructor allows the creation of a human player with initial values for both name and token.
	* @param nameToSet - the name of the new player
	* @param tokenToSet - the desired token of the new player
	*/
	public HumanPlayer(String nameToSet,char tokenToSet){
		this.setName(nameToSet);
		this.setToken(tokenToSet);
	}
	
	/** This constructor allows the creation of a computer player with pre-determined Token.
	* @param newToken - the token to initialize
	*/
	public HumanPlayer(char newToken){
		super(newToken);
	}
	
	/** This method allows the name of the player to be set.
	* @param title - the name of the player
	*/
	public void setName(String title){
		name = title;
	}
	
	
	/** This method retrieves to current name of the player
	* @return - the name of the player
	*/
	public String getName(){
		return name;
	}
	
	
	/** This method increments count by one, to be used in the event of a win.
	*/
	public void incrementScore(){
		score++;
		if (score > highscore){
			highscore = score;
			saveHighscore();
		}
	}
	
	
	/** This method resets score to 0, to be used in the event of a loss.
	*/
	public void resetScore(){
		score = 0;
	}	
		
		
	/** This method retrieves to current score of the player
	* @return - the current score of the player
	*/
	public int getScore(){
		return score;
	}
	
	
	/** This method retrieves to current highscore of all time.
	* @return - the number of times beating a computer consecutively without losing.
	*/
	public int getHighscore(){
		loadHighscore();
		return highscore;
	}
	
	/**Retrieves the current best high score from the given value in the file.
	*/
	public void loadHighscore(){
		try{
			BufferedReader reader = new BufferedReader(new FileReader("highscore.txt"));
			String line = reader.readLine();
			highscore = Integer.parseInt(line);
			reader.close();
		}catch(FileNotFoundException e){
			System.out.println("That player file does not exist.");
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	/**Saves the high score for future reference
	*/
	public void saveHighscore(){
		try{
			PrintWriter writer = new PrintWriter("highscore.txt");
			writer.println(highscore);
			writer.close();
		}catch(FileNotFoundException e){
			System.out.println("That player file does not exist.");
		}
	}
	
	/**Sets the highscore to 0
	*/
	public void resetHighscore(){
		try{
			PrintWriter writer = new PrintWriter("highscore.txt");
			writer.println(0);
			writer.close();
		}catch(FileNotFoundException e){
			System.out.println("That player file does not exist.");
		}
	}
	 
	/** This method uses the console to retrieve the desired move of a player in a text based game.
	* @return - the desired column of a move for the player
	*/ 
	public int getMove(){
		/* Initialize the Scanner in the variable keyboard */
		Scanner keyboard = new Scanner(System.in);
		/* Prompt the player for a column number */
		System.out.println("Enter a valid column between 1-7 to place a token: ");
		/* Save the given integer inside the variable column */
		int column = keyboard.nextInt();
		column --;
		/* Confirm that the integer is a valid number, and keep prompting the user if it isn't */
		while(column < 0 && column > 6){
			System.out.println("That is not a valid row on the board");
			System.out.println("Enter a valid column between 1-7 to place a token: ");
			column = keyboard.nextInt();
		}

		
		return column;
	}

}