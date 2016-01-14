import java.awt.Dimension;
import java.awt.Graphics;

/**************************************************************************************************
 * Programmer: Geoff Miller z1644162
 * Due Date: 11/13/13
 * Assignment 4 part 2
 * Course: CSCI 680
 * 
 * Class: Question
 * 
 * Notes: BONUS OPTIONS COMPLETED
 * This establishes the base-type for a question.
 ***************************************************************************************************/
public abstract class Question {
    // DATA MEMBERS
    // PUBLIC
    // PRIVATE
    private String question, answer, usersAnswer;
    private int score;

    // PROTECTED

    /**************************************************
     * DEFAULT CONSTRUCTOR
     * 
     * Notes:
     * Default constructor, does nothing
     **************************************************/
    public Question() {

    }

    /**************************************************
     * CONSTRUCTOR
     * 
     * Notes:
     * Set data members to parameters
     **************************************************/
    public Question(String inQuestion, String inAnswer) {
        question = inQuestion;
        answer = inAnswer;
    }

    /**************************************************
     * 
     * 
     * Notes:
     * 
     **************************************************/
    public abstract void draw(Graphics g, Dimension d);

    /**************************************************
     * judgeAnswer
     * 
     * Notes:
     * Set score to 1 if answers match, otherwise 0
     **************************************************/
    public void judgeAnswer(String inAnswer) {
        System.out.println("Q: " + question);
        System.out.println("A:" + answer + ".");
        System.out.println("U:" + inAnswer + ".");
        if (answer.equalsIgnoreCase(inAnswer)){
            score = 1;
            System.out.println("R: correct");
        }
        else{
            score = 0;
            System.out.println("R: incorrect");
        }
        usersAnswer = inAnswer;
    }

    /**************************************************
     * getQuestion
     * 
     * Notes:
     * getter for question member
     **************************************************/
    public String getQuestion() {
        return question;
    }

    /**************************************************
     * getAnswer
     * 
     * Notes:
     * getter for answer member
     **************************************************/
    public String getAnswer() {
        return answer;
    }

    /**************************************************
     * getScore
     * 
     * Notes:
     * getter for score member
     **************************************************/
    public int getScore() {
        return score;
    }
    
    /**************************************************
     * getUsersAnswer
     * 
     * Notes:
     * getter for usersAnswer member
     **************************************************/
    public String getUsersAnswer() {
        return usersAnswer;
    }
}
