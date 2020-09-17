package plumber.engine.designer;

import plumber.engine.WaterPath;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;


public class CanvasPanel
        extends JPanel {
    private static final int BOARD_HEIGHT = 5      ;
    private static final int BOARD_WIDTH = 5;
    private static int TOOLS_WIDTH = 7;
    private final int[] elements_unique_rotations = {2, 4, 1, 2, 4, 4, 1};
    private final int[] elements_unique_rotations2 = {4, 4, 4, 4, 4, 4, 4};
    private final int[] elements_unique_rotations3 = {4, 4, 4, 1, 1, 2, 4};
    private static final String[][] elements = {
            {"pipe1.png", "pipe2.png", null, null},
            {"pipe90_1.png", "pipe90_2.png", "pipe90_3.png", "pipe90_4.png"},
            {"cross1.png", null, null, null},
            {"pipe290_1.png", "pipe290_2.png", null, null},
            {"valve1.png", "valve2.png", "valve3.png", "valve4.png"},
            {"house_levels_1.png", "house_levels_2.png", "house_levels_3.png", "house_levels_4.png"},
            {"remove.png", null, null, null},
           };
    ;

    private static final String[][] elements2 = {
            {"portal.png", "portal2.png", "portal3.png", "portal4.png"},
            {"blue_green.png", "blue_green2.png", "blue_green3.png", "blue_green4.png"},
            {"blue_pink.png", "blue_pink2.png", "blue_pink3.png", "blue_pink4.png"},
            {"green_pink.png", "green_pink2.png", "green_pink3.png", "green_pink4.png"},
            {"valve_blue.png", "valve_blue2.png", "valve_blue3.png", "valve_blue4.png"},
            {"valve_green.png", "valve_green2.png", "valve_green3.png", "valve_green4.png"},
            {"valve_pink.png", "valve_pink2.png", "valve_pink3.png", "valve_pink4.png"},
    };

    private static final String[][] elements3 = {
            {"container_blue_1.png", "container_blue_2.png", "container_blue_3.png", "container_blue_4.png"},
            {"container_green_1.png", "container_green_2.png", "container_green_3.png", "container_green_4.png"},
            {"container_pink_1.png", "container_pink_2.png", "container_pink_3.png", "container_pink_4.png"},
            {"metal.png", null, null, null},
            {"cube.png", null, null, null},
            {"rotate_pipe1.png", "rotate_pipe2.png", null, null},
            {"rotate_angle1.png", "rotate_angle2.png", "rotate_angle3.png", "rotate_angle4.png"},
    };
    private Toolkit toolkit;


    private int selectedToolIndex = 1;

    private int cellSize  = 70;
    private int[][] board;
    private boolean[][] isFrozen;
    private boolean[][] isRotatable;
    private Image[][] images;
    private Image[][] images2;
    private Image[][] images3;
    private Image back;

    private TextArea  textArea;
    private static final int MAX_ELEMENT_ROTATION = 4;

    public CanvasPanel() {
        toolkit = Toolkit.getDefaultToolkit();
        initBoard();
        try {
            loadImages();
        } catch (IOException ex) {
            Logger.getLogger(CanvasPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        addMouseListener(new CanvasMouseListener());
    }


    public void paintComponent(Graphics g) {
        for (int i = 0; i < elements_unique_rotations.length; i++) {
            g.drawImage(images[i][0], TOOLS_WIDTH * cellSize, i * cellSize, cellSize, cellSize, null);
        }

        for (int i = 0; i < elements_unique_rotations2.length; i++) {
            g.drawImage(images2[i][0], (TOOLS_WIDTH + 1) * cellSize, i * cellSize, cellSize, cellSize, null);
        }

        for (int i = 0; i < elements_unique_rotations3.length; i++) {
            g.drawImage(images3[i][0], (TOOLS_WIDTH + 2) * cellSize, i * cellSize, cellSize, cellSize, null);
        }

        g.drawRect(TOOLS_WIDTH * cellSize, (selectedToolIndex - 1) * cellSize, cellSize, cellSize);

        g.drawImage(back, 0, 0, BOARD_HEIGHT * cellSize, BOARD_WIDTH * cellSize, null);
//        g.drawRect(0, 0, 600, 800);

        //Drawing frozen elements
        for (int i = 0; i < BOARD_WIDTH; i++) {
            for (int j = 0; j < BOARD_HEIGHT; j++) {
                if (isFrozen[i][j]) {
                    g.drawImage(images3[3][0], i * cellSize, j * cellSize, cellSize, cellSize, null);
                }
            }
        }


        for (int i = 0; i < BOARD_WIDTH; i++) {
            for (int j = 0; j < BOARD_HEIGHT; j++) {
                if (board[i][j] / 10 < 10) {
                    if (board[i][j] / 10 < 7) {
                        if (isRotatable[i][j] && board[i][j]/10 == 1) {
                            g.drawImage(images3[5][(board[i][j] % 10)], i * cellSize, j * cellSize, cellSize, cellSize, null);

                        }else
                        if (isRotatable[i][j] && board[i][j]/10 == 2) {
                            g.drawImage(images3[6][(board[i][j] % 10)], i * cellSize, j * cellSize, cellSize, cellSize, null);

                        }else{
                            g.drawImage(images[(board[i][j] / 10 - 1)][(board[i][j] % 10)], i * cellSize, j * cellSize, cellSize, cellSize, null);
                        }
                    }
                    if (board[i][j] / 10 == 9 ) {
                        g.drawImage(images3[4][0], i * cellSize, j * cellSize, cellSize, cellSize, null);
                    }
                }else {
                    if (board[i][j] / 10 == 51) {
                        g.drawImage(images2[4][board[i][j]%10], i * cellSize, j * cellSize, cellSize, cellSize, null);
                    }
                    if (board[i][j] / 10 == 52) {
                        g.drawImage(images2[5][board[i][j]%10], i * cellSize, j * cellSize, cellSize, cellSize, null);
                    }
                    if (board[i][j] / 10 == 53) {
                        g.drawImage(images2[6][board[i][j]%10], i * cellSize, j * cellSize, cellSize, cellSize, null);
                    }

                    if (board[i][j] / 10 == 61) {
                        g.drawImage(images3[0][board[i][j]%10], i * cellSize, j * cellSize, cellSize, cellSize, null);
                    }
                    if (board[i][j] / 10 == 62) {
                        g.drawImage(images3[1][board[i][j]%10], i * cellSize, j * cellSize, cellSize, cellSize, null);
                    }
                    if (board[i][j] / 10 == 63) {
                        g.drawImage(images3[2][board[i][j]%10], i * cellSize, j * cellSize, cellSize, cellSize, null);
                    }
                    if (board[i][j] / 10 == 10) {
                        g.drawImage(images2[0][board[i][j]%10], i * cellSize, j * cellSize, cellSize, cellSize, null);
                    }
                    if (board[i][j] / 1000 == 11) {
                        int in;
                        int out;
                        int rotation;
                        in  = (board[i][j]/ 100) % 10;
                        out = (board[i][j]/ 10) % 10;
                        rotation = board[i][j] % 10;
                        if (in == 1 && out == 2 ) {
                          //  System.out.println("Drawing 1 2");
                            g.drawImage(images2[1][board[i][j]%10], i * cellSize, j * cellSize, cellSize, cellSize, null);
                        }
                        if (in == 1 && out == 3 ) {
                          //  System.out.println("Drawing 1 3");
                            g.drawImage(images2[2][board[i][j]%10], i * cellSize, j * cellSize, cellSize, cellSize, null);
                        }
                        if (in == 2 && out == 3 ) {
                           // System.out.println("Drawing 2 3");
                            g.drawImage(images2[3][board[i][j]%10], i * cellSize, j * cellSize, cellSize, cellSize, null);

                        }

                    }



                }

            }
        }
    }


    private void loadImages()
            throws IOException {
        back = ImageIO.read(getClass().getResourceAsStream("/plumber/engine/designer/back.png"));

        images = new Image[elements_unique_rotations.length][4];
        for (int i = 0; i < elements_unique_rotations.length; i++) {
            for (int j = 0; j < 4; j++) {
                if (elements[i][j] != null) {
                    System.out.println(i + " " + j + "  " + elements[i][j]);
                    images[i][j] = ImageIO.read(getClass().getResourceAsStream("/plumber/engine/designer/" + elements[i][j]));
                }
            }
        }

        images2 = new Image[elements_unique_rotations2.length][4];
        for (int i = 0; i < elements_unique_rotations2.length; i++) {
            for (int j = 0; j < 4; j++) {
                if (elements2[i][j] != null) {
                    System.out.println(i + " " + j + "  " + elements[i][j]);
                    images2[i][j] = ImageIO.read(getClass().getResourceAsStream("/plumber/engine/designer/" + elements2[i][j]));
                }
            }
        }

        images3 = new Image[elements_unique_rotations3.length][4];
        for (int i = 0; i < elements_unique_rotations3.length; i++) {
            for (int j = 0; j < 4; j++) {
                if (elements3[i][j] != null) {
                    System.out.println(i + " " + j + "  " + elements3[i][j]);
                    images3[i][j] = ImageIO.read(getClass().getResourceAsStream("/plumber/engine/designer/" + elements3[i][j]));
                }
            }
        }
    }

    private void initBoard() {
        board = new int[BOARD_WIDTH][BOARD_HEIGHT];
        for (int i = 0; i < BOARD_WIDTH; i++) {
            for (int j = 0; j < BOARD_HEIGHT; j++) {
                board[i][j] = 70;
            }
        }

        isFrozen = new boolean[BOARD_WIDTH][BOARD_HEIGHT];
        isRotatable = new boolean[BOARD_WIDTH][BOARD_HEIGHT];

        for (int i = 0; i < BOARD_WIDTH; i++) {
            for (int j = 0; j < BOARD_HEIGHT; j++) {
                isFrozen[i][j] = false;
                isRotatable[i][j] = false;
            }
        }
    }

    public int[][] getBoardArray() {
        int[][] boardAndroidFormat = new int[BOARD_HEIGHT][BOARD_WIDTH];
        for (int i = 0; i < BOARD_HEIGHT; i++) {
            for (int j = 0; j < BOARD_WIDTH; j++) {
                boardAndroidFormat[i][j] = board[j][i];
            }
        }
        return boardAndroidFormat;
    }

    public boolean[][] getFrozenArray() {
        boolean[][] frozenAndroidFormat = new boolean[BOARD_HEIGHT][BOARD_WIDTH];
        for (int i = 0; i < BOARD_HEIGHT; i++) {
            for (int j = 0; j < BOARD_WIDTH; j++) {
                frozenAndroidFormat[i][j] = isFrozen[j][i];
            }
        }
        return frozenAndroidFormat;
    }

    public boolean[][] getRotatableArray() {
        boolean[][] rotatableAndroidFormat = new boolean[BOARD_HEIGHT][BOARD_WIDTH];
        for (int i = 0; i < BOARD_HEIGHT; i++) {
            for (int j = 0; j < BOARD_WIDTH; j++) {
                rotatableAndroidFormat[i][j] = isRotatable[j][i];
            }
        }
        return rotatableAndroidFormat;
    }

    public String getBoard() {
        StringBuilder level = new StringBuilder();
        level.append(HEIGHT +"\n");
        level.append(WIDTH + "\n");
        for (int i = 0; i < BOARD_WIDTH; i++) {
            for (int j = 0; j < BOARD_HEIGHT; j++) {
                level.append(board[i][j] + "\n");
            }
        }


        return level.toString();
    }

    public int getBoardHeight() {
        return BOARD_HEIGHT;
    }

    public int getBoardWidth() {
        return BOARD_WIDTH;
    }

    public void loadBoard(ArrayList<String> level) {


        int height = Integer.parseInt(level.get(2));
        int width = Integer.parseInt(level.get(3));
        System.out.println("height : " + height + "  width : " + width);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                board[j][i] = Integer.parseInt(level.get((i * width + j + 4)));
            }
        }
        repaint();
    }

    void clearBoard() {
        for (int i = 0; i < BOARD_WIDTH; i++) {
            for (int j = 0; j < BOARD_HEIGHT; j++) {
                board[i][j] = 70;
                isFrozen[i][j] = false;
                isRotatable[i][j] = false;
            }
        }
        repaint();
    }

    public void loadBoardTemplate15(ArrayList<String> level) {
        int height = Integer.parseInt(level.get(0));
        int width = Integer.parseInt(level.get(1));
        System.out.println("height : " + height + "  width : " + width);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                board[j][i] = Integer.parseInt(level.get((i * width + j + 2)));
            }
        }
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                isFrozen[j][i] = Boolean.parseBoolean( level.get((height* width + i * width + j + 2)));
            }
        }
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                isRotatable[j][i] = Boolean.parseBoolean( level.get((2 * height* width + i * width + j + 2)));
            }
        }
        repaint();
    }



    private class CanvasMouseListener
            implements MouseListener {
        public CanvasMouseListener() {
        }

        public void mouseClicked(MouseEvent e) {
            int logX = e.getX() / cellSize;
            int logY = e.getY() / cellSize;

            if (e.getButton() == 1) {
                if ((logX == CanvasPanel.TOOLS_WIDTH) && (logY < CanvasPanel.elements.length)) {
                    selectedToolIndex = (logY + 1);
                }

                if ((logX == CanvasPanel.TOOLS_WIDTH + 1) && (logY < CanvasPanel.elements.length)) {
                    selectedToolIndex = (logY + 10);
                    System.out.println("Index = " + selectedToolIndex);
                }

                if ((logX == CanvasPanel.TOOLS_WIDTH + 2) && (logY < CanvasPanel.elements.length)) {
                    selectedToolIndex = (logY + 61);
                    System.out.println("Index = " + selectedToolIndex);
                }

                if ((logX < BOARD_WIDTH) && (logY < BOARD_HEIGHT)) {

                    if (selectedToolIndex == 7) {
                        board[logX][logY] = 70;
                        isFrozen[logX][logY] = false;
                        isRotatable[logX][logY] = false;

                    }else
                    if (selectedToolIndex == 11) {
                        System.out.println("blue / green");
                        board[logX][logY] = 11120;
                    }else
                    if (selectedToolIndex == 12) {
                        board[logX][logY] = 11130;
                        System.out.println("blue / red");
                    }else
                    if (selectedToolIndex == 13) {
                        board[logX][logY] = 11230;
                        System.out.println("green / reb");
                    }else
                    if (selectedToolIndex == 14) {
                        board[logX][logY] = 510;
                        System.out.println("blue valve");
                    }else
                    if (selectedToolIndex == 15) {
                        board[logX][logY] = 520;
                        System.out.println("green valve");
                    }else
                    if (selectedToolIndex == 16) {
                        board[logX][logY] = 530;
                        System.out.println("red valve");
                    }else
                    if (selectedToolIndex == 61) {
                        board[logX][logY] = 610;
                        System.out.println("blue container ");
                    }else
                    if (selectedToolIndex == 62) {
                        board[logX][logY] = 620;
                        System.out.println("green container ");
                    }else
                    if (selectedToolIndex == 63) {
                        board[logX][logY] = 630;
                        System.out.println("pink container ");
                    }else
                    if (selectedToolIndex == 64) {
                        isFrozen[logX][logY] = true;
                        System.out.println("metal frozen");
                    }else
                    if (selectedToolIndex == 65) {

                        board[logX][logY] = 90;
                        System.out.println("empty block");
                    }
                    else
                    if (selectedToolIndex == 66) {

                        board[logX][logY] = 10;
                        isRotatable[logX][logY] = true;
                        System.out.println("Rotatable pipe");


                    }
                    else
                    if (selectedToolIndex == 67) {

                        board[logX][logY] = 20;
                        isRotatable[logX][logY] = true;
                        System.out.println("Rotatable angle");


                    }else
                    if (selectedToolIndex == 7) {
                        board[logX][logY] = 70;
                        isFrozen[logX][logY] = false;

                    } else {
                        board[logX][logY] = (selectedToolIndex * 10);
                        System.out.println(logX + " " + logY + " : " + e.getClickCount() + " el = " + board[logX][logY]);
                    }
                }
            }


            if ((e.getButton() == 2) || (e.getButton() == 3)) {
                if ((logX < BOARD_WIDTH) && (logY < BOARD_HEIGHT)) {
                    int element = board[logX][logY] / 10;
                    int rotation = board[logX][logY] % 10;
                    if (element < 10) {
                        rotation = (rotation + 1) % elements_unique_rotations[(element - 1)];
                    }else {
                        if (element == 10){
                            rotation = (rotation + 1) % elements_unique_rotations2[(0)];
                        }
                        if (element/ 100 == 11) {
                            rotation = (rotation + 1) % elements_unique_rotations2[(1)];
                         //   System.out.println(rotation);
                        }
                        if (element == 51 || element == 52 || element == 53) {
                            rotation = (rotation + 1) % elements_unique_rotations2[(4)];
                           // System.out.println(rotation);
                        }
                        if (element == 61 || element == 62 || element == 63) {
                            rotation = (rotation + 1) % elements_unique_rotations3[(0)];
                          //  System.out.println(rotation);
                        }
                    }
                    board[logX][logY] = (10 * element + rotation);
                   // System.out.println(logX + " " + logY + " : " + e.getClickCount() + " num = " + board[logX][logY]);
                  //  System.out.println(" el = " + element + " un_rot = " + elements_unique_rotations[(element - 1)]);
                }
            }


            //System.out.println("repaint...");
            repaint();
        }

        public void mousePressed(MouseEvent e) {
        }

        public void mouseReleased(MouseEvent e) {
        }

        public void mouseEntered(MouseEvent e) {
        }

        public void mouseExited(MouseEvent e) {
        }
    }


}
