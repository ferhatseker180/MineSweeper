import java.util.Random;
import java.util.Scanner;

public class MineSweeper {
    String[][] mineBoard;
    String[][] userBoard;
    int rowNumber;
    int colNumber;
    int mineCount;
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

    // In the random function, a random value is first generated and then it is assigned to randomRow and randomCol values to assign randomness to the row and column.
    // Then, it is questioned whether there are mines in the row and col on the mine board. If there are, the mine sign is equalized in this row and column, otherwise the i value is reduced by 1.
    public void generateRandom() {
        try {
            for (int i = 0; i < mineCount; i++) {
                int randomRow = random.nextInt(rowNumber);
                int randomCol = random.nextInt(colNumber);

                if (mineBoard[randomRow][randomCol] != mineSquare) {
                    mineBoard[randomRow][randomCol] = mineSquare;
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

        do {

            System.out.print("Satır Sayısını Giriniz: ");
            this.rowNumber = input.nextInt();
            System.out.print("Sütun Sayısını Giriniz: ");
            this.colNumber = input.nextInt();

            this.mineBoard = new String[rowNumber][colNumber];
            this.mineCount = rowNumber * colNumber / 4;

            generateRandom();

            for (int i = 0; i < rowNumber; i++) {
                for (int j = 0; j < colNumber; j++) {
                    if (mineBoard[i][j] == mineSquare) {
                        System.out.print("* ");
                    } else {
                        System.out.print("- ");
                    }
                }
                System.out.println();
            }

        } while ((rowNumber < 2 && colNumber < 2));

    }


}


