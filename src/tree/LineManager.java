package tree;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.List;
import javax.swing.JLabel;

/**
 * This class manages the lines between points
 * @author Alec Helyar
 * @version 2016.09.12
 */
@SuppressWarnings("serial")
public class LineManager extends JLabel {
    private List<Line> finalLines;
    private Kruskal kruskal;
    private boolean show;
    private double distance;
    private double runtime;

    /**
     * Constructor for LineManger object
     * @param nodeGraphics A list of nodes being used 
     */
    public LineManager(List<NodeGraphic> nodeGraphics)
    {
        show = false;
        kruskal = new Kruskal();
        update(nodeGraphics);
        runtime = 0;
    }

    /**
     * Abstract Java swing method
     */
    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        if (show && finalLines != null) {
            g.setColor(Color.BLACK);
            if (finalLines.size() <= 100) {
                ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                        RenderingHints.VALUE_ANTIALIAS_ON);
            }
            if (finalLines.size() >= 1) {
                for (Line l : finalLines)
                {
                    l.draw(g);
                }
            }
        }
    }

    /**
     * This method is used to evaluate final subset of lines in other classes
     * @return A list of the final subset of lines
     */
    public List<Line> getLines() {
        return finalLines;
    }

    /**
     * This method handles changes to nodeGraphics
     * @param nodeGraphics A list of all nodes
     */
    public void update(List<NodeGraphic> nodeGraphics)
    {
        if (finalLines != null) {
            finalLines.clear();
        }
        if (nodeGraphics != null && nodeGraphics.size() > 1) {
            double[] step = kruskal.calculate(nodeGraphics);
            runtime = step[0] / 1000000;
            distance = step[1];
            finalLines = kruskal.getFinalLines();
        }
        repaint();
    }

    /**
     * Used for the Show lines check box
     * @return Returns true if Show Lines is selected
     */
    public boolean isShow() {
        return show;
    }

    /**
     * Sets the visibility boolean for all lines
     * @param show Whether the lines should be shown or not
     */
    public void setShow(boolean show) {
        this.show = show;
    }

    /**
     * Returns a total distance of all points
     * @return The total distance of the final subset
     */
    public int getDistance() {
        return (int) distance;
    }
    
    /**
     * Returns the runTime of the last calculation
     * @return A double in milliseconds representing the most recent runtime
     */
    public double getRunTime() {
        return runtime;
    }
}
