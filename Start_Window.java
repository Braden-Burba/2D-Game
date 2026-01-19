
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.image.*;
import java.awt.*;

import javax.imageio.ImageIO;
import javax.swing.*; //The swing library is used to create a window for the 2D games
import java.util.*;
import java.io.*;
public class Start_Window implements ActionListener{
	private static windows startWindow;
	private static gameScreenWindow gameWindow;
	private static windows settingsWindow;
	private static windows pauseWindow;
	private static gameLoop runGame;
	private static Thread gameThread;
	private static Start_Window mainWindow;
	
	Start_Window(){
		
	}
	
	public static void main(String[] args) {
		mainWindow = new Start_Window();
		startWindow = new windows("Home Screen", mainWindow);
		gameWindow = new gameScreenWindow("Game Screen", mainWindow);
		settingsWindow = new windows("Settings Screen", mainWindow);
		pauseWindow = new windows("Pause Screen", mainWindow);
		runGame = new gameLoop(gameWindow);
		gameThread = new Thread(runGame);
		runGame.pauseGame();
		gameThread.start();
		gameWindow.setBulletAmount(5);
		gameWindow.setBulletSpeed(10);
		gameWindow.setMoveCounterSpeed(5);
		gameWindow.setMovementSpeed(5);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		if(command.equals("playButton")) {
			startWindow.hideWindow();
			gameWindow.showWindow();
			gameWindow.getPanel().setFocusable(true);
			gameWindow.getPanel().requestFocusInWindow();
			runGame.startGame();
		}
		else if(command.equals("settingsButton")) {
			startWindow.hideWindow();
			settingsWindow.showWindow();
		}
		else if(command.equals("exitButton")) {
			System.exit(0);
		}	
		else if(command.equals("pauseButton")) {
			pauseWindow.showWindow();
			runGame.pauseGame();
		}
		else if(command.equals("backButton")) {
			if(gameWindow.isWindowVisible() == true) {
				settingsWindow.hideWindow();
				pauseWindow.showWindow();
			}
			else {
				settingsWindow.hideWindow();
				startWindow.showWindow();
			}
		}
		else if(command.equals("backButton1")) {
			pauseWindow.hideWindow();
			gameWindow.getPanel().setFocusable(true);
			gameWindow.getPanel().requestFocusInWindow();
			runGame.startGame();
		}
		else if(command.equals("settingsButton1")) {
			pauseWindow.hideWindow();
			settingsWindow.showWindow();
		}
		else if(command.equals("backToMenuButton")) {
			pauseWindow.hideWindow();
			gameWindow.hideWindow();
			startWindow.showWindow();
			runGame.resetGame();
			runGame.resetScore();
		}	
		else if(command.equals("level1Button")) {
			gameWindow.setBulletAmount(5);
			gameWindow.setBulletSpeed(10);
			gameWindow.setMoveCounterSpeed(10);
			gameWindow.setMovementSpeed(5);
			runGame.resetGame();
			runGame.resetScore();
		}
		else if(command.equals("level2Button")) {
			gameWindow.setBulletAmount(3);
			gameWindow.setBulletSpeed(10);
			gameWindow.setMoveCounterSpeed(10);
			gameWindow.setMovementSpeed(20);
			runGame.resetGame();
			runGame.resetScore();
		}
		else if(command.equals("level3Button")) {
			gameWindow.setBulletAmount(2);
			gameWindow.setBulletSpeed(10);
			gameWindow.setMoveCounterSpeed(10);
			gameWindow.setMovementSpeed(30);
			runGame.resetGame();
			runGame.resetScore();
		}
		else if(command.equals("level4Button")) {
			gameWindow.setBulletAmount(1);
			gameWindow.setBulletSpeed(10);
			gameWindow.setMoveCounterSpeed(10);
			gameWindow.setMovementSpeed(40);
			runGame.resetGame();
			runGame.resetScore();
		}
		else if(command.equals("level5Button")) {
			gameWindow.setBulletAmount(1);
			gameWindow.setBulletSpeed(10);
			gameWindow.setMoveCounterSpeed(8);
			gameWindow.setMovementSpeed(50);
			runGame.resetGame();
			runGame.resetScore();
		}
	}
}//END OF main

class windows{
	private JFrame window;
	private JLabel score;
	private Start_Window mainWindow;
	windows(String title,Start_Window mainWindow){
		this.mainWindow = mainWindow;
		this.window = new JFrame(title);
		this.window.setLayout(null);
		this.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//if(title.equals("Home Screen")) {
		//	this.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		//}
		if(title.equals("Home Screen")) {
			double WINDOWWIDTH = 1800;
			double WINDOWHEIGHT = 900;
			double BUTTONWIDTH = 100.0;
			double BUTTONHEIGHT = 50.0;
			double BUTTONHEIGHTSEPERATION = 80;
			double TITLEWIDTH = 700;
			double TITLEHEIGHT = 500;
			double TITLESEPERATION = 50;
			this.window.setSize((int)WINDOWWIDTH,(int)WINDOWHEIGHT);
			this.window.setMinimumSize(new Dimension((int)WINDOWWIDTH,(int)WINDOWHEIGHT));  //Fix Home screen first
			double actualWindowWidth = this.window.getSize().width;
			double actualWindowHeight = this.window.getSize().height;
			JButton playButton = new JButton("Play");
			playButton.setBounds((int)(actualWindowWidth*.5-BUTTONWIDTH*.5), (int)(actualWindowHeight*.5), (int)(actualWindowWidth*BUTTONWIDTH/WINDOWWIDTH), (int)(actualWindowHeight*BUTTONHEIGHT/WINDOWHEIGHT));
			playButton.setActionCommand("playButton");
			playButton.addActionListener(this.mainWindow);
			this.window.add(playButton);
			JButton settingsButton = new JButton("Settings");
			settingsButton.setActionCommand("settingsButton");
			settingsButton.addActionListener(this.mainWindow);
			settingsButton.setBounds((int)(actualWindowWidth*.5-BUTTONWIDTH*.5), (int)(actualWindowHeight*.5+BUTTONHEIGHT/WINDOWHEIGHT*actualWindowHeight+BUTTONHEIGHTSEPERATION/WINDOWHEIGHT*actualWindowHeight), (int)(actualWindowWidth*BUTTONWIDTH/WINDOWWIDTH), (int)(actualWindowHeight*BUTTONHEIGHT/WINDOWHEIGHT));
			this.window.add(settingsButton);
			JButton exitButton = new JButton("Exit");
			exitButton.setActionCommand("exitButton");
			exitButton.addActionListener(this.mainWindow);
			exitButton.setBounds((int)(actualWindowWidth*.5-BUTTONWIDTH*.5), (int)(actualWindowHeight*.5+BUTTONHEIGHT/WINDOWHEIGHT*actualWindowHeight*2+BUTTONHEIGHTSEPERATION/WINDOWHEIGHT*actualWindowHeight*2), (int)(actualWindowWidth*BUTTONWIDTH/WINDOWWIDTH), (int)(actualWindowHeight*BUTTONHEIGHT/WINDOWHEIGHT));
			this.window.add(exitButton);
			JLabel titlePicture = new JLabel();
			titlePicture.setIcon(new ImageIcon(getClass().getResource("/Images/Game Title.PNG")));
			titlePicture.setBounds((int)(actualWindowWidth*.5-TITLEWIDTH*.5), (int)(actualWindowHeight*.5-TITLESEPERATION-TITLEHEIGHT),(int)TITLEWIDTH,(int)TITLEHEIGHT);
			this.window.add(titlePicture);
			this.window.setVisible(true);
		}
		else if(title.equals("Game Screen")) {
			double windowWidth = 1800;
			double windowHeight = 900;
			double pauseButtonWidthStagger = 20;
			double scoreLabelWidthStagger = 1000;
			double buttonHeightStagger = 20;
			double componentWidth = 100.0;
			double componentHeight = 30.0;
			this.window.setSize((int)windowWidth,(int)windowHeight);
			this.window.setMinimumSize(new Dimension((int)windowWidth,(int)windowHeight));
			int actualWindowWidth = this.window.getSize().width;
			int actualWindowHeight = this.window.getSize().height;
			JButton pauseButton = new JButton("Pause");
			pauseButton.setActionCommand("pauseButton");
			pauseButton.addActionListener(this.mainWindow);
			pauseButton.setBounds((int)(actualWindowWidth*pauseButtonWidthStagger/windowWidth), (int)(actualWindowHeight*buttonHeightStagger/windowHeight), (int)(actualWindowWidth*componentWidth/windowWidth), (int)(actualWindowHeight*componentHeight/windowHeight));
			this.window.add(pauseButton);
			score = new JLabel("Current Score: 0");
			score.setBounds((int)(actualWindowWidth*scoreLabelWidthStagger/windowWidth), (int)(actualWindowHeight*buttonHeightStagger/windowHeight), (int)(actualWindowWidth*componentWidth/windowWidth), (int)(actualWindowHeight*componentHeight/windowHeight));
			this.window.add(score);
		}
		else if(title.equals("Settings Screen")) {
			double windowWidth = 1800;
			double windowHeight = 900;
			double[] leftButtonWidthStagger = {20,140,260,380,500};
			double componentWidth = 100;
			double componentHeight = 30;
			double levelButtonHeightStagger = 100;
			this.window.setSize((int)windowWidth,(int)windowHeight);
			this.window.setMinimumSize(new Dimension((int)windowWidth,(int)windowHeight));
			double actualWindowWidth = this.window.getSize().width;
			double actualWindowHeight = this.window.getSize().height;
			JButton backButton = new JButton("Back");
			backButton.setActionCommand("backButton");
			backButton.addActionListener(this.mainWindow);
			backButton.setBounds((int)(actualWindowWidth*leftButtonWidthStagger[0]/windowWidth), (int)(actualWindowHeight*20/windowHeight), (int)(actualWindowWidth*componentWidth/windowWidth), (int)(actualWindowHeight*30/windowHeight));
			this.window.add(backButton);
			JRadioButton level1 = new JRadioButton("Easy");
			JRadioButton level2 = new JRadioButton("Moderate");
			JRadioButton level3 = new JRadioButton("Difficult");
			JRadioButton level4 = new JRadioButton("Expert");
			JRadioButton level5 = new JRadioButton("Impossible");
			level1.addActionListener(this.mainWindow);
			level1.setActionCommand("level1Button");
			level1.setBounds((int)(actualWindowWidth*leftButtonWidthStagger[0]/windowWidth), (int)(actualWindowHeight*levelButtonHeightStagger/windowHeight), (int)(actualWindowWidth*componentWidth/windowWidth), (int)(actualWindowHeight*componentHeight/windowHeight));
			window.add(level1);
			level2.addActionListener(this.mainWindow);
			level2.setActionCommand("level2Button");
			level2.setBounds((int)(actualWindowWidth*leftButtonWidthStagger[1]/windowWidth), (int)(actualWindowHeight*levelButtonHeightStagger/windowHeight), (int)(actualWindowWidth*componentWidth/windowWidth), (int)(actualWindowHeight*componentHeight/windowHeight));
			window.add(level2);
			level3.addActionListener(this.mainWindow);
			level3.setActionCommand("level3Button");
			level3.setBounds((int)(actualWindowWidth*leftButtonWidthStagger[2]/windowWidth), (int)(actualWindowHeight*levelButtonHeightStagger/windowHeight), (int)(actualWindowWidth*componentWidth/windowWidth), (int)(actualWindowHeight*componentHeight/windowHeight));
			window.add(level3);
			level4.addActionListener(this.mainWindow);
			level4.setActionCommand("level4Button");
			level4.setBounds((int)(actualWindowWidth*leftButtonWidthStagger[3]/windowWidth), (int)(actualWindowHeight*levelButtonHeightStagger/windowHeight), (int)(actualWindowWidth*componentWidth/windowWidth), (int)(actualWindowHeight*componentHeight/windowHeight));
			window.add(level4);
			level5.addActionListener(this.mainWindow);
			level5.setActionCommand("level5Button");
			level5.setBounds((int)(actualWindowWidth*leftButtonWidthStagger[4]/windowWidth), (int)(actualWindowHeight*levelButtonHeightStagger/windowHeight), (int)(actualWindowWidth*componentWidth/windowWidth), (int)(actualWindowHeight*componentHeight/windowHeight));
			window.add(level5);
			ButtonGroup difficulty = new ButtonGroup();
			difficulty.add(level1);
			difficulty.add(level2);
			difficulty.add(level3);
			difficulty.add(level4);
			difficulty.add(level5);
			level1.setSelected(true);
		}
		else if(title.equals("Pause Screen")) {
			double pauseWindowWidth = 500;
			double pauseWindowHeight = 500;
			double buttonWidth = 100;
			double buttonHeight = 50;
			double buttonHeightStagger = 20;
			this.window.setSize((int)pauseWindowWidth,(int)pauseWindowHeight);
			this.window.setMinimumSize(new Dimension((int)pauseWindowWidth,(int)pauseWindowHeight));
			this.window.setMaximumSize(new Dimension((int)pauseWindowWidth,(int)pauseWindowHeight));
			double actualWindowWidth = this.window.getSize().width;
			double actualWindowHeight = this.window.getSize().height;
			JButton backButton = new JButton("Back");
			backButton.setActionCommand("backButton1");
			backButton.addActionListener(this.mainWindow);
			backButton.setBounds((int)(actualWindowWidth*.5-buttonWidth*.5),(int)(actualWindowHeight*buttonHeightStagger/pauseWindowHeight),(int)(actualWindowWidth*buttonWidth/pauseWindowWidth),(int)(actualWindowHeight*buttonHeight/pauseWindowHeight));
			JButton settingsButton = new JButton("Settings");
			settingsButton.setActionCommand("settingsButton1"); //
			settingsButton.addActionListener(this.mainWindow);
			settingsButton.setBounds((int)(actualWindowWidth*.5-buttonWidth*.5),(int)(2*actualWindowHeight*buttonHeightStagger/pauseWindowHeight+actualWindowHeight*buttonHeight/pauseWindowHeight),(int)(actualWindowWidth*buttonWidth/pauseWindowWidth),(int)(actualWindowHeight*buttonHeight/pauseWindowHeight));
			JButton backToMenuButton = new JButton("Home");
			backToMenuButton.setActionCommand("backToMenuButton");
			backToMenuButton.addActionListener(this.mainWindow);
			backToMenuButton.setBounds((int)(actualWindowWidth*.5-buttonWidth*.5),(int)(3*actualWindowHeight*buttonHeightStagger/pauseWindowHeight+2*actualWindowHeight*buttonHeight/pauseWindowHeight),(int)(actualWindowWidth*buttonWidth/pauseWindowWidth),(int)(actualWindowHeight*buttonHeight/pauseWindowHeight));
			this.window.add(backButton);
			this.window.add(settingsButton);
			this.window.add(backToMenuButton);
		}
		else {}
	}
	
	public void showWindow() {
		this.window.setVisible(true);
	}
	
	public void hideWindow() {
		this.window.setVisible(false);
	}
	
	public boolean isWindowVisible() {
		return this.window.isVisible();
	}
	
	public int getWindowWidth() {
		return this.window.getWidth();
	}
	
	public int getWindowHeight() {
		return this.window.getHeight();
	}
	
	public void addPanel(gamePanel panel) {
		this.window.add(panel);
	}
	
	public JFrame getWindow() {
		return this.window;
	}
	
	public JLabel getScoreLabel() {
		return this.score;
	}
}//END OF windows

class gameScreenWindow extends windows{
	private player player1;
	private player player2;
	private java.util.List<projectiles> player1Bullets;
	private gamePanel screen;
	private double bulletSpeed = 10;
	private int bulletAmount = 1;
	private int movementSpeed = 0;
	private int moveCounterSpeed = 0;
	gameScreenWindow(String title, Start_Window mainWindow) {
		super(title,mainWindow);
		int initialPlayerStagger = 20;
		int initialPlayerHeight = 450;
		int PLAYERWIDTH = 100;
		player1 = new player(initialPlayerStagger,initialPlayerHeight);
		player2 = new player(this.getWindowWidth()-initialPlayerStagger-PLAYERWIDTH,initialPlayerHeight);
		player1Bullets = Collections.synchronizedList(new ArrayList<>());
		screen = new gamePanel(player1, player2, player1Bullets, bulletSpeed, bulletAmount);
		this.addPanel(screen);
		//this.window.add(screen);
		screen.setBounds(0, 0, this.getWindowWidth(), this.getWindowHeight());
		this.drawScreen();
	}
	
	public void drawScreen() {
		screen.repaint();
		this.getWindow().repaint();
	}
	
	public void setMovementSpeed(int givenMovementSpeed) {
		this.movementSpeed = givenMovementSpeed;
	}
	
	public void setMoveCounterSpeed(int givenMoveCounterSpeed) {
		this.moveCounterSpeed = givenMoveCounterSpeed;
	}
	
	public void setBulletSpeed(double givenBulletSpeed) {
		screen.setBulletSpeed(givenBulletSpeed);
	}
	
	public void setBulletAmount(int givenBulletAmount) {
		screen.setBulletAmount(givenBulletAmount);
	}
	
	public int getMovementSpeed() {
		return this.movementSpeed;
	}
	
	public int getMoveCounterSpeed() {
		return this.moveCounterSpeed;
	}
	
	public int getPlayer1BulletSize() {
		return this.player1Bullets.size();
	}
	
	public projectiles getProjectile(int i) {
		return this.player1Bullets.get(i);
	}
	
	public projectiles removeProjectile(int i) {
		return this.player1Bullets.remove(i);
	}
	
	public void resetplayer1Bullets() {
		this.player1Bullets.clear();
	}
	
	public player getPlayer1() {
		return this.player1;
	}
	
	public player getPlayer2() {
		return this.player2;
	}
	
	public gamePanel getPanel() {
		return this.screen;
	}
	
	static class player{
		private int player_X;
		private int player_Y;
		player(int x, int y){
			player_X = x;
			player_Y = y;
		}
		
		public int getPlayer_X() {
			return this.player_X;
		}
		
		public int getPlayer_Y() {
			return this.player_Y;
		}
		
		public void setPlayer_X(int x) {
			this.player_X = x;
		}
		
		public void setPlayer_Y(int y) {
			this.player_Y = y;
		}
	}
	
	static class projectiles{
		private double launchAngle;
		private double bullet_X;
		private double bullet_Y;
		private double scale_X;
		private double scale_Y;
		private int framesUntil_X_Reached;
		projectiles(double launchAngle,double bullet_X,double bullet_Y, double scale_X, double scale_Y){
			this.launchAngle = launchAngle;
			this.bullet_X = bullet_X;
			this.bullet_Y = bullet_Y;
			this.scale_X = scale_X;
			this.scale_Y = scale_Y;
		}
		
		public double getBullet_X() {
			return this.bullet_X;
		}
		
		public double getBullet_Y() {
			return this.bullet_Y;
		}
		
		public void setBullet_X(double x) {
			this.bullet_X = x;
		}
		
		public void setBullet_Y(double y) {
			this.bullet_Y = y;
		}
		
		public double getScale_X() {
			return this.scale_X;
		}
		
		public double getScale_Y() {
			return this.scale_Y;
		}
		
		public void setFramesUntil_X_Reached(int FramesUntil_X_Reached) {
			this.framesUntil_X_Reached = FramesUntil_X_Reached;
		}
		
		public int getFramesUntil_X_Reached() {
			return this.framesUntil_X_Reached;
		}
		
	}
	
}//END OF gameScreenWindow

class gamePanel extends JPanel implements KeyListener, MouseListener{
	private Map<Integer,String> keyPressed = new HashMap<>();
	private boolean wPressed = false,aPressed = false,sPressed = false,dPressed = false;
	private gameScreenWindow.player player1;
	private gameScreenWindow.player player2;
	private java.util.List<gameScreenWindow.projectiles> player1Bullets;
	private static BufferedImage player1Image;
	private static BufferedImage player2Image;
	private static BufferedImage bulletImage;
	private double bulletSpeed = 0;
	private int bulletAmount = 0;
	gamePanel(gameScreenWindow.player player1, gameScreenWindow.player player2, java.util.List<gameScreenWindow.projectiles> player1Bullets, double givenBulletSpeed, int givenBulletAmount){
		keyPressed.put(KeyEvent.VK_W, "W");
		keyPressed.put(KeyEvent.VK_A, "A");
		keyPressed.put(KeyEvent.VK_S, "S");
		keyPressed.put(KeyEvent.VK_D, "D");
		addKeyListener(this);
		this.player1 = player1;
		this.player2 = player2;
		this.player1Bullets = player1Bullets;
		addMouseListener(this);
		bulletSpeed = givenBulletSpeed;
		bulletAmount = givenBulletAmount;
		try {
			player1Image = ImageIO.read(getClass().getResource("/Images/Player1.PNG"));
			player2Image = ImageIO.read(getClass().getResource("/Images/Player2.png"));
			bulletImage = ImageIO.read(getClass().getResource("/Images/Bullet.PNG"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	protected void setBulletSpeed(double givenBulletSpeed) {
		this.bulletSpeed = givenBulletSpeed;
	}
	
	protected void setBulletAmount(int givenBulletAmount) {
		this.bulletAmount = givenBulletAmount;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int status = e.getKeyCode();
		if(status == KeyEvent.VK_W) {
			this.wPressed = true;
		}
		if(status == KeyEvent.VK_A) {
			this.aPressed = true;
		}
		if(status == KeyEvent.VK_S) {
			this.sPressed = true;
		}
		if(status == KeyEvent.VK_D) {
			this.dPressed = true;
		}
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int status = e.getKeyCode();
		if(status == KeyEvent.VK_W) {
			this.wPressed = false;
		}
		if(status == KeyEvent.VK_A) {
			this.aPressed = false;
		}
		if(status == KeyEvent.VK_S) {
			this.sPressed = false;
		}
		if(status == KeyEvent.VK_D) {
			this.dPressed = false;
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		int PLAYERWIDTH = 100;
		int PLAYERHEIGHT = 50;
		double bulletWidth = 20;
		double bulletHeight = 21;
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(player1Image,this.player1.getPlayer_X(),this.player1.getPlayer_Y(),PLAYERWIDTH,PLAYERHEIGHT,this);
		g2d.drawImage(player2Image,this.player2.getPlayer_X(),this.player2.getPlayer_Y(),PLAYERWIDTH,PLAYERHEIGHT,this);
		int length = 0;
			while(length < this.player1Bullets.size()) {
				g2d.drawImage(bulletImage, (int)this.player1Bullets.get(length).getBullet_X(),(int)this.player1Bullets.get(length).getBullet_Y()+(int)bulletHeight/2,(int)bulletWidth,(int)bulletHeight,this);
				length++;
			}
	}
	
	public boolean getwPressed() {
		return this.wPressed;
	}
	
	public boolean getaPressed() {
		return this.aPressed;
	}
	
	public boolean getsPressed() {
		return this.sPressed;
	}
	
	public boolean getdPressed() {
		return this.dPressed;
	}

	public void resetPressedBooleans() {
		this.wPressed = false;
		this.aPressed = false;
		this.sPressed = false;
		this.dPressed = false;
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		double launchAngle = 0;
		double click_X = e.getX()-(this.player1.getPlayer_X()+100);
		double click_Y = this.player1.getPlayer_Y()+25.0-e.getY();
		double PLAYERWIDTH = 100;
		double PLAYERHEIGHT = 50;
		if(click_X>0 && this.player1Bullets.size()<this.bulletAmount) {
			launchAngle = Math.atan2(click_Y,click_X);
			double launchDistance = Math.sqrt(click_X*click_X+click_Y*click_Y);
			double scale_X = this.bulletSpeed*click_X/launchDistance;
			double scale_Y = this.bulletSpeed*click_Y/launchDistance;
			gameScreenWindow.projectiles bullet = new gameScreenWindow.projectiles(launchAngle,this.player1.getPlayer_X()+PLAYERWIDTH,this.player1.getPlayer_Y()+PLAYERHEIGHT*.5, scale_X, scale_Y);
			this.player1Bullets.add(bullet);
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}
}

class gameLoop implements Runnable{
	private static volatile boolean pause = false;
	private gameScreenWindow gameWindow;
	private int moveCounter = randomMove(300);
	private int movementDirection = randomMove(319);
	private boolean collisionDetected = false;
	private static volatile int score = 0;
	
	gameLoop(gameScreenWindow gameWindow){
		this.gameWindow = gameWindow;
	}
	
	@Override
	public void run() {
		while(true) {
			if(pause == true) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			else if(pause == false) {
				//Game Stuff
				try {
					Thread.sleep(6);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if(this.moveCounter>0) {
					if(predictCollisions(this.gameWindow.getPlayer2().getPlayer_X(), this.gameWindow.getPlayer2().getPlayer_Y()) == true) {
						determineNewPath(this.gameWindow.getPlayer2().getPlayer_X(), this.gameWindow.getPlayer2().getPlayer_Y());
					}
					else {
						moveAI(this.movementDirection);
					}
					this.moveCounter = this.moveCounter - this.gameWindow.getMoveCounterSpeed();
				}
				else {
					this.movementDirection = randomMove(319);
					this.moveCounter = randomMove(300);
				}
				movePlayer();
				updatePlayer1Bullets();
				this.collisionDetected = checkForCollision();
				this.gameWindow.drawScreen();
				if(this.collisionDetected == true) {
					score++;
					this.gameWindow.getScoreLabel().setText("Current Score: " + score);
					resetGame();
				}
			}	
		}
	}
	
	private void determineNewPath(int AI_X, int AI_Y) { //Check how many frames it takes until AI predictcollision is false (Frames1). if Frames1 > bulletFrames then change directions 
		int player2_Yup = this.gameWindow.getPlayer2().getPlayer_Y()-this.gameWindow.getMovementSpeed();
		int player2_Ydown = this.gameWindow.getPlayer2().getPlayer_Y()+this.gameWindow.getMovementSpeed();
		int player2_XLeft = this.gameWindow.getPlayer2().getPlayer_X()-this.gameWindow.getMovementSpeed();
		int player2_XRight = this.gameWindow.getPlayer2().getPlayer_X()+this.gameWindow.getMovementSpeed();
		int rightWall = this.gameWindow.getWindowWidth()-this.gameWindow.getWindow().getInsets().right-this.gameWindow.getWindow().getInsets().left;
		int bottomWall = this.gameWindow.getWindowHeight()-this.gameWindow.getWindow().getInsets().top-this.gameWindow.getWindow().getInsets().bottom;
		String AIDirection = getAIDirection(this.movementDirection);
		int AI_Frames_Until_Dodge = 1;
		int FRAMEVARIABLE = 12;
		switch(AIDirection) {
		case "UP":
			while(predictCollisions(AI_X,AI_Y-AI_Frames_Until_Dodge*this.gameWindow.getMovementSpeed()) == true) {
				AI_Frames_Until_Dodge++;
				if(AI_Frames_Until_Dodge == FRAMEVARIABLE) break;
			}
			if(AI_Frames_Until_Dodge>FRAMEVARIABLE) {
				this.AI_DOWN(player2_Ydown, bottomWall);
			}
			else {
				this.AI_UP(player2_Yup);
			}
			break;
			
		case "LEFT":
			while(predictCollisions(AI_X-AI_Frames_Until_Dodge*this.gameWindow.getMovementSpeed(),AI_Y) == true) {
				AI_Frames_Until_Dodge++;
				if(AI_Frames_Until_Dodge == FRAMEVARIABLE) break;
			}
			if(AI_Frames_Until_Dodge>FRAMEVARIABLE) {
				this.AI_RIGHT(player2_XRight,rightWall);
			}
			else {
				this.AI_LEFT(player2_XLeft);
			}
			break;
			
		case "RIGHT":
			while(predictCollisions(AI_X+AI_Frames_Until_Dodge*this.gameWindow.getMovementSpeed(),AI_Y) == true) {
				AI_Frames_Until_Dodge++;
				if(AI_Frames_Until_Dodge == FRAMEVARIABLE) break;
			}
			if(AI_Frames_Until_Dodge>FRAMEVARIABLE) {
				this.AI_LEFT(player2_XLeft);
			}
			else {
				this.AI_RIGHT(player2_XRight,rightWall);
			}
			break;
			
		case "DOWN":
			while(predictCollisions(AI_X,AI_Y+AI_Frames_Until_Dodge*this.gameWindow.getMovementSpeed()) == true) {
				AI_Frames_Until_Dodge++;
				if(AI_Frames_Until_Dodge == FRAMEVARIABLE) break;
			}
			if(AI_Frames_Until_Dodge>FRAMEVARIABLE) {
				this.AI_UP(player2_Yup);
			}
			else {
				this.AI_DOWN(player2_Ydown, bottomWall);
			}
			break;			
		case "UP_LEFT":
			while(predictCollisions(AI_X-AI_Frames_Until_Dodge*this.gameWindow.getMovementSpeed(),AI_Y-AI_Frames_Until_Dodge*this.gameWindow.getMovementSpeed()) == true) {
				AI_Frames_Until_Dodge++;
				if(AI_Frames_Until_Dodge == FRAMEVARIABLE) break;
			}
			if(AI_Frames_Until_Dodge>FRAMEVARIABLE) {
				this.AI_DOWNRIGHT(player2_Ydown,bottomWall,player2_XRight,rightWall);
			}
			else {
				this.AI_UPLEFT(player2_XLeft,player2_Yup);
			}
			break;	
			
		case "UP_RIGHT":
			while(predictCollisions(AI_X+AI_Frames_Until_Dodge*this.gameWindow.getMovementSpeed(),AI_Y-AI_Frames_Until_Dodge*this.gameWindow.getMovementSpeed()) == true) {
				AI_Frames_Until_Dodge++;
				if(AI_Frames_Until_Dodge == FRAMEVARIABLE) break;
			}
			if(AI_Frames_Until_Dodge>FRAMEVARIABLE) {
				this.AI_DOWNLEFT(player2_Ydown,bottomWall,player2_XLeft);
			}
			else {
				this.AI_UPRIGHT(player2_Yup,player2_XRight,rightWall);
			}
			break;	
			
		case "DOWN_LEFT":
			while(predictCollisions(AI_X-AI_Frames_Until_Dodge*this.gameWindow.getMovementSpeed(),AI_Y+AI_Frames_Until_Dodge*this.gameWindow.getMovementSpeed()) == true) {
				AI_Frames_Until_Dodge++;
				if(AI_Frames_Until_Dodge == FRAMEVARIABLE) break;
			}
			if(AI_Frames_Until_Dodge>FRAMEVARIABLE) {
				this.AI_UPRIGHT(player2_Yup,player2_XRight,rightWall);
			}
			else {
				this.AI_DOWNLEFT(player2_Ydown,bottomWall,player2_XLeft);
			}
			break;	
			
		case "DOWN_RIGHT":
			while(predictCollisions(AI_X+AI_Frames_Until_Dodge*this.gameWindow.getMovementSpeed(),AI_Y+AI_Frames_Until_Dodge*this.gameWindow.getMovementSpeed()) == true) {
				AI_Frames_Until_Dodge++;
				if(AI_Frames_Until_Dodge == FRAMEVARIABLE) break;
			}
			if(AI_Frames_Until_Dodge>FRAMEVARIABLE) {
				this.AI_UPLEFT(player2_XLeft,player2_Yup);
			}
			else {
				this.AI_DOWNRIGHT(player2_Ydown,bottomWall,player2_XRight,rightWall);
			}
			break;	
			
		default:
			break;
	}
	}
	
	private boolean checkForCollision() {
		boolean collided = false;
		int length = this.gameWindow.getPlayer1BulletSize()-1;
		while(length>=0) {
			if(this.gameWindow.getProjectile(length).getBullet_X()<this.gameWindow.getPlayer2().getPlayer_X()+100 && this.gameWindow.getProjectile(length).getBullet_X()+20>this.gameWindow.getPlayer2().getPlayer_X() && this.gameWindow.getProjectile(length).getBullet_Y()<this.gameWindow.getPlayer2().getPlayer_Y()+50 && this.gameWindow.getProjectile(length).getBullet_Y()+21>this.gameWindow.getPlayer2().getPlayer_Y()) {
				collided = true;
				break;
			}
			length--;
		}
		return collided;
	}
	
	private void updatePlayer1Bullets() {
		int length = this.gameWindow.getPlayer1BulletSize()-1;
		double scaleX = 0;
		double scaleY = 0;
		int rightWall = this.gameWindow.getWindowWidth()-this.gameWindow.getWindow().getInsets().right-this.gameWindow.getWindow().getInsets().left;
		int topWall = 60;
		int bottomWall = this.gameWindow.getWindowHeight()-this.gameWindow.getWindow().getInsets().top-this.gameWindow.getWindow().getInsets().bottom;
		while(length>=0) {
			scaleX = this.gameWindow.getProjectile(length).getScale_X();
			scaleY = this.gameWindow.getProjectile(length).getScale_Y();
			if(this.gameWindow.getProjectile(length).getBullet_X()+20+scaleX >= rightWall || this.gameWindow.getProjectile(length).getBullet_Y() - scaleY <= topWall || this.gameWindow.getProjectile(length).getBullet_Y() + scaleY >= bottomWall) {
				this.gameWindow.removeProjectile(length);
				length--;
			}
			else{
				this.gameWindow.getProjectile(length).setBullet_X(this.gameWindow.getProjectile(length).getBullet_X() + scaleX);
				this.gameWindow.getProjectile(length).setBullet_Y(this.gameWindow.getProjectile(length).getBullet_Y() - scaleY);
				length--;
			}
		}
	}

	private void moveAI(int movementDirection1) {
		//int movementDirection = movementDirection1;
		//player2 AI
		int player2_Yup = this.gameWindow.getPlayer2().getPlayer_Y()-this.gameWindow.getMovementSpeed();
		int player2_Ydown = this.gameWindow.getPlayer2().getPlayer_Y()+this.gameWindow.getMovementSpeed();
		int player2_XLeft = this.gameWindow.getPlayer2().getPlayer_X()-this.gameWindow.getMovementSpeed();
		int player2_XRight = this.gameWindow.getPlayer2().getPlayer_X()+this.gameWindow.getMovementSpeed();
		int rightWall = this.gameWindow.getWindowWidth()-this.gameWindow.getWindow().getInsets().right-this.gameWindow.getWindow().getInsets().left;
		int bottomWall = this.gameWindow.getWindowHeight()-this.gameWindow.getWindow().getInsets().top-this.gameWindow.getWindow().getInsets().bottom;
		String AIDirection = getAIDirection(movementDirection1);
		switch(AIDirection) {
			case "UP":
				this.AI_UP(player2_Yup);
				break;
				
			case "LEFT":
				this.AI_LEFT(player2_XLeft);
				break;
				
			case "RIGHT":
				this.AI_RIGHT(player2_XRight,rightWall);
				break;
				
			case "DOWN":
				this.AI_DOWN(player2_Ydown, bottomWall);
				break;
				
			case "UP_LEFT":
				this.AI_UPLEFT(player2_XLeft,player2_Yup);
				break;
				
			case "UP_RIGHT":
				this.AI_UPRIGHT(player2_Yup,player2_XRight,rightWall);
				break;
				
			case "DOWN_LEFT":
				this.AI_DOWNLEFT(player2_Ydown,bottomWall,player2_XLeft);
				break;
				
			case "DOWN_RIGHT":
				this.AI_DOWNRIGHT(player2_Ydown,bottomWall,player2_XRight,rightWall);
				break;
				
			default:
				break;
		}
	}
	
	private void AI_UP(int player2_Yup) {
		if(player2_Yup>=60) { //up //Original 0-39
			this.gameWindow.getPlayer2().setPlayer_Y(player2_Yup);
		}
	}
	
	private void AI_DOWN(int player2_Ydown, int bottomWall) {
		if(player2_Ydown+50 <= bottomWall) { //down //Original 120-159
			this.gameWindow.getPlayer2().setPlayer_Y(player2_Ydown);
		}
	}
	
	private void AI_LEFT(int player2_XLeft) {
		if(player2_XLeft >= this.gameWindow.getWindowWidth()/2) { //left
			this.gameWindow.getPlayer2().setPlayer_X(player2_XLeft);
		}
	}
	
	private void AI_RIGHT(int player2_XRight, int rightWall) {
		if(player2_XRight+100 <= rightWall) { //right
			this.gameWindow.getPlayer2().setPlayer_X(player2_XRight);
		}
	}
	
	private void AI_DOWNRIGHT(int player2_Ydown, int bottomWall, int player2_XRight, int rightWall) {
		if(player2_Ydown+50 <= bottomWall && player2_XRight+100 <= rightWall) { //down/right
			this.gameWindow.getPlayer2().setPlayer_Y(player2_Ydown);
			this.gameWindow.getPlayer2().setPlayer_X(player2_XRight);
		}
		this.AI_RIGHT(player2_XRight,rightWall);
		this.AI_DOWN(player2_Ydown, bottomWall);
	}
	
	private void AI_DOWNLEFT(int player2_Ydown, int bottomWall, int player2_XLeft) {
		if(player2_Ydown+50 <= bottomWall && player2_XLeft >= this.gameWindow.getWindowWidth()/2) {  //down/left
			this.gameWindow.getPlayer2().setPlayer_Y(player2_Ydown);
			this.gameWindow.getPlayer2().setPlayer_X(player2_XLeft);
		}
		this.AI_LEFT(player2_XLeft);
		this.AI_DOWN(player2_Ydown, bottomWall);
	}
	
	private void AI_UPRIGHT(int player2_Yup, int player2_XRight, int rightWall) {
		if(player2_Yup>=60 && player2_XRight+100 <= rightWall) { //up/right
			this.gameWindow.getPlayer2().setPlayer_Y(player2_Yup);
			this.gameWindow.getPlayer2().setPlayer_X(player2_XRight);
		}
		this.AI_RIGHT(player2_XRight,rightWall);
		this.AI_UP(player2_Yup);
	}
	
	private void AI_UPLEFT(int player2_XLeft, int player2_Yup) {
		if(player2_XLeft >= this.gameWindow.getWindowWidth()/2 && player2_Yup>=60) { //up/left
			this.gameWindow.getPlayer2().setPlayer_Y(player2_Yup);
			this.gameWindow.getPlayer2().setPlayer_X(player2_XLeft);
		}
		this.AI_LEFT(player2_XLeft);
		this.AI_UP(player2_Yup);
	}
	
	private String getAIDirection(int movementDirection) {
		if(20 <= movementDirection && movementDirection <= 39) { //up //Original 0-39
			return "UP";
		}
		else if(40 <= movementDirection && movementDirection <= 79) { //left
			return "LEFT";
		}
		else if(80 <= movementDirection && movementDirection <= 119) { //right
			return "RIGHT";
		}
		else if(120 <= movementDirection && movementDirection <= 159) { //down //Original 120-159
			return "DOWN";
		}
		else if(160 <= movementDirection && movementDirection <= 199) { //up/left
			return "UP_LEFT";
		}
		else if(200 <= movementDirection && movementDirection <= 239) { //up/right
			return "UP_RIGHT";
		}
		else if(240 <= movementDirection && movementDirection <= 279) { //down/left
			return "DOWN_LEFT";
		}
		else if(280 <= movementDirection && movementDirection <= 319) { //down/right
			return "DOWN_RIGHT";
		}
		else return "UP";
	}
	
	private boolean predictCollisions(int AI_X, int AI_Y) {
		int PLAYERWIDTH = 100;
		int PLAYERHEIGHT = 50;
		int BULLETWIDTH = 20;
		int BULLETHEIGHT = 21;
		int length = gameWindow.getPlayer1BulletSize()-1;
		boolean willCollide = false;
		while(length>=0) {
			double projectile_X = gameWindow.getProjectile(length).getBullet_X();
			double projectile_Y = gameWindow.getProjectile(length).getBullet_Y();
			double projectile_X_Velocity = gameWindow.getProjectile(length).getScale_X();
			double projectile_Y_Velocity = gameWindow.getProjectile(length).getScale_Y();
			if (projectile_X_Velocity == 0) {
				length--;
				continue;
			}
			double framesUntil_X_Reached = (AI_X - projectile_X)/projectile_X_Velocity;
			//this.gameWindow.getProjectile(length).setFramesUntil_X_Reached((int)framesUntil_X_Reached);
			if(framesUntil_X_Reached <= 0) {
				length--;
				continue;
			}
			double projectile_X_AtFrame = projectile_X + projectile_X_Velocity * framesUntil_X_Reached;
			double projectile_Y_AtFrame = projectile_Y - projectile_Y_Velocity * framesUntil_X_Reached;
			
			int PLAYERLeft_X = AI_X;
			int PLAYERRight_X = AI_X+PLAYERWIDTH;
			int PLAYERTop_Y = AI_Y;
			int PLAYERBottom_Y = AI_Y+PLAYERHEIGHT;
			willCollide = projectile_X_AtFrame<PLAYERRight_X && projectile_X_AtFrame+BULLETWIDTH>PLAYERLeft_X && projectile_Y_AtFrame < PLAYERBottom_Y && projectile_Y_AtFrame+BULLETHEIGHT > PLAYERTop_Y;
			if(willCollide == true) return true;
			length--;
		}
		return willCollide;
	}
	
	private void movePlayer() {
		if(this.gameWindow.getPanel().getwPressed() == true) { //up
			if(this.gameWindow.getPlayer1().getPlayer_Y()-1>=60) {
				this.gameWindow.getPlayer1().setPlayer_Y(this.gameWindow.getPlayer1().getPlayer_Y()-1);
			}
		}
		if(this.gameWindow.getPanel().getaPressed() == true) { //left
			if(this.gameWindow.getPlayer1().getPlayer_X()-1>=0)
				this.gameWindow.getPlayer1().setPlayer_X(this.gameWindow.getPlayer1().getPlayer_X()-1);
		}
		if(this.gameWindow.getPanel().getsPressed() == true) { //down
			if(this.gameWindow.getPlayer1().getPlayer_Y()+1+50<=this.gameWindow.getWindowHeight()-this.gameWindow.getWindow().getInsets().top-this.gameWindow.getWindow().getInsets().bottom) {
				this.gameWindow.getPlayer1().setPlayer_Y(this.gameWindow.getPlayer1().getPlayer_Y()+1);
			}
		}
		if(this.gameWindow.getPanel().getdPressed() == true) { //right
			if(this.gameWindow.getPlayer1().getPlayer_X()+100+1<=this.gameWindow.getWindowWidth()/2) {
				this.gameWindow.getPlayer1().setPlayer_X(this.gameWindow.getPlayer1().getPlayer_X()+1);
			}
		}
	}
	
	public void pauseGame() {
		pause = true;
	}
	
	public void startGame() {
		pause = false;
	}
	
	public void resetGame() {
		this.gameWindow.getPlayer1().setPlayer_X(20);
		this.gameWindow.getPlayer1().setPlayer_Y(450);
		this.gameWindow.getPlayer2().setPlayer_X(this.gameWindow.getWindowWidth()-20-100);
		this.gameWindow.getPlayer2().setPlayer_Y(450);
		this.collisionDetected = false;
		this.gameWindow.drawScreen();
		this.gameWindow.getPanel().resetPressedBooleans();
		this.gameWindow.resetplayer1Bullets();
	}
	
	public void resetScore() {
		score = 0;
		this.gameWindow.getScoreLabel().setText("Current Score: " + score);
	}
	
	private static int randomMove(int topRange) {
		Random rand = new Random();
		return rand.nextInt(topRange);
	}
	
}//END OF gameLoop