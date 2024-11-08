package com.csc;

import java.util.Scanner;
import java.util.Random;

public class board {
    char[][] positions = {{'1', '2', '3'}, {'4', '5', '6'}, {'7', '8', '9'}};
    Scanner sc = new Scanner(System.in);
    String input = "";
    int playerCount = 0;
    Boolean status = false;

    int[] indexes(String input) {
        int[] idx = new int[2];
        int val = Integer.parseInt(input) - 1;
        idx[0] = val / 3;
        idx[1] = val % 3;
        return idx;
    }

    int[] indexes(int input) {
        int[] idx = new int[2];
        idx[0] = input / 3;
        idx[1] = input % 3;
        return idx;
    }

    void displayBoard() {
        String row1 = positions[0][0] + " | " + positions[0][1] + " | " + positions[0][2];
        String row2 = positions[1][0] + " | " + positions[1][1] + " | " + positions[1][2];
        String row3 = positions[2][0] + " | " + positions[2][1] + " | " + positions[2][2];
        System.out.println(row1);
        System.out.println("---------");
        System.out.println(row2);
        System.out.println("---------");
        System.out.println(row3 + "\n");
    }

    Boolean isValidPosition(int[] pos) {
        int pos1 = pos[0];
        int pos2 = pos[1];
        if (positions[pos1][pos2] == 'x' || positions[pos1][pos2] == 'o') {
            return false;
        }

        return true;
    }

    Boolean isValidInput() {
        try {
            int test = Integer.parseInt(input);
            if (test < 1 || test > 9) {
                return false;
            }
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public void setPositions(int[] pos) {
        int pos1 = pos[0];
        int pos2 = pos[1];
        if (!isValidPosition(pos)) {
            System.out.println("That move is invalid!");
        }
        if (playerCount % 2 == 0) {
            positions[pos1][pos2] = 'x';
        } else {
            positions[pos1][pos2] = 'o';
        }
    }

    public void botSetPosition(int[] pos) {
        int pos1 = pos[0];
        int pos2 = pos[1];
        positions[pos1][pos2] = 'o';
    }

    String whichPlayer() {
        if (playerCount % 2 == 0) {
            return "one";
        } else {
            return "two";
        }
    }

    void promptUser() {
        String s = whichPlayer();
        System.out.println("Player " + s + " - where would you like to move?: ");
        input = sc.nextLine();
    }

    Boolean checkGameStatus() {
        for (int i = 0; i <= 2; i++) {
            String s = "";
            for (int j = 0; j <= 2; j++) {
                s += positions[i][j];
            }
            if (s.equals("xxx") || s.equals("ooo")) {
                return true;
            }
        }
        for (int i = 0; i <= 2; i++) {
            String s = "";
            for (int j = 0; j <= 2; j++) {
                s += positions[j][i];
            }
            if (s.equals("xxx") || s.equals("ooo")) {
                return true;
            }
        }
        if (positions[0][0] == positions[1][1] && positions[1][1] == positions[2][2]) {
            return true;
        }
        if (positions[2][0] == positions[1][1] && positions[1][1] == positions[0][2]) {
            return true;
        }
        return false;
    }

    void botMove(){
        Random rand = new Random();
        int[] botIdx = new int[2];
        Boolean good = false;
        while (!good) {
            int spot = rand.nextInt(9);
            botIdx = indexes(spot);
            good = isValidPosition(botIdx);
        }
        botSetPosition(botIdx);
    }

    void pvpGame() {
        while (!status) {
            this.displayBoard();
            promptUser();
            if ((!isValidInput())) {
                System.out.println("That move is invalid!\n");
                continue;
            }
            if ((!isValidPosition(indexes(input)) || (Integer.parseInt(input) > 9 || Integer.parseInt(input) < 1))) {
                System.out.println("That move is invalid!\n");
                continue;
            }
            setPositions(indexes(input));
            if (checkGameStatus()) {
                displayBoard();
                System.out.println("Congratulations! Player " + whichPlayer() + " wins!\n");
            }
            playerCount++;
            if (playerCount >= 9) {
                displayBoard();
                status = true;
                System.out.println("Cat's game!");
                continue;
            }
            status = checkGameStatus();
        }
    }

    void displayWinner(){
        displayBoard();
        System.out.println("Congratulations! Player " + whichPlayer() + " wins!\n");
    }
    void botGame() {
        while (!status) {
            this.displayBoard();
            status = checkGameStatus();
            if (checkGameStatus()) {
                displayBoard();
            }
            promptUser();
            if ((!isValidInput())) {
                System.out.println("That move is invalid!\n");
                continue;
            }
            if ((!isValidPosition(indexes(input)) || (Integer.parseInt(input) > 9 || Integer.parseInt(input) < 1))) {
                System.out.println("That move is invalid!\n");
                continue;
            }
            setPositions(indexes(input));
            status = checkGameStatus();
            if (status) {
                displayWinner();
                continue;
            }

            playerCount++;
            if (playerCount >= 9) {
                status = true;
                displayBoard();
                System.out.println("Cat's game!");
                continue;
            }
            botMove();
            status = checkGameStatus();
            if (status) {
                displayWinner();
                continue;
            }
            playerCount++;


        }
    }


    int menu() {
        System.out.println("Welcome to Tic-Tac-Toe!\nEnter 1 to enter a PvP game or 2 to play against a computer\n");
        Boolean goodGame = false;
        int gameAnswerInt = 0;
        while (!goodGame) {
            String gameAnswer = sc.nextLine();
            try {
                int tempGameAnswerInt = Integer.parseInt(gameAnswer);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input\n");
                continue;
            }
            int tempGameAnswerInt = Integer.parseInt(gameAnswer);
            if (tempGameAnswerInt < 1 || tempGameAnswerInt > 2) {
                System.out.println("Invalid input\n");
                continue;
            }
            gameAnswerInt = tempGameAnswerInt;
            goodGame = true;
        }
        return gameAnswerInt;
    }

    int askAgain() {
        System.out.println("Enter 1 to play again or 2 to exit\n");
        Boolean goodAnswer = false;
        int gameAnswerInt = 0;
        while (!goodAnswer) {
            String gameAnswer = sc.nextLine();
            try {
                int tempGameAnswerInt = Integer.parseInt(gameAnswer);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input\n");
                continue;
            }
            int tempGameAnswerInt = Integer.parseInt(gameAnswer);
            if (tempGameAnswerInt < 1 || tempGameAnswerInt > 2) {
                System.out.println("Invalid input\n");
                continue;
            }
            gameAnswerInt = tempGameAnswerInt;
            goodAnswer = true;
        }
        return gameAnswerInt;
    }

    void resetGame(){
        char[][] tempPositions = {{'1', '2', '3'}, {'4', '5', '6'}, {'7', '8', '9'}};
        System.arraycopy(tempPositions, 0, positions, 0, tempPositions.length);
        input = "";
        playerCount = 0;
        status = false;
    }


    void runGame() {
        int game = menu();
        Boolean done = false;
        while (!done) {
            if (game == 1) {
                pvpGame();
            }
            if (game == 2) {
                botGame();
            }
            int ans = askAgain();
            if (ans == 2) {
                System.out.println("Exited\n");
                done = true;
                }
            resetGame();
            }
        }
    }


