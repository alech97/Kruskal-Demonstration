package tree;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Kruskal's MST for Underground cables
 * I had to comment out stuff to understand this one.
 * @author Alec Helyar
 * @version 2016.09.12
 */
public class Kruskal {
    private static int[][] pointArray;
    private List<Line> finalLines = new ArrayList<Line>();
    private List<NodeGraphic> nodes;

    /**
     * Edge objects represent an edge between two points given as integers
     * @author Alec Helyar
     * @version 2016.09.12
     */
    public static class Edge
    {
        int a, b;
        double length;
        /**
         * Constructor for Edge object.
         * An Edge object represents a line between two points.
         * @param a An index in points[][] representing the first point
         * @param b An index in points[][] representing the second point
         * @param length Length of the edge
         */
        public Edge(int a, int b, double length)
        {
            this.a = a;
            this.b = b;
            this.length = length;
        }
        
        /**
         * This method is used to return point a's coordinates
         * @return Returns a point object representing point a
         */
        public Point getPointOne() {
            return new Point(pointArray[a][0], pointArray[a][1]);
        }
        
        /**
         * This method is used to return point a's coordinates
         * @return Returns a point object representing point b
         */
        public Point getPointTwo() {
            return new Point(pointArray[b][0], pointArray[b][1]);
        }
    }

    /**
     * Main execution method
     * @param nodes The list of points to be evaluated.
     * @return An array containing the runtime in 0 and the total distance in 1
     */
    public double[] calculate(List<NodeGraphic> nodes) {
        //Return double[]
        double[] ret = new double[2];
        
        //Time calculation
        long start = System.nanoTime();
        
        //Input
        this.nodes = nodes;
        int n = nodes.size();
        pointArray = new int[n][2];
        for (int i = 0; i < n; i++)
        {
            pointArray[i][0] = nodes.get(i).getXCenter();
            pointArray[i][1] = nodes.get(i).getYCenter();
        }
        
        //If no nodes
        if (n == 0)
        {
            ret[0] = (double) System.nanoTime() - start;
            ret[1] = 0;
            return ret;
        }
        
        //Sort edges by distance
        PriorityQueue<Edge> sortEdge = new PriorityQueue<Edge>(n*(n-1)/2, new Comparator<Edge>() {
            public int compare(Edge e1, Edge e2)
            {
                return (int) Math.signum(e1.length - e2.length);
            }
        });
        for (int i = 0; i < n - 1; i++)
        {
            for (int g = i + 1; g < n; g++)
            {
                sortEdge.add(new Edge(i, g, distance(i, g)));
            }
        }
        
        //Return double[] ret containing the time start and the total distance.
        ret[0] = (double) System.nanoTime() - start;
        ret[1] = solveMST(n, sortEdge);
        return ret;
    }

    /**
     * Quick calculation of distance between two points
     * @param p1 First point being used
     * @param p2 Second point being used
     * @return Distance between two given points
     */
    public static double distance(int p1, int p2)
    {
        return Math.sqrt(Math.pow((pointArray[p1][0] - pointArray[p2][0]),2) 
                + Math.pow((pointArray[p1][1] - pointArray[p2][1]), 2));
    }

    /**
     * Kruskal's Clustering MSP
     * @param numPoints Number of points on the tree
     * @param edges The sorted queue of edges
     * @return The total length of the MST
     */
    public double solveMST(int numPoints, PriorityQueue<Edge> edges) {
        int[] cluster = new int[numPoints];
        for (int i = 0; i < numPoints; i++)
        {
            cluster[i] = i;
        }

        double totalLength = 0;
        while (0 < edges.size())
        {
            Edge e = edges.poll();
            if (cluster[e.a] != cluster[e.b])
            {
                totalLength += e.length;
                finalLines.add(new Line(nodes.get(e.a), nodes.get(e.b)));
                int clusterB = cluster[e.b];
                for (int k = 0; k < numPoints; k++)
                {
                    if (cluster[k] == clusterB)
                    {
                        cluster[k] = cluster[e.a];
                    }
                }
            }
        }
        return totalLength;
    }

    /**
     * This method returns a subset of total edges that represents the MST.
     * @return A list containing the final subset
     */
    public List<Line> getFinalLines() {
        return finalLines;
    }
}
