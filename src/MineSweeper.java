import java.sql.Array;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class MineSweeper {
    String[][] mineBoard;
    String[][] userBoard;
    int rowNumber;  // tahtanın rowu
    int colNumber; // tahtanının colu
    int mineCount;
    int[] may_col;
    int[] may_row;
    String closeSquare = "-";
    String mineSquare = "*";
    int count;
    Random random = new Random();
    Scanner input = new Scanner(System.in);

    public MineSweeper() {

    }

    // Function that asks the user how many rows and columns of a board do you want when the game starts
    public void takeUsersInputAndCreateBoard() {
        do {

            System.out.print("Enter The Number of Rows: ");
            rowNumber = input.nextInt();
            System.out.print("Enter The Number of Column: ");
            colNumber = input.nextInt();

            if ((rowNumber < 2 || colNumber < 2)) {
                System.out.println("You entered an incorrect value range! Row and column values must be at least 2");
            }

            mineBoard = new String[rowNumber][colNumber];
            userBoard = new String[rowNumber][colNumber];
            mineCount = rowNumber * colNumber / 4;

        } while ((rowNumber < 2 || colNumber < 2));


    }

    // In the random function, a random value is first generated and then it is assigned to randomRow and randomCol values to assign randomness to the row and column.
    // Then, it is questioned whether there are mines in the row and col on the mine board. If there are, the mine sign is equalized in this row and column, otherwise the i value is reduced by 1.
    public void generateRandom() {
        try {
            takeUsersInputAndCreateBoard();

            may_col = new int[mineCount];
            may_row = new int[mineCount];

            for (int i = 0; i < this.mineCount; i++) {
                int randomRow = random.nextInt(this.rowNumber);
                int randomCol = random.nextInt(this.colNumber);

                if (mineBoard[randomRow][randomCol] != mineSquare) {
                    may_col[i] = randomCol;
                    may_row[i] = randomRow;

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

    // Row and column information is received from the user and the number of mines is determined according to this information.
    // The random function is called and it is checked whether the mine is on the board or not, the derived numbers are ready to be used below.
    // Each row and col is navigated with a for loop, and if there is a mine, it is printed with a * sign, otherwise a - sign is placed.
    // If the user enters a value less than 2*2, it asks for row and col again
    public void createMineBoard() {

        generateRandom();
        System.out.println("Location of Mines ");

        for (int i = 0; i < rowNumber; i++) {
            for (int j = 0; j < colNumber; j++) {
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
        for (int i = 0; i < rowNumber; i++) {
            for (int j = 0; j < colNumber; j++) {
                if (userBoard[i][j] == mineSquare) {
                    System.out.print(closeSquare + " ");
                } else {
                    System.out.print(closeSquare + " ");
                }
            }
            System.out.println();
        }
        takeUsersAnswer();
    }

    // Function that asks the row and column information that the user wants to select in the game
    public void takeUsersAnswer() {
        int counter = 0;
        while (counter <= userBoard.length * userBoard[0].length) {

            System.out.print("Enter The Number of Rows: ");
            rowNumber = input.nextInt();
            System.out.print("Enter The Number of Column: ");
            colNumber = input.nextInt();
            System.out.println("===========================");

            if ((rowNumber < userBoard.length) && (colNumber < userBoard[0].length)) {
                if (userBoard[rowNumber][colNumber] != null && userBoard[rowNumber][colNumber] != mineSquare) {
                    System.out.println("Bu alan önceden seçildi tekrar giriniz !!");
                    continue;
                }

                if (userBoard[rowNumber][colNumber] == mineSquare) {
                    checkUserInput(rowNumber, colNumber);
                    break;
                }
                if ((userBoard[rowNumber][colNumber] != (mineSquare))) {
                    checkUserInput(rowNumber, colNumber);
                    break;
                }

            } else {
                System.out.println("You Must Enter The Invalid Row Number Or Column Number so You Must Enter The New Row and Column Number");
            }
            counter++;

        }

    }
    // It is checked whether the row and column value entered by the user is within the specified range.


    public int countNeighborMines(int row, int col) {

        count = 0;
        // Check the squares around the selected box
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int newRow = row + i;
                int newCol = col + j;
                // As you check each square around, see if it's moving out of area.
                if (newRow >= 0 && newCol >= 0) {
                    for (int b = 0; b < may_col.length; b++) {
                        if (may_row[b] == newRow && may_col[b] == newCol) {
                            count++;
                        }
                    }
                }
            }
        }
        return count;
    }


    public void checkUserInput(int row, int col) {
         count = countNeighborMines(row, col);

        if (mineBoard[row][col] == mineSquare) {
            System.out.println("Game Over!! You stepped on a mine.");
            updateBoard(userBoard);
        } else {
            userBoard[row][col] = Integer.toString(count);
            updateBoard(userBoard);
        }
    }

    public void updateBoard(String[][] userBoard) {
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
        if (mineBoard[rowNumber][colNumber] == mineSquare) {

        } else {
            takeUsersAnswer();
        }

    }


    public void runGame() {
        createMineBoard();

    }

}




