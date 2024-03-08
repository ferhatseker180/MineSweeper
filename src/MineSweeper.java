import java.util.Random;
import java.util.Scanner;

/**
 * This is a Minesweeper Game backend project
 *
 * @author Ferhat Şeker
 * @version Java 1.8
 */
public class MineSweeper {
    String[][] mineBoard;
    String[][] userBoard;
    int boardRowNumber;  // Board Total Row
    int boardColNumber; // Board Total Column
    int selectedBoardRowNumber; // User choose row number
    int selectedBoardColNumber; // User chhose col number
    int mineCount;
    int[] mine_column;
    int[] mine_row;
    String closeSquare = "-"; // Unopened box symbol
    String mineSquare = "*"; // mine symbol
    int neighborMineCount; // Number of neighboring mines
    int gameStepCounter = 0; // Number of steps of the game
    Random random = new Random(); // Random number generation
    Scanner input = new Scanner(System.in); // Scanner Class

    // Constructor : I didn't fill constructor because I don't need constructor in main method so I allow for Java creating constructor automatically.
    public MineSweeper() {

    }

    // Function that asks the user how many rows and columns of a board do you want when the game starts
    // Then it checks if the entered values are in a valid range.
    // Evaluation Form 7
    public void takeUserInputAndCreateBoard() {
        do {

            System.out.print("Enter The Number of Rows: ");
            boardRowNumber = input.nextInt();
            System.out.print("Enter The Number of Column: ");
            boardColNumber = input.nextInt();

            if ((boardRowNumber < 2 || boardColNumber < 2)) {
                System.out.println("You entered an incorrect value range! Row and column values must be at least 2");
            }

            mineBoard = new String[boardRowNumber][boardColNumber];
            userBoard = new String[boardRowNumber][boardColNumber];
            mineCount = boardRowNumber * boardColNumber / 4;

        } while ((boardRowNumber < 2 || boardColNumber < 2));
    }

    // In the random function, a random value is first generated and then it is assigned to randomRow and randomCol values to assign randomness to the row and column.
    // Then, it is questioned whether there are mines in the row and col on the mine board. If there are, the mine sign is equalized in this row and column, otherwise the i value is reduced by 1.
    // Also the locations of the mines are stored in an array.
    // Evaluation Form 8
    public void generateRandomNumber() {
        try {
            takeUserInputAndCreateBoard();

            mine_column = new int[mineCount];
            mine_row = new int[mineCount];

            for (int i = 0; i < this.mineCount; i++) {
                int randomRow = random.nextInt(this.boardRowNumber);
                int randomCol = random.nextInt(this.boardColNumber);

                if (mineBoard[randomRow][randomCol] != mineSquare) {
                    mine_column[i] = randomCol;
                    mine_row[i] = randomRow;
                    mineBoard[randomRow][randomCol] = mineSquare;
                    userBoard[randomRow][randomCol] = mineBoard[randomRow][randomCol];
                } else {
                    i--;
                }
            }

        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }

    // The mine board is created within this function.
    public void createMineBoard() {
        generateRandomNumber();
        System.out.println("========== Location of Mines ==========");
        for (int i = 0; i < boardRowNumber; i++) {
            for (int j = 0; j < boardColNumber; j++) {
                if (mineBoard[i][j] == mineSquare) {
                    System.out.print(mineSquare + " ");
                } else {
                    System.out.print(closeSquare + " ");
                }
            }
            System.out.println();
        }
        createUserBoard();
    }

    //A welcome message was printed for the user with the createUserBoard function and the user board designed for the user was written in this function.
    public void createUserBoard() {
        System.out.println("========== Welcome to MineSweeper Game ==========");
        for (int i = 0; i < boardRowNumber; i++) {
            for (int j = 0; j < boardColNumber; j++) {
                System.out.print(closeSquare + " ");
            }
            System.out.println();
        }
        takeUserAnswer();
    }

    // It asks the row and column information that the user wants to select in the game and calls the function
    // that queries whether this location information has been entered before, whether there are mines there or whether the game is completed.
    // Evaluation Form 9 and 10 : The row and column information that the user wants to mark is obtained and it is checked whether it is within the valid range.
    public void takeUserAnswer() {
        // The while loop ends when the user loses or wins the game.
        while (true) {
            System.out.print("Enter The Number of Rows: ");
            selectedBoardRowNumber = input.nextInt();
            System.out.print("Enter The Number of Column: ");
            selectedBoardColNumber = input.nextInt();
            System.out.println("===========================");

            if ((selectedBoardRowNumber < userBoard.length) && (selectedBoardColNumber < userBoard[0].length)) {
                if (userBoard[selectedBoardRowNumber][selectedBoardColNumber] != null && userBoard[selectedBoardRowNumber][selectedBoardColNumber] != mineSquare) {
                    System.out.println("This coordinate has been selected before, please enter another coordinate!!");
                    continue;
                }

                if (userBoard[selectedBoardRowNumber][selectedBoardColNumber] == mineSquare) {
                    checkUserInput(selectedBoardRowNumber, selectedBoardColNumber);
                    break;
                }
                if ((userBoard[selectedBoardRowNumber][selectedBoardColNumber] != (mineSquare))) {
                    gameStepCounter++;
                    checkUserInput(selectedBoardRowNumber, selectedBoardColNumber);
                    break;
                }

                if (gameStepCounter == boardColNumber * boardRowNumber - mineCount) {
                    //
                    checkUserInput(selectedBoardRowNumber, selectedBoardColNumber);
                    break;
                }
            } else {
                System.out.println("You Must Enter The Invalid Row Number Or Column Number so You Must Enter The New Row and Column Number");
            }
        }
    }

    // It calculates to inform the user whether there are any mines in the neighboring areas of the box in the row and column entered by the user and if so, how many there are.
    // Evaluation Form 12 : The number of neighboring mines is calculated here
    public int countNeighborMines(int row, int col) {
        neighborMineCount = 0;
        // Check the squares around the selected box
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int newRow = row + i;
                int newCol = col + j;
                // As you check each square around, see if it's moving out of area.
                if (newRow >= 0 && newCol >= 0) {
                    for (int b = 0; b < mine_column.length; b++) {
                        if (mine_row[b] == newRow && mine_column[b] == newCol) {
                            neighborMineCount++;
                        }
                    }
                }
            }
        }
        return neighborMineCount;
    }

    //It evaluates the row and column information entered by the user, checks whether there are mines there,
// whether the game is over or whether the game is continuing, and calls the function to update the board.
    // Evaluation Form 12 : This rule is fulfilled in two separate functions. The countNeighborMines function simply detects how many neighboring mines there are, sends this data to the checkUserInput function and prints it.
    public void checkUserInput(int row, int col) {
        neighborMineCount = countNeighborMines(row, col);
        if (mineBoard[row][col] == mineSquare || (gameStepCounter == boardColNumber * boardRowNumber - mineCount)) {
            updateBoard(userBoard);
        } else {
            userBoard[row][col] = Integer.toString(neighborMineCount);
            updateBoard(userBoard);
        }
    }

    // It checks whether there are mines at the selected points and whether the box in the next row and column has been opened before.
    public void controlMineOrNull() {
        for (int i = 0; i < userBoard.length; i++) {
            for (int j = 0; j < userBoard[0].length; j++) {
                if (userBoard[i][j] == mineSquare || userBoard[i][j] == null) {
                    System.out.print(closeSquare + " ");
                } else {
                    System.out.print(userBoard[i][j] + " ");
                }
            }
            System.out.println();
        }
    }

    // This function updates the user board based on whether the game is won, lost or continued.
    // Evaluation Form 11 : In all cases such as winning, losing or continuing, the board is updated in this function.
    public void updateBoard(String[][] userBoard) {
        checkGameResult();
    }

    // This function allows the user to print a board showing the completed version of the game when they win the game.
    // Evaluation Form 15
    public void printFinalStageAnswerBoard() {
        for (int i = 0; i < boardRowNumber; i++) {
            for (int j = 0; j < boardColNumber; j++) {
                int neighborCount = countNeighborMines(i, j);
                if (mineBoard[i][j] == mineSquare) {
                    System.out.print(mineSquare + " ");
                } else {
                    System.out.print(neighborCount + " ");
                }
            }
            System.out.println();
        }
    }

    // Evaluation Form 13 : As mentioned in Rule 11, it updates the board and suppresses the message that you have lost the game if a mine has been stepped on in the current table.
    // Evaluation Form 14 : As mentioned in Rule 11, it updates the board and informs you that you have won if the game is completed without hitting a mine on the updated table.
    public void checkGameResult() {
        controlMineOrNull();
        if (mineBoard[selectedBoardRowNumber][selectedBoardColNumber] == (mineSquare)) {
            loseGameMessage();
            return;
        }
        if ((gameStepCounter == boardColNumber * boardRowNumber - mineCount)) {
            showWinGameMessage();
            return;
        }
        if ((gameStepCounter != boardColNumber * boardRowNumber - mineCount) && mineBoard[selectedBoardRowNumber][selectedBoardColNumber] != (mineSquare)) {
            takeUserAnswer();
        }

    }

    // Evaluation Form 15 : The winning message is printed here, and the function that prints the current board when winning is called here.
    public void showWinGameMessage() {
        System.out.println("Congratulations!! You completed the game without stepping on a mine.");
        printFinalStageAnswerBoard();
    }

    // Evaluation Form 15 : The losing message is printed here.
    public void loseGameMessage() {
        System.out.println("Game Over!! You stepped on a mine.");
    }


    // It is the main function in which all functions are collected and will be called within the main structure.
    // This is the main function with which the game is launched. The game is started by calling it within the Main method.
    // Evaluation form 6
    public void runGame() {
        createMineBoard();
    }

}




