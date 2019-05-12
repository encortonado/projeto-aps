package components;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import components.Ground;
import javafx.scene.paint.Color;
import utility.Resource;

public class Player {
  private static int playerBaseY, playerTopY, playerStartX, playerEndX;
  private static int playerTop, playerBottom, topPoint;

  private static boolean topPointReached;
  private static int jumpFactor = 20;

  public static final int STAND_STILL = 1,
                    RUNNING = 2,
                    JUMPING = 3,
                    DIE = 4;
  private final int LEFT_FOOT = 1,
                    RIGHT_FOOT = 2,
                    NO_FOOT = 3;
  
  private static int state;

  private int foot;

  static BufferedImage image;
  BufferedImage leftFoot;
  BufferedImage rightFoot;
  BufferedImage dead;

  public Player() {
    image = new Resource().getResourceImage("../images/garStand.png");
    leftFoot = new Resource().getResourceImage("../images/left.png");
    rightFoot = new Resource().getResourceImage("../images/right.png");
    dead = new Resource().getResourceImage("../images/jump.png");

    playerBaseY = Ground.GROUND_Y + 5;
    playerTopY = Ground.GROUND_Y - image.getHeight() + 5;
    playerStartX = 100;
    playerEndX = playerStartX + image.getWidth();
    topPoint = playerTopY - 120;

    state = 1;
    foot = NO_FOOT;
  }

  public void create(Graphics g) {
    playerBottom = playerTop + image.getHeight();

    // g.drawRect(getPlayer().x, getPlayer().y, getPlayer().width, getPlayer().height);

    switch(state) {

      case STAND_STILL:
        System.out.println("stand");
        g.drawImage(image, playerStartX, playerTopY, null);
        break;

      case RUNNING:
        if(foot == NO_FOOT) {
          foot = LEFT_FOOT;
          g.drawImage(leftFoot, playerStartX, playerTopY, null);
        } else if(foot == LEFT_FOOT) {
          foot = RIGHT_FOOT;
          g.drawImage(rightFoot, playerStartX, playerTopY, null);
        } else {
          foot = LEFT_FOOT;
          g.drawImage(leftFoot, playerStartX, playerTopY, null);
        }
        break;

      case JUMPING:
        if(playerTop > topPoint && !topPointReached) {
          g.drawImage(image, playerStartX, playerTop -= jumpFactor, null);
          break;
        } 
        if(playerTop >= topPoint && !topPointReached) {
          topPointReached = true;
          g.drawImage(image, playerStartX, playerTop += jumpFactor, null);
          break;
        }         
        if(playerTop > topPoint && topPointReached) {      
          if(playerTopY == playerTop && topPointReached) {
            state = RUNNING;
            topPointReached = false;
            break;
          }    
          g.drawImage(image, playerStartX, playerTop += jumpFactor, null);          
          break;
        }
      case DIE: 
        g.drawImage(dead, playerStartX, playerTop, null);    
        break;     
    }
  }

  public void die() {
    state = DIE;
  }

  public static Rectangle getPlayer() {
    Rectangle player = new Rectangle();
    player.x = playerStartX;

    if(state == JUMPING && !topPointReached) player.y = playerTop - jumpFactor;
    else if(state == JUMPING && topPointReached) player.y = playerTop + jumpFactor;
    else if(state != JUMPING) player.y = playerTop;

    player.width = image.getWidth();
    player.height = image.getHeight();

    return player;
  }

  public void startRunning() {
    playerTop = playerTopY;
    state = RUNNING;
  }

  public void jump() {
    playerTop = playerTopY;
    topPointReached = false;
    state = JUMPING;
  }

  private class DinoImages {

  }

}