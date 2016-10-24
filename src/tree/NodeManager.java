package tree;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.JLabel;

/**
 * This class manages the nodes and the list that contains them.
 * @author Alec Helyar
 * @version 2016.09.12
 */
@SuppressWarnings("serial")
public class NodeManager extends JLabel {
    private List<NodeGraphic> nodes = new ArrayList<NodeGraphic>();
    private Random random;
    Mouse m = new Mouse();

    /**
     * Default constructor
     */
    public NodeManager()
    {
        addMouseListener(m);
        addMouseMotionListener(m);
        random = new Random();
        
    }

    /**
     * This method sets the number of nodes in the list
     * @param num Number of nodes in the list
     */
    public void setNodes(int num) {
        int n = nodes.size();
        if (num > n) {
            add(num - n);
        }
        else if (num < n) {
            remove(n - num);
        }
        repaint();
    }

    /**
     * This method removes a certain number of nodes
     * @param numRemove Number of nodes to remove
     */
    public void remove(int numRemove)
    {
        int n = nodes.size() - 1;
        int goal = nodes.size() - numRemove;
        while (n >= goal) {
            nodes.remove(n);
            n--;
        }
    }

    /**
     * This method adds a number of nodes
     * @param numAdd Number of nodes to add
     */
    public void add(int numAdd)
    {
        int[][] spot = findSpots(numAdd);
        for (int i = 0; i < spot.length; i++) {
            NodeGraphic nG = new NodeGraphic(spot[i][0], spot[i][1], nodes.size() + 1);
            nodes.add(nG);
            nG.addMouseListener(m);
            nG.addMouseMotionListener(m);
        }
    }

    /**
     * This method clears the list and updates the front-end
     */
    public void clear() {
        nodes.clear();
        Window.getLM().update(nodes);
        Window.getWindow().updateGraphics();
        repaint();
    }

    /**
     * Abstract swing method
     */
    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        //Anti-aliasing
        if (nodes.size() <= 100) {
            ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                    RenderingHints.VALUE_ANTIALIAS_ON);
        }
        for (NodeGraphic o : nodes)
        {
            o.draw(g);
        }
    }

    /**
     * Returns the list of nodes
     * @return A list of all nodes
     */
    public List<NodeGraphic> getNodeList() {
        return nodes;
    }

    /**
     * This method generates a spot for a node to be placed randomly
     * @return An array containing an x and y coordinate
     */
    public int[] generateSpot() {
        int[] ret = new int[2];
        ret[0] = random.nextInt(Window.getXBound() - 50) + 25;
        ret[1] = random.nextInt(Window.getYBound() - 100) + 25;
        return ret;
    }

    /**
     * This method finds a number of random spots for nodes
     * @param n Number of spots needed
     * @return An array[n][2] containing n spots of x and y coordinates.
     */
    public int[][] findSpots(int n) {
        int[][] ret = new int[n][2];
        for (int i = 0; i < n; i++)
        {
            ret[i] = generateSpot();
        }
        return ret;
    }
}