public class Time {
	private int tP,tC; //All parameters for the Time class
	
	Time(int timePos, int timeCount){
		this.tP = timePos;
		this.tC = timeCount;
	}
	
	public int getTP() { //Returning all getter methods
		return tP;
	}
	
	public int getTC() {
		tC += 1;
		return tC;
	}
	
	public void timeDecrease(int xdir) { //Decreasing the x value of the time rectangle as time passes
		tP -= xdir;
	}
}
