To Play a GUI based version of the Game:

1 - Using command prompt, open the directory "PlayGame"
2 - compile all the files using the command "javac *.java"
3 - Run the game from the command prompt by using the command "java Connect4GUI"



To play a text based version of the Game:

1 - Using command prompt open the directory "PlayGame"
2 - compile all the files using the command "javac *.java"
3 - Run the game from the command prompt by using the command "java Game"



To run automated JUnit test on the classes:

1 - Using command prompt open the directory "Tests"
2 - compile all the files using the command "javac -cp .:junit-4.12.jar:hamcrest-core-1.3.jar *.java"
3 - run the tests by using the command "java -cp .:junit-4.12.jar:hamcrest-core-1.3.jar org.junit.runner.JUnitCore Connect4Test"



To test the game without automation:

1 - Using command prompt, open the directory "PlayGame"
2 - compile all the files using the command "javac *.java"
3 - Run the game from the command prompt by using the command "java "
4 - Play the game, press each button to ensure that it preforms the necessary tasks.
5 - using either 2 human players or an easy computer, test extreme conditions such as full column or full board.
6 - Test the hint button by using 2 human players and setting up scenarios which should have obvious outcomes (ex: can win, should block etc.)


Notes about Application:
1 - Highscore measures how many games in a row you have won against a computer, it doesn't show up when playing against a human.
