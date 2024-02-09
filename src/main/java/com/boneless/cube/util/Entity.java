package com.boneless.cube.util;

import javax.swing.*;

import static com.boneless.cube.CubeGame.objectList;

public class Entity extends JPanel {
    protected int x;
    protected int y;
    protected final int boardWidth;
    protected final int boardHeight;
    protected final int[][] boardData;
    protected Entity(int x, int y, int[][] boardData, int boardWidth, int boardHeight){
        this.x = x;
        this.y = y;
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        this.boardData = boardData;
    }
    public void move(String dir){
        int newX = x;
        int newY = y;

        switch (dir) {
            case "w" -> newX--;
            case "s" -> newX++;
            case "a" -> newY--;
            case "d" -> newY++;
        }

        if(newX >= 0 && newX < boardWidth && newY >= 0 && newY < boardHeight){
            //check wall collision
            if(wallCheck(newX,newY)) {
                //check entity collision
                collisionCheck(newX,newY);
                boardData[x][y] = 0;
                boardData[newX][newY] = getEntityID();
                x = newX;
                y = newY;
            }
        }
    }
    protected void collisionCheck(int newX, int newY){
        if(boardData[newX][newY] == objectList.get("void")){
            kill();
        }
    }
    protected boolean wallCheck(int newX, int newY){
        return boardData[newX][newY] != objectList.get("wall");
    }
    public void kill(){
        boardData[x][y] = 0;
    }
    protected int getEntityID(){
        return 0;
    }
}
