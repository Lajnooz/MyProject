import javax.swing.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.util.Timer;
import java.util.TimerTask;

public class MainWindow extends JFrame implements KeyListener{

	private Timer timer;
	
	private Image img;
	private Image imgPlayer;
	private BufferedImage bf;
	private Graphics gTemp;
	
	private float img1;
	private float img2;
	private float img3;
	
	private playerOne pOne;
	private boolean up;
	private boolean right;
	private boolean left;
	private boolean down;
	
	public void gameLoop(){
		timer = new Timer();
		timer.schedule(new theGameLoop(), 0, 1000 / 60);
	}
	
	private class theGameLoop extends TimerTask{
		
		public void run(){
			try {
				update();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	static Container con;
	
	MainWindow() throws InterruptedException, FileNotFoundException{
		
		//Initiate UI-Element
		//Create game object
		img1 = 0.0f;
		img2 = -1080.0f;
		img3 = -2160.0f;
		
		up = false;
		right = false;
		left = false;
		down = false;
		
		drawMap();
		
		con = getContentPane();
		
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		int width = gd.getDisplayMode().getWidth();
		int height = gd.getDisplayMode().getHeight();
		
		pOne = new playerOne((width/2)-102, height-102, 102);
		
		setTitle("#C:GAMING#");
		setSize(width,height);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		addKeyListener(this);
		setFocusable(true);
		
		setExtendedState(JFrame.MAXIMIZED_BOTH); 
		setUndecorated(true);
		
		img = Toolkit.getDefaultToolkit().createImage("img/pic.png");
		imgPlayer = Toolkit.getDefaultToolkit().createImage("img/xwing_2.png");
		
		//Build up UI design/layout
		
		//Add to container object
		setVisible(true);
		
		//isRunning = true;
		gameLoop();
	}

	public void paint(Graphics g) {

		bf = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
		gTemp = bf.getGraphics();
		
		gTemp.drawImage(img, 0, (int)img1, this);
		gTemp.drawImage(img, 0, (int)img2, this);
		gTemp.drawImage(img, 0, (int)img3, this);
		
		gTemp.drawImage(imgPlayer, (int)pOne.xPos, (int)pOne.yPos, this);

		g.drawImage(bf, 0, 0, this);
    }
	
	public void update() throws InterruptedException{
		
		img1+=2;
		img2+=2;
		img3+=2;
		
		if(img1 > 1079){
			img1 = -2160.0f;
        }else if(img2 > 1079){
        	img2 = -2160.0f;
        }else if(img3 > 1079){
        	img3 = -2160.0f;
        }
		
		if(up && pOne.yPos >= 0){
			pOne.yPos = pOne.yPos-=5f;
		}if(right && pOne.xPos < (1920 - pOne.size)){
			pOne.xPos = pOne.xPos+=5f;
		}if(left && pOne.xPos >= 0){
			pOne.xPos = pOne.xPos-=5f;
		}if(down && pOne.yPos < (1080 - pOne.size)){
			pOne.yPos = pOne.yPos+=5f;
		}
		
		render();
		//check game if gameover?
		//Colistioncheck
	}
	
	public void render(){
		this.repaint();
	}
	
	public void drawMap(){
	}
	
	public void clearMap(){
	}
	
	public void keyPressed(KeyEvent e){
		
		if(e.getKeyCode() == KeyEvent.VK_UP){
			up = true;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_RIGHT){
			right = true;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_LEFT){
			left = true;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_DOWN){
			down = true;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
			System.exit(0);
		}
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_UP){
			up = false;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_RIGHT){
			right = false;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_LEFT){
			left = false;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_DOWN){
			down = false;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}
}
