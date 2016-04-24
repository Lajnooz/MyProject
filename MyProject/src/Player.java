
public class Player {

	private float x;
	private float y;
	
	private float size;
	
	public static final int UP = 0;
	public static final int RIGHT = 1;
	public static final int LEFT = 2;
	public static final int DOWN = 3;
	
	Player(float x, float y, float size){
		this.x = x;  
		this.y = y;
		
		this.size = size;
	}

	public void move(int dir, float speed){
		if(dir == UP)
			y-=speed;
		else if (dir == RIGHT)
			x+=speed;
		else if (dir == LEFT)
			x-=speed;
		else if (dir == DOWN)
			y+=speed;
	}
	
	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getSize() {
		return size;
	}

	public void setSize(float size) {
		this.size = size;
	}
}
