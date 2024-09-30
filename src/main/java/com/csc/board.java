package com.csc;

import java.util.Scanner;

public class board {
    char[][] positions = {{'1','2','3'},{'4','5','6'},{'7','8','9'}};
    Scanner sc = new Scanner(System.in);
    String input = "";
    int playerCount = 0;
    Boolean status = false;

    int[] indexes(String input){
        int[] idx = new int[2];
        int val =  Integer.parseInt(input) - 1;
        idx[0] = val/3;
        idx[1] = val%3;
        return idx;
    }

    void displayBoard(){
        String row1 = positions[0][0] + " | " + positions[0][1] + " | " + positions[0][2];
        String row2 = positions[1][0] + " | " + positions[1][1] + " | " + positions[1][2];
        String row3 = positions[2][0] + " | " + positions[2][1] + " | " + positions[2][2];
        System.out.println(row1);
        System.out.println("---------");
        System.out.println(row2);
        System.out.println("---------");
        System.out.println(row3 + "\n");
    }

    Boolean isValidPosition(int[] pos){
        int pos1 = pos[0];
        int pos2 = pos[1];
        if(positions[pos1][pos2] == 'x'|| positions[pos1][pos2] == 'o') {
            return false;
        }

        return true;
    }

    Boolean isValidInput() {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    public void setPositions(int[] pos) {
        int pos1 = pos[0];
        int pos2 = pos[1];
        if (!isValidPosition(pos)){
            System.out.println("That move is invalid!");
        }
        if (playerCount % 2 == 0){
            positions[pos1][pos2] = 'x';
        } else {
            positions[pos1][pos2] = 'o';
        }
    }
    String whichPlayer(){
        if (playerCount % 2 == 0){
            return "one";
        } else {
            return "two";
        }
    }

    void promptUser(){
        String s = whichPlayer();
        System.out.println("Player " + s + " - where would you like to move?: ");
        input = sc.nextLine();
    }

    Boolean checkGameStatus(){
        for(int i = 0; i <= 2; i++){
            String s = "";
            for(int j = 0; j <= 2; j++){
                s += positions[i][j];
            }
            if(s.equals("xxx") || s.equals("ooo")){
                return true;
            }
        }
        for(int i = 0; i <= 2; i++){
            String s = "";
            for(int j = 0; j <= 2; j++){
                s += positions[j][i];
            }
            if(s.equals("xxx") || s.equals("ooo")){
                return true;
            }
        }
        if(positions[0][0] == positions[1][1] && positions[1][1] == positions[2][2]){
            return true;
        }
        if(positions[2][0] == positions[1][1] && positions[1][1] == positions[0][2]){
            return true;
        }
        return false;
    }

    void runGame(){
        while(!status){
            this.displayBoard();
            promptUser();
            if ((!isValidInput())){
                System.out.println("That move is invalid!\n");
                continue;
            }
            if ((!isValidPosition(indexes(input)) || (Integer.parseInt(input) > 9 || Integer.parseInt(input) < 1))){
                System.out.println("That move is invalid!\n");
                continue;
            }
            setPositions(indexes(input));
            if(checkGameStatus()){
                displayBoard();
                System.out.println("Congratulations! Player " + whichPlayer() + " wins!\n");
            }
            playerCount++;
            if(playerCount>=9){
                displayBoard();
                status = true;
                System.out.println("Cat's game!");
                continue;
            }
            status = checkGameStatus();
        }
    }
}

