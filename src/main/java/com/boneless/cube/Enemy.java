package com.boneless.cube;

import com.boneless.cube.util.Entity;

import java.util.Random;

import static com.boneless.cube.CubeGame.*;

public class Enemy extends Entity {
    public Enemy(int x, int y, int[][] boardData, int boardWidth, int boardHeight) {
        super(x, y, boardData, boardWidth, boardHeight);
        intiAI();
    }
    private void intiAI(){
        Thread loop = new Thread(() -> {
            while (true){
                try {
                    Thread.sleep(1000);
                    move(calcNextMove());
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        });
        loop.start();
    }
    private String calcNextMove(){
        Random rand = new Random();
        return switch (rand.nextInt(4)){
            case 0 -> "w";
            case 1 -> "a";
            case 2 -> "s";
            case 3 -> "d";
            default -> "";
        };
    }

    @Override
    protected int getEntityID() {
        return 4;
    }
    protected void collisionCheck(int newX, int newY){
        if(boardData[newX][newY] == objectList.get("player1") || boardData[newX][newY] == objectList.get("player2")){
            player1.kill();
            player2.kill();
        }
    }
    protected boolean wallCheck(int newX, int newY){
        return boardData[newX][newY] != objectList.get("wall") || boardData[newX][newY] != objectList.get("enemy");
    }
}
