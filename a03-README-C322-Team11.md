## C322 - Assignment 03
### 4/28/21
Sammy Cokeley - scokeley@iu.edu\
Ben Gramling - bengraml@iu.edu\
Kendall Mangrum - kmangrum@iu.edu
---------
## HOW TO START THE GAME
- Open Terminal/ Command Prompt
- Navigate to the ForagerGame/src Folder
- Compile The Java Source Files
    - javac *.java
- Start the RMI Registry
    - rmiregistry
- In a new terminal window (in the src folder), run GameOfLifeModel and supply modifythread/socket permissions as well as the game map.
    - java -Djava.security.policy=permit GameOfLifeModel map
        - in place of 'map', type the name of the map you want:
            - fifty_fifty = same size clusters
            - eighty_twenty = one small cluster one large
            - bullseye = a cool bullseye design
    - (The game is now running, a countdown from 5 minutes will display in the terminal)
- In a new terminal window, start the client
    - java GameOfLifeController
- Have Fun! Use the W, A, S, D keys to move around.
### Join a Game With a Second Player
- To join a running game with a second player, start by opening a new terminal window in the src folder and type:
    - java GameOfLifeController
-------

## Corrections:
- Fixed updateResources() method (resource generation and growth)
    - The updateResources() method that the previous team had created didn't properly control the growth of resources on the board. The resources had the ability to randomly generate throughout the board. This was incorrect, as the resources growth should be based on the growth formula and rely on the surrounding cells already having resources grown. We corrected the updateResources() method based on the implementation that Ben's group had from their Assignment02. We also adjusted the growth parameter g, so that the growth rate is much more reasonable.


## Additions:
- Updated RMI interface based on class decisions.
- Added reference to Model in Controller
- Added timer to stop the game after a certain amount of time and to call updateResources


## Updates to the UML Class Diagram
- View
	- Added a Jframe, JLabel, and method showScore() so that we could display the user's current score to them
- RMI Interface
	- Removed updateResources() method, added generatePlayerID() as decided during lecture
- Model
	- Added variables scheduler, numUpdates, and gameSecondsLeft. These are used for the timer and to display to the user how much time is remaining in the game.


## Our Design Choices:
- Player Interaction with Resources
    - Every time a user goes across a cell that has 1 or more resources, the player collects one of that resource and the resource level of that cell is decreased by 1.

- Player Interaction with Other Players
    - The team before had already considered what they wanted to do for player interaction and had things set up so that players were not able to be in the same location as another player. We decided to keep this player interaction for our program since it was already introduced into the game. This means that another player is blocked from going through a cell that already has another player inside of it.

- Starting Assignment of Resources
    - For the starting assignment of resources, we chose to allow the users to select which resource configuration to begin with by inputting the name of the starting file they would like. We gave the users the options of: 50/50, 80/20, and a bullseye pattern. To choose which starting resource file to use, the user should type in the name of the file they want after they type in the java security line to their terminal. For example, if the user wants to use the 50/50 configuration, they should type:
  java -Djava.security.policy=permit GameOfLifeModel fifty_fifty


- Game Timer
    - We have implemented a game timer in the main method of the Model. We make use of Runnable and inside of the run method, we call updateResources that is located in the model as well. The timer calls updateResources every 18 milliseconds to update the game board. The timer is set to end after 5 minutes, which ends the game.


## Design Pattern Use:
- Simple Factory
    - The Simple Factory is used for our Player interface. It is used to determine which type of Player is created, and OddPlayer or an EvenPlayer. In our design, the Simple Factory is used to randomly select one of two colors for the user to be displayed as. If the user has an even playerID, they are displayed as a purple circle. If the user has an odd playerID, they are displayed as a blue circle.

- Observer
    - The View class observes the Controller class, which implements the observable interface. This is how the view knows when to update itself and change the display.
