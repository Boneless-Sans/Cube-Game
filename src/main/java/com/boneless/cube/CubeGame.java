package com.boneless.cube;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;
import java.util.HashMap;

public class CubeGame extends JFrame implements KeyListener {
    private static final int[][] board1 = {
            {3,3,3,3,3,3,3,3,3,3},
            {3,0,0,0,0,0,0,0,0,3},
            {3,0,3,0,0,0,0,0,0,3},
            {3,0,0,1,0,2,0,0,0,3},
            {3,0,0,0,0,0,0,0,0,3},
            {3,0,0,0,0,0,0,0,0,3},
            {3,0,0,0,0,0,0,0,0,3},
            {3,0,0,0,0,4,0,0,0,3},
            {3,0,0,0,0,0,0,0,0,3},
            {3,3,3,3,3,3,3,3,3,3}
    };
    static boolean runGame = true;
    private boolean hasPlayer1;
    private boolean hasPlayer2;
    private static int boardWidth;
    private static int boardHeight;
    private static int[][] boardData;
    private JPanel[][] gameBoard;
    private JPanel menuPanel;
    public static Player player1;
    public static Player player2;
    public final static HashMap<String, Integer> objectList = new HashMap<>();
    public CubeGame(){
        objectList.put("air", 0);
        objectList.put("player1", 1);
        objectList.put("player2", 2);
        objectList.put("wall", 3);
        objectList.put("enemy", 4);
        initMenu();
        addKeyListener(this);

        setVisible(true);
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
        setIconImage(new ImageIcon("src/main/resources/assets/textures/icon.png").getImage());

        menuPanel = new JPanel();
        menuPanel.add(createMenuButton("Levels", 0));
        menuPanel.add(createMenuButton("Map Maker", 1));
        menuPanel.add(createMenuButton("Exit", 2));
        initGame(board1);
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
    private void initGame(int[][] map){
        parseBoardSize(map);
        int width = boardWidth > 15 ? 100 * boardWidth : 500;
        int height = boardHeight > 15 ? 100 * boardHeight : 500;

        setSize(width,height);
        JPanel masterBoard = new JPanel(new GridLayout(boardWidth,boardHeight,1,1));
        masterBoard.setBackground(Color.gray);

        String XY = "something";
        String XY2 = "something";
        for(int i = 0; i < boardData.length; i++){
            for(int j = 0; j < boardData[i].length; j++){
                String EXY;
                String WXY;
                gameBoard[i][j] = new JPanel();
                masterBoard.add(gameBoard[i][j]);
                int num = boardData[i][j];
                if(num == 1){
                    System.out.println(XY);
                    XY = i + "," + j;
                    System.out.println(XY);
                }
                if(num == 2){
                    System.out.println(XY2);
                    XY2 = i + "," + j;
                    System.out.println(XY2);
                }if(num == 4){
                    EXY = i + "," + j;
                    if(!EXY.equals("something")) {
                        String[] splitEXY = EXY.split(",");
                        int newEX = Integer.parseInt(splitEXY[0]);
                        int newEY = Integer.parseInt(splitEXY[1]);
                        System.out.println(EXY);
                        new Enemy(newEX, newEY, boardData, boardWidth, boardHeight);
                    }
                }
            }
        }
        add(masterBoard);
        //spawn player 1
        if(!XY.equals("something")) {
            hasPlayer1 = true;
            String[] splitXY = XY.split(",");
            int newX = Integer.parseInt(splitXY[0]);
            int newY = Integer.parseInt(splitXY[1]);
            player1 = new Player(1, newX, newY, boardData, boardWidth, boardHeight);
        }
        //spawn player 2
        if(!XY2.equals("something")) {
            hasPlayer2 = true;
            String[] splitXY2 = XY2.split(",");
            int newX2 = Integer.parseInt(splitXY2[0]);
            int newY2 = Integer.parseInt(splitXY2[1]);
            player2 = new Player(2, newX2, newY2, boardData, boardWidth, boardHeight);
        }

        gameLoop();
    }
    private void parseBoardSize(int[][] map){
        boardWidth = map.length;
        boardHeight = map[0].length;
        gameBoard = new JPanel[boardWidth][boardHeight];
        boardData = map;
    }
    @SuppressWarnings({"BusyWait", "CallToPrintStackTrace"})

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
                    case 0 -> gameBoard[i][j].setBackground(Color.white); //nothing / background
                    case 1 -> gameBoard[i][j].setBackground(new Color(60,60,255)); //player 1
                    case 2 -> gameBoard[i][j].setBackground(new Color(60,200,60)); //player 2
                    case 3 -> gameBoard[i][j].setBackground(Color.black); //wall
                    case 4 -> gameBoard[i][j].setBackground(Color.red); //enemy
                    default -> gameBoard[i][j].setBackground(Color.cyan); //error
                }
            }
        }
    }
    @Override
    public void keyTyped(KeyEvent e) {
        char keyChar = e.getKeyChar();
        if(keyChar == KeyEvent.VK_UP){
            System.out.println("UP");
        }
        if (keyChar == 'w' || keyChar == 's' || keyChar == 'a' || keyChar == 'd') {
            if(hasPlayer1){
                player1.move(String.valueOf(keyChar));
            }
        } else if (keyChar == 'p') {
            System.out.println(Arrays.deepToString(boardData));
        } else if (keyChar == KeyEvent.VK_ESCAPE) {
            System.exit(0);
        }
    }
    @Override
    public void keyPressed(KeyEvent e) {
        int key = Integer.parseInt(String.valueOf(e.getKeyCode()));
        if(hasPlayer2) {
            if (key == 38) {
                player2.move("w");
            } else if (key == 40) {
                player2.move("s");
            } else if (key == 37) {
                player2.move("a");
            } else if (key == 39) {
                player2.move("d");
            }
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {}
    public static void main(String[] args){
        new CubeGame();
    }
}