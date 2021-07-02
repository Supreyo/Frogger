public class Vehicles{
	private int x, y, w, h, s; //All parameters for the log class

	Vehicles(int x, int y, int w, int h, int s){
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.s = s;
	}
	
	public int getEnemyX() { //Returning all getter methods
		return x;
	}
	
	public int getEnemyY() {
		return y;
	}
	
	public int getEnemyW() {
		return w;
	}
	
	public int getEnemyH() {
		return h;
	}
	
	public int getEnemyS() {
		return s;
	}
		
	public void carMove(int xdir) { //Moving the cars by its parameter and speed
		x += xdir * s;
		
		if (s > 0 && x > 800) { //Transporting the car to its original position after it reached the end. Continuously does this while the game has not ended
			x = -h - 50;
		}
		
		else if (s < 0 && x < -h - 50) {
			x = 800;
		}
	}
}

