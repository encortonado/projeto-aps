import java.io.*;
import javax.imageio.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

import components.Ground;
import components.Obstacles;
import components.Player;

class GamePanel extends JPanel implements KeyListener, Runnable {
  
  public static int WIDTH;
  public static int HEIGHT;
  private Thread animator;
  
  private boolean running = false;
  private boolean gameOver = false;
  
  Ground ground;
  Player player;
  Obstacles obstacles;

  private int score;
  
  public GamePanel() {
    WIDTH = UserInterface.WIDTH;
    HEIGHT = UserInterface.HEIGHT;
    
    ground = new Ground(HEIGHT);
    player = new Player();
    obstacles = new Obstacles((int)(WIDTH * 1.5));

    score = 0;
    
    setSize(WIDTH, HEIGHT);
    setVisible(true);
  }
  
  public void paint(Graphics g) {
    super.paint(g);
    g.setFont(new Font("Century Gothic", Font.PLAIN, 30));
    g.drawString(Integer.toString(score), getWidth()/2 - 5, 100);
    ground.create(g);
    player.create(g);
    obstacles.create(g);
  }
  
  public void run() {
    running = true;

    while(running) {
      updateGame();
      repaint();      
      try {
        Thread.sleep(50);
      } catch(InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
  
  public void updateGame() {
    score += 1;

    ground.update();
    // dino.update();
    obstacles.update();

    if(obstacles.hasCollided()) {
      player.die();
      repaint();
      running = false;
      gameOver = true;
      System.out.println("colisão");
    }
    // game complete condition
  }

  public void reset() {
    score = 0;
    System.out.println("reinicio");
    obstacles.resume();
    gameOver = false;
  }
  
  public void keyTyped(KeyEvent e) {
    // System.out.println(e);
    if(e.getKeyChar() == ' ') {    
      if(gameOver) reset();
      if (animator == null || !running) {
        System.out.println("começou");        
        animator = new Thread(this);
        animator.start();     
        player.startRunning();   
      } else {
        player.jump();
      }
    }
  }
  
  public void keyPressed(KeyEvent e) {
    // System.out.println("keyPressed: "+e);
  }
  
  public void keyReleased(KeyEvent e) {
    // System.out.println("keyReleased: "+e);
  }
}