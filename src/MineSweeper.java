import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class MineSweeper {
    String[][] mineBoard;
    String[][] userBoard;
    int rowNumber;
    int colNumber;
    int mineCount;
     ArrayList<Integer> mayinRow = new ArrayList<>();
    ArrayList<Integer> mayinCol = new ArrayList<>();
    String closeSquare = "-";
    String mineSquare = "*";
    Random random = new Random();
    Scanner input = new Scanner(System.in);


    public MineSweeper(int rowNumber, int colNumber) {
        this.rowNumber = rowNumber;
        this.colNumber = colNumber;
        this.mineCount = rowNumber * colNumber / 4;
        this.userBoard = new String[rowNumber][colNumber];
        this.mineBoard = new String[rowNumber][colNumber];

    }

    // Function that asks the user how many rows and columns of a board do you want when the game starts
    public void takeUsersInputAndCreateBoard() {
        do {
            System.out.print("Satır Sayısını Giriniz: ");
            rowNumber = input.nextInt();
            System.out.print("Sütun Sayısını Giriniz: ");
            colNumber = input.nextInt();

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

            for (int i = 0; i < this.mineCount; i++) {
                int randomRow = random.nextInt(this.rowNumber);
                int randomCol = random.nextInt(this.colNumber);

                if (mineBoard[randomRow][randomCol] != mineSquare) {
                    mayinRow.add(randomRow);
                    mayinCol.add(randomCol);
                    mineBoard[randomRow][randomCol] = mineSquare;
                    userBoard[randomRow][randomCol] = "*";     //mineBoard[randomRow][randomCol];
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
        System.out.println("Mayınların Konumu ");

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
        do {

            System.out.print("Satır Sayısını Giriniz: ");
            rowNumber = input.nextInt();
            System.out.print("Sütun Sayısını Giriniz: ");
            colNumber = input.nextInt();
            System.out.println("===========================");

            // It is checked whether the row and column value entered by the user is within the specified range.
            if ((rowNumber < userBoard.length) && (colNumber < userBoard[0].length)) {
                checkUserInput(rowNumber, colNumber);
            }

        } while (rowNumber >= userBoard.length || colNumber >= userBoard[0].length);
    }

    public int countNeighborMines(int row, int col) {
        int count = 0;
        // Check the squares around the selected box
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int newRow = row + i;
                int newCol = col + j;
                // As you check each square around, see if it's moving out of area.
                if (newRow >= 0 && newCol >= 0) {
                    for (int b = 0; b < mayinCol.size(); b++) {
                        if (mayinRow.get(b) == newRow && mayinCol.get(b) == newCol) {
                            count++;
                        }
                    }
                }
            }
        }
        return count;
    }


    public void checkUserInput(int row, int col) {
        int count = countNeighborMines(row, col);

        if (mineBoard[row][col] == mineSquare) {
            System.out.println("Game Over! Mayına bastınız.");
        } else {
            System.out.println("Seçtiğiniz kutuda " + count + " adet mayın var.");
            userBoard[row][col] = Integer.toString(count);
            updateBoard(userBoard, count);
        }
    }

    public void updateBoard(String[][] userBoard, int count){
        for (int i=0; i< userBoard.length; i++){
            for (int j=0; j<userBoard[0].length; j++){

                if (userBoard[i][j] == mineSquare) {
                    System.out.print(closeSquare + " ");
                }
                else {
                    System.out.print(userBoard[i][j] + " ");
                }
            }
            System.out.println();
        }
    }


    public void runGame() {
        createMineBoard();

    }

}




