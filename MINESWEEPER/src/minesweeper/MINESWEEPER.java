/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minesweeper;

import java.util.Random;
import tools.input;

/**
 *
 * @author musang
 */
public class MINESWEEPER {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        int options, col, row, positionRow, positionCol;
        int bombs;
        int num;
        int maxscore ;

        System.out.println("========================================");
        System.out.println("=========Welcome to Minesweeper=========");
        System.out.println("========================================");
        System.out.println("1- 8 x 8 with 10 bombs");
        System.out.println("2- 16 x 20 with 50 bombs");
        System.out.println("3- Customized");
        options = input.askforInt("Choose an option :");

        switch (options) {
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
                row = input.askforInt("Rows : ", 2, 40);
                col = input.askforInt("Columns : ", 2, 40);
                bombs = input.askforInt("Bombs : ", 1, row * col);
                break;
            default:
                row = 0;
                col = 0;
                bombs = 0;
                break;
        }
        char board[][] = new char[col][row];
        char playerboard[][] = new char[col][row];
        fillboard(playerboard);
        fillboard(board);
        fillbombs(board, bombs);
        for (int y = 0; y < board[0].length; y++) {
            for (int x = 0; x < board.length; x++) {
                num = fillnumbers(board, x, y);
                if (num != -1) {
                    if (num == 0) {
                        board[x][y] = ' ';

                    } else {
                        board[x][y] = (char) (num + 48);
                    }
                }
            }
        }
        // printboard(board);
        maxscore = score(board);
        do {
            printboard(playerboard);
            positionRow = input.askforInt("Row :", 1, board[0].length) - 1;
            positionCol = input.askforInt("Col :", 1, board.length) - 1;
            discover(board, positionRow, positionCol, playerboard);
            if (score(playerboard) == maxscore) {
                System.out.println("---Congrats You Won---");
                printboard(board);
                System.out.println("Your Score Is  : "+maxscore);
                System.exit(0);
            }
        } while (playerboard[positionCol][positionRow] != '*');

    }

    public static void discover(char board[][], int row, int col, char playerboard[][]) {
        if (board[col][row] == '*') {
            playerboard[col][row] = '*';
            printboard(playerboard);
            System.out.println("Game Over");
            System.out.println("Your Score is : "+ score(playerboard));
        } else if ((board[col][row] >= 49 && board[col][row] <= 57) || playerboard[col][row] == ' ') {
            playerboard[col][row] = board[col][row];
        } else {
            playerboard[col][row] = ' ';
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    if (!(col + i < 0 || col + i > board.length - 1 || row + j < 0 || row + j > board[0].length - 1)) {
                        discover(board, row + j, col + i, playerboard);
                    }

                }

            }

        }

    }

    public static void printboard(char board[][]) {
        System.out.print("     ");
        for (int i = 0; i < board.length; i++) {
            System.out.printf("%2d ", i + 1);
        }
        System.out.println("");
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
            System.out.println("");
        }
    }

    public static void fillboard(char board[][]) {

        for (int y = 0; y < board[0].length; y++) {
            for (int x = 0; x < board.length; x++) {
                board[x][y] = '·';
            }
        }

    }

    public static void fillbombs(char board[][], int bombs) {

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

    public static int fillnumbers(char board[][], int x, int y) {
        int counter = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (board[x][y] == '*') {
                    return -1;
                }
                if (!(x + i < 0 || x + i > board.length - 1 || y + j < 0 || y + j > board[0].length - 1)) {
                    if (board[x + i][y + j] == '*') {
                        counter++;

                    }

                }

            }

        }
        return counter;
    }

    public static int score(char playerboard[][]) {
        int counter = 0;
        for (int y = 0; y < playerboard[0].length; y++) {
            for (int x = 0; x < playerboard.length; x++) {
                if (playerboard[x][y] >= 49 && playerboard[x][y] <= 57) {
                    counter += (int) playerboard[x][y]-48 ;
                }
            }
        }
        return counter ;
    }
}
