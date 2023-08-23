import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class GameField extends JPanel implements ActionListener {
    private static final int WIDTH = 550;
    private static final int HEIGHT = 350;
    private static final int UNIT_SIZE = 20;
    private static final int DELAY = 50;
    private final Timer timer;
    private int appleX;
    private int appleY;

    public GameField() {
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

        g.setColor(Color.red);
        g.fillOval(appleX + 50, appleY + 50, UNIT_SIZE, UNIT_SIZE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
            newApple();
            repaint();
    }
}
