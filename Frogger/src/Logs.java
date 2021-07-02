public class Logs{
	private int x, y, w, h, s; //All parameters for the Logs class

	Logs(int x, int y, int w, int h, int s){
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.s = s;
	}
	
	public int getLogX() { //Returning all getter methods
		return x;
	}
	
	public int getLogY() {
		return y;
	}
	
	public int getLogW() {
		return w;
	}
	
	public int getLogH() {
		return h;
	}
	
	public int getLogS() {
		return s;
	}
		
	public void logMove(int xdir) { //Moving the logs by its parameter and speed
		x += xdir * s;
		
		if (s > 0 && x > 800) { //Transporting the log to its original position after it reached the end. Continuously does this while the game has not ended
			x = -h - 100;
		}
		
		else if (s < 0 && x < -h - 100) {
			x = 800;
		}
	}
}

