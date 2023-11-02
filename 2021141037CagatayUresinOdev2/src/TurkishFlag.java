import javax.swing.*;
import java.awt.*;
import java.awt.geom.Path2D;

class Canvas extends JPanel {
    @Override
    public void paintComponent(Graphics graphics){
        super.paintComponent(graphics);
        drawTurkishFlag(graphics);
    }

    public void drawCircleByCenter(Graphics g, int x, int y, int radius){
        g.fillOval(x-radius, y-radius, 2*radius, 2*radius);
    }

    private static Shape drawStarByCenter(double x, double y, double radius)
    {
        double innerRadius = radius*0.38;
        double startAng = Math.toRadians(-18);
        double deltaAng = Math.PI / 5;
        Path2D path = new Path2D.Double();
        for (int i = 0; i < 10; i++)
        {
            double angleRad = startAng + i * deltaAng;
            double ca = Math.cos(angleRad);
            double sa = Math.sin(angleRad);
            double relativeX = ca;
            double relativeY = sa;
            if ((i & 1) == 0) {
                relativeX *= radius;
                relativeY *= radius;
            } else {
                relativeX *= innerRadius;
                relativeY *= innerRadius;
            }

            if (i == 0) {
                path.moveTo(x + relativeX, y + relativeY);
            } else {
                path.lineTo(x + relativeX, y + relativeY);
            }
        }
        path.closePath();
        return path;
    }

    private void drawTurkishFlag(Graphics graphics) {
        Graphics2D g = (Graphics2D) graphics;

        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);

        int G = getHeight(); // Height
        int A = G/2; // Outer Moon's center's distance of Left
        int B = G/2; // Outer Moons' diameter
        int C = G/16; // Distance of Outer and Inner Moon's Centers
        int D = (int) (0.4*G); // Inner Moon' Diameter
        int E = G/3; // Distance of Star and Inner Moon
        int F = G/4; // Star Diameter
        int L = (int) (1.5*G); // Width
        int M = G/30; // Indent

        Color AL = new Color(227, 10, 23);
        Color AK = new Color(255, 255, 255);

        int[] outerMoonCoords = {A+M, G/2};
        int[] innerMoonCoords = {A+M+C, G/2};
        int[] starCoords = {M+E+B/2+F, G/2};

        g.setColor(AL);
        g.fillRect(0,0,L+M, G);
        g.setColor(AK);
        g.fillRect(0, 0, M, G);
        drawCircleByCenter(g, outerMoonCoords[0], outerMoonCoords[1], B/2);
        g.setColor(AL);
        drawCircleByCenter(g, innerMoonCoords[0], innerMoonCoords[1], D/2);
        Shape star = drawStarByCenter(starCoords[0], starCoords[1], (double) F/2);
        g.rotate(Math.toRadians(-90), starCoords[0], starCoords[1]);
        g.setColor(AK);
        g.fill(star);
    }
}

public class TurkishFlag extends JFrame {
    public TurkishFlag(){
        initUI();
    }

    private void initUI() {
        final Canvas canvas = new Canvas();
        final ImageIcon logo = new ImageIcon("src/logo.png");
        int h = 800;
        int w = (int) (h*1.5);
        add(canvas);
        setTitle("Turkish Flag");
        setSize(w, h);
        setLocationRelativeTo(null); // Center of the screen
        setIconImage(logo.getImage()); // Logo
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(TurkishFlag::new);
    }
}

