import javax.swing.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainWindow extends JFrame implements KeyListener{
	static Container con;
	
	private Timer timer;
	private Random rand;
	
	private int screenWidth;
	private int screenHeight;
	
	private static final int PLAYER_SIZE = 102;
	private static final float PLAYER_SPEED = 5.0f;
	private static final int ENEMY_SIZE = 102;
	private static final float BG_SPEED = 2.0f;
	private static final int MAX_ENEMIES = 10;
	
	private Image backgroundSprite;
	private Image playerSprite;
	private Image enemy1Sprite;
	private BufferedImage bi;
	private Graphics gTemp;
	
	private float bg1;
	private float bg2;
	private float bg3;
	
	private Player player;
	
	private boolean up;
	private boolean right;
	private boolean left;
	private boolean down;
	
	private int maxEnemies;
	private ArrayList<Enemy> enemies;
	private int enemyCounter;
	private Enemy tempEnemy;
	
	private Rectangle rect;
	private Rectangle enemyRect;
	private Rectangle enemyCheckRect;
	private boolean spawnSuccess;
	
	
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
	
	MainWindow() throws InterruptedException, FileNotFoundException{
		con = getContentPane();
		rand = new Random();
		
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		screenWidth = gd.getDisplayMode().getWidth();
		screenHeight = gd.getDisplayMode().getHeight();
		
		bg1 = 0.0f;
		bg2 = -screenHeight;
		bg3 = -screenHeight*2;
		
		up = false;
		right = false;
		left = false;
		down = false;
		
		player = new Player((screenWidth/2)-PLAYER_SIZE, screenHeight-PLAYER_SIZE, PLAYER_SIZE);
		enemies = new ArrayList<Enemy>();
		
		setTitle("#C:GAMING#");
		setSize(screenWidth,screenHeight);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		addKeyListener(this);
		setFocusable(true);
		
		setExtendedState(JFrame.MAXIMIZED_BOTH); 
		setUndecorated(true);
		
		backgroundSprite = Toolkit.getDefaultToolkit().createImage("img/pic.png");
		playerSprite = Toolkit.getDefaultToolkit().createImage("img/xwing_2.png");
		enemy1Sprite = Toolkit.getDefaultToolkit().createImage("img/enemy1.png");
		
		maxEnemies = MAX_ENEMIES;
		enemyCounter = 0;
		//Build up UI design/layout
		
		
		//Add to container object
		setVisible(true);
		
		gameLoop();
	}

	public void paint(Graphics g) {
		bi = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
		gTemp = bi.getGraphics();
		
		gTemp.drawImage(backgroundSprite, 0, (int)bg1, this);
		gTemp.drawImage(backgroundSprite, 0, (int)bg2, this);
		gTemp.drawImage(backgroundSprite, 0, (int)bg3, this);
		
		gTemp.drawImage(playerSprite, (int)player.getX(), (int)player.getY(), this);
		
		for(Enemy e : enemies)
			gTemp.drawImage(enemy1Sprite, (int)e.getX(), (int)e.getY(), this);
		
		g.drawImage(bi, 0, 0, this);
    }
	
	public void update() throws InterruptedException{
		drawMap();
		movePlayer();
		updateEnemies();
		randomSpawnEnemy();
		render();
	}
	
	public void render(){
		this.repaint();
	}
	
	private Enemy newEnemy(){
		tempEnemy = new Enemy(rand.nextInt(screenWidth-ENEMY_SIZE), rand.nextInt(3)+3, ENEMY_SIZE);
		
		enemyRect = new Rectangle((int)tempEnemy.getX(),(int)tempEnemy.getY(),tempEnemy.getSize(), tempEnemy.getSize());
		while(!spawnSuccess){
			spawnSuccess = true;
			for(int i=0; i<enemies.size(); i++){
				enemyCheckRect = new Rectangle((int)enemies.get(i).getX(),(int)enemies.get(i).getY(), enemies.get(i).getSize(), enemies.get(i).getSize());
				if(enemyRect.intersects(enemyCheckRect)){
					spawnSuccess = false;
					tempEnemy.setX(rand.nextInt(screenWidth-tempEnemy.getSize()));
					enemyRect = new Rectangle((int)tempEnemy.getX(),(int)tempEnemy.getY(),tempEnemy.getSize(), tempEnemy.getSize());
					break;
				}
			}
		}
		return tempEnemy;
	}
	
	public void drawMap(){
		bg1+=BG_SPEED;
		bg2+=BG_SPEED;
		bg3+=BG_SPEED;
		
		if(bg1 > screenHeight-1 ){
			bg1 = -screenHeight*2;
        }else if(bg2 > screenHeight-1){
        	bg2 = -screenHeight*2;
        }else if(bg3 > screenHeight-1){
        	bg3 = -screenHeight*2;
        }
	}
	
	public void movePlayer(){
		if(up && player.getY() >= 0){
			player.move(Player.UP, PLAYER_SPEED);
		}if(right && player.getX() < (screenWidth - player.getSize())){
			player.move(Player.RIGHT, PLAYER_SPEED);
		}if(left && player.getX() >= 0){
			player.move(Player.LEFT, PLAYER_SPEED);
		}if(down && player.getY() < (screenHeight - player.getSize())){
			player.move(Player.DOWN, PLAYER_SPEED);
		}
	}
	
	public void updateEnemies(){
		rect = new Rectangle((int)player.getX(),(int)player.getY(),(int)player.getSize(), (int)player.getSize());
		enemyRect = null;
		enemyCheckRect = null;
		spawnSuccess = false;
		
		for (int j = 0; j<enemies.size(); j++){
			spawnSuccess = false;
			enemies.get(j).move();
			
			enemyRect = new Rectangle((int)enemies.get(j).getX(),(int)enemies.get(j).getY(),enemies.get(j).getSize(), enemies.get(j).getSize());
			if(rect.intersects(enemyRect)){
				System.exit(0);
			}
			
			if(enemies.get(j).getY()> screenHeight-1){
				enemies.remove(j);
				enemies.add(newEnemy());
			}
		}
	}
	
	public void randomSpawnEnemy(){
		enemyCounter++;
		spawnSuccess = false;
		if(enemyCounter >= 100 && maxEnemies>enemies.size()){
			if(rand.nextInt(100)>=95){
				enemies.add(newEnemy());
				enemyCounter = 0;
			}
		}
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
