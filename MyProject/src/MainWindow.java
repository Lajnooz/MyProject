import javax.swing.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileNotFoundException;
import java.util.Timer;
import java.util.TimerTask;

public class MainWindow extends JFrame implements KeyListener{

	private Timer timer;
	private Image img;
	private float img1;
	private float img2;
	private float img3;
	
	public void gameLoop(){
		timer = new Timer();
		timer.schedule(new theGameLoop(), 0, 1000 / 15);
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
		
		drawMap();
		
		con = getContentPane();
		
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		int width = gd.getDisplayMode().getWidth();
		int height = gd.getDisplayMode().getHeight();
		
		setTitle("#C:GAMING#");
		setSize(width,height);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		addKeyListener(this);
		setFocusable(true);
		
		img = Toolkit.getDefaultToolkit().createImage("img/pic.png");
		
		//Build up UI design/layout
		
		//Add to container object
		setVisible(true);
		
		//isRunning = true;
		gameLoop();
	}

	public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(img, 0, (int)img1, this);
        g.drawImage(img, 0, (int)img2, this);
        g.drawImage(img, 0, (int)img3, this);
        
    }
	
	public void update() throws InterruptedException{
		
		img1+=10;
		img2+=10;
		img3+=10;
		
		if(img1 > 1079){
			img1 = -2160.0f;
        }else if(img2 > 1079){
        	img2 = -2160.0f;
        }else if(img3 > 1079){
        	img3 = -2160.0f;
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
			render();
		}
		
		if(e.getKeyCode() == KeyEvent.VK_RIGHT){
			render();
		}
		
		if(e.getKeyCode() == KeyEvent.VK_LEFT){
			render();
		}
		
		if(e.getKeyCode() == KeyEvent.VK_DOWN){
			render();
		}
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}
}
