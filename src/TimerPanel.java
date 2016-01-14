import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

/**************************************************************************************************
 * Programmer: Geoff Miller z1644162
 * Due Date: 11/13/13
 * Assignment 4 part 2
 * Course: CSCI 680
 * 
 * Class: TimerPanel
 * 
 * Notes:
 * This class is for the quiz timer. It starts the timer and displays the countdown. At the end
 * of the countdown it stops the quiz by calling on another class's method.
 ***************************************************************************************************/
public class TimerPanel extends JPanel implements ActionListener {

    // DATA MEMBERS
    // PUBLIC
    // PRIVATE
    public JTextField txtClockTime;
    private Timer timer;
    private QuizPanel parent;
    private int clockSecsRemain;

    // PROTECTED

    /**************************************************
     * CONSTRUCTOR
     * 
     * Notes:
     * Stores link to parent
     * Adds clock time text field to layout
     * Makes clock time text field uneditable
     * Initializes timer
     **************************************************/
    public TimerPanel(QuizPanel inParent) {
        // Stores link to parent
        parent = inParent;
        // Adds clock time text field to layout
        txtClockTime = new JTextField(3);
        add(txtClockTime);
        // Makes clock time text field uneditable
        txtClockTime.setEnabled(false);
        // initialize timer
        timer = new Timer(1000, this);
    }

    /**************************************************
     * actionPerformed
     * 
     * Notes:
     * Decrements clock seconds remaining by 1
     * If remaining seconds is <= 0, call stopQuiz().
     * Otherwise, update the clock time text field with
     * the remaining seconds
     **************************************************/
    @Override
    public void actionPerformed(ActionEvent e) {
        String seconds = getUpdateTxtClockTime(clockSecsRemain);
        txtClockTime.setText(seconds);
        if(clockSecsRemain-- <= 0) stopQuiz();
    }

    /**************************************************
     * startTimer
     * 
     * Notes:
     * Multiple the duration in minutes by 60 to get the
     * clock seconds remaining. Update the clock time
     * text field with the remaining seconds start the
     * timer.
     **************************************************/
    public void startTimer(int duration) {
        duration *= 60;
        clockSecsRemain = duration;
        timer.start();
    }

    /**************************************************
     * stopQuiz
     * 
     * Notes:
     * Call stopTimer()
     * Call parent.endQuiz()
     **************************************************/
    public void stopQuiz() {
        stopTimer();
        parent.toggleControls();
        parent.endQuiz();
    }

    /**************************************************
     * stopTimer
     * 
     * Notes:
     * Stop the timer
     * Clear the clock time text field
     **************************************************/
    public void stopTimer() {
        timer.stop();
    }

    /**************************************************
     * getUpdateTxtClockTime
     * 
     * Notes:
     * Takes an int argument and converts it to a
     * string that shows the min and sec computation
     * of that int. Then sets the clock time txtbox
     * to that string.
     **************************************************/
    public String getUpdateTxtClockTime(int inSec) {
        String clockString;
        int mins = inSec / 60;
        int remainSecs = inSec % 60;
        clockString = Integer.toString(mins) + ":"
                + Integer.toString(remainSecs);
        clockString = String.format("%02d %02d", mins, remainSecs);
        return clockString;
    }
}