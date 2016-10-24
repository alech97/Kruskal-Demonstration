package tree;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Ellipse2D;

import javax.swing.*;
@SuppressWarnings("serial")
/**
 * This class manages the NodeGraphic Shape that is drawn for each node
 * @author Alec Helyar
 * @version 2016.09.12
 */
public class NodeGraphic extends JComponent{

    private int x, y, num, radius, innerRadius, xDiff, yDiff;
    private String xString, yString;
    private Ellipse2D outerRing;
    private boolean moving;

    /**
     * This method handles the look of each Node.
     * @param g Abstract Graphics object passed in.
     */
    public void draw(Graphics g)
    {
        //Initializations
        radius = 25;
        innerRadius = 2;
        xString = "("+x;
        yString = y+")";
        g.setFont(new Font("Century Gothic", Font.PLAIN, 9));
        FontMetrics fontMetrics = ((Graphics2D) g).getFontMetrics();

        //Outer Circle
        g.setColor(Color.WHITE);
        g.fillOval(x - radius, y - radius, 2 * radius, 2 * radius);
        g.setColor(Color.BLUE);
        outerRing = new Ellipse2D.Double(x - radius, y - radius, 2 * radius, 2 * radius);

        ((Graphics2D) g).draw(outerRing);

        //Inner Dot
        g.fillOval(x - innerRadius, y - innerRadius, 2 * innerRadius, 2 * innerRadius);

        //Coordinate Strings
        g.drawString(xString, x - 21, y + 10);
        g.drawString(yString, x + 22 - fontMetrics.stringWidth(yString), y + 10);
        repaint();

        //Number String
        g.setFont(new Font("Century Gothic", Font.BOLD, 15));
        fontMetrics = ((Graphics2D) g).getFontMetrics();
        g.drawString(num + "", x - (fontMetrics.stringWidth(num + "") / 2), y - 6);
    }

    /**
     * Constructor for NodeGraphic
     * @param x X-Coordinate for node
     * @param y Y-Coordinate for node
     * @param num The index of this node in the final list.
     */
    public NodeGraphic(int x, int y, int num)
    {
        this.x = x;
        this.y = y;
        this.num = num;
        repaint();
    }

    /**
     * This method moves the node to another spot
     * @param xM The x-coordinate of the Mouse
     * @param yM The y-coordinate of the Mouse
     * @param xBound The x-boundary of the node board
     * @param yBound The y-boundary of the node board
     */
    public void move(int xM, int yM, int xBound, int yBound)
    {
        if (moving) {
            x = inRegion(xM + xDiff, xBound - 1);
            y = inRegion(yM + yDiff, yBound - 2);
        }
        repaint();
    }

    /**
     * This method tests whether a node is being moved to a valid location.
     * @param mCoord The coordinate of the mouse
     * @param maxBound The maximum boundary of the mode
     * @return Returns a correct coordinate; returns mCoord if already valid.
     */
    public int inRegion(int mCoord, int maxBound)
    {
        if (mCoord - radius < 0) {
            return radius;
        }
        else if (mCoord + radius > maxBound) {
            return maxBound - radius;
        }
        else {
            return mCoord;
        }
    }

    /**
     * This method returns the Ellipse2D shape of the outer ring of this node
     * @return Ellipse2D shape of the outer ring
     */
    public Ellipse2D getShape() {
        return outerRing;
    }

    /**
     * Used to calculate the x of this node
     * @return X-coordinate of this node
     */
    public int getXCenter() {
        return x;
    }

    /**
     * Used to calculate the y of this node
     * @return Y-coordinate of this node
     */
    public int getYCenter() {
        return y;
    }

    /**
     * Used to set the node to being dragged by the mouse
     * @param moving Whether the node is moving or not
     */
    public void setMoving(boolean moving)
    {
        this.moving = moving;
    }

    /**
     * Returns XDiff of this object
     * @return XDiff of this node
     */
    public int getXDiff() {
        return xDiff;
    }

    /**
     * Sets the xDiff for this object using x
     * @param xM x-coordinate of Mouse
     */
    public void setXDiff(int xM) {
        xDiff = x - xM;
    }

    /**
     * Returns YDiff of this object
     * @return YDiff of this node
     */
    public int getYDiff() {
        return yDiff;
    }

    /**
     * Sets the uDiff for this object using y
     * @param yM y-coordinate of Mouse
     */
    public void setYDiff(int yM) {
        yDiff = y - yM;
    }

    /**
     * This method is used to get the coordinates of this node
     * @return Coordinates of this node in the form of a Point
     */
    public Point getPoint() {
        return new Point(x, y);
    }
}
