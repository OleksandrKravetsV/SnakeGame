import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GameField extends JPanel implements ActionListener {
    private static final int WIDTH = 600;
    private static final int HEIGHT = 400;
    private static final int UNIT_SIZE = 20;
    private static final int GAME_UNITS = (WIDTH * HEIGHT) / (UNIT_SIZE * UNIT_SIZE); // Total number of game cells on the field
    private static int DELAY = 200;
    private final Timer timer;

    // For storing coordinates of snake segments
    private final int[] snakeX = new int[GAME_UNITS];
    private final int[] snakeY = new int[GAME_UNITS];

    private int appleX;
    private int appleY;

    // Initial game parameters
    private boolean isRunning = false;
    private boolean isWaitingForStart = true;
    private int bodyParts = 6;
    private int applesEaten;
    private char direction = 'R'; // Direction of movement

    public GameField() {
        isRunning = true;
        setFocusable(true);
        addKeyListener(new MyKeyAdapter());
        newApple();
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

        if (isWaitingForStart) {
            // Display a game start message
            g.setColor(Color.green);
            g.setFont(new Font("Arial", Font.BOLD, 60));
            g.drawString("Press any key to start", (getWidth() - g.getFontMetrics().stringWidth("Press any key to start")) / 2, HEIGHT / 2);
        } else if (isRunning) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(Color.white);
            g2d.setStroke(new BasicStroke(4));
            g2d.drawRect(100, 100, WIDTH, HEIGHT);

            // Draw apple
            g.setColor(Color.red);
            g.fillOval(appleX + 100, appleY + 100, UNIT_SIZE, UNIT_SIZE);

            // Draw snake segments
            for (int i = 0; i < bodyParts; i++) {
                if (i == 0) {
                    g.setColor(new Color(30, 150, 50));
                } else {
                    g.setColor(Color.green);
                }
                g.fillRect(snakeX[i] + 100, snakeY[i] + 100, UNIT_SIZE, UNIT_SIZE);
            }

            // Draw score counter
            g.setFont(new Font("Arial", Font.BOLD, 40));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("score: " + applesEaten, (getWidth() - metrics.stringWidth("Score: " + applesEaten)) / 2, metrics.getAscent() + 25);
        } else {
            // The display of a game over message
            g.setColor(Color.green);
            g.setFont(new Font("Arial", Font.BOLD, 60));
            FontMetrics metrics;
            g.drawString("Game Over", (getWidth() - g.getFontMetrics().stringWidth("Game Over")) / 2, HEIGHT / 2);

            g.setFont(new Font("Arial", Font.BOLD, 40));
            metrics = getFontMetrics(g.getFont());
            g.drawString("score: " + applesEaten, (getWidth() - metrics.stringWidth("Score: " + applesEaten)) / 2, (getHeight() - g.getFontMetrics().getHeight()) / 2 + g.getFontMetrics().getAscent());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        checkCollisions();
        checkApple();
        repaint();
    }

    private class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            //start menu
            if (isWaitingForStart) {
                isWaitingForStart = false;
            }

            //pause
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                if (isRunning) {
                    timer.stop();
                } else {
                    timer.start();
                }
                isRunning = !isRunning;
            }

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

    // Increase DELAY after +applesEaten
    public void lvlUp(){
        if (applesEaten > 0) {
            DELAY = DELAY - 10;
            timer.setDelay(DELAY);
        }
    }

    public void checkApple() {
        // Check if the snake has eaten the apple
        if (snakeX[0] == appleX && snakeY[0] == appleY) {
            bodyParts++;
            applesEaten++;
            newApple();
            lvlUp();
        }
    }

    public void checkCollisions() {
        // Stop game on collision
        if (!isRunning) {
            timer.stop();
        }

        // Check for collision with itself
        for (int i = bodyParts; i > 0; i--) {
            if (snakeX[0] == snakeX[i] && snakeY[0] == snakeY[i]) {
                isRunning = false;
            }
        }

        // Wall collision check
        if (snakeX[0] < 0 || snakeX[0] >= WIDTH || snakeY[0] < 0 || snakeY[0] >= HEIGHT) {
            isRunning = false;
        }
    }
}
