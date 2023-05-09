package shoot;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import Main.*;

public class ShootingGame extends JFrame {
	//���� ���۸��� ����ϱ� ���� ���� (1-2)
	private Image bufferImage;
	private Graphics screenGraphic;
	
	//����ȭ�� �̹��� (1-3)
	private Image mainScreen = new ImageIcon("src/images/main_screen.png").getImage();
	private Image gameScreen = new ImageIcon("src/images/game_screen.png").getImage();
	
	//� ȭ�� ����� ����(true�� ȭ���� �߰� �����)
	private boolean isMainScreen = false, isLoadingScreen = false, isGameScreen = false;
	
	private Game game = new Game();
	
	private Audio backgroundMusic;
	
	GamePanel gamePanel = new GamePanel();
	
	//���� ������ ���(������) (1-1)
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

	//�ΰ��� �������� �� �޼ҵ�
	public void GameStart () {
		//���� ȭ���� ���� ���� isGameScreen�� true�� ��
		isMainScreen = true;
		remove(gamePanel);
		setSize(1280,720);
		setLocationRelativeTo(null);
		setTitle("ShootingGame");
		
		addKeyListener(new KeyListener()); //���� keyListener �߰��ϱ�(���� �����ϰ�)
		
		backgroundMusic = new Audio("src/audio/mainback.wav",true);
		backgroundMusic.start();
		
	}
	
	public void GameStart2 () {
		//���� ȭ���� ���� ���� isGameScreen�� true�� ��
		isMainScreen = false;
		isGameScreen = true;
		
		backgroundMusic.stop();
		
		game.start(); //Game Ŭ�������� ���� run() ȣ��
	}
	
	
	//���� ���۸��� ����Ͽ� ����ȭ�� ��� (1-4)
	public void paint(Graphics g) {
		bufferImage = createImage(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT); //ȭ�� ũ�⸸�� �̹��� ����
		screenGraphic = bufferImage.getGraphics(); //screenGraphic�� bufferImage�� Graphics ��ü�� �ޤ���
		screenDraw(screenGraphic); //screenGraphic�� �׸��� �׸�(�׸��� �ؿ� public void screenDraw(Graphics g)���� �׸�)
		g.drawImage(bufferImage, 0, 0, null); //���̴� ȭ�鿡 �׸��� ���
	}
	
	public void screenDraw(Graphics g) {
		if(isMainScreen == true) {//���� ȭ�� ����
			g.drawImage(mainScreen, 0, 0, null);
			this.repaint();
		}
		else if(isGameScreen == true) { //���� ȭ�� ����
			g.drawImage(gameScreen, 0, 0, null);
			game.gameDraw(g);
			this.repaint();
		}
		
	}
	

	//Ű �Է� �ޱ�
	class KeyListener extends KeyAdapter {
		//������ ���� ��
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
				case KeyEvent.VK_ENTER: //���� ������ ���� ȭ������ �Ѿ
					GameStart2();
					break;
				case KeyEvent.VK_ESCAPE: //ESC������ ����(1-5)
					System.exit(0);
					break;
			}
			
		}
		//���� ��
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







