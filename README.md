# Chess for Java refactor



1. The original assignment required  to:

Refactor the codebase using the Model-View-Controller (MVC) architectural pattern.

Apply object-oriented design principles to isolate game logic into appropriate layers.

Introduce unit tests using JUnit 5 to validate:

All piece movement rules

Special rules (castling, en passant, pawn promotion)

Game state transitions (check, checkmate, stalemate)

Improve code readability and scalability.

Maintain consistent GitHub commits reflecting progress.


2. What I Did (My Contributions)
I implemented the required functionality and went beyond the core instructions by introducing the following improvements:
 
 2.1 Refactoring into MVC Pattern

2.2 Separated logic between:

    Model: Game rules and data (model.*)

    View: GUI-related code (view.*)

    Controller: Orchestration logic


Created a controller package and added a new class: GameController.java
This class handles:

    Game initialization

    Switching turns (switchTurn)

    Move execution (makeMove)

    Determining game outcome (getWinner, isGameOver)

 




3. Helper Methods Added to Improve Design

In order to support clean control flow and testing, I added 6 public
helper methods inside the Game class:

Board getBoard()                             //Returns the board instance for interaction
boolean isGameOver()                         //Checks if game is over (checkmate/stalemate)
Checks if game is over (checkmate/stalemate)     // Executes a move like "e2 e4", returns success/failure
Color getWinner()                               //Returns WHITE, BLACK, or null depending on the winner
void switchTurn()                             // Alternates turn between players 
Color getCurrentPlayer()                     // Returns current player's color


4. I add Gamecontroller.java class in contorller layer which sperate the game logic (model) from user interface
(view) and manage interaction between them. it acts as the "Controller" in Model-View-Controller(MVC) design pattern
its main responsibility is to control game flow: handling moves, switching turns, and updating th UI accordingly.

5. I add 6 helper method for improving existing project, this methods are: public Board getBoard()	; // return current board intance
public boolean isGameOver()	; // chechk the game has ended like chackmate or stalemate
public boolean makeMove(String move)	; // tries to move on board like e2 24 returns success/fails
public Color getWinner()	; // return winner of the game
public void switchTurn()	;  // changes the turn to the next player( white with black and vica versa)
public Color getCurrentPlayer()	; // returns whose turn it currently is WHITE or BLACK

6.JUnit 5 Testing
   I developed comprehensive test coverage for both standard and special rules.

 6.1 Piece Movement Tests
PawnTest.java

RookTest.java

BishopTest.java

KnightTest.java

QueenTest.java

KingTest.java

6. 2 Special Move Rule Tests
CastlingTest.java

EnPassantTest.java

PawnPromotionTest.java

6. 3 Game State Logic Tests
CheckDetectionTest.java

CheckmateDetectionTest.java

6. 4 Test Suite
AllPieceTestsSuite.java aggregates all test classes for batch execution.



Introduced Color.java as an enum to represent player identity (WHITE, BLACK).

This eliminates magic numbers and improves readability and maintainability of game logic.


7. ## 💻 9. Technologies Used

This project was developed using the following technologies and tools:

| Technology             | Purpose                                                  |
|------------------------|----------------------------------------------------------|
| **Java 17+**           | Core programming language                                |
| **Swing (javax.swing)**| GUI framework for building the chess game interface     |
| **JUnit 5**            | Unit testing framework for validating game functionality |
| **Maven**              | Dependency and build management                          |
| **IntelliJ IDEA**      | Integrated Development Environment (IDE)                 |
| **Git & GitHub**       | Version control and commit tracking                      |
| **Object-Oriented Design** | Use of encapsulation, inheritance, polymorphism    |
| **MVC Architecture**   | Clear separation of Model, View, and Controller logic    |

your sincerely,
Japaridze Tornike

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------

# Chess for Java

In the Spring of 2014, I created a two-player Chess game, with checkmate detection and a chess clock as a part of a Programming course at Penn. Our objective was to develop and test a bug-free standalone game in Java, complete with a GUI and game logic components.

I developed a bug-free, fast and well-designed product with a clean user interface and received the highest possible score in the assignment. The source code is in this repository.

## Technology

This game is built using core Java, Java Swing GUI libraries and the jUnit test suite. It uses custom drawing for game components and self-programmed logic for checkmate detection. The code is modular, standalone and object oriented, which was a grading criteria for the assignment.

## Running

Compile the project into an executable .jar file by running the following ANT build script on the command line. Make sure jar-in-jar-loader.zip in this repository is in the folder.

```
ant -f build.xml
```

Then, run the executable .jar file, named _chess-java.jar_ to play.


-------------------------------------------------





