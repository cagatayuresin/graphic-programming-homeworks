import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Random;

class Canvas extends JPanel implements ActionListener {
    private Timer timer;
    public Canvas() {
        initTimer();
    }

    private void initTimer(){
        int DELAY = 1500;

        timer = new Timer(DELAY, this);
        timer.start();
    }

    public Timer getTimer() {
        return timer;
    }


    @Override
    public void paintComponent(Graphics graphics){
        super.paintComponent(graphics);
        doDrawing(graphics);
    }

    private void doDrawing(Graphics graphics) {
        Graphics2D graphics2D = (Graphics2D) graphics;

        int margin = 20;
        int sw = 50; // Square width

        int x1 = margin;
        int y1 = margin;
        int x2 = margin;
        int y2 = 8*sw+margin;

        Random r = new Random();

        for (int i = 0; i<18; i++){
            graphics2D.setColor(Color.BLACK);
            graphics2D.drawLine(x1,y1,x2,y2);

            if(i<8){
                x1+=sw;
                x2+=sw;
            }else{
                if(i==8){
                    x1=margin;
                    y1=margin;
                    x2=8*sw+margin;
                    y2=margin;
                }else{
                    y1+=sw;
                    y2+=sw;
                }
            }
        }

        x1 = 2*margin+8*sw;
        y1 = margin;
        x2 = 2*margin+8*sw;
        y2 = margin+8*sw;

        for (int i = 0; i<18; i++){
            graphics2D.setStroke(new BasicStroke(Math.abs(r.nextInt())%15));
            graphics2D.setColor(randomColor());
            graphics2D.drawLine(x1,y1,x2,y2);

            if(i<8){
                x1+=sw;
                x2+=sw;
            }else{
                if(i==8){
                    x1=2*margin+8*sw;
                    y1=margin;
                    x2=16*sw+2*margin;
                    y2=margin;
                }else{
                    y1+=sw;
                    y2+=sw;
                }
            }
        }

        y1 = 2*margin+8*sw;
        x1 = margin;

        for(int i=0; i<64; i++){
            if(i%8 == 0 && i!=0){
                y1+=sw;
                x1=margin;
            }else{
                switchBW(graphics2D);
            }
            graphics2D.fillRect(x1,y1,sw,sw);
            x1+=sw;
        }

    }

    private void switchBW(Graphics graphics2D) {
        if(graphics2D.getColor()==Color.BLACK){
            graphics2D.setColor(Color.WHITE);
        }else{
            graphics2D.setColor(Color.BLACK);
        }
    }

    private Color randomColor() {
        Random r = new Random();
        return new Color(
                Math.abs(r.nextInt())%255,
                Math.abs(r.nextInt())%255,
                Math.abs(r.nextInt())%255
        );
    }

    @Override
    public void actionPerformed(ActionEvent e){
        repaint();
    }
}

public class CheckersOne extends JFrame {
    public CheckersOne(){
        initUI();
    }

    private void initUI() {
        final Canvas canvas = new Canvas();
        final ImageIcon logo = new ImageIcon("src/logo.png");

        add(canvas);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Timer timer = canvas.getTimer();
                timer.stop();
            }
        });

        setTitle("Checkers - 1");
        setSize(880, 920);
        setLocationRelativeTo(null); // Center of the screen
        setIconImage(logo.getImage()); // Logo
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }


    public static void main(String[] args) {
        EventQueue.invokeLater(CheckersOne::new);
    }
}

