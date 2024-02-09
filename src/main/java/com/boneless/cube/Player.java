package com.boneless.cube;

import com.boneless.cube.util.Entity;

import static com.boneless.cube.CubeGame.objectList;

public class Player extends Entity {
    private final int playerNum;
    public Player(int playerNum, int x, int y, int[][] boardData, int boardWidth, int boardHeight){
        super(x,y,boardData,boardWidth,boardHeight);
        this.playerNum = playerNum;
    }
    @Override
    public void move(String dir) {
        int newX = x;
        int newY = y;

        if(playerNum == 1) {
            //System.out.println("Player 1 move: " + dir);
            switch (dir) {
                case "w" -> newX--;
                case "s" -> newX++;
                case "a" -> newY--;
                case "d" -> newY++;
            }
        }else{
            //System.out.println("Player 2 move: " + dir);
            switch (dir) {
                case "w" -> newX--;
                case "s" -> newX++;
                case "a" -> newY--;
                case "d" -> newY++;
            }
        }
        if(newX >= 0 && newX < boardWidth && newY >= 0 && newY < boardHeight){
            //check wall collision
            if(boardData[newX][newY] != objectList.get("wall")
                    && boardData[newX][newY] != objectList.get("player1")
                    && boardData[newX][newY] != objectList.get("player2")) {
                //check entity collision
                if(boardData[newX][newY] == objectList.get("enemy")){
                    kill();
                }
                boardData[x][y] = 0;
                if(playerNum == 1) {
                    boardData[newX][newY] = 1;
                } else if (playerNum == 2) {
                    boardData[newX][newY] = 2;
                }
                x = newX;
                y = newY;
            }
        }//puzzle game like hell taker or pico
    }
    @Override
    @SuppressWarnings({"CallToPrintStackTrace"})
    public void kill(){
        boardData[x][y] = 0;
        try{
            Thread.sleep(50);
            CubeGame.runGame = false;
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
