import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GamePanel extends JPanel implements ActionListener, KeyListener {
    private static final int TILE_SIZE = 20;
    private static final int GRID_SIZE = 20;
    private int[] x, y;
    private int length;
    private char direction;
    private boolean running;
    private Timer timer;

    public GamePanel() {
        setPreferredSize(new Dimension(TILE_SIZE * GRID_SIZE, TILE_SIZE * GRID_SIZE));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);
        initializeGame();
    }

    private void initializeGame() {
        x = new int[GRID_SIZE * GRID_SIZE];
        y = new int[GRID_SIZE * GRID_SIZE];
        length = 3;
        direction = 'R';
        running = true;

        for (int i = 0; i < length; i++) {
            x[i] = TILE_SIZE * GRID_SIZE / 2 - i * TILE_SIZE;
            y[i] = TILE_SIZE * GRID_SIZE / 2;
        }

        timer = new Timer(100, this);
        timer.start();
    }

    private void move() {
        for (int i = length - 1; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }

        switch (direction) {
            case 'U':
                y[0] -= TILE_SIZE;
                break;
            case 'D':
                y[0] += TILE_SIZE;
                break;
            case 'L':
                x[0] -= TILE_SIZE;
                break;
            case 'R':
                x[0] += TILE_SIZE;
                break;
        }
    }

    private void checkCollision() {
        if (x[0] < 0 || x[0] >= TILE_SIZE * GRID_SIZE || y[0] < 0 || y[0] >= TILE_SIZE * GRID_SIZE) {
            running = false;
            timer.stop();
        }

        for (int i = 1; i < length; i++) {
            if (x[0] == x[i] && y[0] == y[i]) {
                running = false;
                timer.stop();
            }
        }
    }

    private void checkFood() {
        if (x[0] == foodX && y[0] == foodY) {
            // Snake eats the food
            length++;
            score++;
            placeFood();
        }
    }
    
    private void paintSnake(Graphics g) {
        g.setColor(Color.GREEN);
        for (int i = 0; i < length; i++) {
            g.fillRect(x[i], y[i], TILE_SIZE, TILE_SIZE);
        }
    }

    private void paintFood(Graphics g) {
        // Implement food drawing logic here
    }

    private void paintFood(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(foodX, foodY, TILE_SIZE, TILE_SIZE);
    }

    private void paintScore(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString("Score: " + score, 10, 20);
    }

    private void gameOver(Graphics g) {
        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Game Over", TILE_SIZE * GRID_SIZE / 2 - 70, TILE_SIZE * GRID_SIZE / 2);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (running) {
            paintSnake(g);
            paintFood(g);
        } else {
            gameOver(g);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            move();
            checkCollision();
            checkFood();
            repaint();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
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
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
