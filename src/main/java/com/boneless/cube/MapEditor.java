package com.boneless.cube;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MapEditor extends JFrame {
    int[][] currentBoard = new int[5][5];
    JPanel[][] panelArray = new JPanel[5][5];
    int updateX = 5;
    int updateY = 5;
    JFrame sizeFrame;
    public MapEditor(){
        initUI();
        setVisible(true);
        updateLoop();
    }
    private JPanel createMenuBar(){
        JPanel panel = new JPanel(new FlowLayout());
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenu editMenu = new JMenu("Edit");

        JMenuItem saveFile = new JMenuItem("Save");
        JMenuItem loadFile = new JMenuItem("Load");
        JMenuItem newFile = new JMenuItem("New");
        JMenuItem exit = new JMenuItem("Exit");

        fileMenu.add(saveFile);
        fileMenu.add(loadFile);
        fileMenu.add(newFile);
        fileMenu.add(exit);

        menuBar.add(fileMenu);

        panel.add(menuBar);
        panel.add(editMenu);
        return panel;
    }
    private void initUI(){
        setSize(500,500);
        setLocationRelativeTo(null);
        addWindowFocusListener(adapter());
        setTitle("Map Maker");
        setIconImage(new ImageIcon("src/main/resources/assets/textures/icon_editor.png").getImage());

        JPanel masterPanel = new JPanel();

        JPanel boardPanel = new JPanel(new GridLayout(5,5,1,1));

        masterPanel.add(createMenuBar(), BorderLayout.NORTH);
        masterPanel.add(boardPanel, BorderLayout.CENTER);
        add(masterPanel);

        sizeFrame = new JFrame();
        sizeFrame.setLocation(getX() + getWidth() + 100, getY());
        sizeFrame.setSize(500,300);
        sizeFrame.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        sizeFrame.setVisible(true);
    }
    private WindowAdapter adapter(){
        return new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);

            }
        };
    }
    @SuppressWarnings({"BusyWait", "CallToPrintStackTrace"})
    private void updateLoop(){
            Thread updateThread = new Thread(() -> {
                while(true) {
                    try {
                        updateTick();
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        updateThread.start();
    }
    private void updateTick(){
        if(currentBoard.length > updateX || currentBoard.length < updateX || currentBoard[0].length > updateY || currentBoard[0].length < updateY) {
            System.out.println("changed");
        }
    }
    public static void main(String[] args){
        new MapEditor();
    }
}
