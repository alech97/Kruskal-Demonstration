package tree;

import java.awt.*;

import javax.swing.*;
@SuppressWarnings("serial")
public class Line extends JComponent{
    private int x1, y1, x2, y2;
    
    public void draw(Graphics g)
    {
        g.drawLine(x1, y1, x2, y2);
    }
    
    public Line(NodeGraphic n1, NodeGraphic n2)
    {
        this.x1 = n1.getXCenter();
        this.x2 = n2.getXCenter();
        this.y1 = n1.getYCenter();
        this.y2 = n2.getYCenter();
    }
}
