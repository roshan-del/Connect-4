/** This class is the parent class to both ComputerPlayer and HumanPlayer
* @author Tim Dees
*/
public class Player{
	char token;
	
	/** This constructor allows the creation of a player with no initial values.
	*/
	public Player(){
	}
	
	/** This constructor allows the creation of a player with pre-determined token.
	* @param newToken - the token color to initialize for the new ComputerPlayer
	*/
	public Player(char newToken){
		this.setToken(newToken);
	}
	
	
	/** This method sets the token/color for the player
	* @param newToken - the token to which to set the player
	*/
	public void setToken(char newToken){
		token = newToken;
	}
	
	
	/** This method returns the token/color for the player
	* @return the token of the player
	*/
	public char getToken(){
		return token;
	}
}