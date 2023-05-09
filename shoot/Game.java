package shoot;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Game extends Thread {
	
	//�÷��̾� �̹���
	private Image player = new ImageIcon("src/images/player.png").getImage();
	
	private int playerX, playerY; //�÷��̾� ĳ���� ��ǥ
	private int playerWidth = player.getWidth(null); //�÷��̾� ĳ���� ���α���
	private int playerHeight = player.getHeight(null); //�÷��̾� ĳ���� ���α���
	private int playerSpeed = 10; //�÷��̾� ĳ���� �̵� �ӵ�
	private int playerHp = 30; //�÷��̾� ĳ���� ü��
	
	private boolean up, down, left, right; //�����̱� ���� ���� ����
	private boolean shooting; //true�� ��� ���� �߻�
	private boolean isOver; //���ӿ��� ����
	
	private Audio backgroundMusic,hitsound,killsound;
	
	private int score = 0; //������ ��Ÿ�� ����
	
	
	int delta,i=1;
	
	
	//�÷��̾��� ������ ���� ArrayList(ArrayList�� ������� �����̱� ������ ���� ����(������ �����ϸ� �ڵ����� ����))
	ArrayList<PlayerAttack> playerAttackList = new ArrayList<PlayerAttack>();
	private PlayerAttack playerAttack; //ArrayList���� ���뿡 ���� ������ �� �ְ� ���� ����
	//���� �̵��� ���� ������ ���� ArrayList
	ArrayList<Enemy> enemyList = new ArrayList<Enemy>(); //가변 
	ArrayList<EnemyAttack> enemyAttackList = new ArrayList<EnemyAttack>();
	private Enemy enemy;
	private EnemyAttack enemyAttack;
	ArrayList<Enemy2> enemy2List = new ArrayList<Enemy2>();
	ArrayList<Enemy2Attack> enemy2AttackList = new ArrayList<Enemy2Attack>();
	private Enemy2 enemy2;
	private Enemy2Attack enemy2Attack;
	ArrayList<Enemy3> enemy3List = new ArrayList<Enemy3>();
	ArrayList<Enemy3Attack> enemy3AttackList = new ArrayList<Enemy3Attack>();
	private Enemy3 enemy3;
	private Enemy3Attack enemy3Attack;
	ArrayList<Enemy4> enemy4List = new ArrayList<Enemy4>();
	ArrayList<Enemy4Attack> enemy4AttackList = new ArrayList<Enemy4Attack>();
	private Enemy4 enemy4;
	private Enemy4Attack enemy4Attack;
	
	//�����尡 �����ϸ�(start()�� ����) run() �޼ҵ� ����
	public void run() {
		/*������ 60���������� ����
		  1/60�ʸ��� ��ġ�� �ٲ�� �˰����� ®��
		  ��Ÿ(���� �ð�(�и���))�� ���� 1000/60�и��ʰ� �� �� ���� 0���� �ʱ�ȭ �ϴ� ���
		 */
		long currentTime;
		long lastTime = System.nanoTime();
		int delta = 0;
		
		//���� ��� ����
		backgroundMusic = new Audio("src/audio/gameback.wav",true);
		hitsound = new Audio("src/audio/hitsound.wav",true);
		killsound = new Audio("src/audio/killsound.wav",true);
		
		reset();
		while(true) {
			while(!isOver) { //isOver�� false�� ���� ���� ����
				currentTime = System.nanoTime();
				delta += (currentTime - lastTime);
				lastTime = currentTime;
				if(delta >= 1000000000/60) {
					keyProcess();
					playerAttackProcess();
					if(score<=100) {
					enemyAppearProcess();
					enemyMoveProcess();
					enemyAttackProcess();
					}
					if(score==200) {
						enemyList.clear();
						enemyAttackList.clear();
					}
					if(score>=200 && score <=300) {
					enemy2AppearProcess();
					enemy2MoveProcess();
					enemy2AttackProcess();
					}
					if(score==300) {
						enemy2List.clear();
						enemy2AttackList.clear();
					}
					if(score>=300 && score < 1300) {
					enemy3AppearProcess();
					enemy3MoveProcess();
					enemy3AttackProcess();
					}
					if(score== 1300) {
						enemy3List.clear();
						enemy3AttackList.clear();
					}
					if(score>=1300) {
						enemy4AppearProcess();
						enemy4MoveProcess();
						enemy4AttackProcess();
					}
					System.out.println(Enemy3.isUltimate);
					i++; //���ݼӵ� ���ϱ� ���� ����
					delta = delta - (1000000000/60);
				}
				
			}
			Thread.interrupted();
			
		}
		
	}
	
	public void reset() {
		i=1;
		playerAttackList.clear();
		enemyList.clear();
		enemyAttackList.clear();
		enemy2List.clear();
		enemy2AttackList.clear();
		enemy3List.clear();
		enemy3AttackList.clear();
		enemy4List.clear();
		enemy4AttackList.clear();
		playerHp = 30;
		playerX = 10;
		playerY = (Main.SCREEN_HEIGHT - playerHeight) / 2;
		score = 0;
		isOver = false;
		backgroundMusic.start();
		
	}
	
	//�����¿� ���� ����(â ������ ĳ���Ͱ� ������ �ʵ��� ���ǵ� �߰���)
	private void keyProcess() {
		if (up == true && playerY - playerSpeed > 0) playerY -= playerSpeed;
		if (down == true && playerY + playerHeight + playerSpeed < Main.SCREEN_HEIGHT) playerY += playerSpeed;
		if (left == true && playerX - playerSpeed > 0) playerX -= playerSpeed;
		if (right == true && playerX + playerWidth + playerSpeed < Main.SCREEN_WIDTH) playerX += playerSpeed;
		//���� �ӵ��� 0.2�ʷ� ����
		if (shooting == true && i % 20 == 0) {
			playerAttack = new PlayerAttack(playerX + 100,playerY - 30); //������ �Ű������� ���� ���� �������� ����
			playerAttackList.add(playerAttack); //�ε����� ��� �߰��ϸ� ����� ��� �÷���(run()���� keyProcess()�� ������ ��� ���� ����)
			
		}
	}
	
	/*�ؿ� ������ �ݺ����鿡 ���� ����
	  >������� ���� �޼ҵ���� �ſ� ������ ���ư��� ������
	  for(int i=0; i < ������.size(); i++) {
	  �������� = ������.get(i);
	  �޼ҵ��
	  }
	  ���� ������ ���� �༮�� �׳� ��� i�鿡 ���� �޼ҵ���� ����ȴٰ� �����ϸ� ���ϴ�
	 */
	
	//���� ������ �����°� ����
	private void playerAttackProcess() {
		for(int i=0; i < playerAttackList.size(); i++) {
			playerAttack = playerAttackList.get(i); 
			playerAttack.fire(); //���� ���������� �̵���Ű�� �޼ҵ�
			
			//���� ����
			if(score <=100) {
			for(int j = 0; j < enemyList.size(); j++) {
				enemy = enemyList.get(j);
				if((playerAttack.x+playerAttack.width>enemy.x&&playerAttack.x+playerAttack.width<enemy.x+enemy.width&&playerAttack.y+playerAttack.height>enemy.y&&playerAttack.y+playerAttack.height<enemy.y+enemy.height)||(playerAttack.x+playerAttack.width>enemy.x&&playerAttack.x+playerAttack.width<enemy.x+enemy.width&&playerAttack.y>enemy.y&&playerAttack.y<enemy.y+enemy.height)) { //��Ʈ�ڽ� ����(��ǥ ������ �׻� ���� ���� ���� �𼭸�)
					enemy.hp -= playerAttack.attack; //������ ����
					playerAttackList.remove(playerAttack); //���� ���� ��ü�� ����
				}
				if(enemy.hp <= 0) {
					killsound.start();
					enemyList.remove(enemy);
					score += 100;
				}
					
			}
			}
			if(score >= 200 && score <= 300) {
				for(int j = 0; j < enemy2List.size(); j++) {
					enemy2 = enemy2List.get(j);
					if((playerAttack.x+playerAttack.width>enemy2.x&&playerAttack.x+playerAttack.width<enemy2.x+enemy2.width&&playerAttack.y+playerAttack.height>enemy2.y&&playerAttack.y+playerAttack.height<enemy2.y+enemy2.height)||(playerAttack.x+playerAttack.width>enemy2.x&&playerAttack.x+playerAttack.width<enemy2.x+enemy2.width&&playerAttack.y>enemy2.y&&playerAttack.y<enemy2.y+enemy2.height)) { //��Ʈ�ڽ� ����(��ǥ ������ �׻� ���� ���� ���� �𼭸�)
						enemy2.hp -= playerAttack.attack; //������ ����
						playerAttackList.remove(playerAttack); //���� ���� ��ü�� ����
					}
					if(enemy2.hp <= 0) {
						killsound.start();
						enemy2List.remove(enemy2);
						score += 100;
					}
						
				}
				
			}
			
			if(score >= 300 && score < 1300) {
				for(int j = 0; j < enemy3List.size(); j++) {
					enemy3 = enemy3List.get(j);
					if((playerAttack.x+playerAttack.width>enemy3.x&&playerAttack.x+playerAttack.width<enemy3.x+enemy3.width&&playerAttack.y+playerAttack.height>enemy3.y&&playerAttack.y+playerAttack.height<enemy3.y+enemy3.height)||(playerAttack.x+playerAttack.width>enemy3.x&&playerAttack.x+playerAttack.width<enemy3.x+enemy3.width&&playerAttack.y>enemy3.y&&playerAttack.y<enemy3.y+enemy3.height)) { //��Ʈ�ڽ� ����(��ǥ ������ �׻� ���� ���� ���� �𼭸�)
						enemy3.hp -= playerAttack.attack; //������ ����
						playerAttackList.remove(playerAttack); //���� ���� ��ü�� ����
					}
					if(enemy3.hp <= 0) {
						killsound.start();
						enemy3List.remove(enemy3);
						score += 1000;
					}
						
				}
				
			}
			if(score >= 1300) {
				for(int j = 0; j < enemy4List.size(); j++) {
					enemy4 = enemy4List.get(j);
					if((playerAttack.x+playerAttack.width>enemy4.x&&playerAttack.x+playerAttack.width<enemy4.x+enemy4.width&&playerAttack.y+playerAttack.height>enemy4.y&&playerAttack.y+playerAttack.height<enemy4.y+enemy4.height)||(playerAttack.x+playerAttack.width>enemy4.x&&playerAttack.x+playerAttack.width<enemy4.x+enemy4.width&&playerAttack.y>enemy4.y&&playerAttack.y<enemy4.y+enemy4.height)) { //��Ʈ�ڽ� ����(��ǥ ������ �׻� ���� ���� ���� �𼭸�)
						enemy4.hp -= playerAttack.attack; //������ ����
						playerAttackList.remove(playerAttack); //���� ���� ��ü�� ����
					}
					if(enemy4.hp <= 0) {
						killsound.start();
						enemy4List.remove(enemy4);
						score += 100;
					}
						
				}
				
			}
		}
	}

	//적 등장
	private void enemyAppearProcess() {
		if(i % 160 == 0) {
			enemy = new Enemy(1120,(int)(Math.random()*476));
			enemyList.add(enemy);
		}
	}
	
	
	//적 이동구현
	private void enemyMoveProcess() {
		for(int i= 0; i<enemyList.size(); i++) {
			enemy = enemyList.get(i);
			enemy.move(); //적 이동
				
		}
	}
	
	//적 공격
	private void enemyAttackProcess() {
		if(i % 100 == 0 && enemyList.size() > 0) { //enemyList.size() > 0 ���� ���� : ������ �� ��ġ �������� ������ ������ ���� ������ ���� ������ ������ ������ �߻��Ѵ�
			for(int j = 0; j < enemyList.size(); j++) { //enemyAttackList�� �� ��ü ����ŭ enemyAttack�� �߰��ϱ� ���� �ݺ��� �߰�
			enemyAttack = new EnemyAttack(enemyList.get(j).x - 79, enemyList.get(j).y + 35); //�� ��ü ���� ���� ������ �� EnemyAttack ���� ��ǥ�� enemy.x, enemy.y�� �θ� �������� ���� �����Լ� EnemyAttack�� ��ø���� ���´�. ���� ��ǥ�� enemyList�� �� �ε��� ������ xy��ǥ�� �־� ���� EnemyAttack�� ���� �ϳ��� ������. 
			enemyAttackList.add(enemyAttack);
			}
		}
		
		for(int i = 0; i < enemyAttackList.size(); i++) {
			enemyAttack = enemyAttackList.get(i);
			enemyAttack.fire();
			
			if((enemyAttack.x<playerX&&enemyAttack.x+enemyAttack.width>playerX&&enemyAttack.y+enemyAttack.height>playerY&&enemyAttack.y+enemyAttack.height<playerY+playerHeight)||(enemyAttack.x<playerX+playerWidth&&enemyAttack.x>playerX&&enemyAttack.y>playerY&&enemyAttack.y<playerY+playerHeight)) {//적 공격 피격범위
				hitsound.start();
				playerHp -= enemyAttack.attack;
				enemyAttackList.remove(enemyAttack);
			}
			if(playerHp <= 0) {
				isOver = true;
			}
					
			
					
		}
	}
	
	
	
	private void enemy2AppearProcess() {
		if(i % 160 == 0) {
			enemy2 = new Enemy2(1120,(int)(Math.random()*476));
			enemy2List.add(enemy2);
		}
	}
	
	
	//���� �̵� ����
	private void enemy2MoveProcess() {
		for(int i= 0; i<enemy2List.size(); i++) {
			enemy2 = enemy2List.get(i);
			enemy2.move(); //�� �̵�
		}
	}
	
	private void enemy2AttackProcess() {
		if(i % 120 == 0 && enemy2List.size() > 0) {
			for(int j = 0; j < enemy2List.size(); j++) {
			enemy2Attack = new Enemy2Attack(enemy2List.get(j).x - 100 , enemy2List.get(j).y + 75);
			enemy2AttackList.add(enemy2Attack);
			}
		}
		
		for(int i = 0; i < enemy2AttackList.size(); i++) {
			enemy2Attack = enemy2AttackList.get(i);
			enemy2Attack.fire();
			
			if((enemy2Attack.x<playerX+playerWidth&&enemy2Attack.x>playerX&&enemy2Attack.y+enemy2Attack.height>playerY&&enemy2Attack.y+enemy2Attack.height<playerY+playerHeight)||(enemy2Attack.x<playerX+playerWidth&&enemy2Attack.x>playerX&&enemy2Attack.y>playerY&&enemy2Attack.y<playerY+playerHeight)) {
				hitsound.start();
				playerHp -= enemy2Attack.attack;
				enemy2AttackList.remove(enemy2Attack);
			}
			if(playerHp <= 0) {
				isOver = true;
			}
						
		}
		
	}
	
	private void enemy3AppearProcess() {
		if(enemy3List.size() == 0) {
			enemy3 = new Enemy3(1120, 133);
			enemy3List.add(enemy3);
		}
	}
	
	
	//���� �̵� ����
	private void enemy3MoveProcess() {
		for(int i= 0; i<enemy3List.size(); i++) {
			enemy3 = enemy3List.get(i);
			if(enemy3.x >800)
			enemy3.move(); //�� �̵�
		}
	}
	
	private void enemy3AttackProcess() {
		if(i % 120 == 0 && enemy3List.size() > 0) {
			for(int j = 0; j < enemy3List.size(); j++) {
			enemy3Attack = new Enemy3Attack(enemy3List.get(j).x - 100 , enemy3List.get(j).y + 75);
			enemy3AttackList.add(enemy3Attack);
			}
		}
		
		for(int i = 0; i < enemy3AttackList.size(); i++) {
			enemy3Attack = enemy3AttackList.get(i);
			enemy3Attack.fire();
			
			if((playerX+playerWidth>enemy3Attack.x&&playerX+playerWidth<enemy3Attack.x+enemy3Attack.width&&playerY+playerHeight>enemy3Attack.y&&playerY+playerHeight<enemy3Attack.y+enemy3Attack.height)||(playerX+playerWidth>enemy3Attack.x&&playerX+playerWidth<enemy3Attack.x+enemy3Attack.width&&playerY>enemy3Attack.y&&playerY<enemy3Attack.y+enemy3Attack.height)) {
				hitsound.start();
				playerHp -= enemy3Attack.attack;
				enemy3AttackList.remove(enemy3Attack);
			}
			if(playerHp <= 0) {
				isOver = true;
			}
			if(enemy3.hp == 50) {
				Enemy3.isUltimate = true;
				enemy3.hp = 262;
			}
					
			
					
		}
		
	}
	private void enemy4AppearProcess() {
		if(i % 160 == 0) {
			enemy4 = new Enemy4(1120,(int)(Math.random()*476));
			enemy4List.add(enemy4);
		}
	}
	
	
	//���� �̵� ����
	private void enemy4MoveProcess() {
		for(int i= 0; i<enemy4List.size(); i++) {
			enemy4 = enemy4List.get(i);
			enemy4.move(); //�� �̵�
		}
	}
	
	private void enemy4AttackProcess() {
		if(i % 120 == 0 && enemy4List.size() > 0) {
			for(int j = 0; j < enemy4List.size(); j++) {
			enemy4Attack = new Enemy4Attack(enemy4List.get(j).x - 100 , enemy4List.get(j).y + 75);
			enemy4AttackList.add(enemy4Attack);
			}
		}
		
		for(int i = 0; i < enemy4AttackList.size(); i++) {
			enemy4Attack = enemy4AttackList.get(i);
			enemy4Attack.fire();
			
			if((enemy4Attack.x<playerX+playerWidth&&enemy4Attack.x>playerX&&enemy4Attack.y+enemy4Attack.height>playerY&&enemy4Attack.y+enemy4Attack.height<playerY+playerHeight)||(enemy4Attack.x<playerX+playerWidth&&enemy4Attack.x>playerX&&enemy4Attack.y>playerY&&enemy4Attack.y<playerY+playerHeight)) {
				hitsound.start();
				playerHp -= enemy4Attack.attack;
				enemy4AttackList.remove(enemy4Attack);
			}
			if(playerHp <= 0) {
				isOver = true;
			}
						
		}
		
	}

	 
	 
	//게임화면 캐릭터 그리기
	public void gameDraw(Graphics g) {
		playerDraw(g);
		enemyDraw(g);
		enemy2Draw(g);
		enemy3Draw(g);
		enemy4Draw(g);
		infoDraw(g);
	}

	public void infoDraw(Graphics g) {
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.BOLD, 40));
		g.drawString("SCORE : " + score, 40, 80);
		if(isOver == true) {
			g.setColor(Color.BLACK);
			g.setFont(new Font("Arial", Font.BOLD, 80));
			g.drawString("Press R to restart", 295, 380);
		}
		
	}
	
	//플레이어 그림
	public void playerDraw(Graphics g) {
		g.drawImage(player,  playerX, playerY, null);
		g.setColor(Color.GREEN);
		g.fillRect(playerX - 1, playerY - 40, playerHp *6, 20);
		
		//플레이어 공격
		for(int i=0; i < playerAttackList.size(); i++) {
			playerAttack = playerAttackList.get(i);
			g.drawImage(playerAttack.attackImage, playerAttack.x, playerAttack.y, null);
		}
	}
	
	public void enemyDraw(Graphics g) {
		for(int i= 0; i < enemyList.size(); i++) {
			enemy = enemyList.get(i);
			g.drawImage(enemy.image, enemy.x, enemy.y, null);
			g.setColor(Color.red);
			g.fillRect(enemy.x - 1, enemy.y - 40, enemy.hp *7, 20);
			
		}
		for(int i = 0; i < enemyAttackList.size(); i++) {
			enemyAttack = enemyAttackList.get(i);
			g.drawImage(enemyAttack.image, enemyAttack.x, enemyAttack.y, null);
		}
	}
	
	public void enemy2Draw(Graphics g) {
		for(int i= 0; i < enemy2List.size(); i++) {
			enemy2 = enemy2List.get(i);
			g.drawImage(enemy2.image, enemy2.x, enemy2.y, null);
			g.setColor(Color.red);
			g.fillRect(enemy2.x - 1, enemy2.y - 40, enemy2.hp *7, 20);
			
		}
		for(int i = 0; i < enemy2AttackList.size(); i++) {
			enemy2Attack = enemy2AttackList.get(i);
			g.drawImage(enemy2Attack.image, enemy2Attack.x, enemy2Attack.y, null);
		}
		
	}
	
	public void enemy3Draw(Graphics g) {
		for(int i= 0; i < enemy3List.size(); i++) {
			enemy3 = enemy3List.get(i);
			if(Enemy3.isUltimate == false)
			g.drawImage(enemy3.image, enemy3.x, enemy3.y, null);
			else if(Enemy3.isUltimate == true)
			g.drawImage(enemy3.image2, enemy3.x, enemy3.y, null);
			
			g.setColor(Color.red);
			g.fillRect(enemy3.x - 1, enemy3.y - 40, (int)(enemy3.hp * 1.5) , 20);
			
		}
		for(int i = 0; i < enemy3AttackList.size(); i++) {
			enemy3Attack = enemy3AttackList.get(i);
			g.drawImage(enemy3Attack.image, enemy3Attack.x, enemy3Attack.y, null);			
		}
		
	}
	public void enemy4Draw(Graphics g) {
		for(int i= 0; i < enemy4List.size(); i++) {
			enemy4 = enemy4List.get(i);
			g.drawImage(enemy4.image, enemy4.x, enemy4.y, null);
			g.setColor(Color.red);
			g.fillRect(enemy4.x - 1, enemy4.y - 40, enemy4.hp *7, 20);
			
		}
		for(int i = 0; i < enemy4AttackList.size(); i++) {
			enemy4Attack = enemy4AttackList.get(i);
			g.drawImage(enemy4Attack.image, enemy4Attack.x, enemy4Attack.y, null);
		}
		
	}
	

	public boolean isOver() {
		return isOver;
	}

	//up, down, left, right�� private�̱� ������ setter�� �̿���
	public void setUp(boolean up) {
		this.up = up;
	}

	public void setDown(boolean down) {
		this.down = down;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

	public void setRight(boolean right) {
		this.right = right;
	}

	public void setShooting(boolean shooting) {
		this.shooting = shooting;
	}
	
	
	

}
