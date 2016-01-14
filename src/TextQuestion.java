import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

/**************************************************************************************************
*  Programmer: Geoff Miller z1644162
*  Due Date: 11/13/13
*  Assignment 4 part 2
*  Course: CSCI 680
*  
*  Class: TextQuestion
* 
*  Notes: BONUS OPTIONS COMPLETED
*  This inherits from Question and adds text specific support.
***************************************************************************************************/
public class TextQuestion extends Question{
    //DATA MEMBERS
    //PUBLIC
    //PRIVATE
    private Font thisFont;
    //PROTECTED
      
    /**************************************************
     * DEFAULT CONSTRUCTOR
     * 
     * Notes:
     * Calls superclass constructor passing it question 
     * and answer.
     **************************************************/
    public TextQuestion(String question, String answer){
        super(question, answer);
        thisFont = new Font("TimesRoman", Font.BOLD, 20);
    }
    
    /**************************************************
     * draw
     * 
     * Notes:
     * Set font
     * Get FontMetrics for font
     * Compute x and y coordinates at which to place string
     * Use g.drawString() to draw question string at that location
     **************************************************/
    public void draw(Graphics g, Dimension d) {
        g.setFont(thisFont);
        g.drawString(super.getQuestion(), 10, 25);
    }
}
