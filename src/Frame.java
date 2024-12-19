import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.*;
import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.util.Random;
import java.util.*;

public class Frame extends JPanel implements ActionListener, MouseListener, KeyListener {

    static int width = 1080;
    static int height = 720;
    private int mouseLeftPos;
    private int mouseTopPos;
    private boolean debug = false;

    private int foodResourceCounter = 0;
    private int woodResourceCounter = 0;
    private int stoneResourceCounter = 0;
    private int steelResourceCounter = 0;

    Font myFont = new Font("Serif", Font.BOLD, 20);

    //Create an instance of the FoodResource class
    FoodResource food = new FoodResource(width/8, height/8);

    WoodResource wood = new WoodResource(width/2+10, height/8);

    StoneResource stone = new StoneResource(width/4+75, height/8);

    SteelResource steel = new SteelResource(775, height/8);




    public void paint(Graphics g) {
        super.paintComponent(g);

        food.paint(g);
        wood.paint(g);
        stone.paint(g);
        steel.paint(g);

        if(debug) {
            food.drawHitBox(g);                                                           //Draws the hitbox for debugging purposes
            wood.drawHitBox(g);                                                           //Draws the hitbox for debugging purposes
            stone.drawHitBox(g);                                                          //Draws the hitbox for debugging purposes
            steel.drawHitBox(g);                                                          //Draws the hitbox for debugging purposes
            g.drawRect(mouseLeftPos - 10, mouseTopPos - 30, 2, 2);                //Draws a small rectangle at the mouse position for debugging purposes
            g.drawString("DEBUG MODE", 900, 20);                                          //Displays "DEBUG MODE" in the top right corner of the screen for debugging


        }


        g.setFont(myFont);
        g.setColor(Color.BLACK);
        g.drawString("Food Resource Counter: " + foodResourceCounter, 10, 20);
        g.drawString("Wood Resource Counter: " + woodResourceCounter, 10, 40);
        g.drawString("Stone Resource Counter: " + stoneResourceCounter, 10, 60);
        g.drawString("Steel Resource Counter: " + steelResourceCounter, 10, 80);


    }





    public static void main(String[] args) {
        Frame f = new Frame();

    }

    public Frame() {

        //Create the frame of the window

        JFrame f = new JFrame("j nation");
        f.setSize(new Dimension(width, height));
        f.setBackground(Color.white);
        f.add(this);
        f.setResizable(false);
        f.addMouseListener(this);
        f.addKeyListener(this);


        Timer t = new Timer(16, this);
        t.start();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);


    }



    @Override
    public void mouseClicked(MouseEvent arg0) {

    }

    @Override
    public void mouseEntered(MouseEvent arg0) {

    }

    @Override
    public void mouseExited(MouseEvent arg0) {

    }

    @Override
    public void mousePressed(MouseEvent arg0) {
        mouseLeftPos = arg0.getX();
        mouseTopPos = arg0.getY();


        //This is the code that will increment the foodResourceCounter variable every time the user left-clicks on the food resource.
        if(arg0.getButton() == 1 && food.isCursorInside(arg0.getX()-10, arg0.getY()-30)){
            foodResourceCounter++;
            System.out.println(foodResourceCounter);

        }

        if(arg0.getButton() == 1 && debug){

            System.out.println("Mouse X: " + arg0.getX() + " Mouse Y: " + arg0.getY());

        }


        //This is the code that will increment the woodResourceCounter variable every time the user left-clicks on the wood resource.
        if(arg0.getButton() == 1 && wood.isCursorInside(arg0.getX()-10, arg0.getY()-30)){
            woodResourceCounter++;
            System.out.println(woodResourceCounter);

        }


        //This is the code that will increment the woodResourceCounter variable every time the user left-clicks on the wood resource.
        if(arg0.getButton() == 1 && stone.isCursorInside(arg0.getX()-10, arg0.getY()-30)){
            stoneResourceCounter++;
            System.out.println(stoneResourceCounter);

        }

        //This is the code that will increment the woodResourceCounter variable every time the user left-clicks on the wood resource.
        if(arg0.getButton() == 1 && steel.isCursorInside(arg0.getX()-10, arg0.getY()-30)){
            steelResourceCounter++;
            System.out.println(steelResourceCounter);

        }

    }

    @Override
    public void mouseReleased(MouseEvent arg0) {

    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent arg0) {
        if(arg0.getKeyCode() == 112 && !debug){
        debug = true;
        }else if(arg0.getKeyCode() == 112){
        debug = false;
        }

    }

    @Override
    public void keyReleased(KeyEvent arg0) {

    }

    @Override
    public void keyTyped(KeyEvent arg0) {

    }

}