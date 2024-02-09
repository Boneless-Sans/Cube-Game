package com.boneless.cube;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Dev implements KeyListener {
    public static void main(String[] args){
        new Dev();
    }
    public Dev(){
        initUI();
    }
    private void initUI(){
        JFrame frame = new JFrame();
        frame.addKeyListener(this);
        frame.setVisible(true);
        //frame.setFocusTraversalKeysEnabled(false);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        System.out.println("up");
    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println("Key Pressed: " + e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        //System.out.println("Key Pressed: " + e.getKeyCode());
    }
}
