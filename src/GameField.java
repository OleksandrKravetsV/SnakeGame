import javax.swing.*;
import java.awt.*;

public class GameField extends JPanel {

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        paintGameField(g);

    }

    public void paintGameField(Graphics g) {
        setBackground(Color.black);
        Graphics2D g2d = (Graphics2D) g;

        //square field
        g.setColor(Color.white);
        g2d.setStroke(new BasicStroke(2)); //Line thickness
        int sideLength = 400; // Square side length
        int x = (getWidth() - sideLength) / 2; // X coordinate
        int y = (getHeight() - sideLength) / 2; // Y coordinate

        g.drawRect(x, y, sideLength, sideLength);
    }
}
