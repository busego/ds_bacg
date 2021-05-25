import java.awt.*;
import java.util.ArrayList;

public class Algorithm {

    long lastTime = 0;


    public ArrayList<Point> stepByStep(int x1, int y1, int x2, int y2){
        ArrayList<Point> points = new ArrayList<>();
        long start = System.nanoTime();
        double k = ((double)y2 - (double)y1) / ((double)x2 - (double)x1);
        double b = y2 - k * x2;
        double dx = (double)Math.abs(x2 - x1) / ((double)Math.max(Math.abs(x2 - x1), Math.abs(y2 - y1)) * 2);
        dx = (x2 > x1 ? dx : -dx);

        for (double x = x1; (int)x != x2; x += dx){
            int y = (int) (k * x + b);
            points.add(new Point((int)x, y));
        }
            points.add(new Point(x2, y2));
        lastTime = System.nanoTime() - start;
        return points;
    }

    public ArrayList<Point> cda(int x1, int y1, int x2, int y2){
        ArrayList<Point> points = new ArrayList<>();
        long start = System.nanoTime();

        int L = Math.max(Math.abs(x2 - x1), Math.abs(y2 - y1));
        double dX = (x2 - x1) * 1.0 / L;
        double dY = (y2 - y1) * 1.0 / L;
        points.add(new Point(x1, y1));
        double prevX = x1;
        double prevY = y1;
        int i = 1;

        while (i < L)
        {
            prevX = prevX + dX;
            prevY = prevY + dY;
            points.add(new Point((int)Math.round(prevX), (int)Math.round(prevY)));
            i++;
        }
        lastTime = System.nanoTime() - start;
        return points;
    }


    public ArrayList<Point> brezenkhem(int x1, int y1, int x2, int y2) {
        ArrayList<Point> points = new ArrayList<>();

        long start = System.nanoTime();

        boolean steep = Math.abs(y2 - y1) > Math.abs(x2 - x1);
        if (steep)
        {
            int temp = x1;
            x1 = y1;
            y1 = temp;

            temp = x2;
            y2 = x2;
            y2 = temp;
        }

        if (x1 > x2)
        {
            int temp = x1;
            x1 = x2;
            x2 = temp;

            temp = y1;
            y1 = y2;
            y2 = temp;
        }

        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);
        int error = dx / 2;
        int ystep = (y1 < y2) ? 1 : -1;
        int y = y1;
        for (int x = x1; x <= x2; x++)
        {
            points.add(new Point(steep ? y : x, steep ? x : y));
            error -= dy;
            if (error < 0)
            {
                y += ystep;
                error += dx;
            }
        }

        lastTime = System.nanoTime() - start;

        return points;
    }

    public ArrayList<Point> brezenkhemCircle(int x1, int y1, int r) {
        ArrayList<Point> points = new ArrayList<>();
        long start = System.nanoTime();

        int x = 0;
        int y = r;
        int e = 3 - 2 * r;
        points.add(new Point(x + x1, y + y1));
        points.add(new Point(x + x1, -y + y1));
        points.add(new Point(-x + x1, y + y1));
        points.add(new Point(-x + x1, -y + y1));

        points.add(new Point(y + x1, x + y1));
        points.add(new Point(-y + x1, x + y1));
        points.add(new Point(y + x1, -x + y1));
        points.add(new Point(-y + x1, -x + y1));
        while (x < y) {
            if (e >= 0) {
                e = e + 4 * (x - y) + 10;
                x = x + 1;
                y = y - 1;
            } else {
                e = e + 4 * x + 6;
                x = x + 1;
            }

            points.add(new Point(x + x1, y + y1));
            points.add(new Point(x + x1, -y + y1));
            points.add(new Point(-x + x1, y + y1));
            points.add(new Point(-x + x1, -y + y1));

            points.add(new Point(y + x1, x + y1));
            points.add(new Point(-y + x1, x + y1));
            points.add(new Point(y + x1, -x + y1));
            points.add(new Point(-y + x1, -x + y1));


        }

        lastTime = System.nanoTime() - start;

        return points;
    }
}

