package com.boneless.cube;

public class Board3DTest {
    public static void main(String[] args){
        int[][][] board3dTest = {
                {//[0][x][y] //plan: have map objects on layer 0, entity's on layer 1
                    {3,0,0,0,3},
                    {0,0,0,0,0},
                    {0,0,5,0,0},
                    {0,0,0,0,0},
                    {3,0,0,0,3},
                },
                {//[1][x][y]
                    {0,0,0,0,0},
                    {0,0,0,0,0},
                    {0,1,0,2,0},
                    {0,0,0,0,0},
                    {0,0,0,0,0},

                },
        };
        System.out.println(board3dTest[0][2][2]); //prints 5
    }
}
