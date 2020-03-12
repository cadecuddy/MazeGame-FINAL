/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.testserver;

import java.awt.Color;
import java.awt.Graphics;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

/**
 *
 * @author cuddy_864631
 */
public class MazeBuilder extends javax.swing.JFrame {
    private String[][][] maze;
    private int mazeSize;
    private int[][] lastMove;
    private int playerR;
    private int playerC;
    private int endR;
    private int endC;
    private Vote currentVote;
    private Vote pastVote;
    private boolean win;
    private boolean clicked;
    private String mazeString;
    
    
    public MazeBuilder() throws FileNotFoundException {
        mazeString = "";
        clicked = false;
        initComponents();
        currentVote = new Vote();
        maze = new String[9][9][1];
        mazeSize = 9;
        win = false;
        
        // Generates the walls of the maze and random interior 
        for (int r = 0; r < maze.length; r++){
            for (int c = 0; c < maze[r].length; c++){
                    maze[r][c][0] = "W";
            }
        }
        createPathPoint();
        mazeString = maze.toString();
        writeFile();
        writeWinFile();
    }
    
    public MazeBuilder(int n) throws FileNotFoundException {
        mazeString = "";
        clicked = false;
        win = false;
        maze = new String[n][n][1];
        mazeSize = n;
        
        // Generates the walls of the maze and random interior 
        for (int r = 0; r < maze.length; r++){
            for (int c = 0; c < maze[r].length; c++){
                    maze[r][c][0] = "W";
            }
        }
        createPathPoint();
        createEndSpace();
        setPlayerPoint();
        mazeString = maze.toString();
        writeFile();
        writeWinFile();
    }
    
    private void createEndSpace()
    {
        Random rand = new Random();
        int r = rand.nextInt(mazeSize);
        while (r % 2 == 0) {
            r = rand.nextInt(mazeSize);
        }
        int c = rand.nextInt(mazeSize);
        while (c % 2 == 0) {
            c = rand.nextInt(mazeSize);
        }
        
        maze[r][c][0] = "E";
    }
    
    private int randomize()
    {
        int rand = (int) (Math.random() * mazeSize);
        return rand;
    }
    
    private void createPathPoint()
    {
        //creates a starting point for the depth-first search to create
        //traversable paths within the maze
        Random rand = new Random();
        int r = rand.nextInt(mazeSize);
        while (r % 2 == 0) {
            r = rand.nextInt(mazeSize);
        }
        int c = rand.nextInt(mazeSize);
        while (c % 2 == 0) {
            c = rand.nextInt(mazeSize);
        }
        
        maze[r][c][0] = "E";
        endR = r;
        endC = c;
        
        //actually creating the path
        digMaze(r,c);
        playerR = r;
        playerC = c;
    }
    
    @Override
    public String toString() {
        String mazeOutput = "";
        
        for (int r = 0; r < maze.length; r++){
            for (int c = 0; c < maze[r].length; c++){
                mazeOutput += maze[r][c][0];
                mazeOutput += " ";
            }
            mazeOutput += "\n";
        }
        return mazeOutput;
    }
    
    public void writeFile() throws FileNotFoundException
    {
        try (PrintStream out = new PrintStream(new FileOutputStream("test1.txt"))) {
            String mazeOutput = "";
            for (int r = 0; r < maze.length; r++) {
                for (int c = 0; c < maze[r].length; c++) {
                    out.print(maze[r][c][0]);
                }
            }
        }

    }
    
    public void writeWinFile() throws FileNotFoundException
    {
        try (PrintStream out = new PrintStream(new FileOutputStream("win.txt"))) {
            String mazeOutput = Boolean.toString(win);
            out.print(mazeOutput);
        }

    }
    

    private void digMaze(int r, int c) {
        Integer[] randomDirections = generateRandomDirections();
        lastMove = new int[mazeSize*2][mazeSize*2];

        for (int i = 0; i < randomDirections.length; i++) {

            switch (randomDirections[i]) {
                case 1: // Up
                    if (r - 2 <= 0) {
                        continue;
                    }
                    if (maze[r - 2][c][0] != "O") {
                        maze[r - 2][c][0] = "O";
                        maze[r - 1][c][0] = "O";
                        digMaze(r - 2, c);
                    }
                    break;
                case 2: // Right
                    if (c + 2 >= mazeSize - 1) {
                        continue;
                    }
                    //Makes sure it isn't an outer wall
                    if (maze[r][c + 2][0] != "O") {
                        maze[r][c + 2][0] = "O";
                        maze[r][c + 1][0] = "O";
                        digMaze(r, c + 2);
                    }
                    break;
                case 3: // Down
                    if (r + 2 >= mazeSize - 1) {
                        continue;
                    }
                    //Makes sure it isn't a wall
                    if (maze[r + 2][c][0] != "O") {
                        maze[r + 2][c][0] = "O";
                        maze[r + 1][c][0] = "O";
                        digMaze(r + 2, c);
                    }
                    break;
                case 4: // Left
                    if (c - 2 <= 0) {
                        continue;
                    }
                    //Makes sure it isn't a wall
                    if (maze[r][c - 2][0] != "O") {
                        maze[r][c - 2][0] = "O";
                        maze[r][c - 1][0] = "O";
                        digMaze(r, c - 2);
                    }
                    break;
            }

        }
    }

    private Integer[] generateRandomDirections() {
        ArrayList<Integer> randoms = new ArrayList<Integer>();
        for (int i = 0; i < 4; i++) {
            randoms.add(i + 1);
        }
        Collections.shuffle(randoms);

        return randoms.toArray(new Integer[4]);
    }
    
    private void setPlayerPoint()
    {
        Random rand = new Random();
        playerR = rand.nextInt(mazeSize);
        playerC = rand.nextInt(mazeSize);

        
            while(playerR == 0 || playerR == mazeSize-1)
            {
                playerR = rand.nextInt(mazeSize);
            }

            playerC = rand.nextInt(mazeSize);
            while(playerC == 0 || playerC == mazeSize-1)
            {
               playerC = rand.nextInt(mazeSize);
            }
        
        maze[playerR ][playerC][0] = "P";
    }
    
    public void movePlayer(String vote) throws FileNotFoundException
    {
        //Logic for moving based off the vote that was casted
        switch (vote)
        {
            case "LEFT":
                if ("O".equals(maze[playerR][playerC - 1][0])) {
                    maze[playerR][playerC - 1][0] = "P";
                    maze[playerR][playerC][0] = "O";
                    playerC--;
                }
                else if ("W".equals(maze[playerR][playerC - 1][0])) {
                    System.out.println("BLOCKED BY WALL! LEFT");
                }
                else if ("E".equals(maze[playerR][playerC - 1][0]))
                {
                    maze[playerR][playerC - 1][0] = "P";
                    maze[playerR][playerC][0] = "O";
                    playerC--;
                    playerWin();
                }
            break;
            case "RIGHT":
                if ("O".equals(maze[playerR][playerC + 1][0])) {
                    maze[playerR][playerC + 1][0] = "P";
                    maze[playerR][playerC][0] = "O";
                    playerC++;
                }
                else if ("W".equals(maze[playerR][playerC + 1][0])) {
                    System.out.println("BLOCKED BY WALL! RIGHT");
                }
                else if ("E".equals(maze[playerR][playerC + 1][0]))
                {
                    maze[playerR][playerC + 1][0] = "P";
                    maze[playerR][playerC][0] = "O";
                    playerC++;
                    playerWin();
                }
            break;
            case "UP":
                if ("O".equals(maze[playerR -1][playerC][0])) {
                    maze[playerR -1][playerC][0] = "P";
                    maze[playerR][playerC][0] = "O";
                    playerR--;
                }
                else if ("W".equals(maze[playerR - 1][playerC][0])) {
                    System.out.println("BLOCKED BY WALL! UP");
                }
                else if ("E".equals(maze[playerR -1][playerC][0]))
                {
                    maze[playerR -1][playerC][0] = "P";
                    maze[playerR][playerC][0] = "O";
                    playerR--;
                    playerWin();
                }
            break;
            case "DOWN":
                if ("O".equals(maze[playerR +1][playerC][0])) {
                    maze[playerR +1][playerC][0] = "P";
                    maze[playerR][playerC][0] = "O";
                    playerR++;
                }
                else if ("W".equals(maze[playerR +1][playerC][0])) {
                    System.out.println("BLOCKED BY WALL! DOWN");
                }
                else if ("E".equals(maze[playerR +1][playerC][0]))
                {
                    maze[playerR +1][playerC][0] = "P";
                    maze[playerR][playerC][0] = "O";
                    playerR++;
                    playerWin();
                }
            break;
        }
        writeFile();
        writeWinFile();
        repaint();
    }

    private void playerWin() throws FileNotFoundException {
        writeWinFile();
        win = true;
    }
    
    public boolean getWin()
    {
        return win;
    }
    
    public boolean getClicked()
    {
        return clicked;
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1500, 800));
        setResizable(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 929, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 607, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
