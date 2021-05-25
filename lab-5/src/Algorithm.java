import java.awt.*;
import java.util.ArrayList;

public class Algorithm {

    public ArrayList<Point> stepByStep(int x1, int y1, int x2, int y2){
        ArrayList<Point> points = new ArrayList<>();
        double k = ((double)y2 - (double)y1) / ((double)x2 - (double)x1);
        double b = y2 - k * x2;
        double dx = (double)Math.abs(x2 - x1) / ((double)Math.max(Math.abs(x2 - x1), Math.abs(y2 - y1)) * 2);
        dx = (x2 > x1 ? dx : -dx);

        for (double x = x1; (int)x != x2; x += dx){
            int y = (int) (k * x + b);
            points.add(new Point((int)x, y));
        }
            points.add(new Point(x2, y2));
        return points;
    }

    public static boolean eq(double f1, double f2) {
        return (Math.abs(f1 - f2) < 0.01f);
    }

    private static final int INSIDE = 0;
    private static final int LEFT   = 1;
    private static final int RIGHT  = 2;
    private static final int BOTTOM = 4;
    private static final int TOP    = 8;

    private static int getO(double x, double y, int xMin, int yMin, int xMax, int yMax) {
        int code = x < xMin
                ? LEFT
                : x > xMax
                ? RIGHT
                : INSIDE;
        if (y < yMin) code |= BOTTOM;
        else if (y > yMax) code |= TOP;
        return code;
    }

    public static ArrayList<Integer> fixSegment(ArrayList<Integer> segment, ArrayList<Integer> rectangle){
        double x1 = segment.get(0);
        double y1 = segment.get(1);
        double x2 = segment.get(2);
        double y2 = segment.get(3);

        int xMin = rectangle.get(0);
        int yMin = rectangle.get(1);
        int xMax = rectangle.get(2);
        int yMax = rectangle.get(3);

        double qx = 0;
        double qy = 0;

        boolean vertical = (x1 == x2);

        double slope = vertical ? 0 : (y2 - y1) / (x2 - x1);

        int o1 = getO(x1, y1, xMin, yMin, xMax, yMax);
        int o2 = getO(x2, y2, xMin, yMin, yMax, yMax);

        ArrayList<Integer> fixedSegment = new ArrayList<>();

        while (o1 != INSIDE || o2 != INSIDE) {

            if ((o1 & o2) != INSIDE)
                return fixedSegment;

            int o = o1 == INSIDE ? o2 : o1;

            if ((o & LEFT) != INSIDE) {
                qx = xMin;
                qy = (eq(qx, x1) ? 0 : qx-x1)*slope + y1;
            }
            else if ((o & RIGHT) != INSIDE) {
                qx = xMax;
                qy = (eq(qx, x1) ? 0 : qx-x1)*slope + y1;
            }
            else if ((o & BOTTOM) != INSIDE) {
                qy = yMin;
                qx = vertical
                        ? x1
                        : (eq(qy, y1) ? 0 : qy-y1)/slope + x1;
            }
            else if ((o & TOP) != INSIDE) {
                qy = yMax;
                qx = vertical
                        ? x1
                        : (eq(qy, y1) ? 0 : qy-y1)/slope + x1;
            }

            if (o == o1) {
                x1 = qx;
                y1 = qy;
                o1  = getO(x1, y1, xMin, yMin, xMax, yMax);
            }
            else {
                x2 = qx;
                y2 = qy;
                o2 = getO(x2, y2, xMin, yMin, xMax, yMax);
            }
        }

        fixedSegment.add((int)x1);
        fixedSegment.add((int)y1);
        fixedSegment.add((int)x2);
        fixedSegment.add((int)y2);

        return fixedSegment;
    }

    public ArrayList<ArrayList<Integer>> fixedSegments(ArrayList<ArrayList<Integer>> segments,
                                                     ArrayList<Integer> rectangle){
        ArrayList<ArrayList<Integer>> fixedRectangle = new ArrayList<>();

        for (ArrayList<Integer> segment : segments){
            ArrayList<Integer> fixedSegment = fixSegment(segment, rectangle);
            if (fixedSegment.size() != 0){
                fixedRectangle.add(fixedSegment);
            }
        }

        return fixedRectangle;
    }

}

