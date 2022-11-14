import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class GamePanel extends JPanel implements KeyListener, ActionListener {

    ImageIcon snaketitle = new ImageIcon(getClass().getResource("snakeTitle.jpg"));
    ImageIcon downmouth = new ImageIcon(getClass().getResource("downmouth.png"));
    ImageIcon upmouth = new ImageIcon(getClass().getResource("upmouth.png"));
    ImageIcon leftmouth = new ImageIcon(getClass().getResource("leftmouth.png"));
    ImageIcon rightmouth = new ImageIcon(getClass().getResource("rightmouth.png"));
    ImageIcon enemy = new ImageIcon(getClass().getResource("enemy.png"));
    ImageIcon snakeimage = new ImageIcon(getClass().getResource("snakeimage.png"));

    // x position array of enemy
    int[] xpos = {25, 50, 75, 100, 125, 150, 175, 200, 225, 250, 275, 300, 325, 350, 375, 400, 425, 450, 475, 500, 525, 550, 575, 600, 625, 650, 675, 700, 725, 750, 775, 800, 825, 850};

    // y position array of enemy
    int[] ypos = {75, 100, 125, 150, 175, 200, 225, 250, 275, 300, 325, 350, 375, 400, 425, 450, 475, 500, 525, 550, 575, 600, 625};

    // randomly positioning of enemy
    Random random = new Random();

    //default position at start of game
    int enemyx = 150, enemyy = 200;
    // snake postion in x and y directions
    int[] snakexlength = new int[750];
    int[] snakeylength = new int[750];
    // snake's facing status
    boolean left = false;
    // initial facing side is right
    boolean right = true;
    boolean up = false;
    boolean down = false;
    boolean gameover = false;
    // Move count
    int move = 0;
    // starting snake's length
    int lengthOfSnake = 3;
    int score = 0;
    //timer will start with action listener
    Timer time;
    int delay = 150;

    // Constructor to move the snake
    GamePanel() {
        // Take input via keyboard
        addKeyListener(this);
        setFocusable(true);
        //delay in pressing key
        time = new Timer(delay, this);
        time.start();
    }

    public void newEnemy() {
        //create new enemy at random position
        enemyx = xpos[random.nextInt(34)];
        enemyy = ypos[random.nextInt(23)];
        // to avoid the enemy to settle on snake
        for (int i = lengthOfSnake - 1; i >= 0; i--) {
            if (snakexlength[i] == enemyx && snakeylength[i] == enemyy) {
                newEnemy();
            }
        }
    }

    public void collidewithenemy() {
        // snake collides with enemy destroy it
        if (snakexlength[0] == enemyx && snakeylength[0] == enemyy) {
            // create new enemy
            newEnemy();
            lengthOfSnake++;
            score++;
            snakexlength[lengthOfSnake - 1] = snakexlength[lengthOfSnake - 2];
            snakeylength[lengthOfSnake - 1] = snakeylength[lengthOfSnake - 2];
        }
    }

    public void collidewithbody() {
        // snake collides with itself then game over
        for (int i = lengthOfSnake - 1; i > 0; i--) {
            if (snakexlength[i] == snakexlength[0] && snakeylength[i] == snakeylength[0]) {
                time.stop();
                gameover = true;
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.white);
        // Rectangle section for heading/title part
        g.drawRect(24, 10, 851, 55);
        // Rectangle section for gaming part
        g.drawRect(24, 74, 851, 576);
        // paint this icon in panel
        snaketitle.paintIcon(this, g, 25, 11);
        //set color to graphic (playing area)
        g.setColor(Color.black);
        g.fillRect(25, 75, 851, 576);
        // In the starting of the game
        if (move == 0) {
            //head
            snakexlength[0] = 100;
            //other body part length
            snakexlength[1] = 75;
            snakexlength[2] = 50;
            // Every body part of snake at 100 px from top border
            snakeylength[0] = 100;
            snakeylength[1] = 100;
            snakeylength[2] = 100;
            move++;
        }
        if (left) {
            //left facing mouth of snake
            leftmouth.paintIcon(this, g, snakexlength[0], snakeylength[0]);
        }
        if (right) {
            //right facing mouth of snake
            rightmouth.paintIcon(this, g, snakexlength[0], snakeylength[0]);
        }
        if (up) {
            //up facing mouth of snake
            upmouth.paintIcon(this, g, snakexlength[0], snakeylength[0]);
        }
        if (down) {
            //down facing mouth of snake
            downmouth.paintIcon(this, g, snakexlength[0], snakeylength[0]);
        }

        // paint rest body of snake
        for (int i = 1; i < lengthOfSnake; i++) {
            snakeimage.paintIcon(this, g, snakexlength[i], snakeylength[i]);
        }
        enemy.paintIcon(this, g, enemyx, enemyy);

        // display of game over and restart
        if (gameover) {
            g.setColor(Color.white);
            g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));
            g.drawString("Game Over", 375, 350);
            g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 10));
            g.drawString("Press the space key to restart", 385, 410);
        }

        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.PLAIN, 15));
        g.drawString("Score : " + score, 750, 30);
        g.drawString("Length : " + lengthOfSnake, 750, 50);
        g.dispose();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_SPACE && gameover) {
            restart();
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT && (!right)) {
            // update snake's facing
            left = true;
            right = false;
            up = false;
            down = false;
            move++;
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT && (!left)) {
            left = false;
            right = true;
            up = false;
            down = false;
            move++;
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN && (!up)) {
            left = false;
            right = false;
            up = false;
            down = true;
            move++;
        }
        if (e.getKeyCode() == KeyEvent.VK_UP && (!down)) {
            left = false;
            right = false;
            up = true;
            down = false;
            move++;
        }
    }
    private void restart() {
        // reset all values to default
        gameover = false;
        move = 0;
        lengthOfSnake = 3;
        score = 0;
        left = false;
        right = true;
        up = false;
        down = false;
        time.start();
        repaint();
    }


    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // print snake after every key pressed
        // i > 0 because 0th position is of head of snake
        for (int i = lengthOfSnake - 1; i > 0; i--) {
            //copy previous position to move ahead
            snakexlength[i] = snakexlength[i - 1];
            snakeylength[i] = snakeylength[i - 1];
        }
        if (left) { // back direction
            snakexlength[0] = snakexlength[0] - 25;
        }
        if (right) {
            snakexlength[0] = snakexlength[0] + 25;
        }
        if (down) {
            snakeylength[0] = snakeylength[0] + 25;
        }
        if (up) {
            snakeylength[0] = snakeylength[0] - 25;
        }
        // If snake goes out of frame it will come from opposite side
        if (snakexlength[0] > 850) {
            snakexlength[0] = 25;
        }
        if (snakexlength[0] < 25) {
            snakexlength[0] = 850;
        }
        if (snakeylength[0] > 625) {
            snakeylength[0] = 75;
        }
        if (snakeylength[0] < 75) {
            snakeylength[0] = 625;
        }
        // call the functions
        collidewithenemy();
        collidewithbody();
        repaint();
    }
}
