import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MainWindow extends JFrame {

    private int WIDTH = 1100;
    private int HEIGHT = 800;

    private DrawPanel drawingPanel = new DrawPanel();

    private JButton selectFileButton;
    private JButton clearButton;
    private JButton fixButton;

    private final double PANEL_SCALE_W = 0.7;
    private final double PANEL_SCALE_H = 1.0;

    private Algorithm algorithm = new Algorithm();
    private ArrayList<ArrayList<Integer>> segments = new ArrayList<>();
    private ArrayList<Integer> rectangle = new ArrayList<>();


    public MainWindow(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setVisible(true);
        setLayout(null);

        drawingPanel.setBounds((int)((1 - PANEL_SCALE_W) * WIDTH), 0, (int)(PANEL_SCALE_W * WIDTH), (int)(PANEL_SCALE_H * HEIGHT));
        add(drawingPanel);


        Font labelFont = new Font("Serif", Font.PLAIN, 25);

        selectFileButton = new JButton("Select");
        selectFileButton.setBounds(10, 0, 200, 40);
        selectFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter(
                        "TXT files", "txt");
                chooser.setFileFilter(filter);
                int returnVal = chooser.showOpenDialog(null);
                if(returnVal == JFileChooser.APPROVE_OPTION) {
                    getParameters(chooser.getSelectedFile().getAbsolutePath());
                    drawLines();
                }

            }
        });
        add(selectFileButton);

        clearButton = new JButton("Clear");
        clearButton.setBounds(10, 100, 200, 40);
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawingPanel.clear();
            }
        });
        add(clearButton);

        fixButton = new JButton("fix");
        fixButton.setBounds(10, 50, 200, 40);
        fixButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                segments = algorithm.fixedSegments(segments, rectangle);
                drawingPanel.clear();
                drawLines();
            }
        });

        add(fixButton);

        setResizable(false);
    }

    private void getParameters(String filePath){
        try {
            Scanner scanner = new Scanner(new File(filePath));
            int n = Integer.parseInt(scanner.nextLine());

            for(int i = 0; i < n; i++){
                ArrayList<Integer> segment = new ArrayList<>(4);
                for (int j = 0; j < 4; j++){
                    segment.add(Integer.parseInt(scanner.next()));
                }
                segments.add(segment);
            }
            rectangle = new ArrayList<>();
            for (int i = 0; i < 4; i++){
                rectangle.add(Integer.parseInt(scanner.next()));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void drawLines(){
        //segments = algorithm.fixedSegments(segments, rectangle);
        for (ArrayList<Integer> segment : segments){

            int x1 = segment.get(0);
            int y1 = segment.get(1);
            int x2 = segment.get(2);
            int y2 = segment.get(3);

            drawingPanel.drawPoints(algorithm.stepByStep(x1, y1, x2, y2));
        }
        drawingPanel.drawRectangle(rectangle);
    }

    public static void main(String[] args) {
        MainWindow mainWindow = new MainWindow();
        mainWindow.getParameters("parameters.txt");
        mainWindow.drawLines();
        mainWindow.repaint();
    }
}
