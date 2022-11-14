import javax.swing.*;
import java.awt.*;

public class SnakeGame {
    // Create frame
    JFrame frame;
    SnakeGame() {
        //Initialising frame
        frame = new JFrame("Snake Game");
        frame.setBounds(10,10, 905,700);
        ImageIcon icon = new ImageIcon("snake.png");
        frame.setIconImage(icon.getImage());
        GamePanel panel = new GamePanel();
        //set panel location and size
        panel.setBounds(10,10, 905,700);
        //background color of game area
        panel.setBackground(Color.GRAY);
        //add panel to frame
        frame.add(panel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    public static void main(String[] args) {
        SnakeGame snake = new SnakeGame();
    }
}
