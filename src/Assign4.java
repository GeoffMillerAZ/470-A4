/**************************************************************************************************
*  Programmer: Geoff Miller z1644162
*  Due Date: 11/1/13
*  Assignment 4 part 1
*  Course: CSCI 680
*  
*  Class: Assign4
* 
*  Notes:  BONUS OPTIONS COMPLETED
* This class presents a JFrame that will contain two JPanels, one on the right and one on the left.
* Each of these two JPanels will contain part of the assignment (part 1 and part 2). This
* submission includes part 1 only but a placeholder has been made for part 2. Part 1 is a tile
* designer that allows the user to place some number of images into a grid in whatever manner the
* user desires in order to design a tile sequence.
***************************************************************************************************/
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;


public class Assign4 {
    //initialize the JFrame that is the body of the application.
    private JFrame mainFrame;

    /**************************************************
     *  main()
     * 
     * Notes:
     * This function is what drives execution of the
     * application. This is the starting point, so
     * to say. The main creates a new instance of the
     * application and displays it to the user.
     **************************************************/
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Assign4 window = new Assign4();
                    window.mainFrame.setVisible(true);
                    
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**************************************************
     *  DEFAULT CONSTRUCTOR
     * 
     * Notes:
     * This initializes the JFrame and then sets the
     * layout for the JFrame to allow to JPanels. Each
     * JPanel holds one part of the assignment. The
     * left panel holds assignment 1. The right JPanel
     * holds assignment 2.
     **************************************************/
    public Assign4() {
        initialize();
        GridLayout mainPageGridLayout = new GridLayout(1,2);
        JPanel mainPanel = new JPanel(mainPageGridLayout);
        TileDesigner gamePanel = new TileDesigner();
        gamePanel.setBorder(BorderFactory.createEmptyBorder(0, 10,
                10, 10));
        QuizPanel studyPanel = new QuizPanel();
        studyPanel.setBackground(Color.CYAN);
        studyPanel.setBorder(BorderFactory.createEmptyBorder(0, 10,
                10, 10));
        this.mainFrame.add(mainPanel);
        mainPanel.add(gamePanel);
        mainPanel.add(studyPanel);
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        mainFrame = new JFrame();
        mainFrame.setBounds(100, 100, 900, 500);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
