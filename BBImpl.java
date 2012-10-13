import java.awt.*;
import javax.swing.*;
import wiiremotej.*;
import wiiremotej.event.*;
import javax.sound.sampled.*;
import java.io.*;

/**
 * Implements BalanceBoardListener and acts as a general test class. Note that you can ignore the main method pretty much,
 * as it mostly has to do with the graphs and GUIs.
 * At the very end though, there's an example of how to connect to a balance board.
 * @author Michael Diamond
 * @version 2/26/09
 */

public class BBImpl extends BalanceBoardAdapter
{   
    private BalanceBoard board;
    private static JFrame graphFrame;
    private static JPanel graph;
    private static double massX;
    private static double massY;
    
    public static void main(String args[])
    {
        //basic console logging options...
        WiiRemoteJ.setConsoleLoggingAll();
        //WiiRemoteJ.setConsoleLoggingOff();
        
        try
        {
            graphFrame = new JFrame();
            graphFrame.setTitle("Mass Graph");
            graphFrame.setSize(800, 600);
            graphFrame.setResizable(false);
            
            graph = new JPanel()
            {
                public void paintComponent(Graphics graphics)
                {
                    graphics.clearRect(0, 0, 800, 600);
                    graphics.fillRect(0, 0, 800, 600);
                    graphics.setColor(Color.WHITE);
                    graphics.drawLine(0, 300, 800, 300);
                    graphics.drawLine(400, 0, 400, 600);
                    
                    graphics.setColor(Color.RED);
                    graphics.fillOval((int)(massX * 800+400-15), (int)(massY * 600+300-15), 30, 30);
                }
            };
            
            graph.setDoubleBuffered(true);
            graphFrame.add(graph);
            graphFrame.setVisible(true);
            
            //Find and connect to a Balance Board
            BalanceBoard board = null;
            
            while (board == null) {
                try {
                    board = WiiRemoteJ.findBalanceBoard();
                }
                catch(Exception e) {
                    board = null;
                    e.printStackTrace();
                    System.out.println("Failed to connect board. Trying again.");
                }
            }
            
            board.addBalanceBoardListener(new BBImpl(board));
            board.setLEDIlluminated(true);
            
            final BalanceBoard boardF = board;
            Runtime.getRuntime().addShutdownHook(new Thread(new Runnable(){public void run(){boardF.disconnect();}}));
        }
        catch(Exception e){e.printStackTrace();}
    }
    
    public BBImpl(BalanceBoard board)
    {
        this.board = board;
    }
    
    public void disconnected()
    {
        System.out.println("Board disconnected... Please Wii again.");
        System.exit(0);
    }

    
    public void massInputReceived(BBMassEvent evt)
    {
    	double massTL = evt.getMass(MassConstants.TOP, MassConstants.LEFT);
    	double massTR = evt.getMass(MassConstants.TOP, MassConstants.RIGHT);
    	double massBL = evt.getMass(MassConstants.BOTTOM, MassConstants.LEFT);
    	double massBR = evt.getMass(MassConstants.BOTTOM, MassConstants.RIGHT);
    	
    	if (evt.getTotalMass() > 3) { // if mass is too small, don't bother
	    	double topMass = massTL + massTR;
	    	double bottomMass = massBL + massBR;
	    	double leftMass = massTL + massBL;
	    	double rightMass = massTR + massBR;
	    	
	    	double vertRange = topMass + bottomMass;
	    	double horizRange = rightMass + leftMass;
	    	
	    	massX = (rightMass-leftMass)/horizRange;
	    	massY = -(topMass-bottomMass)/vertRange;
    	}
    	else {
    		massX = 0;
    		massY = 0;
    	}
    	
    	graph.repaint();
    }
    
    public void buttonInputReceived(BBButtonEvent evt) {
    	if (evt.wasPressed()) {
    		System.out.println("Disconnecting! Please Wii again.");
    		System.exit(0);
    	}
    }
    
    
}