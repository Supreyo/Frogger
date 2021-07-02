import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.*;
import javax.swing.*;
import javax.swing.border.Border;

import java.awt.MouseInfo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

//=================================================================================================================================================================================
//=================================================================================================================================================================================

public class Frogger extends JFrame implements ActionListener {
	GamePanel Game;
	Timer myTimer;
	
    public Frogger() {
		super("Frogger");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(768,800);
		setLayout(new BorderLayout());
		
		Game = new GamePanel();
		add(Game);

		setVisible(true);
		
		myTimer = new Timer(10,this); 
		myTimer.start();
    }
    
    //=====================================================================================================================================================
  	//=====================================================================================================================================================

    public void actionPerformed(ActionEvent evt) {
    	Object source = evt.getSource();
    	if (source == myTimer) {
    		Game.repaint();
    		Game.playerMove();
    		Game.enemyMove();
    		Game.collisionCheck();
    		Game.time();
    		Game.gameEnd();
    	}
    }
    
    //=====================================================================================================================================================
  	//=====================================================================================================================================================

    public static void main(String[] arguments) throws FileNotFoundException, JavaLayerException{
		Frogger frame = new Frogger();
		
		while (true) {
			FileInputStream song  = new FileInputStream("C:\\Users\\Supreyo Atonu\\eclipse-workspace\\Frogger\\BackgroundMusic.mp3"); //Reading the mp3 file and playing it to 
			Player player = new Player(song);																						  //use as background music
			player.play();
		}
    }
}

//=================================================================================================================================================================================
//=================================================================================================================================================================================

class GamePanel extends JPanel implements KeyListener{
	private Font myFont = new Font("Hel",Font.BOLD,20);
	
    //=====================================================================================================================================================
  	//=====================================================================================================================================================
	
	boolean[] keys;  //Initiating all boolean variables 
	boolean onLog = false; //Boolean to check if player is on a log so the player can move with it
	boolean gameEnd = false;
	boolean gameStart = false;
	boolean infoScreen = false;
	boolean gameWon = false;
	
    //=====================================================================================================================================================
  	//=====================================================================================================================================================
	
	int score = 0; //Initiating all integer variables
	int vehicleIndex = 0;  //Variables that keep track of the vehicle, log, and goal indexes 
	int logIndex = 0;
	int goalIndex = 0;
	int numOfCars, numOfLogs, numOfGoals, numOfSpikes; //Keeps track of the number of objects in the current game
	int playerGraphicsIndex = 2; //Integer variable to keep track of the players direction and animation picture
								 //Starts at 2 because 2 is when the player is facing the up direction
	
    //=====================================================================================================================================================
  	//=====================================================================================================================================================
	
	Frog frog; //Initiating all game objects and game object lists
	Time time;
	Water water;
	Vehicles[] car; 
	Logs[] log;
	GoalBlock[] goal;
	Spikes[] spike;
	Hearts[] heart;

    //=====================================================================================================================================================
  	//=====================================================================================================================================================
																			  //Initiating all the images to be used as graphics for the game
	Image playerGraphics1, playerGraphics2, playerGraphics3, playerGraphics4; //4 player images for 4 different direction images
	Image carGraphics1, carGraphics2, carGraphics3, carGraphics4, carGraphics5, carGraphics6; //3 different car images that face right and left since cars can go in 2 directions 
	Image logGraphics,logGraphics2; //2 log images for different sized logs                     and 3 different sizes 																
	Image spikeGraphics, goalGraphics1, goalGraphics2; //1 goal image indicates that player hasn't reached it yet while the other indicates player has reached it
	Image heartGraphics;
	Image wallPaper, info, gameWonScreen, gameLostScreen;  //All the different screens that are being used in the game
	
	String gameEndStatus = ""; //A string to show what caused the game to end
	
    //=====================================================================================================================================================
  	//=====================================================================================================================================================
	
	public GamePanel(){	    
		keys = new boolean[KeyEvent.KEY_LAST+1];
		
	    //=================================================================================================================================================
	  	//=================================================================================================================================================
		
		spikeGraphics = new ImageIcon("C:\\Users\\Supreyo Atonu\\eclipse-workspace\\Frogger\\Spikes.png").getImage();   //Getting all images and resizing 
		spikeGraphics = spikeGraphics.getScaledInstance(50,50,0);
		
		goalGraphics1 = new ImageIcon("C:\\Users\\Supreyo Atonu\\eclipse-workspace\\Frogger\\Lillypad.png").getImage();
		goalGraphics1 = goalGraphics1.getScaledInstance(40,40,0);
		
		goalGraphics2 = new ImageIcon("C:\\Users\\Supreyo Atonu\\eclipse-workspace\\Frogger\\Lillypad2.png").getImage();
		goalGraphics2 = goalGraphics2.getScaledInstance(40,40,0);
		
		heartGraphics = new ImageIcon("C:\\Users\\Supreyo Atonu\\eclipse-workspace\\Frogger\\Lives.png").getImage();
		heartGraphics = heartGraphics.getScaledInstance(25,30,0);
		
		//=================================================================================================================================================
	  	//=================================================================================================================================================

		carGraphics1 = new ImageIcon("C:\\Users\\Supreyo Atonu\\eclipse-workspace\\Frogger\\RaceCar1.png").getImage(); 
		carGraphics1 = carGraphics1.getScaledInstance(50,50,0);
		
		carGraphics2 = new ImageIcon("C:\\Users\\Supreyo Atonu\\eclipse-workspace\\Frogger\\RaceCar2.png").getImage();
		carGraphics2 = carGraphics2.getScaledInstance(50,50,0);
		
		carGraphics3 = new ImageIcon("C:\\Users\\Supreyo Atonu\\eclipse-workspace\\Frogger\\Truck2.png").getImage();
		carGraphics3 = carGraphics3.getScaledInstance(100,50,0);
		
		carGraphics4 = new ImageIcon("C:\\Users\\Supreyo Atonu\\eclipse-workspace\\Frogger\\Truck1.png").getImage();
		carGraphics4 = carGraphics4.getScaledInstance(100,50,0);
		
		carGraphics5 = new ImageIcon("C:\\Users\\Supreyo Atonu\\eclipse-workspace\\Frogger\\Car2.png").getImage();
		carGraphics5 = carGraphics5.getScaledInstance(75,50,0);
		
		carGraphics6 = new ImageIcon("C:\\Users\\Supreyo Atonu\\eclipse-workspace\\Frogger\\Car1.png").getImage();
		carGraphics6 = carGraphics6.getScaledInstance(75,50,0);
		
		//=================================================================================================================================================
	  	//=================================================================================================================================================
		
		logGraphics = new ImageIcon("C:\\Users\\Supreyo Atonu\\eclipse-workspace\\Frogger\\log.png").getImage();
		logGraphics = logGraphics.getScaledInstance(100,50,0);
		
		logGraphics2 = new ImageIcon("C:\\Users\\Supreyo Atonu\\eclipse-workspace\\Frogger\\log.png").getImage();
		logGraphics2 = logGraphics2.getScaledInstance(150,50,0);
		
		//=================================================================================================================================================
	  	//=================================================================================================================================================
					
		frog = new Frog(350,600,50,50,10,6,5,0); //Creating the player 
					  //x, y, w, h, speed, lives, number of goals, number of goals reached
		
		playerGraphics1 = new ImageIcon("C:\\Users\\Supreyo Atonu\\eclipse-workspace\\Frogger\\PlayerGraphics1.png").getImage();
		playerGraphics1 = playerGraphics1.getScaledInstance(frog.getW(),frog.getH(),0);
		
		playerGraphics2 = new ImageIcon("C:\\Users\\Supreyo Atonu\\eclipse-workspace\\Frogger\\PlayerGraphics2.png").getImage();
		playerGraphics2 = playerGraphics2.getScaledInstance(frog.getW(),frog.getH(),0);
		
		playerGraphics3 = new ImageIcon("C:\\Users\\Supreyo Atonu\\eclipse-workspace\\Frogger\\PlayerGraphics3.png").getImage();
		playerGraphics3 = playerGraphics3.getScaledInstance(frog.getW(),frog.getH(),0);
		
		playerGraphics4 = new ImageIcon("C:\\Users\\Supreyo Atonu\\eclipse-workspace\\Frogger\\PlayerGraphics4.png").getImage();
		playerGraphics4 = playerGraphics4.getScaledInstance(frog.getW(),frog.getH(),0);
		
		//=================================================================================================================================================
	  	//=================================================================================================================================================
		
		wallPaper = new ImageIcon("C:\\Users\\Supreyo Atonu\\eclipse-workspace\\Frogger\\WallPaper.png").getImage();
		wallPaper = wallPaper.getScaledInstance(770,800,0);
		
		
		info = new ImageIcon("C:\\Users\\Supreyo Atonu\\eclipse-workspace\\Frogger\\Information.png").getImage();
		info = info.getScaledInstance(750,753,0);
		
		gameWonScreen = new ImageIcon("C:\\Users\\Supreyo Atonu\\eclipse-workspace\\Frogger\\Game Won.png").getImage();
		gameWonScreen = gameWonScreen.getScaledInstance(750,753,0);
		
		gameLostScreen = new ImageIcon("C:\\Users\\Supreyo Atonu\\eclipse-workspace\\Frogger\\Game Lost.png").getImage();
		gameLostScreen = gameLostScreen.getScaledInstance(750,753,0);
		
		//=================================================================================================================================================
	  	//=================================================================================================================================================
		
		numOfCars = 16;
		numOfLogs = 8;
		numOfGoals = 5;
		numOfSpikes = 10;
		
		//==========================================================================================
	  	//==========================================================================================
		
		car = new Vehicles[numOfCars];  //Creating all object list size and objects in the game
		log = new Logs[numOfLogs];
		goal = new GoalBlock[numOfGoals];
		spike = new Spikes[numOfSpikes];
		heart = new Hearts[frog.getL()];
		water = new Water(0,100,752,150);
		time = new Time(728,0);
		
		//==========================================================================================
	  	//==========================================================================================
			
		//ROW 1
		for (int i = 0; i < 3; i++) { //Loops 3 times to set only 3 cars in one lane
			int x = i * 300; //3 cars are spaced out by 300 pixels
			car[vehicleIndex] = new Vehicles(x,550,100,50,1);  //Y value of 550, length of 100, and speed of 1    *** All cars/logs have a width of 50 pixels 
			vehicleIndex++; //Adding to vehicle index to keep track of every car				                  *** All cars/logs are created the same way as the cars in Row 1	
		}
			
		//ROW 2
		for (int i = 0; i < 3; i++) {
			int x = i * 250;
			car[vehicleIndex] = new Vehicles(x,500,75,50,-2);
			vehicleIndex++;
		}
			
		//ROW 3
		for (int i = 0; i < 2; i++) {
			int x = i * 400;
			car[vehicleIndex] = new Vehicles(x,450,75,50,3);
			vehicleIndex++;
		}
			
		//ROW 4
		for (int i = 0; i < 4; i++) {
			int x = i * 250;
			car[vehicleIndex] = new Vehicles(x,350,50,50,-2);
			vehicleIndex++;
		}
			
		//ROW 5
		for (int i = 0; i < 4; i++) {
			int x = i * 200;
			car[vehicleIndex] = new Vehicles(x,300,50,50,2);
			vehicleIndex++;
		}
		
		//==========================================================================================
	  	//==========================================================================================
			
		//ROW 6
		for (int i = 0; i < 2; i++) {
			int x = i * 400;
			log[logIndex] = new Logs(x,200,150,50,-2);
			logIndex++;
		}
			
		//ROW 7
		for (int i = 0; i < 3; i++) {
			int x = i * 300;
			log[logIndex] = new Logs(x,150,150,50,2);
			logIndex++;
		}
			
		//ROW 8
		for (int i = 0; i < 3; i++) {
			int x = i * 200;
			log[logIndex] = new Logs(x,100,100,50,-2);
			logIndex++;
		}
		
		//==========================================================================================
	  	//==========================================================================================
			
		//GOALS
		goal[0] = new GoalBlock(50,50,50,50,false); //Creating the 5 different goals the player has to reach at the end 
		goal[1] = new GoalBlock(200,50,50,50,false); //They have a false as their last parameter since its a boolean variable that keeps track if player has reached this
		goal[2] = new GoalBlock(350,50,50,50,false); //goal or not. Goals have a space of 150 pixels between them
		goal[3] = new GoalBlock(500,50,50,50,false);
		goal[4] = new GoalBlock(650,50,50,50,false);
		
		//==========================================================================================
	  	//==========================================================================================
			
		//SPIKES
		spike[0] = new Spikes(0,50,50,50);    //Creating all the spikes that are next to the goals 
		spike[1] = new Spikes(100,50,50,50);  //Since the goals are spaced out 150 pixels between them, the game will be too easy since the player
		spike[2] = new Spikes(150,50,50,50);  //can just jump in between these spaces and easily go to the goal. With spikes, if the player jumps
		spike[3] = new Spikes(250,50,50,50);  //between the goals, they will lose a life
		spike[4] = new Spikes(300,50,50,50);
		spike[5] = new Spikes(400,50,50,50);
		spike[6] = new Spikes(450,50,50,50);
		spike[7] = new Spikes(550,50,50,50);
		spike[8] = new Spikes(600,50,50,50);
		spike[9] = new Spikes(700,50,50,50);
		
		//==========================================================================================
	  	//==========================================================================================
	
		addKeyListener(this);
	}
	
	//=================================================================================================================================================
  	//=================================================================================================================================================

	public void keyTyped(KeyEvent e){
		
	}

	public void keyPressed(KeyEvent e){
		keys[e.getKeyCode()] = true;
	}
	
	public void keyReleased(KeyEvent e){
		keys[e.getKeyCode()] = false;
	}
	
	//=================================================================================================================================================
  	//=================================================================================================================================================
	
	public void playerMove() {
		requestFocus();
		
		if (frog.getSCounter() % frog.getS() == 0) {  //A timer to make sure the player does not go fast since it moves to 50 pixels 
			if (keys[KeyEvent.VK_RIGHT] && frog.getX() < 700){ //Player can't go beyond 700 pixels in the x direction
				frog.playerMove(1, 0); //Moving right (1*50)
				playerGraphicsIndex = 1; //Changing the graphics image index for each direction 
			}
			
			else if (keys[KeyEvent.VK_LEFT] && frog.getX() > 0){ //Player can't go beyond 0 pixels in the x direction
				frog.playerMove(-1, 0); //Moving left (-1*50)
				playerGraphicsIndex = 3;
			}
			
			else if (keys[KeyEvent.VK_UP] && frog.getY() > 50){ //Player can't go beyond 50 pixels in the y direction
				frog.playerMove(0, -1); //Moving down (-1*50)
				playerGraphicsIndex = 2;
				score += 5;
			}
			
			else if (keys[KeyEvent.VK_DOWN] && frog.getY() < 600){ //Player can't go beyond 600 pixels in the y direction
				frog.playerMove(0, 1); //Moving right(1*50)
				playerGraphicsIndex = 4;
			}
		}
		
		if (keys[KeyEvent.VK_SPACE]){ //Pressing space will start the game and remove the game start screen
			gameStart = true;
		}
		
		if (keys[KeyEvent.VK_I]) { //Pressing I will show the information screen and remove the game start screen
			infoScreen = true;
		}
			
		if (keys[KeyEvent.VK_ESCAPE]){ //Pressing escape will go back to the game start screen.
			infoScreen = false;
		}
		
		if (gameEnd && keys[KeyEvent.VK_SPACE]) {  //Restarting the game after you finished
			frog = new Frog(350,600,50,50,10,6,5,0); //Resetting the player position and goals reached parameters
			time = new Time(728,0); //Resetting the time to full 
			score = 0; //Resetting score 
			
			for (int i = 0; i < numOfGoals; i++) { //Resetting all the goals to false. False makes it so that player has to reach them all over again 
				goal[i] = new GoalBlock(goal[i].getGoalX(),goal[i].getGoalY(),goal[i].getGoalW(),goal[i].getGoalH(),false);
			}
			
			gameEnd = false;
			gameStart = true;
		}
		
		if (frog.getX() > 700 + frog.getW() || frog.getX() < 0 - frog.getW()) { //While riding a log, the player can still go off the screen so when they are off the screen, 
			frog = new Frog(350,600,frog.getW(),frog.getH(),frog.getS(),frog.getL() - 1, frog.numOfGoals(), frog.goalsReached()); //they will be sent back to starting position
			score -= 10;																										  //and will lose a life and 10 points
		}																														  		
	}

	//=================================================================================================================================================
  	//=================================================================================================================================================
		
	public void enemyMove(){
		requestFocus();

		for (int i = 0; i < numOfCars; i++) { //Methods to move all the cars and logs that relies on the direction and speed of the car/log
			car[i].carMove(1);				  //carMove/logMove is a void method from the frog class
		}
		
		for (int i = 0; i < numOfLogs; i++) {
			log[i].logMove(1);
		}	
	}
	
	//=================================================================================================================================================
  	//=================================================================================================================================================
	
	public void collisionCheck() {
		requestFocus();
		
		for (int i = 0; i < numOfLogs; i++) { //Checking for log collision      ***collides is a boolean method from the frog class
			if (frog.collides(log[i].getLogX(),log[i].getLogY(),log[i].getLogW(),log[i].getLogH())) { //Goes through all logs currently on the screen and checks for collision
				frog.attach(log[i]); //Void method where when a player is on a log, the player moves with the log 										
				onLog = true; //Boolean variable to keep track if the player is on the log 
			}																						//***Checking for collisions is the same for all objects on the game
		}
											
		//===========================================================================================================================
	  	//===========================================================================================================================
		
		if (frog.collides(water.getWaterX(),water.getWaterY(),water.getWaterW(),water.getWaterH()) && onLog == false) { //Checking for water collision
			frog = new Frog(350,600,frog.getW(),frog.getH(),frog.getS(),frog.getL() - 1, frog.numOfGoals(), frog.goalsReached()); //If the player is not on a log and touches
			score -= 15; //Loses 15 points for colliding with water																	water, it loses a life and goes back to
		}																														  //starting position 			
		
		else if (frog.collides(water.getWaterX(),water.getWaterY(),water.getWaterW(),water.getWaterH()) && onLog) { //When a frog is on a log and falls off, its not
			onLog = false;																							//on a log anymore and touches water
		}
		
		//===========================================================================================================================
	  	//===========================================================================================================================
		
		for (int i = 0; i < numOfCars; i++) {  //Checking for vehicle collision
			if (frog.collides(car[i].getEnemyX(),car[i].getEnemyY(),car[i].getEnemyW(),car[i].getEnemyH())) { //Goes through all cars on the screen and checks for collision
				frog = new Frog(350,600,frog.getW(),frog.getH(),frog.getS(),frog.getL() - 1, frog.numOfGoals(), frog.goalsReached());
				score -= 15; //Loses 15 points
				onLog = false;                           
			}
		}
		
		//===========================================================================================================================
	  	//===========================================================================================================================
		
		for (int i = 0; i < numOfSpikes; i++) {  //Checking for spike collision
			if (frog.collides(spike[i].getSpikeX(),spike[i].getSpikeY(),spike[i].getSpikeW(),spike[i].getSpikeH())) {
				frog = new Frog(350,600,frog.getW(),frog.getH(),frog.getS(),frog.getL() - 1, frog.numOfGoals(), frog.goalsReached());
				score -= 30;
				onLog = false;
			}
		}
		
		//===========================================================================================================================
	  	//===========================================================================================================================
		
		for (int i = 0; i < numOfGoals; i++) {  //Checking for goal collision
			if (frog.collides(goal[i].getGoalX(),goal[i].getGoalY(),goal[i].getGoalW(),goal[i].getGoalH()) && goal[i].reached() == false) { //Frog has not reached this goal yet
				frog = new Frog(350,600,frog.getW(),frog.getH(),frog.getS(),frog.getL() + 1, frog.numOfGoals(), frog.goalsReached() + 1); //Gains 1 life, gains 100 points
				goal[i] = new GoalBlock(goal[i].getGoalX(),goal[i].getGoalY(),goal[i].getGoalW(),goal[i].getGoalH(),true);	//Last parameter goes from false to true to keep track
				score += 100;																								//of the goals the frog has reached
			}	
			else if (frog.collides(goal[i].getGoalX(),goal[i].getGoalY(),goal[i].getGoalW(),goal[i].getGoalH()) && goal[i].reached()) { //Frog has already reached this goal
				frog = new Frog(350,600,frog.getW(),frog.getH(),frog.getS(),frog.getL() - 1, frog.numOfGoals(), frog.goalsReached()); //Loses a life and loses 50 points
				score -= 50;
			}
			onLog = false;
		}
	}
	
	//=================================================================================================================================================
  	//=================================================================================================================================================
	
	public void time() {
		requestFocus();

		if (time.getTC() % 30 == 0 ) { //X value of the time rectangle loses 2 pixels every 30 milliseconds 
			time.timeDecrease(2);
	    }
	}

	//=================================================================================================================================================
  	//=================================================================================================================================================
	
	public void gameEnd() {
		requestFocus();
		
		if (frog.numOfGoals() == frog.goalsReached()) { //Checking if frog has reached all the goals
			gameEnd = true; //If reached all the goals, you win the game
			gameWon = true;
			gameEndStatus = "You Reached All The Lilypads!"; //Changing the game end status 
		}
		
		if (frog.getL() <= 0) { //Checking if frog has less than 0 lives 
			gameEnd = true;
			gameWon = false;
			gameEndStatus = "You Lost All Your Lives!";
		}
		
		if (time.getTP() <=0) { //Checking if time has ran out
			gameEnd = true;
			gameWon = false;
			gameEndStatus = "Your Time Ran Out";
		}
	}

	//=================================================================================================================================================
  	//=================================================================================================================================================

	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		if (gameStart) {
	        g.setColor(Color.BLACK);
	        g.fillRect(0,0,getWidth(),getHeight()); //Setting background color to black 

	        g.setColor(new Color(153,101,21));  //Player information background
	        g.fillRect(10,662,728,80);

		    //===========================================================================================================================
		  	//===========================================================================================================================
	        
	        g.setColor(Color.WHITE);   //Player score
	        g.setFont(myFont);
	        g.drawString(Integer.toString(score), getWidth() / 2, 695); //Displaying the current score of player as a string
	        				//Converting the score to a string
		    //===========================================================================================================================
		  	//===========================================================================================================================
			
		    g.setColor(new Color(34,139,34)); //Safety lanes
		    g.fillRect(1,601,748,50); //Lanes where there are no cars, logs, or water
		    g.fillRect(1,401,748,50);
		    g.fillRect(1,251,748,50);
		    g.fillRect(1,51,748,50);
		    g.fillRect(1,1,748,50);
		    
		    g.setColor(Color.DARK_GRAY); //Road
		    g.fillRect(1,450,748,150);
		    g.fillRect(1,302,748,99);
		    	    		    
		    //===========================================================================================================================
		  	//===========================================================================================================================
		        
		    for (int i = 0; i < numOfSpikes; i++) {  //Drawing the spike image 
		        g.drawImage(spikeGraphics, spike[i].getSpikeX(), spike[i].getSpikeY(), this); //Spikes 
		    }
		    
		    for (int x = 0; x < 750; x += 50) { 
		        g.drawImage(spikeGraphics, x, 0, this); //Spikes 
		    }
		    		  
		    //===========================================================================================================================
		  	//===========================================================================================================================
		        
		    g.setColor(new Color(30,144,255)); //Drawing the water
		    g.fillRect(water.getWaterX(),water.getWaterY(),water.getWaterW(),water.getWaterH()); //Water
		    
		    //===========================================================================================================================
		  	//===========================================================================================================================
		        
		    for (int i = 0; i < numOfGoals; i++) {  //Drawing the 2 different images of the goal
		    	g.fillRect(goal[i].getGoalX(), goal[i].getGoalY(),50,50); //Drawing the color of the water under the goals 
		    	
		    	if (goal[i].reached() == false) {
		        	g.drawImage(goalGraphics1, goal[i].getGoalX() + 5, goal[i].getGoalY() + 5, this);  //Drawing goal image when player hasn't reached goal yet
		        }
		        	
		        else if (goal[i].reached()) {
		        	g.drawImage(goalGraphics2, goal[i].getGoalX() + 5, goal[i].getGoalY() + 5, this);  //Drawing goal image when player has reached goal to indicate that the
		        																					   //player has already reached the goal
		        }	
		    }
		    		        
		    //===========================================================================================================================
		  	//===========================================================================================================================
		         
		    for (int i = 0; i < numOfCars; i++) {	//Drawing all the different images for cars including the width and direction the car is going 	       	
		       	if (car[i].getEnemyS() > 0 && car[i].getEnemyW() == 50) {
		       		g.drawImage(carGraphics1, car[i].getEnemyX(),car[i].getEnemyY(), this);  //If speed of car is less than 0, its going from right to left 
		       	}																			 //If speed of car is greater than 0, its going from left to right  
		       																				 //3 different car designs for 3 different widths of the cars
		       	if (car[i].getEnemyS() < 0 && car[i].getEnemyW() == 50) {					 //Width of 50 is the race car	
		       		g.drawImage(carGraphics2, car[i].getEnemyX(),car[i].getEnemyY(), this);  //Width of 75 is the purple car
		       	}																			 //Width of 100 is the truck
		       	
		       	if (car[i].getEnemyS() > 0 && car[i].getEnemyW() == 100) {
		       		g.drawImage(carGraphics3, car[i].getEnemyX(),car[i].getEnemyY(), this);
		       	}
		       	
		       	if (car[i].getEnemyS() < 0 && car[i].getEnemyW() == 100) {
		       		g.drawImage(carGraphics4, car[i].getEnemyX(),car[i].getEnemyY(), this);
		       	}
		       	
		       	if (car[i].getEnemyS() > 0 && car[i].getEnemyW() == 75) {
		       		g.drawImage(carGraphics5, car[i].getEnemyX(),car[i].getEnemyY(), this);
		       	}
		       	
		       	if (car[i].getEnemyS() < 0 && car[i].getEnemyW() == 75) {
		       		g.drawImage(carGraphics6, car[i].getEnemyX(),car[i].getEnemyY(), this);
		       	}
		    }
		    
		    //===========================================================================================================================
		  	//===========================================================================================================================
		        
		    for (int i = 0; i < numOfLogs; i++) {	//Drawing all the different images for the logs including only the size	     	
		    	if (log[i].getLogW() == 100) {
		    		g.drawImage(logGraphics,log[i].getLogX(),log[i].getLogY(), this); //Log     //One image for log with width of 100 which is shorter than the log image
		    	}																				//with the width of 150	
		    	
		    	if (log[i].getLogW() == 150) {
		    		g.drawImage(logGraphics2,log[i].getLogX(),log[i].getLogY(), this);
		    	}
		    }
		        
		    //===========================================================================================================================
		  	//===========================================================================================================================
		    
		    if (playerGraphicsIndex == 1) { //Drawing the 4 images for the different graphics index of the player 
		    	g.drawImage(playerGraphics1,frog.getX(),frog.getY(), this);
		    }
		    
		    else if (playerGraphicsIndex == 2) {
		    	g.drawImage(playerGraphics2,frog.getX(),frog.getY(), this);
		    }
		    
		    else if (playerGraphicsIndex == 3) {
		    	g.drawImage(playerGraphics3,frog.getX(),frog.getY(), this);
		    }
		    
		    else if (playerGraphicsIndex == 4) {
		    	g.drawImage(playerGraphics4,frog.getX(),frog.getY(), this);
		    }
		    
		    for (int i = 0; i < frog.getL(); i++) { //Drawing the image of the player lives
		    	int x = i * 30; //Spaced out by 30 pixels
		    	g.drawImage(heartGraphics, 11 + x, 699, this);
		    }								
			 
		    //===========================================================================================================================
		  	//===========================================================================================================================
		     
		    if (time.getTP() > 319) {  //Drawing the time rectangles for 3 intervals
		    	g.setColor(Color.GREEN);  //The rectangle is green when the time position (length) is over 319(half of 728 which is the starting length)
		        g.fillRect(10,726,time.getTP(),15);   	
		    }
		         
		    else if(time.getTP() > 160) { //The rectangle is yellow when the time position is between 160 (half of 319) to 319
		    	g.setColor(Color.YELLOW);
		        g.fillRect(10,726,time.getTP(),15);
		    }
		         
		    else {
		    	g.setColor(Color.RED); //The rectangle is red when the time position is between 0 to 160
		        g.fillRect(10,726,time.getTP(),15);
		    }

		    if (gameEnd) {  //If the game has ended, will draw the gameWonScreen/gameLostScreen and the game status string
		        if (gameWon) {
		        	g.drawImage(gameWonScreen, 0,0, this);
		        }
		        
		        if (gameWon == false) {
		        	g.drawImage(gameLostScreen, 0,0, this);
		        }
		        
		        g.setColor(new Color(250,250,250)); 
		        g.drawString(gameEndStatus, getWidth()/ 3,getHeight()/ 4);
		    }
		}
		
		else if (gameStart == false) {
	    	g.drawImage(wallPaper,0,0, this); //Draws the main menu screen when the player hasn't pressed space yet 
	    	
	    	if (infoScreen) {
	    		g.drawImage(info, 0,0, this); //Draws the information screen when the player hasn't pressed space yet 
	    	}
	    }
	}
}