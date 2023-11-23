import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;

class Ball {
    Image image;
    double x, y, speed, theta, x_speed, y_speed;

    Ball (String path){
        this.image = new ImageIcon(path).getImage();
    }
    public void calculateSpeeds(){
        x_speed = Math.cos(Math.toRadians(theta))*speed;
        y_speed = Math.sin(Math.toRadians(theta))*speed;
    }
    @Override
    public String toString() {
        return "Ball{" +
                " x=" + x +
                ", y=" + y +
                ", speed=" + speed +
                ", theta=" + theta +
                ", x_speed=" + x_speed +
                ", y_speed=" + y_speed +
                '}';
    }
}
class Surface extends JPanel implements ActionListener {
    private Timer timer;
    private final ArrayList<double[]> trail = new ArrayList<>();
    private final int DELAY = 10;
    private final int ONE_METER = 1;
    private static final double G = 9.8;
    private final int BALL_COORD_X = 50;
    private final int BALL_COORD_Y = 900;

    private static final Ball ball = new Ball("src/ball.png");
    public Surface() {
        ball.x = BALL_COORD_X;
        ball.y = BALL_COORD_Y;
        initTimer();
    }
    private void initTimer() {
        timer = new Timer(DELAY, this);
    }
    private void doDrawing(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.drawLine(
                0,
                BALL_COORD_Y + ball.image.getWidth(null),
                getWidth(),
                BALL_COORD_Y + ball.image.getHeight(null)
        );
        g2d.setColor(Color.RED);
        g2d.drawImage(ball.image, (int) ball.x, (int) ball.y,null);
        g2d.drawLine(
                (int) (ball.x + (double) ball.image.getWidth(null) / 2),
                (int) (ball.y + (double) ball.image.getHeight(null) / 2),
                (int) (ball.x + (double) ball.image.getWidth(null) / 2 + ball.x_speed),
                (int) (ball.y + (double) ball.image.getHeight(null) / 2)
        );
        g2d.drawLine(
                (int) (ball.x + (double) ball.image.getWidth(null) / 2),
                (int) (ball.y + (double) ball.image.getHeight(null) / 2),
                (int) (ball.x + (double) ball.image.getWidth(null) / 2),
                (int) (ball.y + (double) ball.image.getHeight(null) / 2 - ball.y_speed)
        );
        g2d.drawLine(
                (int) (ball.x + (double) ball.image.getWidth(null) / 2),
                (int) (ball.y + (double) ball.image.getHeight(null) / 2),
                (int) (ball.x + (double) ball.image.getWidth(null) / 2 + ball.x_speed),
                (int) (ball.y + (double) ball.image.getHeight(null) / 2 - ball.y_speed)
        );
        for (double[] point : trail) {
            g.fillOval((int) point[0], (int) point[1], 5, 5);
        }
    }
    void startMove(JTextField f_a, JTextField f_s) {
        VerticalProjectileMotion.result.setText("");
        ball.x = BALL_COORD_X;
        ball.y = BALL_COORD_Y;
        ball.theta = Double.parseDouble(f_a.getText());
        ball.speed = Double.parseDouble(f_s.getText());
        ball.calculateSpeeds();
        timer.start();
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        make_move();
        repaint();
    }
    private void make_move() {
        if (ball.y <= BALL_COORD_Y) {
            ball.x += ball.x_speed * ONE_METER * DELAY / 100;
            ball.y -= ball.y_speed * ONE_METER * DELAY / 100;
            ball.y_speed -= G * DELAY / 100;
            trail.add(new double[]{
                    ball.x + (double) ball.image.getWidth(null) / 2,
                    ball.y + (double) ball.image.getHeight(null) / 2
            });
        } else {
            VerticalProjectileMotion.result.setText(
                    "Flight Time: " + calculateFlightTime(ball.speed, ball.theta) + "s - Range: " +
                            calculateRange(ball.speed, ball.theta) + "m"
            );
            timer.stop();
        }
        System.out.println(ball);
    }
    private String calculateFlightTime(double v0, double theta){
        double vy = Math.sin(Math.toRadians(theta))*v0;
        return String.format("%.2f", 2*vy/G);
    }
    private String calculateRange(double v0, double theta){
        double vy = Math.sin(Math.toRadians(theta))*v0;
        double vx = Math.cos(Math.toRadians(theta))*v0;
        return String.format("%.2f", vx*2*vy/G);
    }
}
public class VerticalProjectileMotion extends JFrame {
    public static JLabel result = new JLabel();
    public VerticalProjectileMotion() {
        initUI();
    }
    private void initUI() {
        Surface surface = new Surface();
        setSize(1500, 1000);
        JTextField f_s = new JTextField("m/s");
        f_s.setBounds(5,5,50,30);
        JTextField f_a = new JTextField("ang");
        f_a.setBounds(60,5, 50,30);
        JButton btn = new JButton("START");
        btn.setBounds(115,5, 80, 30);
        result.setBounds(100, 100, 300, 30);
        add(f_s);
        add(f_a);
        add(btn);
        add(result);
        add(surface);
        btn.addActionListener(e -> surface.startMove(f_a, f_s));
        setTitle("Vertical Projectile Motion");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    public static void main(String[] args) {
        EventQueue.invokeLater(VerticalProjectileMotion::new);
    }
}