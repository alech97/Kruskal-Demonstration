package tree;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.List;
import javax.swing.event.MouseInputAdapter;

/**
 * This class handles mouse movements regarding nodes
 * @author Alec Helyar
 * @version 2016.09.12
 */
public class Mouse extends MouseInputAdapter implements MouseMotionListener{
    private NodeGraphic dragging;

    /**
     * This method handles a mouse press event on a node
     */
    public void mousePressed(MouseEvent e)
    {
        NodeGraphic n = getNodeGraphic(e.getX(), e.getY());
        if (n != null) {
            dragging = n;
            dragging.setMoving(true);
            dragging.setXDiff(e.getX());
            dragging.setYDiff(e.getY());
        }
    }

    /**
     * This method handles a mouse release event on a node
     */
    public void mouseReleased(MouseEvent e)
    {
        if (dragging != null)
        {
            dragging.setMoving(false);
            dragging = null;
        }
    }

    /**
     * This method handles a mouse drag event on a node
     */
    public void mouseDragged(MouseEvent e)
    {
        if (Window.getLM().isShow()) {
            Window.getLblDistance().setText(
                    "Distance: "+Window.getLM().getDistance());
        }
        else {
            Window.getLblDistance().setText("Distance: ");
        }
        Window.getWindow().updateGraphics();
        if (dragging != null)
        {
            dragging.move(e.getX(), e.getY(), 
                    Window.getXBound(), Window.getYBound() - 50);
            Window.getLM().update(Window.getWindow().getNM().getNodeList());
        }
    }

    /**
     * This method returns a node at a certain spot, 
     * used for finding nodes under mouse clicks.
     * @param x X coordinate of location
     * @param y Y coordinate of location
     * @return Uppermost node at this spot
     */
    public NodeGraphic getNodeGraphic(int x, int y)
    {
        List<NodeGraphic> nodes = Window.getWindow().getNM().getNodeList();
        NodeGraphic g = null;
        for (int i = 0; i < nodes.size(); i++)
        {
            NodeGraphic check = nodes.get(i);
            if (check.getShape().contains(x, y)) {
                g = check;
            }
        }
        return g;
    }
}