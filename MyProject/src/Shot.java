
public class Shot {

	private float x;
	private float y;
	
	Shot(float x, float y){
		this.x = x;  
		this.y = y;
	}
	
	public void move(float speed){
		this.y = y-=speed;
	}
	
	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}
}
