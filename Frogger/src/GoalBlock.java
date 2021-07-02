public class GoalBlock{
	private int x, y, w, h; //All parameters for the GoalBlock class
	private boolean r; //Boolean method used to check if the player has reached this block or not
	
	GoalBlock(int x, int y, int w, int h, boolean reached){
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.r = reached;
	}
	
	public int getGoalX() {  //Returning all getter methods
		return x;
	}
	
	public int getGoalY() {
		return y;
	}

	public int getGoalW() {
		return w;
	}
	
	public int getGoalH() {
		return h;
	}
	
	public boolean reached() {
		return r;
	}
}
