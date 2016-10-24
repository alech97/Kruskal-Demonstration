package tree;

import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.InputStream;
import java.util.Scanner;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JPanel;
import java.awt.SystemColor;
import javax.swing.JCheckBox;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JSpinner;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.Font;

import javax.swing.SpinnerNumberModel;

@SuppressWarnings("serial")
public class Window extends JComponent{

    private static int xBound, yBound;
    private JFrame frame;
    private JLayeredPane contentPane;
    private JCheckBox chckbxDrawLines;
    private NodeManager nM;
    private static Dimension boardSize;
    private static LineManager lM;
    private static JLabel lblDistance, lblRuntime;
    private static Window window;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    window = new Window();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public Window() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        //Initializations------------------------------------------------------
        frame = new JFrame();
        frame.setBackground(Color.WHITE);
        contentPane = new JLayeredPane();
        xBound = 936;
        yBound = 669;
        Dimension d = new Dimension(xBound, yBound);
        nM = new NodeManager();
        lM = new LineManager(nM.getNodeList());
        boardSize = new Dimension(xBound, yBound - 50);
        nM.setSize(boardSize);
        lM.setSize(boardSize);

        //ContentPane
        contentPane.setBackground(Color.WHITE);
        contentPane.setOpaque(true);
        contentPane.setPreferredSize(d);
        contentPane.add(nM);
        contentPane.add(lM);


        //Frame----------------------------------------------------------------
        frame.setContentPane(contentPane);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Minimum Spanning Tree");
        frame.getContentPane().setVisible(true);
        frame.setLocationByPlatform(true);

        //Bar Panel------------------------------------------------------------

        //JPanel
        JPanel barPanel = new JPanel();
        contentPane.setLayer(barPanel, 1);
        barPanel.setBackground(SystemColor.activeCaptionBorder);
        barPanel.setBounds(0, 618, xBound, 51);
        contentPane.add(barPanel);
        barPanel.setLayout(null);

        //Draw Lines Check box
        chckbxDrawLines = new JCheckBox("Show Lines");
        chckbxDrawLines.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                updateGraphics();
            }
        });
        chckbxDrawLines.setHorizontalAlignment(SwingConstants.CENTER);
        chckbxDrawLines.setBounds(510, 11, 104, 23);
        barPanel.add(chckbxDrawLines);

        //Distance Label
        lblDistance = new JLabel("Distance: ");
        lblDistance.setBackground(Color.WHITE);
        lblDistance.setBounds(783, 11, 131, 14);
        barPanel.add(lblDistance);

        //Number of Points spinner
        JSpinner spinner = new JSpinner();
        spinner.setModel(new SpinnerNumberModel(0, 0, 1000, 1));
        spinner.setBounds(166, 12, 46, 20);
        barPanel.add(spinner);
        spinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if ((int) spinner.getValue() < 0) {
                    spinner.setValue((Object) 0);
                }
                nM.setNodes((int) spinner.getValue());
                updateGraphics();
            }
        });

        //Clear Button
        JButton btnClear = new JButton("Clear");
        btnClear.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent arg0) {
                nM.clear();
                spinner.setValue((Object) 0);
            }
        });
        btnClear.setBounds(222, 13, 75, 19);
        barPanel.add(btnClear);

        JLabel lblNumberOfPoints = new JLabel("Number of points:");
        lblNumberOfPoints.setBounds(44, 15, 112, 14);
        barPanel.add(lblNumberOfPoints);

        lblRuntime = new JLabel("Run time:");
        lblRuntime.setBounds(783, 26, 131, 14);
        barPanel.add(lblRuntime);

        //Welcome Panel--------------------------------------------------------

        //Panel
        JPanel welcomePanel = new JPanel();
        contentPane.setLayer(welcomePanel, 2);
        welcomePanel.setBounds(0, 0, 936, 669);
        contentPane.add(welcomePanel);
        welcomePanel.setLayout(null);

        //Button
        JButton btnClose = new JButton("Close");
        btnClose.setFont(new Font("Tahoma", Font.PLAIN, 13));
        btnClose.setBounds(422, 607, 94, 36);
        btnClose.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent arg0) {
                welcomePanel.setVisible(false);
            }
        });
        welcomePanel.add(btnClose);

        //Text File
        String welcomeString;
        try {
            InputStream stream = Window.class.getResourceAsStream("WelcomeText.txt");
            Scanner scan = new Scanner(stream);
            welcomeString = scan.useDelimiter("\\Z").next();
            scan.close();
            
        } catch (Exception e1) {
            welcomeString = "The text file for this page did not load properly.";
            e1.printStackTrace();
        }
        
        //Text Label
        JLabel lblNewLabel = new JLabel(welcomeString);
        lblNewLabel.setBackground(Color.WHITE);
        lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        lblNewLabel.setVerticalAlignment(SwingConstants.TOP);
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setBounds(0, 23, 938, 644);
        welcomePanel.add(lblNewLabel);
        
        //Kruskal GIF 
        JLabel lblNewLabel_1 = new JLabel(new ImageIcon(Window.class.getResource("KruskalGIF.gif")));
        lblNewLabel_1.setBounds(99, 299, 781, 297);
        welcomePanel.add(lblNewLabel_1);

        //Final size adjustments-----------------------------------------------
        frame.pack();
    }

    /**
     * Update Distance and Run time check boxes.
     */
    public void updateGraphics() {
        if (chckbxDrawLines.isSelected()) {
            lM.setShow(true);
            lblDistance.setText("Distance: "+ lM.getDistance());
            String runTime = String.format("%1.4f", lM.getRunTime());
            lblRuntime.setText("Run time: " + runTime + "ms");
        }
        else {
            lM.setShow(false);
            lblDistance.setText("Distance: ");
            lblRuntime.setText("Run time: ");
        }
        lM.update(nM.getNodeList());
        repaint();
    }

    /**
     * This method returns the window for Mouse
     * @return
     */
    public static Window getWindow() {
        return window;
    }
    
    /**
     * Returns the NodeManager for Mouse
     * @return NodeManager being used by Window
     */
    public NodeManager getNM() {
        return nM;
    }
    
    /**
     * Returns the LineManager for NodeManager
     * @return The lineManager being used by Window
     */
    public static LineManager getLM() {
        return lM;
    }

    /**
     * Returns the x boundary of the board.
     * @return X Boundary of board
     */
    public static int getXBound() {
        return xBound;
    }

    /**
     * Returns the y boundary of the board.
     * @return Y Boundary of board
     */
    public static int getYBound() {
        return yBound;
    }

    /**
     * Returns the Distance label for NodeManager
     * @return Distance label of bar.
     */
    public static JLabel getLblDistance() {
        return lblDistance;
    }
}
