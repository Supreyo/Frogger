public class Spikes{
	private int x, y, w, h; //All parameters for the Spikes class
	
	Spikes(int x, int y, int w, int h){
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}
	
	public int getSpikeX() { //Returning all getter methods
		return x;
	}
	
	public int getSpikeY() {
		return y;
	}

	public int getSpikeW() {
		return w;
	}
	
	public int getSpikeH() {
		return h;
	}
}
