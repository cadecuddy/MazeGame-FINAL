/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.testserver;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author Cuddy_850631
 */
public class GameBoard extends JPanel implements ActionListener {
    
    private Timer timer;
    
    public GameBoard(){
        timer = new Timer(25, this);
        timer.start();
    }
   
    
    public void paint(Graphics g)
    {
        super.paint(g);
        g.setColor(Color.BLACK);
        g.setFont(new Font("TimesRoman", Font.ROMAN_BASELINE, 45));
        g.drawString("MAZE GAME", 235,50);
        try {
            Scanner viper = new Scanner(new File("test1.txt"));
            String current = viper.nextLine();
            int x = 2;
            int y = 60;
            for (int i = 0; i < current.length(); i++) {
                if (x == 13)
                {
                    x = 2;
                }
                if (i % 11 == 0 && i != 0) {
                    y += 50;
                    //System.out.println("IF1");
                }
                if (current.charAt(i) == 'W') {
                    g.setColor(Color.BLACK);
                    g.fillRect(x * 50, y, 50, 50);
                    //System.out.println("IF2");
                } else if (current.charAt(i) == 'O') {
                    g.setColor(Color.LIGHT_GRAY);
                    g.drawRect(x * 50, y, 50, 50);
                    //System.out.println("IF3");
                } else if (current.charAt(i) == 'P') {
                    g.setColor(Color.RED);
                    g.fillOval(x * 50, y, 50, 50);
                    //System.out.println("IF4");
                } else if (current.charAt(i) == 'E') {
                    g.setColor(Color.YELLOW);
                    g.fillRect(x * 50, y, 50, 50);
                   // System.out.println("IF5");
                }
                x++;

            }
            Scanner viper2 = new Scanner(new File("win.txt"));
            String win = viper2.next();
            if (win.equals("true"))
            {
                g.setColor(Color.RED);
                g.setFont(new Font("TimesRoman", Font.ROMAN_BASELINE, 45));
                g.drawString("YOU WIN!", 260, 200);
                Image img = ImageIO.read(new File("win.jpg"));
                g.drawImage(img, 235, 200, null);
            }

        } catch (FileNotFoundException ex) {
        } catch (IOException ex) {
            Logger.getLogger(GameBoard.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void winPaint(Graphics g)
    {
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }
}
