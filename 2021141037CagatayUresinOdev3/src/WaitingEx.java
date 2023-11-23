import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.util.Random;
import java.awt.geom.Ellipse2D;

class Surface extends JPanel implements ActionListener {
    private Timer timer;
    private final int INITIAL_DELAY = 200;
    private final int DELAY = 60;
    private final int BUBBLE_R = 20;
    private final int CENTER_R = 60;
    private final int BUBBLE_COUNT = 5;
    private static float[] trs = {0, 0.25f, 0.5f, 0.75f, 1f};


    public Surface() {
        initTimer();
    }

    private void initTimer() {
        timer = new Timer(DELAY, this);
        timer.setInitialDelay(INITIAL_DELAY);
        timer.start();
    }

    private void doDrawing(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();

        int width = getWidth();
        int height = getHeight();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);

        Ellipse2D.Double[] bubbles = new Ellipse2D.Double[5];

        g2d.setPaint(
                new GradientPaint(
                        (float) 0,
                        (float) 0,
                        randomColor(0.5f),
                        (float) (width),
                        (float) (height),
                        randomColor(0.5f)
                )
        );
        g2d.fillRect(0,0,width, height);

        for(int i=0; i<BUBBLE_COUNT; i++){
            double a = (double) width/2+CENTER_R*Math.cos(2*Math.PI*i/BUBBLE_COUNT);
            double b = (double) height/2+CENTER_R*Math.sin(2*Math.PI*i/BUBBLE_COUNT);
            bubbles[i] = createBubbleWithCenter((int) a, (int) b, BUBBLE_R);
            g2d.setPaint(
                    new GradientPaint(
                            (float) a,
                            (float) b,
                            randomColor(trs[i]),
                            (float) (a+BUBBLE_R),
                            (float) (b+BUBBLE_R),
                            randomColor(trs[i])
                    )
            );
            g2d.fill(bubbles[i]);
        }
        shift(trs);
    }
    public static Color randomColor(float trs) {
        Random r = new Random();
        return new Color(
                Math.abs(r.nextFloat())%1,
                Math.abs(r.nextFloat())%1,
                Math.abs(r.nextFloat())%1,
                trs
        );
    }
    private void shift(float[] arr){
        float first = arr[0];
        for(int i=0; i<arr.length-1; i++){
            arr[i] = arr[i+1];
        }
        arr[arr.length-1] = first;
    }
    private Ellipse2D.Double createBubbleWithCenter(int x, int y, int r){
        return new Ellipse2D.Double(x-r, y-r, 2*r, 2*r);
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

public class WaitingEx extends JFrame {
    public WaitingEx() {
        initUI();
    }
    private void initUI() {
        Surface surface = new Surface();
        add(surface);
        setTitle("Waiting");
        setSize(300, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(WaitingEx::new);
    }
}