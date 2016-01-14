import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**************************************************************************************************
 * Programmer: Geoff Miller z1644162
 * Due Date: 11/13/13
 * Assignment 4 part 2
 * Course: CSCI 680
 * 
 * Class: QuestionPanel
 * 
 * Notes: BONUS OPTIONS COMPLETED
 * This is the panel that will actually contain the question. This Class also handles the reading
 * of the input file and starting and stopping of the quiz.
 ***************************************************************************************************/
public class QuestionPanel extends JPanel implements Runnable {
    // DATA MEMBERS
    // PUBLIC
    // PRIVATE
    private ArrayList<Question> questionList;
    private QuizPanel parent;
    private Thread runner = null;
    // Flag set to true when all questions have been answered
    private boolean isQuizDone = false;
    private int curQuestion = -1;
    // Flag for paintComponent to know when to start painting
    private boolean isFirstTime = true;
    // Flag set to true when time limit expires
    private boolean isTimeUp = false;
    private Font thisFont;

    // PROTECTED

    /**************************************************
     * DEFAULT CONSTRUCTOR
     * 
     * Notes:
     * Stores link to parent
     **************************************************/
    public QuestionPanel(QuizPanel inParent) {
        parent = inParent;
        questionList = new ArrayList<Question>();
        thisFont = new Font("TimesRoman", Font.BOLD, 16);
    }

    /**************************************************
     * startQuiz
     * 
     * Notes:
     * If runner == null, use runner to create a new
     * Thread object and start it
     **************************************************/
    public void startQuiz() {
        if (runner == null) {
            // create a new thread object
            runner = new Thread(this);
            runner.start();
        }
    }

    /**************************************************
     * setTimeIsUpFlag
     * 
     * Notes:
     * 
     **************************************************/
    public void setTimeIsUpFlag(boolean status) {
        isTimeUp = status;
    }

    /**************************************************
     * paintComponent
     * 
     * Notes:
     * Call superclass version
     * Get dimensions of panel
     * If firstTime flag is true, return
     * If quizDone flag is true, call computeQuizScore() to compute final score;
     * draw a string
     * with the final score
     * Otherwise, get the current question from the the ArrayList and call
     * draw() for it.
     * Clear the answer text field.
     **************************************************/
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Dimension thisSize = getSize();
        if (isFirstTime) {
            g.setFont(thisFont);
            g.drawString("Enter a duration. Min time is 1 min. Max is 20.", 10,
                    50);
            return;
        }
        if (isQuizDone) {
            String theAnswer, usersAnswer;
            g.setFont(thisFont);
            // print final score
            g.drawString(
                    "Quiz is finished. Your total score is "
                            + Integer.toString(computeQuizScore()) + ".", 10,
                    50);
            // print answer key
            int i = 1;
            for (Question thisQ : questionList) {
                if (thisQ.getScore() > 0) {
                    g.drawString("Correct! Q" + Integer.toString(i) + " A: "
                            + thisQ.getAnswer(), 10, 50 + (i * 18));
                } else {
                    g.drawString(
                            "Wrong!   Q" + Integer.toString(i) + " A: "
                                    + thisQ.getAnswer() + " Your A: "
                                    + thisQ.getUsersAnswer(), 10, 50 + (i * 18));
                }
                i++;
            }
        } else {
            questionList.get(curQuestion).draw(g, thisSize);
        }
        parent.clearTxtAnswer();
    }

    /**************************************************
     * readQuestionFile
     * 
     * Notes:
     * Create a Scanner object and use it to read the
     * "quiz.txt" text file, creating a series of
     * TextQuestion or ImageQuestion objects and
     * adding them to the ArrayList
     **************************************************/
    public void readQuestionFile() {
        String thisLine, nextLine, ending;
        // Location of file to read
        File inFile = new File("res/questions/quiz.txt");

        try {
            Scanner scanner = new Scanner(inFile);

            while (scanner.hasNextLine()) {
                thisLine = scanner.nextLine().trim();
                nextLine = scanner.nextLine().trim().toLowerCase();
                ending = thisLine.substring(thisLine.lastIndexOf('.') + 1);
                // System.out.println(line);
                if (ending.equalsIgnoreCase("jpg")) {
                    questionList
                            .add(new ImageQuestion(thisLine, nextLine, this));
                } else {
                    questionList.add(new TextQuestion(thisLine, nextLine));
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error reading from file!",
                    "File IO Error!", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**************************************************
     * processAnswer
     * 
     * Notes:
     * Get the current question
     * Call judgeAnswer(), passing it the user's answer
     * Increment current question
     * If no more questions, set quizDone to true
     * Call repaint()
     **************************************************/
    public void processAnswer(String usersAnswer) {
        questionList.get(curQuestion++).judgeAnswer(usersAnswer);
        if (curQuestion >= questionList.size())
            isQuizDone = true;
        repaint();
    }

    /**************************************************
     * computeQuizScore
     * 
     * Notes:
     * Loop through ArrayList and add up the score for
     * each Question in the list
     * Return total
     **************************************************/
    public int computeQuizScore() {
        int tScore = 0;
        for (Question thisQ : questionList) {
            tScore += thisQ.getScore();
        }
        return tScore;
    }

    /**************************************************
     * run
     * 
     * Notes:
     * Set quizDone and timeIsUp flags to false
     * Clear the question ArrayList
     * Call readQuestionFile()
     * Increment current question
     * Set firstTime flag to false, call repaint() to
     * display first question.
     * while !timeIsUp && !doneMessage, sleep for 1
     * second.
     * Once loop ends, if !timeIsUp, call
     * parent.stopTimer().
     * Set quizDone to true, call repaint() to print
     * quiz score.
     * Set runner to null, current question to -1,
     * enable / disable controls.
     **************************************************/
    public void run() {
        isQuizDone = isTimeUp = false;
        questionList.clear();
        readQuestionFile();
        curQuestion++;
        isFirstTime = false;
        // display 1st question
        repaint();
        while (!isTimeUp && !isQuizDone) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        parent.toggleControls();
        if (!isTimeUp)
            parent.stopTimer();
        isQuizDone = true;
        repaint();
        runner = null;
        curQuestion = -1;
    }
}
