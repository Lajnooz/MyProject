
public class Enemy {
	private float x, y, dy;
	private int size;
	
	public Enemy(float x, float dy, int size){
		this.x = x;
		this.dy = dy;
		y = -size;
		this.size = size;
	}
	
	public void move(){
		y += dy;
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

	public float getDy() {
		return dy;
	}

	public void setDy(float dy) {
		this.dy = dy;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
}
