import java.awt.geom.*;

public class Star2D extends RectangularShape {
    double x;
    double y;
    double radius;

    public Star2D(double x, double y, double radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    @Override
    public double getX() {
        return this.x;
    }

    @Override
    public double getY() {
        return this.y;
    }

    @Override
    public double getWidth() {
        return 2*radius;
    }

    @Override
    public double getHeight() {
        return 2*radius;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void setFrame(double x, double y, double w, double h) {
        this.x = x;
        this.y = y;
        this.radius = w/2;
    }

    @Override
    public Rectangle2D getBounds2D() {
        return null;
    }

    @Override
    public boolean contains(double x, double y) {
        return false;
    }

    @Override
    public boolean intersects(double x, double y, double w, double h) {
        return false;
    }

    @Override
    public boolean contains(double x, double y, double w, double h) {
        return false;
    }

    @Override
    public PathIterator getPathIterator(AffineTransform at) {
        return getPath().getPathIterator(at);
    }
    private Path2D getPath()
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
}
