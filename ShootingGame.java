package shoot;

import java.util.Timer;
import java.util.TimerTask;

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
	private Game game = new Game();
	
	

	
	//����ȭ�� �̹��� (1-3)
	private Image mainScreen = new ImageIcon("src/images/main_screen.gif").getImage();
	private Image loadingScreen = new ImageIcon("src/images/loading_screen.gif").getImage();
	private Image gameScreen = new ImageIcon("src/images/game_screen.gif").getImage();
	private Image potionImage = new ImageIcon("src/images/potion.png").getImage();
	private int potionCount = 4;

	
	//� ȭ�� ����� ����(true�� ȭ���� �߰� �����)
	private boolean isMainScreen = false, isLoadingScreen = false, isGameScreen = false;
	
	private Audio backgroundMusic;
	
	GamePanel gamePanel = new GamePanel();
	public int p;
	
	//���� ������ ���(������) (1-1)
	public ShootingGame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		setResizable(false); 
		setTitle("Java Escape"); 
		
		add(gamePanel); 
		
		pack(); 
		
		setLocationRelativeTo(null);  
		setVisible(true); 
		
		
		gamePanel.startGameThread();
	
	}

	//�ΰ��� �������� �� �޼ҵ�
	public void GameStart () {
		//���� ȭ���� ���� ���� isGameScreen�� true�� ��
		isLoadingScreen = true;
		remove(gamePanel);
		setSize(1280,720);
		setLocationRelativeTo(null);
		setTitle("ShootingGame");
		
		Timer loadingTimer = new Timer();
        TimerTask loadingTask = new TimerTask() {
            @Override
            public void run() {
            	isMainScreen = true;
        		isLoadingScreen = false;
        		
        		addKeyListener(new KeyListener()); //���� keyListener �߰��ϱ�(���� �����ϰ�)
        		
        		backgroundMusic = new Audio("src/audio/mainback.wav",true);
        		backgroundMusic.start();
            }
        };
        loadingTimer.schedule(loadingTask, 3000); // 3�� �Ŀ� �ε��� ����
		
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
			for (int i = 0; i < potionCount; i++) {
				int x = 10 + (i * 30);
				int y = 10;
				g.drawImage(potionImage, x, y, null);
			}

			this.repaint();
		}
		else if(isLoadingScreen == true) { //���� ȭ�� ����
			g.drawImage(loadingScreen, 0, 0, null);
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
					p=0;
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
				case KeyEvent.VK_1:
					game.setSkill(true);
					break;
				case KeyEvent.VK_2:
					if(p < 4 &&game.playerHp<100) {
						if(game.playerHp > 50)
							game.playerHp = 100;
						else
							game.playerHp +=50;
						p++;
						potionCount--; // 포션 개수 감소

						if (potionCount == 0) {
							gamePanel.removeKeyListener(this); // KeyListener 비활성화
						}
					}
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
				case KeyEvent.VK_1:
					break;
				case KeyEvent.VK_2:
		               break;
				
	}
			
			
		}
	}
}
