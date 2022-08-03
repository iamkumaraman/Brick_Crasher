/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.brick_crasher;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;
import javax.swing.Timer;


/**
 *
 * @author Aman Aashish
 */
public class GamePlay extends JPanel implements KeyListener, ActionListener{
    private boolean play = false;
    private int score = 0;
    private int totalBricks = 21;
    private Timer timer;
    private int delay = 8;
    private int playerX = 310;
    private int ballPosX = 120;
    private int ballPosY = 350;
    private int ballXDir = -1;
    private int ballYDir = -2;
    private MapGenerator map;
    
    public GamePlay(){
        map = new MapGenerator(3, 7);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer  = new Timer(delay, this);
        timer.start();
        
    }
    
    public void paint(Graphics g){
        g.setColor(Color.black);
        g.fillRect(1, 1, 692, 592);
        map.draw((Graphics2D) g);
        g.setColor(Color.yellow);
        g.fillRect(0,0, 3, 592);
        g.fillRect(0, 0, 692, 3);
        g.fillRect(691, 0, 3, 592);
        
        g.setColor(Color.white);
        g.setFont(new Font("serif", Font.BOLD, 25));
        g.drawString(""+score, 590, 30);
        
        g.setColor(Color.yellow);
        g.fillRect(playerX, 550, 100, 8);
        
        g.setColor(Color.GREEN);
        g.fillOval(ballPosX, ballPosY, 20, 20);
        
        if(ballPosY>570){
            play = false;
            ballXDir = 0;
            ballYDir = 0;
            g.setColor(Color.red);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("GAME OVER : "+score, 190, 300);
            
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("PRESS ENTER TO RESTART", 190, 340);
        }
        
        if(totalBricks == 0){
            play = false;
            ballYDir = -2;
            ballXDir = -1;
            g.setColor(Color.red);
            g.drawString("GAME OVER : "+score, 190, 300);
            
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("PRESS ENTER TO RESTART", 190, 340);
        }
        
        g.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();
        if(play){
            if(new Rectangle(ballPosX, ballPosY, 20, 20).intersects(new Rectangle(playerX, 550, 100, 8))){
                ballYDir = -ballYDir;
            }
            A:
            for(int i=0; i<map.map.length; i++){
                for(int j=0;j<map.map[0].length; j++){
                    if(map.map[i][j]>0){
                        int brickX = j* map.brickWidth +80;
                        int brickY = i* map.brickHeight + 50;
                        int brickWidth = map.brickWidth;
                        int brickHeight = map.brickHeight;
                        
                        Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
                        Rectangle ballrect= new Rectangle(ballPosX, ballPosY, 20, 20);
                        Rectangle brickrect = rect;
                        
                        if(ballrect.intersects(brickrect)){
                            map.setBrickValue(0, i, j);
                            totalBricks--;
                            score+=5;
                            if(ballPosX+19 <= brickrect.x || ballPosX+1 >= brickrect.x+brickWidth){
                                ballXDir = -ballXDir;
                            }else{
                                ballYDir = -ballYDir;
                            }
                            break A;
                        }
                    }
                }
            }
            
            ballPosX+=ballXDir;
            ballPosY+=ballYDir;
            
            if(ballPosX<0){
                ballXDir = -ballXDir;                
            }
            
            if(ballPosY<0){
                ballYDir = -ballYDir;
                
            }
            
            if(ballPosX > 670){
                ballXDir = -ballXDir;
            }
        }
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_RIGHT){
            if(playerX>=600) {
                playerX = 600;
            }else {
                moveRight();
            }
        }
        
        if(e.getKeyCode() == KeyEvent.VK_LEFT){
            if(playerX<10) {
                playerX = 10;
            }else {
                moveLeft();
            }
        }
        
        if(e.getKeyCode() == KeyEvent.VK_ENTER){
            if(!play) {
                ballPosX = 120;
                ballPosY = 350;
                ballXDir = -1;
                ballYDir = -2;
                score = 0;
                playerX = 310;
                totalBricks = 21;
                map =new MapGenerator(3, 7);
                repaint();
            }
        }
    }
    
    public void moveRight(){
        play = true;
        playerX += 20;
    }
    
    public void moveLeft(){
        play = true;
        playerX -= 20;
    }    

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
