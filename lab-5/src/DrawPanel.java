import javafx.util.Pair;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

public class DrawPanel extends JPanel {

    private int numCellsX = 30;
    private int numCellsY;
    private int cellSize = 10;
    private ArrayList<Point> points = new ArrayList<>();
    private ArrayList<Integer> rectangle = new ArrayList<>();



    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawCoordinates(g);
        drawCoordinateRectangles(numCellsX, g);

        for (Point point : points) {
            fillCell(point.x, point.y, cellSize, g, Color.BLACK);
        }

        List<Point> rectanglePoints = getRectanglePoints();
        if (rectanglePoints != null){
            for (Point point : rectanglePoints) {
                fillCell(point.x, point.y, cellSize, g, Color.BLUE);
            }
        }

    }

    private List<Point> getRectanglePoints(){
        if (rectangle.size() != 4)
            return null;

        List<Point> rectanglePoints = new ArrayList<>();

        int x1 = rectangle.get(0);
        int y1 = rectangle.get(1);
        int x2 = rectangle.get(2);
        int y2 = rectangle.get(3);

        for (int i = x1; i < x2; i++){
            rectanglePoints.add(new Point(i, y1));
            rectanglePoints.add(new Point(i, y2));
        }

        for (int i = y1; i < y2; i++){
            rectanglePoints.add(new Point(x1, i));
            rectanglePoints.add(new Point(x2, i));
        }

        return rectanglePoints;
    }

    private void drawCoordinates(Graphics g){
        Graphics2D g2 = (Graphics2D)g;
        Rectangle bounds = getBounds();

        Line2D yLine = new Line2D.Double((int)(bounds.width / 2), 0, (int)(bounds.width / 2), bounds.height);
        Line2D xLine = new Line2D.Double(0, (int)(bounds.height / 2), bounds.width, (int)(bounds.height / 2));


        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(3));

        g2.draw(xLine);
        g2.draw(yLine);
    }

    private void drawCoordinateRectangles(int n, Graphics g){
        Graphics2D g2 = (Graphics2D)g;
        Rectangle bounds = getBounds();

        cellSize = (bounds.width < bounds.height ? bounds.width : bounds.height) / n;

        int nX = bounds.width / cellSize;
        int nY = bounds.height / cellSize;

        numCellsY = nY * 2;

        g2.setStroke(new BasicStroke(1));
        for (int i = -nX / 2; i < nX / 2; i++){
            Line2D line = new Line2D.Double((int)(bounds.width / 2) + cellSize * i,0,(int)(bounds.width / 2) + cellSize * i,bounds.height);

            g2.draw(line);
        }

        for (int i = -nY / 2; i < nY / 2; i++){
            Line2D line = new Line2D.Double(0,(int)(bounds.height / 2) + cellSize * i,bounds.width,(int)(bounds.height / 2) +cellSize * i);
            g2.draw(line);
        }

    }

    private void fillCell(int x, int y, int cellSize, Graphics g, Color color){
        Graphics2D g2 = (Graphics2D)g;
        Rectangle bounds = getBounds();

        g.setColor(color);

        g2.fillRect((bounds.width / 2) + cellSize * (x - 1), (bounds.height / 2) - cellSize * (y ), cellSize, cellSize);
    }


    public void drawRectangle(ArrayList<Integer> rectangle){
        if (rectangle.size() != 4)
            return;

        this.rectangle = (ArrayList<Integer>) rectangle.clone();

        repaint();
    }

    public void drawPoints(ArrayList<Point> points){
        this.points.addAll(points);
        Pair<Integer, Integer> deltas = getMaxDxDy(this.points);

        if (numCellsX / 2 < deltas.getKey() || numCellsX / 2 + 6 > deltas.getKey())
            numCellsX = deltas.getKey() * 2 + 6;

        if (numCellsY / 2 < deltas.getValue())
            numCellsX = deltas.getValue() * 2 + 2;

        repaint();
    }

    private Pair<Integer, Integer> getMaxDxDy(ArrayList<Point> points){
        int dx = 0;
        int dy = 0;
        for (Point point : points){
            if (Math.abs(point.x) > dx)
                dx = Math.abs(point.x);

            if (Math.abs(point.y) > dy)
                dy = Math.abs(point.y);
        }
        return new Pair<Integer, Integer>(dx, dy);
    }

    public void clear(){
        this.rectangle.clear();
        this.points.clear();
        repaint();
    }

}
