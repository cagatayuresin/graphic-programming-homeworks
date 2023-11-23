import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

class Surface2 extends JPanel implements ActionListener {
    private Star2D[] stars;
    private double ssize[];
    private float sstroke[];
    private double maxSize = 0;
    private final int NUMBER_OF_STARS = 25;
    private final int DELAY = 30;
    private final int INITIAL_DELAY = 150;
    private Timer timer;
    public Surface2() {
        initSurface();
        initStars();
        initTimer();
    }
    private void initSurface() {
        setBackground(Color.black);
        stars = new Star2D[NUMBER_OF_STARS];
        ssize = new double[stars.length];
        sstroke = new float[stars.length];
    }
    private void initStars() {
        int w = 350;
        int h = 250;
        maxSize = w / 10;
        for (int i = 0; i < stars.length; i++) {
            stars[i] = new Star2D(0,0, 10);
            posRandStars(i, maxSize * Math.random(), w, h);
        }
    }
    private void initTimer() {
        timer = new Timer(DELAY, this);
        timer.setInitialDelay(INITIAL_DELAY);
        timer.start();
    }
    private void posRandStars(int i, double size, int w, int h) {
        ssize[i] = size;
        sstroke[i] = 1.0f;
        double x = Math.random() * (w - (maxSize / 2));
        double y = Math.random() * (h - (maxSize / 2));
        stars[i].setFrame(x, y, size, size);
    }
    private void doStep(int w, int h) {
        for (int i = 0; i < stars.length; i++) {
            sstroke[i] += 0.025f;
            ssize[i]++;
            if (ssize[i] > maxSize)
                posRandStars(i, 1, w, h);
            else
                stars[i].setFrame(stars[i].getX(), stars[i].getY(), ssize[i], ssize[i]);
        }
    }
    public static Color randomColor() {
        Random r = new Random();
        return new Color(
                Math.abs(r.nextFloat())%1,
                Math.abs(r.nextFloat())%1,
                Math.abs(r.nextFloat())%1
        );
    }
    private void drawStars(Graphics2D g2d) {
        for (int i = 0; i < stars.length; i++) {
            g2d.setColor(randomColor());
            g2d.setStroke(new BasicStroke(sstroke[i]));
            g2d.fill(stars[i]);
        }
    }
    private void doDrawing(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        RenderingHints rh = new RenderingHints(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );
        rh.put(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY
        );
        g2d.setRenderingHints(rh);
        Dimension size = getSize();
        doStep(size.width, size.height);
        drawStars(g2d);
        g2d.dispose();
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }
}
public class StarsEx extends JFrame {
    public StarsEx() {
        initUI();
    }
    private void initUI() {
        add(new Surface2());
        setTitle("Bubbles");
        setSize(350, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    public static void main(String[] args) {
        EventQueue.invokeLater(StarsEx::new);
    }
}
