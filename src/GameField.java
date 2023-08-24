import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GameField extends JPanel implements ActionListener {
    private static final int WIDTH = 550;
    private static final int HEIGHT = 350;
    private static final int UNIT_SIZE = 20;
    private static final int GAME_UNITS = (WIDTH * HEIGHT) / (UNIT_SIZE * UNIT_SIZE); // Total number of game cells on the field
    private static final int DELAY = 50;
    private final Timer timer;

    // For storing coordinates of snake segments
    private final int[] snakeX = new int[GAME_UNITS];
    private final int[] snakeY = new int[GAME_UNITS];

    private int appleX;
    private int appleY;

    // Initial game parameters
    private final int bodyParts = 6;
    private char direction = 'R'; // Direction of movement

    public GameField() {
        setFocusable(true);
        addKeyListener(new MyKeyAdapter());
        timer = new Timer(DELAY, this);
        timer.start();
    }

    public void newApple() {
        appleX = new Random().nextInt(WIDTH / UNIT_SIZE) * UNIT_SIZE;
        appleY = new Random().nextInt(HEIGHT / UNIT_SIZE) * UNIT_SIZE;
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        paintGameField(g);
    }

    public void paintGameField(Graphics g) {
        setBackground(Color.black);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.white);
        g2d.setStroke(new BasicStroke(4));
        g2d.drawRect(50, 50, WIDTH, HEIGHT);

        // Draw apple
        g.setColor(Color.red);
        g.fillOval(appleX + 50, appleY + 50, UNIT_SIZE, UNIT_SIZE);

        // Draw snake segments
        for (int i = 0; i < bodyParts; i++) {
            if (i == 0) {
                g.setColor(new Color(30, 150, 50));
            } else {
                g.setColor(Color.green);
            }
            g.fillRect(snakeX[i] + 100, snakeY[i] + 100, UNIT_SIZE, UNIT_SIZE);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        newApple();
        move();
        repaint();
    }

    private class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {

            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if (direction != 'R') {
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (direction != 'L') {
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if (direction != 'D') {
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (direction != 'U') {
                        direction = 'D';
                    }
                    break;
            }
        }
    }

    public void move() {
        // Updating the position of segments
        for (int i = bodyParts; i > 0; i--) {
            snakeX[i] = snakeX[i - 1];
            snakeY[i] = snakeY[i - 1];
        }

        // Changing the position of the snake's head depending on the direction
        switch (direction) {
            case 'U':
                snakeY[0] -= UNIT_SIZE;
                break;
            case 'D':
                snakeY[0] += UNIT_SIZE;
                break;
            case 'L':
                snakeX[0] -= UNIT_SIZE;
                break;
            case 'R':
                snakeX[0] += UNIT_SIZE;
                break;
        }
    }
}
