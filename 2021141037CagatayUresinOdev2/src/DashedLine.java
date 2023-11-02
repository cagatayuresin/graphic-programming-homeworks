import javax.swing.*;
import java.awt.*;

class Canvas2 extends JPanel {
    @Override
    public void paintComponent(Graphics graphics){
        super.paintComponent(graphics);
        drawDashedLineHW(graphics);
    }

    private void drawDashedLine(Graphics2D g, int x1, int y1, int length){
        int dashWidth = 7;
        int dashDist = 3;
        for(int i = 0; i < length/(dashDist+dashWidth); i++){
            g.drawLine(x1 + i*(dashWidth+dashDist), y1, x1 + i*(dashWidth+dashDist)+ dashWidth, y1);
        }
        g.drawLine(x1+length, y1, x1+length, y1);
    }

    private void drawDashedLineHW(Graphics graphics) {
        Graphics2D g = (Graphics2D) graphics;
        drawDashedLine(g,30, 30, 300);
}

public static class DashedLine extends JFrame {
    public DashedLine(){
        initUI();
    }

    private void initUI() {
        final Canvas2 canvas = new Canvas2();
        final ImageIcon logo = new ImageIcon("src/logo.png");
        add(canvas);
        setTitle("Dashed Line");
        setSize(360, 100);
        setLocationRelativeTo(null); // Center of the screen
        setIconImage(logo.getImage()); // Logo
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}

    public static void main(String[] args) {
        EventQueue.invokeLater(DashedLine::new);
    }
}

