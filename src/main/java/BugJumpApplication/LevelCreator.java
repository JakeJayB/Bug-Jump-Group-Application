package BugJumpApplication;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.HashSet;


import acm.graphics.GImage;
import acm.graphics.GObject;

public class LevelCreator extends GraphicsPane {
	
	private MainApplication program;
	private Dimension dimension;
	
	
	private HashSet<GImage> terrain;
	private HashSet<GImage> enemies;
	private HashSet<GImage> weapons;
	private HashSet<GImage> collectables;

	private int[] coords = new int[2];
	private int offsetX = 0;
	private int offsetY = 0;
	
	private GParagraph coordText;
	private GParagraph resizeText;
	private GButton plusWButton;
	private GButton minusWButton;
	private GButton plusHButton;
	private GButton minusHButton;
	
	
	private GImage moveImage;
	private GImage resizeImage;
	private GImage playerImage;
	
	public LevelCreator(MainApplication e) {
		program = e;
		

	}

	@Override
	public void showContents() {
		dimension = Toolkit.getDefaultToolkit().getScreenSize();
		terrain = new HashSet<GImage>();
		weapons =  new HashSet<GImage>();
		enemies =  new HashSet<GImage>();
		collectables =  new HashSet<GImage>();
		program.setupTimer(25);
		setupGUI();
	}

	@Override
	public void hideContents() {
		program.removeAll();
		dimension = null;
		terrain = null;
		weapons = null;
		enemies = null;
		collectables = null;
		System.gc();
	}
	
	public void setupGUI() {
	
		coordText = new GParagraph("(-,-)", 0, 50);
		coordText.setFont("Arial-Bold-Italic-30");
		coordText.setColor(Color.black);
		program.add(coordText);
		
		
		GParagraph p = new GParagraph("Obj Width:", 0,  75);
		p.setFont("Arial-Bold-20");
		System.out.println();
		program.add(p);
		minusWButton = new GButton("-", 110, 80-20, 20, 20);
		program.add(minusWButton);
		plusWButton = new GButton("+", 135, 80-20, 20, 20);
		program.add(plusWButton);
		
		p = new GParagraph("Obj Height:", 0, 100);
		p.setFont("Arial-Bold-20");
		program.add(p);
		minusHButton = new GButton("-", 110, 105-20, 20, 20);
		program.add(minusHButton);
		plusHButton = new GButton("+", 135, 105-20, 20, 20);
		program.add(plusHButton);
		
		resizeText = new GParagraph("No resize obj. selected", 0, 130);
		resizeText.setFont("Alpaca-Bold-22");
		resizeText.setColor(Color.red);
		program.add(resizeText);

		playerImage = new GImage(program.getImage("Images/rightPlayer.png"), 200, 300);
		program.add(playerImage);
		
	}
	
	
	public void resetResizeImg() {
		resizeImage = null;
		resizeText.setText("No resize obj. selected");
		resizeText.setColor(Color.red);
	}
	
	
	public void deleteObj(GImage obj) {
		
		if(terrain.contains(obj)) terrain.remove(obj);
		else if(enemies.contains(obj)) enemies.remove(obj);
		else if(weapons.contains(obj)) weapons.remove(obj);
		else if(collectables.contains(obj)) collectables.remove(obj);
	}
	
	public void checkBoundries() {
		
		/*Checks the boundries of the mouse cursor. Moves screen when cursor
		 * gets closer to the edge
		 */
		int x = coords[0]; 
		if(resizeImage != null) return;
		
		
		
		if(x <= dimension.getWidth()*.1) {
			playerImage.move(10, 0);
			for(GImage img : terrain) img.move(10, 0);
			for(GImage img : weapons) img.move(10, 0);		
			for(GImage img : enemies) img.move(10, 0);
			for(GImage img : collectables) img.move(10, 0);
		}
		else if(x >= dimension.getWidth()*.9) {
			playerImage.move(-10, 0);
			for(GImage img : terrain) img.move(-10, 0);
			for(GImage img : weapons) img.move(-10, 0);		
			for(GImage img : enemies) img.move(-10, 0);
			for(GImage img : collectables)  img.move(-10, 0);
		}
		else if(x <= dimension.getWidth()*.2) {
			playerImage.move(5, 0);
			for(GImage img : terrain) img.move(5, 0);
			for(GImage img : weapons) img.move(5, 0);		
			for(GImage img : enemies) img.move(5, 0);
			for(GImage img : collectables) img.move(5, 0);
		}
		else if(x >= dimension.getWidth()*.8) {
			playerImage.move(-5, 0);
			for(GImage img : terrain) img.move(-5, 0);
			for(GImage img : weapons) img.move(-5, 0);		
			for(GImage img : enemies) img.move(-5, 0);
			for(GImage img : collectables) img.move(-5, 0);
		}
	}

	@Override
	public void performAction(ActionEvent e) {
		checkBoundries();
		
		coordText.sendToFront();
		minusWButton.sendToFront();
		plusWButton.sendToFront();
		minusHButton.sendToFront();
		plusHButton.sendToFront();
		resizeText.sendToFront();
	}

	
	@Override
	public void mouseMoved(MouseEvent e) {
		coords[0] = e.getX(); coords[1] = e.getY();
		coordText.setText("( " + coords[0] + " , " + coords[1] + " )");
	}
	
	
	@Override
	public void mousePressed(MouseEvent e) {
		System.out.println("This is mouse pressed");
		GObject obj = program.getElementAt(e.getX(), e.getY());
		if(obj == null) { return;}
		
		if(obj instanceof GImage) {
			moveImage = (GImage) obj;
			offsetX = (int) (e.getX() - moveImage.getX());
			offsetY = (int) (e.getY() - moveImage.getY());
		}
		
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		
		System.out.println("This is mouse clicked");
		GObject obj = program.getElementAt(e.getX(), e.getY());
		if(obj == null) return;
		
			
		if(obj instanceof GImage && obj != playerImage) {
			resizeImage = (GImage) obj;
			resizeText.setText("Resize obj. selected");
			resizeText.setColor(Color.green);
		}
		else if (obj instanceof GButton && resizeImage != null) {
			obj = (GButton) obj;
			System.out.println("Gbutton selected");
			if(obj == minusWButton) {
				resizeImage.setSize(resizeImage.getWidth()-20, resizeImage.getHeight());
			}
			else if (obj == plusWButton) {
				resizeImage.setSize(resizeImage.getWidth()+20, resizeImage.getHeight());
			}
			else if(obj == minusHButton) {
				resizeImage.setSize(resizeImage.getWidth(), resizeImage.getHeight()-20);
			}
			else if(obj == plusHButton) {
				resizeImage.setSize(resizeImage.getWidth(), resizeImage.getHeight()+20);
			}
			
		}
	}
	
	
	@Override
	public void mouseDragged(MouseEvent e) {
		coords[0] = e.getX(); coords[1] = e.getY();
		coordText.setText("( " + coords[0] + " , " + coords[1] + " )");

		resetResizeImg();
		
		if(moveImage != null) {
			
			int x = (int) e.getX() - offsetX;
			int y = (int) e.getY() - offsetY;
			moveImage.setLocation(x, y);
		}
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		moveImage = null;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		int x = coords[0] % (int) dimension.getWidth();
		int y = coords[1] % (int) dimension.getHeight();
		
		System.out.println("Dimension: " + x + " " + y);
		System.out.println("Key: " + key);
		
		GImage img;
		switch (key) {
			case 8:
				if(resizeImage == null) return;
				program.remove(resizeImage);
				deleteObj(resizeImage);
				resetResizeImg();
				break;
			case 49: // spawn grass terrain
				img = new GImage(program.getImage("Images/grass.png"), x, y);
				img.setSize(100, 50);
				program.add(img);
				terrain.add(img);
				break;
			case 50: // spawn star terrain 
				img = new GImage(program.getImage("Images/star.png"), x, y);
				collectables.add(img);
				program.add(img);	
				break;
			case 51: // spawn heart collectable
				img = new GImage(program.getImage("Images/heart.png"), x, y);
				collectables.add(img);
				program.add(img);
				break;
			case 52: // spawn gun weapon
				img = new GImage(program.getImage("Images/handheld.png"), x, y);
				weapons.add(img);
				program.add(img);
				break;
			case 53: // spawn sword weapon
				img = new GImage(program.getImage("Images/melee.png"), x, y);
				weapons.add(img);
				program.add(img);
				break;
			case 54: // spawn beetle enemy
				img = new GImage(program.getImage("Images/rightBeetle.png"), x, y);
				enemies.add(img);
				program.add(img);
				break;
			case 55: // spawn flower enemy
				img = new GImage(program.getImage("Images/sunflower.png"), x, y);
				enemies.add(img);
				program.add(img);
				break;
			case 56: // spawn spider enemy
				img = new GImage(program.getImage("Images/spider.png"), x, y);
				enemies.add(img);
				program.add(img);
				break;
			case 57: // spawn worm enemy
				img = new GImage(program.getImage("Images/rightWorm.png"), x, y);
				enemies.add(img);
				program.add(img);
				break;
			case 48: // spawn cheese collectable
				img = new GImage(program.getImage("Images/cheese.png"), x, y);
				collectables.add(img);
				program.add(img);
				break;
			default:
				break;
		}
		
	}
}
