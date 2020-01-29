/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minesweeper;

import java.util.Random;

import static tools.input.askforInt;

/**
 * @author musang
 */
public class Minesweeper {

    // Global starting time definition
    private static long START_TIME;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // General variable definition
        int option;
        int col;
        int row;
        int positionRow = 0;
        int positionCol = 0;
        int bombs;
        int num;
        int score;
        boolean isPirateMode = false;
        boolean isGameLost = false;

        // Menu display
        System.out.println("========================================\n"
                + "=========Welcome to Minesweeper=========\n"
                + "========================================\n"
                + "1- 8 x 8 with 10 bombs\n"
                + "2- 16 x 20 with 50 bombs\n"
                + "3- Customized");
        option = askforInt("Choose an option :");

        // Play option selection
        switch (option) {
            case 1:
                row = 8;
                col = 8;
                bombs = 10;
                break;
            case 2:
                row = 20;
                col = 16;
                bombs = 50;
                break;
            case 3:
                row = askforInt("Rows : ", 2, 40);
                col = askforInt("Columns : ", 2, 40);
                bombs = askforInt("Bombs : ", 1, row * col);
                break;
            default:
                row = 0;
                col = 0;
                bombs = 0;
                break;
        }

        // Boards definition
        char[][] board = new char[col][row];
        char[][] playerBoard = new char[col][row];
        char[][] boardToPrint = playerBoard;
        boolean[][] eventBoard = new boolean[col][row];

        // Init eventBoard to false
        for (int i = 0; i < eventBoard.length; i++) {
            for (int j = 0; j < eventBoard[0].length; j++) {
                eventBoard[i][j] = false;
            }
        }

        // Boards filling
        fillBoard(playerBoard);
        fillBoard(board);
        fillBombs(board, bombs);
        for (int y = 0; y < board[0].length; y++) {
            for (int x = 0; x < board.length; x++) {
                num = fillNumbers(board, x, y);
                if (num != -1) {
                    if (num == 0) {
                        board[x][y] = ' ';
                    } else {
                        board[x][y] = (char) (num + 48);
                    }
                }
            }
        }

        // Starting time
        START_TIME = System.currentTimeMillis();
        score = getScore(board);

        // Main game loop
        do {
            // Pirate mode on or off
            boardToPrint = isPirateMode ? board : playerBoard;
            printBoard(boardToPrint);
            // User input
            positionRow = askforInt("Row : ", -1, board[0].length) - 1;
            positionCol = askforInt("Col : ", -1, board.length) - 1;
            // Check for pirate mode request
            if (positionCol < 0 || positionRow < 0) {
                isPirateMode = !isPirateMode;
            } else {
                // Cell not open
                if (!eventBoard[positionRow][positionCol]) {
                    eventBoard[positionRow][positionCol] = true;
                    openCell(board, positionRow, positionCol, playerBoard);
                    isGameLost = playerBoard[positionCol][positionRow] == '*';
                // Cell already open
                } else {
                    System.out.println("Cell already open.");
                }
            }

            // Calculate score and check for win
            if (getScore(playerBoard) == score) {
                System.out.println("---Congrats You Won---");
                System.out.println("Your score is  : " + score);
                break;
            }
        } while (!isGameLost);

    }

    /**
     * Opens a cell using the coordinates input by the user.
     *
     * @param board         Board of elements.
     * @param row           Y coordinate.
     * @param col           X coordinate.
     * @param playerBoard   Board viewed by the player.
     */
    private static void openCell(char[][] board, int row, int col, char[][] playerBoard) {
        if (board[col][row] == '*') {
            playerBoard[col][row] = '*';
            printBoard(playerBoard);
            System.out.println("Game Over");
            System.out.println("Your Score is : " + getScore(playerBoard));
        } else if ((board[col][row] >= 49 && board[col][row] <= 57) || playerBoard[col][row] == ' ') {
            playerBoard[col][row] = board[col][row];
        } else {
            playerBoard[col][row] = ' ';
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    if (!(col + i < 0 || col + i > board.length - 1 || row + j < 0 || row + j > board[0].length - 1)) {
                        openCell(board, row + j, col + i, playerBoard);
                    }
                }
            }
        }
    }

    /**
     * Displays the given board on the screen.
     *
     * @param board     Board to be displayed.
     */
    private static void printBoard(char[][] board) {
        System.out.print("\n\n\n     ");
        for (int i = 0; i < board.length; i++) {
            System.out.printf("%2d ", i + 1);
        }
        System.out.println();
        System.out.print("     ");
        for (int i = 0; i < board.length; i++) {
            System.out.print("===");
        }
        System.out.println("");
        for (int y = 0; y < board[0].length; y++) {
            System.out.printf("%2d | ", y + 1);
            for (int x = 0; x < board.length; x++) {
                System.out.print(" " + board[x][y] + " ");
            }
            System.out.println();
        }
        System.out.printf("Time since start: %d s\n", (System.currentTimeMillis() - START_TIME) / 1000);
    }

    private static void fillBoard(char[][] board) {
        for (int y = 0; y < board[0].length; y++) {
            for (int x = 0; x < board.length; x++) {
                board[x][y] = '·';
            }
        }
    }

    private static void fillBombs(char[][] board, int bombs) {
        Random rnd = new Random();
        int x, y;
        do {
            x = rnd.nextInt(board.length);
            y = rnd.nextInt(board[0].length);
            if (board[x][y] != '*') {
                board[x][y] = '*';
                bombs--;
            }
        } while (bombs > 0);
    }

    private static int fillNumbers(char[][] board, int x, int y) {
        int counter = 0;
        if (board[x][y] == '*') {
            return -1;
        }

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (!(x + i < 0 || x + i > board.length - 1 || y + j < 0 || y + j > board[0].length - 1)) {
                    if (board[x + i][y + j] == '*') {
                        counter++;
                    }
                }
            }
        }
        return counter;
    }

    private static int getScore(char[][] playerBoard) {
        int counter = 0;
        for (int y = 0; y < playerBoard[0].length; y++) {
            for (int x = 0; x < playerBoard.length; x++) {
                if (playerBoard[x][y] >= 49 && playerBoard[x][y] <= 57) {
                    counter += (int) playerBoard[x][y] - 48;
                }
            }
        }

        return counter;
    }
}
