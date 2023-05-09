package shoot;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import Main.*;

public class ShootingGame extends JFrame {
	//더블 버퍼링을 사용하기 위해 선언 (1-2)
	private Image bufferImage;
	private Graphics screenGraphic;
	
	//메인화면 이미지 (1-3)
	private Image mainScreen = new ImageIcon("src/images/main_screen.png").getImage();
	private Image gameScreen = new ImageIcon("src/images/game_screen.png").getImage();
	
	//어떤 화면 띄울지 여부(true인 화면이 뜨게 만들거)
	private boolean isMainScreen = false, isLoadingScreen = false, isGameScreen = false;
	
	private Game game = new Game();
	
	private Audio backgroundMusic;
	
	GamePanel gamePanel = new GamePanel();
	
	//게임 프레임 잡기(생성자) (1-1)
	public ShootingGame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		setResizable(false); 
		setTitle("Java Escape"); 
		
		add(gamePanel); 
		
		pack(); 
		
		setLocationRelativeTo(null);  
		setVisible(true); 
		
		
		gamePanel.startGameThread(); //2
		
		
	}

	//인게임 시작했을 때 메소드
	public void GameStart () {
		//게임 화면을 띄우기 위해 isGameScreen만 true로 함
		isMainScreen = true;
		remove(gamePanel);
		setSize(1280,720);
		setLocationRelativeTo(null);
		setTitle("ShootingGame");
		
		addKeyListener(new KeyListener()); //만든 keyListener 추가하기(조작 가능하게)
		
		backgroundMusic = new Audio("src/audio/mainback.wav",true);
		backgroundMusic.start();
		
	}
	
	public void GameStart2 () {
		//게임 화면을 띄우기 위해 isGameScreen만 true로 함
		isMainScreen = false;
		isGameScreen = true;
		
		backgroundMusic.stop();
		
		game.start(); //Game 클래스에서 만든 run() 호출
	}
	
	
	//더블 버퍼링을 사용하여 메인화면 출력 (1-4)
	public void paint(Graphics g) {
		bufferImage = createImage(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT); //화면 크기만한 이미지 만듦
		screenGraphic = bufferImage.getGraphics(); //screenGraphic이 bufferImage의 Graphics 객체를 받ㅇ므
		screenDraw(screenGraphic); //screenGraphic에 그림을 그림(그림은 밑에 public void screenDraw(Graphics g)에서 그림)
		g.drawImage(bufferImage, 0, 0, null); //보이는 화면에 그림을 출력
	}
	
	public void screenDraw(Graphics g) {
		if(isMainScreen == true) {//메인 화면 띄우기
			g.drawImage(mainScreen, 0, 0, null);
			this.repaint();
		}
		else if(isGameScreen == true) { //게임 화면 띄우기
			g.drawImage(gameScreen, 0, 0, null);
			game.gameDraw(g);
			this.repaint();
		}
		
	}
	

	//키 입력 받기
	class KeyListener extends KeyAdapter {
		//누르고 있을 때
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
				case KeyEvent.VK_W:
					game.setUp(true);
					break;
				case KeyEvent.VK_S:
					game.setDown(true);
					break;
				case KeyEvent.VK_A:
					game.setLeft(true);
					break;
				case KeyEvent.VK_D:
					game.setRight(true);
					break;
				case KeyEvent.VK_R:
					game.reset();
					break;
				case KeyEvent.VK_SPACE:
					game.setShooting(true);
					break;
				case KeyEvent.VK_ENTER: //엔터 누르면 게임 화면으로 넘어감
					GameStart2();
					break;
				case KeyEvent.VK_ESCAPE: //ESC누르면 종료(1-5)
					System.exit(0);
					break;
			}
			
		}
		//땠을 때
		public void keyReleased(KeyEvent e) {
			switch(e.getKeyCode()) {
				case KeyEvent.VK_W:
					game.setUp(false);
					break;
				case KeyEvent.VK_S:
					game.setDown(false);
					break;
				case KeyEvent.VK_A:
					game.setLeft(false);
					break;
				case KeyEvent.VK_D:
					game.setRight(false);
					break;
				case KeyEvent.VK_SPACE:
					game.setShooting(false);
					break;
			}
			
		}
	}
}







