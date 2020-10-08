package plumber.engine.designer;

import plumber.engine.PlumberModelOld;
import plumber.engine.WaterPath;
import plumber.engine.WaterPathSegment;
import plumber.engine.models.PlumberModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.logging.Logger;
import javax.swing.*;

public class Designer extends javax.swing.JFrame {
    private static final int BOARD_HEIGHT = 4;
    private static final int BOARD_WIDTH = 4;
    private JPanel canvasPanel;
    private JTextArea textArea;
    private JButton startButton;

    private javax.swing.JOptionPane confirmPane;
    private JMenu jMenu1;
    private JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private JMenuItem newItem;
    private JMenuItem openItem;
    private JMenuItem openItem15;
    private JMenuItem saveAsItem;
    private JMenuItem saveAsTemplate;
    private JMenuItem saveAsTemplate15;
    private JMenuItem saveItem;
    private JFileChooser saveLevelDialog;
    private JMenuItem solve;
    private JMenuItem solve15;



    public static void main(String[] args) {

       // new Designer();
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Designer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(Designer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Designer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            Logger.getLogger(Designer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }


        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Designer().setVisible(true);
            }
        });
    }

    public Designer() {
        initComponents();
    }


    private void initComponents() {
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(1920, 1080);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        setLocation(toolkit.getScreenSize().width / 4, toolkit.getScreenSize().height / 4);

        saveLevelDialog = new JFileChooser();
        confirmPane = new javax.swing.JOptionPane();
        canvasPanel = new CanvasPanel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new JMenu();
        newItem = new JMenuItem();
        openItem = new JMenuItem();
        openItem15 = new JMenuItem();
        saveItem = new JMenuItem();
        saveAsItem = new JMenuItem();
        saveAsTemplate = new JMenuItem();
        saveAsTemplate15 = new JMenuItem();
        jMenu2 = new JMenu();
        solve = new JMenuItem();
        solve15 = new JMenuItem();
        textArea = new JTextArea("Your text here");
        startButton = new JButton("Start");

        saveLevelDialog.setDialogTitle("Level Saver");

        setDefaultCloseOperation(3);

        canvasPanel.setBorder(new javax.swing.border.SoftBevelBorder(0));
        canvasPanel.setToolTipText("");
        canvasPanel.setSize(new java.awt.Dimension(1200, 1000));

        GroupLayout canvasPanelLayout = new GroupLayout(canvasPanel);
        canvasPanel.setLayout(canvasPanelLayout);
        canvasPanelLayout.setHorizontalGroup(canvasPanelLayout
                .createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGap(0, 1108, 32767));

        canvasPanelLayout.setVerticalGroup(canvasPanelLayout
                .createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGap(0, 723, 32767));


        jMenu1.setText("File");

        newItem.setText("New File...");
        newItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                Designer.this.newItemActionPerformed(evt);
            }
        });
        jMenu1.add(newItem);

        openItem.setText("Open...");
        openItem.setToolTipText("");
        openItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                Designer.this.openItemActionPerformed(evt);
            }
        });
        openItem15.setText("Open Template 15...");
        openItem15.setToolTipText("");
        openItem15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                Designer.this.openTemplate15ItemActionPerformed(evt);
            }
        });

        jMenu1.add(openItem);
        jMenu1.add(openItem15);
        saveItem.setText("Save");
        jMenu1.add(saveItem);

        saveAsItem.setText("Save as...");
        saveAsItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                Designer.this.saveAsItemActionPerformed(evt);
            }
        });
        saveAsTemplate.setText("Save as template...");
        saveAsTemplate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Designer.this.saveAsTemplateAction();

            }
        });

        saveAsTemplate15.setText("Save as template 15...");
        saveAsTemplate15.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Designer.this.saveAsTemplate15Action();

            }
        });
        jMenu1.add(saveAsItem);
        jMenu1.add(saveAsTemplate);
        jMenu1.add(saveAsTemplate15);
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Analytics");

        solve.setText("Is Solvable");
        solve.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                Designer.this.solveActionPerformed(evt);
            }
        });

        solve15.setText("Solve 15");
        solve15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                Designer.this.solvePuzzle15Action();
            }
        });

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int depth = Integer.parseInt(textArea.getText().toString().trim());
                int[][] board = ((CanvasPanel) canvasPanel).getBoardArray();
                boolean[][] frozen  = ((CanvasPanel) canvasPanel).getFrozenArray();
                boolean[][] rotatable  = ((CanvasPanel) canvasPanel).getRotatableArray();
                int size = board.length;
                Plumber15 plumber15 = new Plumber15(board, frozen,rotatable, size);
                plumber15.getStateAtDepth(depth);



            }
        });

        jMenu2.add(solve);
        jMenu2.add(solve15);
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout
                .createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                        .addComponent(canvasPanel, -1, -1, 32767)
                        .addComponent(textArea, -1, -1, 32767)
                        .addComponent(startButton, -1, -1, 32767)
                        .addContainerGap()));

        layout.setVerticalGroup(layout
                .createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(textArea, -1, -1, 32767)
                        .addComponent(startButton, -1, -1, 32767)
                        .addComponent(canvasPanel, -1, -1, 32767)));


        pack();
    }

    private void openTemplate15ItemActionPerformed(ActionEvent evt) {
        char[] charLevel = new char['2'];
        ArrayList <String> level = new ArrayList<>();
        int status = saveLevelDialog.showOpenDialog(null);
        if (status == 0) {
            java.io.File fileToOpen = saveLevelDialog.getSelectedFile();
            try {


                java.io.FileReader fw = new java.io.FileReader(fileToOpen);
                String line;

                try {

                    BufferedReader bufferreader = new BufferedReader(fw);


                    while ((line = bufferreader.readLine()) != null) {

                        level.add(line);
                    }

                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                int readSymbols = fw.read(charLevel);
                setTitle(fileToOpen.getName());
                fw.close();

                System.out.println(level);
                ((CanvasPanel) canvasPanel).loadBoardTemplate15(level);

            } catch (IOException iox) {
                iox.printStackTrace();
            }
        }
    }

    private void saveAsTemplate15Action() {
        int[][] level = ((CanvasPanel) canvasPanel).getBoardArray();
        boolean[][] frozen= ((CanvasPanel) canvasPanel).getFrozenArray();
        boolean[][] rotatable = ((CanvasPanel) canvasPanel).getRotatableArray();
        for (int i = 0; i < BOARD_HEIGHT; i++) {
            for (int j = 0; j < BOARD_WIDTH; j++) {

                //Closing for a short time
//                if (level[i][j] / 10 != 5) {
//                    level[i][j] = (level[i][j] /10) * 10;
//                }
                System.out.print(level[i][j] + " ");
            }
            System.out.println("\n");
        }
        int status = saveLevelDialog.showSaveDialog(null);
        if (status == JFileChooser.APPROVE_OPTION) {
            java.io.File fileToSave = saveLevelDialog.getSelectedFile();
            writeToFile15(fileToSave.getAbsolutePath(), level, frozen, rotatable);

        }
    }

    private void writeToFile15(String absolutePath, int[][] level, boolean[][] frozen, boolean[][] rotatable) {
        File file = new File(absolutePath);
        BufferedWriter output = null;
        try {
            output = new BufferedWriter(new FileWriter(file));
            output.write(BOARD_HEIGHT +"\n");
            output.write(BOARD_WIDTH +"\n");
            for (int i = 0; i < BOARD_HEIGHT; i++) {
                for (int j = 0; j < BOARD_WIDTH; j++) {
                    output.write(level[i][j] +"\n");
                }
            }
             for (int i = 0; i < BOARD_HEIGHT; i++) {
                for (int j = 0; j < BOARD_WIDTH; j++) {
                    output.write(frozen[i][j] +"\n");
                }
            }

            for (int i = 0; i < BOARD_HEIGHT; i++) {
                for (int j = 0; j < BOARD_WIDTH; j++) {
                    output.write(rotatable[i][j] +"\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openItemActionPerformed(ActionEvent evt) {

        char[] charLevel = new char['2'];
        ArrayList <String> level = new ArrayList<>();
        int status = saveLevelDialog.showOpenDialog(null);
        if (status == 0) {
            java.io.File fileToOpen = saveLevelDialog.getSelectedFile();
            try {


                java.io.FileReader fw = new java.io.FileReader(fileToOpen);
                String line;

                try {

                    BufferedReader bufferreader = new BufferedReader(fw);


                    while ((line = bufferreader.readLine()) != null) {

                        level.add(line);
                                 }

                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                int readSymbols = fw.read(charLevel);
                setTitle(fileToOpen.getName());
                fw.close();

                System.out.println(level);
                ((CanvasPanel) canvasPanel).loadBoard(level);

            } catch (IOException iox) {
                iox.printStackTrace();
            }
        }
    }


    private void solvePuzzle15Action() {
        int[][] board = ((CanvasPanel) canvasPanel).getBoardArray();
        boolean[][] frozen  = ((CanvasPanel) canvasPanel).getFrozenArray();
        boolean[][] rotatable  = ((CanvasPanel) canvasPanel).getRotatableArray();
        int size = board.length;
        Plumber15 plumber15 = new Plumber15(board, frozen, rotatable, size);
        plumber15.fullInfo();


    }

    private void saveAsTemplateAction() {
        int[][] level = ((CanvasPanel) canvasPanel).getBoardArray();

        for (int i = 0; i < BOARD_HEIGHT; i++) {
            for (int j = 0; j < BOARD_WIDTH; j++) {

                //Closing for a short time
//                if (level[i][j] / 10 != 5) {
//                    level[i][j] = (level[i][j] /10) * 10;
//                }
                System.out.print(level[i][j] + " ");
            }
            System.out.println("\n");
        }
        int status = saveLevelDialog.showSaveDialog(null);
        if (status == JFileChooser.APPROVE_OPTION) {
            java.io.File fileToSave = saveLevelDialog.getSelectedFile();
            writeToFile(fileToSave.getAbsolutePath(), level);

        }

    }

    private void writeToFile(String absolutePath, int[][] level) {
        File file = new File(absolutePath);
        BufferedWriter output = null;
        try {
            output = new BufferedWriter(new FileWriter(file));
            output.write(BOARD_HEIGHT +"\n");
            output.write(BOARD_WIDTH +"\n");
            for (int i = 0; i < BOARD_HEIGHT; i++) {
                for (int j = 0; j < BOARD_WIDTH; j++) {
                    output.write(level[i][j] +"\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void saveAsItemActionPerformed(ActionEvent evt) {
        int[][] level = ((CanvasPanel) canvasPanel).getBoardArray();

        for (int i = 0; i < BOARD_HEIGHT; i++) {
            for (int j = 0; j < BOARD_WIDTH; j++) {


                System.out.print(level[i][j] + " ");
            }
            System.out.println("\n");
        }
        PlumberModel model = new PlumberModel(level, BOARD_HEIGHT, BOARD_WIDTH);


        System.out.println(model.toString());
        ArrayList< WaterPathSegment> solution  = model.shortestPath();
        if (solution == null) {
            return;
        }
        int difficulty = calculateDifficulty(level, solution, BOARD_HEIGHT, BOARD_WIDTH);
        int status = saveLevelDialog.showSaveDialog(null);
        if (status == 0) {
            java.io.File fileToSave = saveLevelDialog.getSelectedFile();

            writeToFile(fileToSave.getAbsolutePath(), level, solution, difficulty);
        }
    }

    private int calculateDifficulty(int[][] level, ArrayList<WaterPathSegment> solution, int height, int width) {
        int angles = 0;
        for (int i= 0 ; i < height; i++) {
            for (int j = 0 ; j < width; j++) {
                if (level[i][j] / 10 == 2 || level[i][j] / 10 == 4) {
                    angles++;
                }
            }
        }
        return (angles + 2 * solution.size()) / 2;
    }

    private void solveActionPerformed(ActionEvent evt) {
        int[][] board = ((CanvasPanel) canvasPanel).getBoardArray();
        int heigth = ((CanvasPanel) canvasPanel).getBoardHeight();
        int width = ((CanvasPanel) canvasPanel).getBoardWidth();

        PlumberModel model = new PlumberModel(board, heigth, width);
        int totalSolutions = model.allSolutions().size();
        ArrayList<WaterPathSegment> path = model.shortestPath();
//        PlumberModelOld model = new PlumberModelOld(board, heigth, width);
//        ArrayList<WaterPath> path = model.shortestPath();
        if (path != null) {
            int solutionLength = path.size();
            System.out.println("Solution");
            for (WaterPathSegment waterPathSegment : path) {
                System.out.println(waterPathSegment.x + " " + waterPathSegment.y + " " + waterPathSegment.rotation);
            }
            javax.swing.JOptionPane.showMessageDialog(null, "The Level Has " + totalSolutions + " solutions." + "Shortest solution with length = " + solutionLength);
        } else {
            javax.swing.JOptionPane.showMessageDialog(null, "The Level Has No Solution");
        }
    }


    private void newItemActionPerformed(ActionEvent evt) {
        ((CanvasPanel) canvasPanel).clearBoard();
    }


    public void writeToFile(String filename, int[][] level, ArrayList<WaterPathSegment> solution, int difficulty) {
        File file = new File(filename);
        BufferedWriter output = null;
        try {
            output = new BufferedWriter(new FileWriter(file));
            output.write(difficulty +"\n");
            output.write(BOARD_HEIGHT +"\n");
            output.write(BOARD_WIDTH +"\n");
            for (int i = 0; i < BOARD_HEIGHT; i++) {
                for (int j = 0; j < BOARD_WIDTH; j++) {
                    output.write(level[i][j] +"\n");
                }
            }

            //Writing solution
            output.write(solution.size() + "\n");
            output.write(solution.get(0).x + " " + solution.get(0).y + " " + solution.get(0).rotation + "\n");
            for (int i = 1; i< solution.size() - 1; i++) {
                output.write(solution.get(i).x + " " + solution.get(i).y + " " + solution.get(i+1).rotation + "\n");
            }
            int endX = solution.get(solution.size() - 1).x;
            int endY = solution.get(solution.size() - 1).y;
            output.write(endX + " " + endY + " " + level[endX][endY] % 10 + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
