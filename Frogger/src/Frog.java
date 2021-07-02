public class Frog{
	private int x, y, w, h, s, sCounter,l,numOfGoals,goalsReached; //All parameters for the Frog class
	private Logs attached = null; //Log object variable to make player move with the log
	
	Frog(int x, int y, int w, int h, int s,int l, int numOfGoals, int goalsReached){
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.s = s;
		this.l = l;
		this.sCounter = 0; //Counter to make player have a delay every time it moves. 
		this.numOfGoals = numOfGoals;
		this.goalsReached = goalsReached;
	}
	
	public int getX() { //Returning all getter methods
		return x;
	}
	
	public int getY() {
		return y;
	}

	public int getW() {
		return w;
	}
	
	public int getH() {
		return h;
	}
	
	public int getS() {
		return s;
	}
	
	public int getL() {
		return l;
	}
	
	public int numOfGoals() {
		return numOfGoals;
	}
	
	public int goalsReached() {
		return goalsReached;
	}
	
	public int getSCounter() {
		sCounter++; //Starts at 0 and increases by 1 every game loop and returns the value
		return sCounter;
	}
	
	public void attach(Logs log) {
		attached = log; //Attached is equal to the current log the frog is on when checking for collision in the main 
		
		if (attached != null) { 
			x += attached.getLogS(); //The player now moves with the log, depending on the speed of the log
		}
	}
		
	public void playerMove(int xdir, int ydir) {	
		x = (x + 25)/50 * 50; //When a player gets of a log, there is a high chance that the player x will no longer be a multiple of 50(0,50,100,150,etc..) 
		y = (y + 25)/50 * 50; //Player x needs to be a multiple of 50 since the game is based on a 50x50 grid system
							  //This makes sure player x is always a multiple of 50
		x += xdir * 50; //Moves the player based on the input (Ex: (1,0), player moves right because 1 * 50 direction for x, and 0 * 50 direction for y)
		y += ydir * 50;
	}
	
	public boolean collides(int x, int y, int w, int h) {
		return this.x < x + w && this.x + this.w > x && this.y < y + h && this.y + this.h > y;  //Boolean method used to check for collision with player and																			
	}																							//any object in the game
}
