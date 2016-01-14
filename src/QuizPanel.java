import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**************************************************************************************************
 * Programmer: Geoff Miller z1644162
 * Due Date: 11/13/13
 * Assignment 4 part 2
 * Course: CSCI 680
 * 
 * Class: QuizPanel
 * 
 * Notes:
 * Builds the layout
 * Adds action listeners for the two buttons
 * Disables the answer controls
 ***************************************************************************************************/
public class QuizPanel extends JPanel implements ActionListener {
    // DATA MEMBERS
    // PUBLIC
    // PRIVATE
    private JTextField txtDuration;
    private JTextField txtAnswer;
    private JLabel lblDuration;
    private JLabel lblAnswer;
    private JButton btnStart;
    private JButton btnSubmit;
    private TimerPanel timerPanel = new TimerPanel(this);
    private QuestionPanel questionPanel = new QuestionPanel(this);
    private JPanel northPanel;
    private JPanel southPanel;

    // PROTECTED

    /**************************************************
     * DEFAULT CONSTRUCTOR
     * 
     * Notes:
     * Builds the layout
     * Adds action listeners for the two buttons
     * Disables the answer controls
     **************************************************/
    public QuizPanel() {
        this.setLayout(new BorderLayout());
        // int width = 250;
        // int height = 250;
        // this.setMinimumSize(new Dimension(width, height));
        // this.setSize(width, height);

        buildLayout();
        // add action listeners to buttons
        btnStart.addActionListener(this);
        btnSubmit.addActionListener(this);
    }

    /**************************************************
     * actionPerformed
     * 
     * Notes:
     * If the action event is produced by the start button
     * ---Gets the duration from the duration text field,
     * ---converts to int.
     * ---Calls timerPanel.startTimer(), passing it
     * ---the duration
     * ---Calls questionPanel.startQuiz() to begin the
     * ---quiz
     * Else if the action event is produced by the
     * submit button
     * ---Gets the answer from the answer text field,
     * ---trims off white space
     * ---Calls questionPanel.processAnswer(), passing
     * ---it the trimmed answer
     * ---Clears the text in the answer text field
     **************************************************/
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnStart) {
            boolean isInputGood = true;
            int minsDesired = 0;
            try {
                minsDesired = Integer.parseInt(txtDuration.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null,
                        "The number you entered is invalid. Integers only please!",
                        "Duration Input Error! Please try again.",
                        JOptionPane.INFORMATION_MESSAGE);
                ex.printStackTrace();
                isInputGood = false;
            }
            if((minsDesired < 1 || minsDesired > 20) && isInputGood){
                JOptionPane.showMessageDialog(null,
                        "The number you entered is invalid. Must be between 1 and 20 min!",
                        "Duration Input Error! Please try again.",
                        JOptionPane.INFORMATION_MESSAGE);
            }
            else if (isInputGood) {
                timerPanel.startTimer(minsDesired);
                questionPanel.startQuiz();
                toggleControls();
                timerPanel.startTimer(minsDesired);
            }
        } else if (e.getSource() == btnSubmit) {
            String cleanAnswer = txtAnswer.getText();
            cleanAnswer = cleanAnswer.trim();
            if(cleanAnswer.equalsIgnoreCase("")){
                JOptionPane.showMessageDialog(null,
                        "You must enter an answer before submitting!",
                        "Answer submission error! Please try again.",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
            questionPanel.processAnswer(cleanAnswer);
            txtAnswer.setText("");
            }
            txtAnswer.requestFocus();
        }
    }

    /**************************************************
     * endQuiz
     * 
     * Notes:
     * Calls set method in questionPanel to set the
     * timeIsUp flag to true
     **************************************************/
    public void endQuiz() {
        toggleControls();

        questionPanel.setTimeIsUpFlag(true);
    }

    /**************************************************
     * stopTimer
     * 
     * Notes:
     * Calls timerPanel.stopTimer()
     **************************************************/
    public void stopTimer() {
        timerPanel.stopTimer();
    }

    /**************************************************
     * buildLayout
     * 
     * Notes:
     * builds the layout of the QuizPanel
     **************************************************/
    public void buildLayout() {
        FlowLayout flowLay = new FlowLayout();
        // setup north panel
        northPanel = new JPanel(flowLay);
        lblDuration = new JLabel("Total Duration (mins) ");
        txtDuration = new JTextField(8);
        btnStart = new JButton("Start");
        northPanel.add(lblDuration);
        northPanel.add(txtDuration);
        northPanel.add(btnStart);
        northPanel.add(timerPanel);
        // setup south panel
        southPanel = new JPanel(flowLay);
        lblAnswer = new JLabel("Your answer: ");
        txtAnswer = new JTextField(20);
        btnSubmit = new JButton("Submit");
        southPanel.add(lblAnswer);
        southPanel.add(txtAnswer);
        southPanel.add(btnSubmit);
        // setup center panel
        questionPanel = new QuestionPanel(this);
        // add north, south & center panels to QuizPanel
        add(northPanel, BorderLayout.NORTH);
        add(southPanel, BorderLayout.SOUTH);
        add(questionPanel, BorderLayout.CENTER);
        txtAnswer.setEnabled(false);
        btnSubmit.setEnabled(false);
    }

    /**************************************************
     * clearTxtAnswer
     * 
     * Notes:
     * clears the answer field
     **************************************************/
    public void clearTxtAnswer() {
        txtAnswer.setText("");
    }

    /**************************************************
     * toggleControls
     * 
     * Notes:
     * toggles if the controls are enabled or disabled
     **************************************************/
    public void toggleControls() {
        btnStart.setEnabled(!btnStart.isEnabled());
        txtDuration.setEnabled(!txtDuration.isEnabled());

        btnSubmit.setEnabled(!btnSubmit.isEnabled());
        txtAnswer.setEnabled(!txtAnswer.isEnabled());
    }
}
