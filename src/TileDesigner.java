/**************************************************************************************************
 *  Programmer: Geoff Miller z1644162
 *  Due Date: 11/1/13
 *  Assignment 4 part 1
 *  Course: CSCI 680
 *  
 *  Class: TitleDesigner
 * 
 *  Notes:
 * This class handles the design of the tile and manipulation with the controls to the designer.
 * The user selects the desired tile to place and places it in the grid. I have added extra features
 * to allow the user to easily delete a tile by right clicking the tile. I have also made it easy for
 * the user to cycle through tile designs by using the mouse scroll wheel instead of needing to click
 * the tile button. The reset button clears the tile design to allow the user to start over at any
 * point. There is no default tile selected, so the user will need to click a tile before anything
 * useful can be done.
 ***************************************************************************************************/
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.Line2D;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JToolBar;

public class TileDesigner extends JPanel {
    // the size of each tile
    private final int tileSize = 25;
    // the size of the grid
    private final int canvasSize = 5;
    // the next two coordiantes are for the upper right of the grid that is
    // drawn
    private int zeroX;
    private int zeroY;
    // holds all lines to be drawn in the grid
    private ArrayList<Line2D> lineList;
    // the gameplay area
    private JPanel gameArea;
    // the toolbar for the tile game
    private JToolBar gameToolBar;
    // toolbar buttons
    private JButton gameTlBrButton1;
    private JButton gameTlBrButton2;
    private JButton gameTlBrButton3;
    private JButton gameTlBrButton4;
    private JButton gameTlBrButton5;
    // reset button
    private JButton resetButton;
    // initialize current selected tile to -1 to indicate nothing selected
    private int curSelctTile = -1;
    // to store file paths for each image
    private final String patternImagePaths[] = new String[canvasSize];
    // to store each image after it has been loaded
    private final Image patternImages[] = new Image[canvasSize];
    // to store the desired state of the canvas at all times
    private int canvasState[][] = new int[canvasSize][canvasSize];
    private JLabel selectedTileTypeText;
    private JLabel selectedTileTypeIcon;

    /**************************************************
     * DEFAULT CONSTRUCTOR
     * 
     * Notes:
     * Instantiates all important GUI elements, assigns
     * them to event handlers and defines the repain
     * method.
     **************************************************/
    public TileDesigner() {
        // initialize linelist
        lineList = new ArrayList<Line2D>();

        // populate parternImages to image paths
        patternImagePaths[0] = "res/images/patterns/pat1.gif";
        patternImagePaths[1] = "res/images/patterns/pat2.gif";
        patternImagePaths[2] = "res/images/patterns/pat3.gif";
        patternImagePaths[3] = "res/images/patterns/pat4.gif";
        patternImagePaths[4] = "res/images/patterns/pat5.gif";

        // populate canvasState to all blank and populate images from image
        // paths
        for (int i = 0; i < canvasSize; i++) {
            patternImages[i] = (Image) Toolkit.getDefaultToolkit().getImage(
                    patternImagePaths[i]);
            for (int j = 0; j < canvasSize; j++) {
                canvasState[i][j] = -1;
            }
        }

        this.setLayout(new BorderLayout());
        int width = 250;
        int height = 250;
        this.setMinimumSize(new Dimension(width, height));
        this.setSize(width, height);
        this.setBackground(Color.WHITE);

        // instanciate buttons for the toolbar and put icons on them
        gameTlBrButton1 = new JButton("", new ImageIcon(patternImages[0]));
        gameTlBrButton2 = new JButton("", new ImageIcon(patternImages[1]));
        gameTlBrButton3 = new JButton("", new ImageIcon(patternImages[2]));
        gameTlBrButton4 = new JButton("", new ImageIcon(patternImages[3]));
        gameTlBrButton5 = new JButton("", new ImageIcon(patternImages[4]));

        // add toolbar event handlers (for each button)
        toolbarButtonEventHandlers();

        // populate the toolbar
        gameToolBar = new JToolBar();
        gameToolBar.add(gameTlBrButton1);
        gameToolBar.add(gameTlBrButton2);
        gameToolBar.add(gameTlBrButton3);
        gameToolBar.add(gameTlBrButton4);
        gameToolBar.add(gameTlBrButton5);
        this.add(gameToolBar, BorderLayout.NORTH);

        // create a new instance of the TileDesigner game
        gameArea = new JPanel() {
            /**************************************************
             * paintComponent()
             * 
             * Arguments:
             * Graphics - a Graphics object
             * 
             * Notes:
             * This event is triggered automatically by the
             * application anytime the size or other property
             * of the application is changed. This redraws all
             * GUI/graphics and allows the graphics to display
             * properly to account for changes no matter what
             * size or shape the window is. The draws are made
             * to the Graphics object.
             **************************************************/
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                // declare a 2d graphics object
                Graphics2D g2 = (Graphics2D) g;

                double cellsFromCenter = (double) (canvasSize / 2);
                if (canvasSize % 2 == 1) {
                    cellsFromCenter += 0.5;
                }

                // compute center points
                double centerX = (float) this.getSize().width / 2;
                double centerY = (float) this.getSize().height / 2;
                double firstX = centerX - (double) (tileSize * cellsFromCenter);
                double firstY = centerY - (double) (tileSize * cellsFromCenter);
                double sixthX = centerX + (double) (tileSize * cellsFromCenter);
                double sixthY = centerY + (double) (tileSize * cellsFromCenter);

                zeroX = (int) firstX;
                zeroY = (int) firstY;

                // clear old lines
                lineList.clear();
                // compute new lines
                for (int i = 0; i < canvasSize + 1; i++) {
                    lineList.add(new Line2D.Double(firstX
                            + (double) (tileSize * i), firstY, firstX
                            + (double) (tileSize * i), sixthY));
                    lineList.add(new Line2D.Double(firstX, firstY
                            + (double) (tileSize * i), sixthX, firstY
                            + (double) (tileSize * i)));
                }
                // draw all lines
                for (Line2D line : lineList) {
                    g2.draw(line);
                }

                // draw all currently positioned tiles
                for (int row = 0; row < canvasSize; row++) {
                    for (int col = 0, x = 0; col < canvasSize; col++) {
                        x = canvasState[row][col];
                        // if -1 then go to next grid space
                        if (x == -1)
                            continue;
                        // draw this grid space's tile in the grid
                        g.drawImage(patternImages[x],
                                (int) (firstX + (double) (col * tileSize)),
                                (int) (firstY + (double) (row * tileSize)),
                                this);
                    }
                }
            }
        };
        Box box1 = Box.createHorizontalBox();
        box1.setAlignmentX(LEFT_ALIGNMENT);

        // add a reset button to the screen
        resetButton = new JButton("Reset");
        // add instructions to the screen
        box1.add(new JTextArea(
                "Left click a button in the toolbar to select a tile type.\n"
                        + "Left click gride to lay selected tile at desired grid point.\n"
                        + "Right click grid to erase a tile at desired grid point.\n"
                        + "Mouse Wheel Up/Down to toggle cycle tile type."));
        box1.add(resetButton);
        add(box1, BorderLayout.SOUTH);
        // add canvas event handlers
        canvasEventHandlers();
        // add labels to show user what tile is selected with icon
        selectedTileTypeText = new JLabel("Current tile type:");
        selectedTileTypeIcon = new JLabel();
        gameArea.add(selectedTileTypeText);
        gameArea.add(selectedTileTypeIcon);
        add(gameArea, BorderLayout.CENTER);
    }

    /**************************************************
     * toolbarButtonEventHandlers()
     * 
     * Notes:
     * This creates event handlers for each button
     * in the toolbar.
     **************************************************/
    public void toolbarButtonEventHandlers() {
        /**************************************************
         * EVENT HANDLER - gameTlBrButton1 - mouse clicked
         * 
         * Notes:
         * sets the selected tile appropriately and then
         * updates the display to show which icon is
         * currently selected.
         **************************************************/
        gameTlBrButton1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                curSelctTile = 0;
                updateSelectedTileIcon();
            }
        });

        /**************************************************
         * EVENT HANDLER - gameTlBrButton2 - mouse clicked
         * 
         * Notes:
         * sets the selected tile appropriately and then
         * updates the display to show which icon is
         * currently selected.
         **************************************************/
        gameTlBrButton2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                curSelctTile = 1;
                updateSelectedTileIcon();
            }
        });

        /**************************************************
         * EVENT HANDLER - gameTlBrButton3 - mouse clicked
         * 
         * Notes:
         * sets the selected tile appropriately and then
         * updates the display to show which icon is
         * currently selected.
         **************************************************/
        gameTlBrButton3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                curSelctTile = 2;
                updateSelectedTileIcon();
            }
        });

        /**************************************************
         * EVENT HANDLER - gameTlBrButton4 - mouse clicked
         * 
         * Notes:
         * sets the selected tile appropriately and then
         * updates the display to show which icon is
         * currently selected.
         **************************************************/
        gameTlBrButton4.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                curSelctTile = 3;
                updateSelectedTileIcon();
            }
        });

        /**************************************************
         * EVENT HANDLER - gameTlBrButton5 - mouse clicked
         * 
         * Notes:
         * sets the selected tile appropriately and then
         * updates the display to show which icon is
         * currently selected.
         **************************************************/
        gameTlBrButton5.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                curSelctTile = 4;
                updateSelectedTileIcon();
            }
        });
    }

    /**************************************************
     * canvasEventHandlers()
     * 
     * Notes:
     * Adds the reset button event handler and the
     * event handler for mouse actions like left and
     * right click and mousewheel on the canvas.
     **************************************************/
    public void canvasEventHandlers() {
        resetButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                for (int i = 0; i < canvasSize; i++) {
                    for (int j = 0; j < canvasSize; j++) {
                        canvasState[i][j] = -1;
                    }
                }
                gameArea.repaint();
            }
        });

        /**************************************************
         * EVENT HANDLER - LEFT/RIGHT Click on canvas
         * 
         * Notes:
         * This computes the mouse position in relation to
         * the grid and then computes the row and column
         * that the click was in. It makes sure the click
         * was in bounds. If it was a left click, it adds
         * the currently selected tile to the grid at the
         * desired location. If it is a right click it
         * removes the tile from that right click location.
         **************************************************/
        gameArea.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // calc the offset of the mouse XY from the top left
                // corner of the grid
                int mouseX = e.getX() - zeroX;
                int mouseY = e.getY() - zeroY;
                // establish the bounds of the grid
                int maxSize = tileSize * canvasSize;
                // determine which row of the grid the mouse is in
                int col = mouseX / tileSize;
                int row = mouseY / tileSize;

                // left click - add a tile to the grid
                // but only if the click was in range and
                // a valid tile option has been selected
                if (mouseX > 0 && mouseY > 0 && mouseX < maxSize
                        && mouseY < maxSize && curSelctTile > -1
                        && !e.isMetaDown()) {
                    canvasState[row][col] = curSelctTile;
                    gameArea.repaint();
                }

                // right click - remove a tile from the grid
                // but only if the click was in range and
                // a valid tile option has been selected
                if (mouseX > 0 && mouseY > 0 && mouseX < maxSize
                        && mouseY < maxSize && curSelctTile > -1
                        && e.isMetaDown()) {
                    canvasState[row][col] = -1;
                    gameArea.repaint();
                }

            }
        });

        /**************************************************
         * EVENT HANDLER - mousewheel
         * 
         * Notes:
         * This feature cycles through the available tiles
         * to make it easier for the user to change through
         * tiles to make designing go faster for the user.
         **************************************************/
        gameArea.addMouseWheelListener(new MouseAdapter() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                int notches = e.getWheelRotation();
                if (curSelctTile > -1) {
                    // wheel up action
                    if (notches < 0) {
                        if (curSelctTile == 4) {
                            curSelctTile = 0;
                        } else {
                            curSelctTile++;
                        }
                    } else { // wheel down action
                        if (curSelctTile == 0) {
                            curSelctTile = 4;
                        } else {
                            curSelctTile--;
                        }
                    }
                }
                updateSelectedTileIcon();
            }
        });
    }

    /**************************************************
     * updateSelectedTileIcon()
     * 
     * Notes:
     * This updates the tile selection label and icon.
     **************************************************/
    private void updateSelectedTileIcon() {
        selectedTileTypeIcon
                .setIcon(new ImageIcon(patternImages[curSelctTile]));
        gameArea.repaint();
    }
}
