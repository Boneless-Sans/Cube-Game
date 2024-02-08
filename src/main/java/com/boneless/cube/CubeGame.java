package com.boneless.cube;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;

public class CubeGame extends JFrame implements KeyListener {
    private static final int[][] board1 = {
            {1,0,0,0,0},
            {0,0,0,0,0},
            {0,0,3,0,0},
            {1,0,0,0,1},
            {2,0,0,0,0},
    };
    private static boolean runGame = true;
    private static int boardWidth;
    private static int boardHeight;
    private static int[][] boardData;
    private int width;
    private int height;
    private static JFrame frame = null;
    private JPanel[][] gameBoard;
    private JPanel menuPanel;
    private Player player;
    public CubeGame(){
        frame = this;
        initMenu();
        addKeyListener(this);

        setVisible(true);
    }
    private void parseBoardSize(int[][] map){
        boardWidth = map.length;
        boardHeight = map[0].length;
        gameBoard = new JPanel[boardWidth][boardHeight];
        boardData = map; //todo - add multiple maps, and map editor use jbuttons with x,y names then get name via !activate

        width = 100 * boardWidth;
        height = 100 * boardHeight;
    }
    private void initMenu(){
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        setSize(500,500);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setTitle("I really need a name");

        menuPanel = new JPanel();
        menuPanel.add(createMenuButton("Levels", 0));
        menuPanel.add(createMenuButton("Map Maker", 1));
        menuPanel.add(createMenuButton("Exit", 2));

    }
    private JButton createMenuButton(String text, int buttonCode){
        JButton button = new JButton(text);
        button.setFocusable(false);
        button.setFont(new Font("Arial", Font.PLAIN, 15));
        button.addActionListener(menuButtonListener(buttonCode));
        return button;
    }
    private ActionListener menuButtonListener(int buttonCode){
        return e -> {
            switch (buttonCode){
                case 1 -> {
                    menuPanel.removeAll();
                    menuPanel.add(createMenuButton("uh",5));
                }//unload current buttons and add buttons per pre-made level
                case 2 -> {
                    dispose();
                    new CubeGame();
                }//load map maker
                case 3 -> System.exit(0);
                default -> System.err.println("Invalid Button code. Button Code: " + buttonCode);
            }
        };
    }
    private void initUI(){
        JPanel masterBoard = new JPanel(new GridLayout(5,5,1,1));
        masterBoard.setBackground(Color.gray);

        String XY = "null";
        for(int i = 0; i < boardData.length; i++){
            for(int j = 0; j < boardData[i].length; j++){
                gameBoard[i][j] = new JPanel();
                masterBoard.add(gameBoard[i][j]);
                int num = boardData[i][j];
                if(num == 2){
                    XY = i + "," + j;
                }
            }
        }
        add(masterBoard);
        //spawn player
        String[] splitXY = XY.split(",");
        int newX = Integer.parseInt(splitXY[0]);
        int newY = Integer.parseInt(splitXY[1]);
        player = new Player(newX,newY);

        gameLoop();
    }
    private void gameLoop(){
        Thread checkLoop = new Thread(() -> {
            while(runGame) {
                updateBoard();
                try {
                    Thread.sleep(16); //~60 fps
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        checkLoop.start();
    }
    private void updateBoard() {
        for (int i = 0; i < boardData.length; i++) {
            for (int j = 0; j < boardData[i].length; j++) {
                switch(boardData[i][j]){
                    case 0 -> gameBoard[i][j].setBackground(Color.white);
                    case 1 -> gameBoard[i][j].setBackground(Color.black);
                    case 2 -> gameBoard[i][j].setBackground(Color.green);
                    case 3 -> gameBoard[i][j].setBackground(Color.red);
                    default -> gameBoard[i][j].setBackground(Color.cyan);
                }
            }
        }
    }
    @Override
    public void keyTyped(KeyEvent e) {
        char keyChar = e.getKeyChar();
        if (keyChar == 'w' || keyChar == 's' || keyChar == 'a' || keyChar == 'd') {
            player.move(String.valueOf(keyChar));
        } else if (keyChar == 'p') {
            System.out.println(Arrays.deepToString(boardData));
        } else if (keyChar == KeyEvent.VK_ESCAPE) {
            System.exit(0);
        }
    }
    @Override
    public void keyPressed(KeyEvent e) {}
    @Override
    public void keyReleased(KeyEvent e) {}
    private static class Player extends JPanel{
        protected int x;
        protected int y;
        protected Player(int x, int y){
            this.x = x;
            this.y = y;
        }
        public void move(String dir){
            int newX = x;
            int newY = y;

            switch (dir){
                case "w" -> newX--;
                case "s" -> newX++;
                case "a" -> newY--;
                case "d" -> newY++;
            }

            if(newX >= 0 && newX < boardWidth && newY >= 0 && newY < boardHeight){
                //check wall collision
                if(boardData[newX][newY] != 1) {
                    //check entity collision
                    if(boardData[newX][newY] == 3){
                        boardData[x][y] = 0;
                        try{
                            Thread.sleep(50);
                            runGame = false;
                        }catch (InterruptedException e){
                            e.printStackTrace();
                        }

                    }
                    boardData[x][y] = 0;
                    boardData[newX][newY] = 2;
                    x = newX;
                    y = newY;
                }
            }
        }
        public int getX(){
            return x;
        }
        public int getY(){
            return y;
        }

    }
    public static void main(String[] args){
        new CubeGame();
    }
}