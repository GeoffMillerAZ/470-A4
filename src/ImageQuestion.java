import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.ImageObserver;

/**************************************************************************************************
*  Programmer: Geoff Miller z1644162
*  Due Date: 11/13/13
*  Assignment 4 part 2
*  Course: CSCI 680
*  
*  Class: ImageQuestion
* 
*  Notes: BONUS OPTIONS COMPLETED
*  This inherits from Question and adds image specific support.
***************************************************************************************************/
public class ImageQuestion extends Question{
    //DATA MEMBERS
    //PUBLIC
    //PRIVATE
    private ImageObserver observer;
    private Image questionImage;
    //PROTECTED
      
    /**************************************************
     * DEFAULT CONSTRUCTOR
     * 
     * Notes:
     * Call superclass constructor passing it question 
     * and answer.
     * Set observer data member to parameter
     * Read image from file name contained in the 
     * string question.
     **************************************************/
    public ImageQuestion(String question, String answer, ImageObserver inObserver){
        super(question, answer);
        observer = inObserver;
        questionImage = (Image) Toolkit.getDefaultToolkit().getImage(question);
    }
    
    /**************************************************
     * draw
     * 
     * Notes:
     * Call getWidth() and getHeight() for questionImage
     * Compute x and y coordinates at which to place image
     * Use g.drawImage() to draw questionImage at that location
     **************************************************/
    public void draw(Graphics g, Dimension d){
        g.drawImage(questionImage,10,10,observer);
    }
}
