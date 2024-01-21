package BugJumpApplication;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import acm.graphics.GImage;
import acm.graphics.GObject;

public class LevelCreator extends GraphicsPane {
	
	private MainApplication program;
	private Dimension dimension;
	
	
	private ArrayList<GImage> terrain;
	private ArrayList<Integer> keyList;

	private int[] coords = new int[2];
	private GParagraph coordText;
	private GImage currImage;
	
	public LevelCreator(MainApplication e) {
		program = e;

	}

	@Override
	public void showContents() {
		dimension = Toolkit.getDefaultToolkit().getScreenSize();
		// TODO Auto-generated method stub
		
		coordText = new GParagraph("(-,-)", 0, 50);
		coordText.setFont("Arial-Bold-Italic-30");
		coordText.setColor(Color.black);
		program.add(coordText);
		
	}

	@Override
	public void hideContents() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void performAction(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

	
	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		coords[0] = e.getX(); coords[1] = e.getY();
		coordText.setText("( " + coords[0] + " , " + coords[1] + " )");
	}
	
	
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		System.out.println("This is mosue pressed");
		GObject obj = program.getElementAt(e.getX(), e.getY());
		if(obj != null && obj.getClass() == GImage.class) {
			currImage = (GImage) obj;
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
		System.out.println("This is mouse clicked");
//		GObject obj = program.getElementAt(e.getX(), e.getY());
//		if(obj != null) {
//			
//		}
		
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
//		int key = e.getKeyCode();
//		
//		System.out.println(key);
//		GImage img = new GImage(program.getImage("Images/grass.png"), 500, 500);
//		terrain.add(img);
		
		
	}
}
