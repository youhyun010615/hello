package shoot;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;

public class Game extends Thread {
	
	//플레이어 이미지
	private Image player = new ImageIcon("src/images/player.png").getImage();
	
	private int playerX, playerY; //플레이어 캐릭터 좌표
	private int playerWidth = player.getWidth(null); //플레이어 캐릭터 가로길이
	private int playerHeight = player.getHeight(null); //플레이어 캐릭터 세로길이
	private int playerSpeed = 10; //플레이어 캐릭터 이동 속도
	double playerHp; //플레이어 캐릭터 체력
	private int playerAttackSpeed = 20; //플레이어 공격 속도(낮을수록 증가)
	
	
	private boolean up, down, left, right; //움직이기 위해 변수 선언
	private boolean shooting; //true일 경우 공격 발사
	public boolean isOver,isEnd; //게임오버 여부
	private boolean skill; //스킬 사용 여부
	private boolean onetimeUltimate = true; //isUltimate 한 번만 사용되게
	
	private Audio backgroundMusic,backgroundMusic2,fireEagleBackSound,hitsound,killsound,enemykillsound,enemyshotsound,enemy2killsound
	,enemy2shotsound,enemy3killsound,enemy3shotsound,enemy3shot2sound,enemy3shot3sound,enemy3ultimatesound
	,enemy5killsound,enemy5shotsound,enemy6shotsound,enemy6shot2sound,nunu_e_sound,nunu_r_sound
	,nunu_w_sound,nunu_appear_sound,nunu_die_sound,enemy7_thunder_sound,enemy7_wind_sound
	,enemy7_firebreath_sound,enemy7_tornado_sound,enemy7_eagleattack_sound;
	
	public int score = 0; //점수를 나타낼 변수
	
	
	int delta;
	static int i,j = 1;
	
	
	
	
	
	
	//플레이어의 공격을 담을 ArrayList(ArrayList의 사이즈는 가변이기 때문에 쓰기 좋음(사이즈 부족하면 자동으로 증가))
	ArrayList<PlayerAttack> playerAttackList = new ArrayList<PlayerAttack>();
	private PlayerAttack playerAttack; //ArrayList안의 내용에 쉽게 접근할 수 있게 변수 선언
	
	ArrayList<SkillAttack> skillAttackList = new ArrayList<SkillAttack>();
	private SkillAttack skillAttack;
	
	//적의 이동과 적의 공격을 담을 ArrayList
	ArrayList<Enemy> enemyList = new ArrayList<Enemy>();
	ArrayList<EnemyAttack> enemyAttackList = new ArrayList<EnemyAttack>();
	private Enemy enemy;
	private EnemyAttack enemyAttack;
	
	ArrayList<Enemy2> enemy2List = new ArrayList<Enemy2>();
	ArrayList<Enemy2Attack> enemy2AttackList = new ArrayList<Enemy2Attack>();
	private Enemy2 enemy2;
	private Enemy2Attack enemy2Attack;
	
	ArrayList<Enemy4> enemy4List = new ArrayList<Enemy4>();
	ArrayList<Enemy4Attack> enemy4AttackList = new ArrayList<Enemy4Attack>();
	ArrayList<Enemy4Attack2> enemy4Attack2List = new ArrayList<Enemy4Attack2>();
	ArrayList<Enemy4Attack3> enemy4Attack3List = new ArrayList<Enemy4Attack3>();
	ArrayList<Enemy4Attack3Warning> enemy4Attack3WarningList = new ArrayList<Enemy4Attack3Warning>();
	ArrayList<Enemy4EmptyAttack3> enemy4EmptyAttack3List = new ArrayList<Enemy4EmptyAttack3>();
	private Enemy4 enemy4;
	private Enemy4Attack enemy4Attack;
	private Enemy4Attack2 enemy4Attack2;
	private Enemy4Attack3 enemy4Attack3;
	private Enemy4Attack3Warning enemy4Attack3Warning;
	private Enemy4EmptyAttack3 enemy4EmptyAttack3;
	
	ArrayList<Enemy3> enemy3List = new ArrayList<Enemy3>();
	ArrayList<Enemy3Attack> enemy3AttackList = new ArrayList<Enemy3Attack>();
	ArrayList<Enemy3Attack2> enemy3Attack2List = new ArrayList<Enemy3Attack2>();
	ArrayList<Enemy3Attack3> enemy3Attack3List = new ArrayList<Enemy3Attack3>();
	private Enemy3 enemy3;
	private Enemy3Attack enemy3Attack;
	private Enemy3Attack2 enemy3Attack2;
	private Enemy3Attack3 enemy3Attack3;
	
	ArrayList<Enemy5> enemy5List = new ArrayList<Enemy5>();
	ArrayList<Enemy5Attack> enemy5AttackList = new ArrayList<Enemy5Attack>();
	private Enemy5 enemy5;
	private Enemy5Attack enemy5Attack;

	ArrayList<Enemy6> enemy6List = new ArrayList<Enemy6>();
	ArrayList<Enemy6Attack> enemy6AttackList = new ArrayList<Enemy6Attack>();
	ArrayList<Enemy6EmptyAttack> enemy6EmptyAttackList = new ArrayList<Enemy6EmptyAttack>();
	ArrayList<Enemy6Attack2> enemy6Attack2List = new ArrayList<Enemy6Attack2>();
	ArrayList<Enemy6Warning> enemy6WarningList = new ArrayList<Enemy6Warning>();
	ArrayList<Enemy6Attack3> enemy6Attack3List = new ArrayList<Enemy6Attack3>();
	private Enemy6 enemy6;
	private Enemy6Attack enemy6Attack;
	private Enemy6EmptyAttack enemy6EmptyAttack;
	private Enemy6Attack2 enemy6Attack2;
	private Enemy6Warning enemy6Warning;
	private Enemy6Attack3 enemy6Attack3;
	
	ArrayList<Enemy7> enemy7List = new ArrayList<Enemy7>();
	ArrayList<Enemy7Attack> enemy7AttackList = new ArrayList<Enemy7Attack>();
	ArrayList<Enemy7Attack2> enemy7Attack2List = new ArrayList<Enemy7Attack2>();
	ArrayList<Enemy7Attack3> enemy7Attack3List = new ArrayList<Enemy7Attack3>();
	ArrayList<Enemy7Attack4> enemy7Attack4List = new ArrayList<Enemy7Attack4>();
	ArrayList<Enemy7Attack5> enemy7Attack5List = new ArrayList<Enemy7Attack5>();
	ArrayList<Enemy7Attack6> enemy7Attack6List = new ArrayList<Enemy7Attack6>();
	ArrayList<Enemy7EmptyAttack> enemy7EmptyAttackList = new ArrayList<Enemy7EmptyAttack>();
	ArrayList<Enemy7Warning> enemy7WarningList = new ArrayList<Enemy7Warning>();
	ArrayList<Enemy7Wind> enemy7WindList = new ArrayList<Enemy7Wind>();
	private Enemy7 enemy7;
	private Enemy7Attack enemy7Attack;
	private Enemy7Attack2 enemy7Attack2;
	private Enemy7Attack3 enemy7Attack3;
	private Enemy7Attack4 enemy7Attack4;
	private Enemy7Attack5 enemy7Attack5;
	private Enemy7Attack6 enemy7Attack6;
	private Enemy7EmptyAttack enemy7EmptyAttack;
	private Enemy7Warning enemy7Warning;
	private Enemy7Wind enemy7Wind;
	
	//쓰레드가 시작하면(start()를 통해) run() 메소드 실행
	public void run() {
		/*게임을 60프레임으로 설정
		  1/60초마다 위치가 바뀌게 알고리즘을 짰음
		  델타(지난 시간(밀리초))의 값이 1000/60밀리초가 될 때 마다 0으로 초기화 하는 방식
		 */
		long currentTime;
		long lastTime = System.nanoTime();
		int delta = 0;
		
		backgroundMusic = new Audio("src/audio/gameback.wav",true);
		backgroundMusic2 = new Audio("src/audio/EagleBackSound.wav",true);
		fireEagleBackSound = new Audio("src/audio/fireEagleBackSound.wav",true);
		hitsound = new Audio("src/audio/hitsound.wav",true);
		killsound = new Audio("src/audio/killsound.wav",true);
		enemykillsound = new Audio("src/audio/enemykillsound.wav",true);
		enemyshotsound = new Audio("src/audio/enemyshotsound.wav",true);
		enemy2killsound = new Audio("src/audio/enemy2killsound.wav",true);
		enemy2shotsound = new Audio("src/audio/enemy2shotsound.wav",true);
		enemy3killsound = new Audio("src/audio/enemy3killsound.wav",true);
		enemy3shotsound = new Audio("src/audio/enemy3shotsound.wav",true);
		enemy3shot2sound = new Audio("src/audio/enemy3shot2sound.wav",true);
		enemy3shot3sound = new Audio("src/audio/enemy3shot3sound.wav",true);
		enemy3ultimatesound = new Audio("src/audio/enemy3ultimatesound.wav",true);
		enemy5killsound = new Audio("src/audio/enemy5killsound.wav",true);
		enemy5shotsound = new Audio("src/audio/enemy5shotsound.wav",true);
		enemy6shotsound = new Audio("src/audio/enemy6shotsound.wav",true);
		enemy6shot2sound = new Audio("src/audio/enemy6shot2sound.wav",true);
		nunu_e_sound = new Audio("src/audio/nunu_e.wav",true);
		nunu_r_sound = new Audio("src/audio/nunu_r.wav",true);
		nunu_w_sound = new Audio("src/audio/nunu_w.wav",true);
		nunu_appear_sound = new Audio("src/audio/nunu_appear.wav",true);
		nunu_die_sound = new Audio("src/audio/nunu_die.wav",true);
		enemy7_thunder_sound = new Audio("src/audio/enemy7_thunder.wav",true);
		enemy7_wind_sound = new Audio("src/audio/enemy7_wind.wav",true);
		enemy7_firebreath_sound = new Audio("src/audio/enemy7_firebreath.wav",true);
		enemy7_tornado_sound = new Audio("src/audio/enemy7_tornado.wav",true);
		enemy7_eagleattack_sound = new Audio("src/audio/enemy7_eagleattack.wav",true);
		
		boolean isEagleBackSound = true,isFireEagleBackSound = true;
		
		reset();
		while(true) {
			while(!isOver) { //isOver이 false일 동안 무한 루프
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
						if(score>=200 && score <300) {
						enemy2AppearProcess();
						enemy2MoveProcess();
						enemy2AttackProcess();
						}
						if(score==300) {
							enemy2List.clear();
							enemy2AttackList.clear();
						}
						if(score>=300 && score < 1300) {
							enemy4AppearProcess();
							enemy4MoveProcess();
							enemy4AttackProcess();
							enemy4Attack2Process();
							enemy4Attack3Process();
							}
						if(score==1300) {
							enemy4List.clear();
							enemy4AttackList.clear();
							enemy4Attack2List.clear();
							enemy4Attack3List.clear();
						}
							
						if(score>=1300 && score < 2300) {
						enemy3AppearProcess();
						enemy3MoveProcess();
						enemy3AttackProcess();
						enemy3Attack2Process();
						enemy3Attack3Process();
						}
						if(score==2300) {
							enemy3List.clear();
							enemy3AttackList.clear();
							enemy3Attack2List.clear();
							enemy3Attack3List.clear();
							}
						if(score>=2300 && score < 2500) {
							enemy5AppearProcess();
							enemy5MoveProcess();
							enemy5AttackProcess();
						}
						if(score==2500) {
							enemy5List.clear();
							enemy5AttackList.clear();
							}
						
						if(score>=2500 && score < 3500) {
							enemy6AppearProcess();
							enemy6MoveProcess();
							enemy6AttackProcess();
							enemy6Attack2Process();
							enemy6Attack3Process();
						}
						if(score==3500) {
							enemy6List.clear();
							enemy6AttackList.clear();
							enemy6Attack2List.clear();
							enemy6Attack3List.clear();
							}
						if(score >= 3500 && isEnd == false) {
							if(isEagleBackSound) {
								backgroundMusic.stop();
								backgroundMusic2.start();
								isEagleBackSound = false;
							}
							enemy7AppearProcess();
							enemy7MoveProcess();
							if(enemy7.hp >= 1000) {
								enemy7Attack4Process();
								enemy7Attack5Process();
							}
							else if(enemy7.hp >= 400 && playerHp < 1000) {
								enemy7Attack4List.clear();
								enemy7Attack5List.clear();
								enemy7AttackProcess();
								enemy7Attack3Process();
								enemy7WindProcess();
							}
							else if(enemy7.hp < 400) {
								if(isFireEagleBackSound) {
									fireEagleBackSound.start();
									isFireEagleBackSound = false;
								}
								enemy7AttackList.clear();
								enemy7Attack2Process();
								enemy7Attack3Process();
								enemy7Attack6Process();
								enemy7WindProcess();
							}
						}
						
						if(playerHp<=50) playerHp=100; 
						System.out.println(Math.sin(Math.toRadians(45)));	
						i++; //공격속도 정하기 위해 만듦
						delta = delta - (1000000000/60);
	
					
				}
				
				
			}
			try {
				sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
	
	public void reset() {
		playerAttackList.clear();
		enemyList.clear();
		enemyAttackList.clear();
		enemy2List.clear();
		enemy2AttackList.clear();
		enemy4List.clear();
		enemy4AttackList.clear();
		enemy4Attack2List.clear();
		enemy4Attack3List.clear();
		enemy3List.clear();
		enemy3AttackList.clear();
		enemy3Attack2List.clear();
		enemy3Attack3List.clear();
		enemy5List.clear();
		enemy5AttackList.clear();
		enemy6List.clear();
		enemy6AttackList.clear();
		enemy6Attack2List.clear();
		enemy6WarningList.clear();
		enemy7List.clear();
		enemy7AttackList.clear();
		enemy7Attack2List.clear();
		enemy7Attack3List.clear();
		enemy7Attack4List.clear();
		enemy7Attack5List.clear();
		enemy7WindList.clear();
		fireBreath=false;
		enemy7Attack3List.clear();
		enemy7WarningList.clear();
		skillAttackList.clear();
		
		i=1;
		
		playerHp = 100;
		playerX = 10;
		playerY = (Main.SCREEN_HEIGHT - playerHeight) / 2;
		playerAttackSpeed = 20;
		score = 0;
		Enemy3.isUltimate = false;
		isOver = false;
		isEnd = false;
		backgroundMusic.start();
		backgroundMusic2.stop();
		fireEagleBackSound.stop();
		
	}
	
	
	
	//상하좌우 조작 설정(창 밖으로 캐릭터가 나가지 않도록 조건도 추가함)
	private void keyProcess() {
		if (up == true && playerY - playerSpeed > 0) playerY -= playerSpeed;
		if (down == true && playerY + playerHeight + playerSpeed < Main.SCREEN_HEIGHT) playerY += playerSpeed;
		if (left == true && playerX - playerSpeed > 0) playerX -= playerSpeed;
		if (right == true && playerX + playerWidth + playerSpeed < Main.SCREEN_WIDTH) playerX += playerSpeed;
		//공격 속도를 0.2초로 설정
		if (shooting == true && i % playerAttackSpeed == 0) {
			if(score >= 2300) playerAttackSpeed = 10;
			playerAttack = new PlayerAttack(playerX + 70,playerY + 20); //생성자 매개변수를 통해 공격 생성지점 설정
			playerAttackList.add(playerAttack); //인덱스에 계속 추가하며 사이즈를 계속 늘려감(run()에서 keyProcess()가 빠르게 계속 돌기 때문)
			
		}
	}
	
	/*밑에 나오는 반복문들에 대한 이해
	  >쓰레드로 인해 메소드들이 매우 빠르게 돌아가기 때문에
	  for(int i=0; i < ㅁㅁㅁ.size(); i++) {
	  ㅁㅁㅁㅁ = ㅁㅁㅁ.get(i);
	  메소드들
	  }
	  같은 구조를 가진 녀석은 그냥 모든 i들에 대해 메소드들이 실행된다고 생각하면 편하다
	 */
	
	//공격 앞으로 나가는거 구현
	private void playerAttackProcess() {
		for(int i=0; i < playerAttackList.size(); i++) {
			playerAttack = playerAttackList.get(i); 
			playerAttack.fire(); //공격 오른쪽으로 이동시키는 메소드
			
			//공격 판정
			if(score <=100) {
			for(int j = 0; j < enemyList.size(); j++) {
				enemy = enemyList.get(j);
				if((playerAttack.x+playerAttack.width>enemy.x&&playerAttack.x+playerAttack.width<enemy.x+enemy.width&&playerAttack.y+playerAttack.height>enemy.y&&playerAttack.y+playerAttack.height<enemy.y+enemy.height)||(playerAttack.x+playerAttack.width>enemy.x&&playerAttack.x+playerAttack.width<enemy.x+enemy.width&&playerAttack.y>enemy.y&&playerAttack.y<enemy.y+enemy.height)) { //히트박스 범위(좌표 기준은 항상 가장 좌측 위에 모서리)
					enemy.hp -= playerAttack.attack; //에너지 깎음
					playerAttackList.remove(playerAttack); //맞춘 공격 물체는 삭제
				}
				if(enemy.hp <= 0) {
					enemykillsound.start();
					enemyList.remove(enemy);
					score += 100;
				}
					
			}
			}
			if(score >= 200 && score <= 300) {
				for(int j = 0; j < enemy2List.size(); j++) {
					enemy2 = enemy2List.get(j);
					if((playerAttack.x+playerAttack.width>enemy2.x&&playerAttack.x+playerAttack.width<enemy2.x+enemy2.width&&playerAttack.y+playerAttack.height>enemy2.y&&playerAttack.y+playerAttack.height<enemy2.y+enemy2.height)||(playerAttack.x+playerAttack.width>enemy2.x&&playerAttack.x+playerAttack.width<enemy2.x+enemy2.width&&playerAttack.y>enemy2.y&&playerAttack.y<enemy2.y+enemy2.height)) { //히트박스 범위(좌표 기준은 항상 가장 좌측 위에 모서리)
						enemy2.hp -= playerAttack.attack; //에너지 깎음
						playerAttackList.remove(playerAttack); //맞춘 공격 물체는 삭제
					}
					if(enemy2.hp <= 0) {
						enemy2killsound.start();
						enemy2List.remove(enemy2);
						score += 100;
					}
						
				}
				
			}
			
			if(score >= 300 && score <= 1300) {
				for(int j = 0; j < enemy4List.size(); j++) {
					enemy4 = enemy4List.get(j);
					if((playerAttack.x+playerAttack.width>enemy4.x&&playerAttack.x+playerAttack.width<enemy4.x+enemy4.width&&playerAttack.y+playerAttack.height>enemy4.y&&playerAttack.y+playerAttack.height<enemy4.y+enemy4.height)||(playerAttack.x+playerAttack.width>enemy4.x&&playerAttack.x+playerAttack.width<enemy4.x+enemy4.width&&playerAttack.y>enemy4.y&&playerAttack.y<enemy4.y+enemy4.height)) { //��Ʈ�ڽ� ����(��ǥ ������ �׻� ���� ���� ���� �𼭸�)
						enemy4.hp -= playerAttack.attack; 
						playerAttackList.remove(playerAttack); 
					}
				
					if(enemy4.hp <= 0) {
						nunu_die_sound.start();
						enemy4List.remove(enemy4);
						score += 1000;
					}
						
				}
				
				
			}
			
			if(score >= 1300 && score < 2300) {
				for(int j = 0; j < enemy3List.size(); j++) {
					enemy3 = enemy3List.get(j);
					if((playerAttack.x+playerAttack.width>enemy3.x&&playerAttack.x+playerAttack.width<enemy3.x+enemy3.width&&playerAttack.y+playerAttack.height>enemy3.y&&playerAttack.y+playerAttack.height<enemy3.y+enemy3.height)||(playerAttack.x+playerAttack.width>enemy3.x&&playerAttack.x+playerAttack.width<enemy3.x+enemy3.width&&playerAttack.y>enemy3.y&&playerAttack.y<enemy3.y+enemy3.height)) { //히트박스 범위(좌표 기준은 항상 가장 좌측 위에 모서리)
						enemy3.hp -= playerAttack.attack; //에너지 깎음
						playerAttackList.remove(playerAttack); //맞춘 공격 물체는 삭제
					}
					if(enemy3.hp <= 0) {
						enemy3killsound.start();
						enemy3List.remove(enemy3);
						score += 1000;
					}
						
				}
				
			}
			
			if(score >= 2300 && score < 2500) {
				for(int j = 0; j < enemy5List.size(); j++) {
					enemy5 = enemy5List.get(j);
					if((playerAttack.x+playerAttack.width>enemy5.x&&playerAttack.x+playerAttack.width<enemy5.x+enemy5.width&&playerAttack.y+playerAttack.height>enemy5.y&&playerAttack.y+playerAttack.height<enemy5.y+enemy5.height)||(playerAttack.x+playerAttack.width>enemy5.x&&playerAttack.x+playerAttack.width<enemy5.x+enemy5.width&&playerAttack.y>enemy5.y&&playerAttack.y<enemy5.y+enemy5.height)) { //히트박스 범위(좌표 기준은 항상 가장 좌측 위에 모서리)
						enemy5.hp -= playerAttack.attack; //에너지 깎음
						playerAttackList.remove(playerAttack); //맞춘 공격 물체는 삭제
					}
					if(enemy5.hp <= 0) {
						enemy5killsound.start();
						enemy5List.remove(enemy5);
						score += 100;
					}
						
				}
				
			}
			
			if(score >= 2500 && score < 3500) {
				
				for(int j = 0; j < enemy6List.size(); j++) {
					enemy6 = enemy6List.get(j);
					if((playerAttack.x+playerAttack.width>enemy6.x&&playerAttack.x+playerAttack.width<enemy6.x+enemy6.width&&playerAttack.y+playerAttack.height>enemy6.y&&playerAttack.y+playerAttack.height<enemy6.y+enemy6.height)||(playerAttack.x+playerAttack.width>enemy6.x&&playerAttack.x+playerAttack.width<enemy6.x+enemy6.width&&playerAttack.y>enemy6.y&&playerAttack.y<enemy6.y+enemy6.height)) { //占쏙옙트占쌘쏙옙 占쏙옙占쏙옙(占쏙옙표 占쏙옙占쏙옙占쏙옙 占쌓삼옙 占쏙옙占쏙옙 占쏙옙占쏙옙 占쏙옙占쏙옙 占쏜서몌옙)
						enemy6.hp -= playerAttack.attack; 
						playerAttackList.remove(playerAttack); 
					}
					if(enemy6.hp <= 0) {
						killsound.start();
						enemy6List.remove(enemy6);
						score += 1000;
					}
						
				}
			}
			
			if(score >= 3500) {
			for(int j = 0; j < enemy7List.size(); j++) {
				enemy7 = enemy7List.get(j);
				if((playerAttack.x+playerAttack.width>enemy7.x+148&&playerAttack.x+playerAttack.width<enemy7.x+enemy7.width-122&&playerAttack.y+playerAttack.height>enemy7.y+136&&playerAttack.y+playerAttack.height<enemy7.y+enemy7.height-183)||(playerAttack.x+playerAttack.width>enemy7.x+148&&playerAttack.x+playerAttack.width<enemy7.x+enemy7.width-122&&playerAttack.y>enemy7.y+136&&playerAttack.y<enemy7.y+enemy7.height-183)) { //占쏙옙트占쌘쏙옙 占쏙옙占쏙옙(占쏙옙표 占쏙옙占쏙옙占쏙옙 占쌓삼옙 占쏙옙占쏙옙 占쏙옙占쏙옙 占쏙옙占쏙옙 占쏜서몌옙)
					enemy7.hp -= playerAttack.attack; 
					playerAttackList.remove(playerAttack); 
				}
				if(enemy7.hp <= 0) {
					killsound.start();
					enemy7List.remove(enemy7);
					enemy7AttackList.clear();
					enemy7Attack2List.clear();
					enemy7Attack3List.clear();
					enemy7Attack4List.clear();
					enemy7Attack5List.clear();
					enemy7WindList.clear();
					enemy7Attack6List.clear();
					enemy7WarningList.clear();
					backgroundMusic2.stop();
					fireEagleBackSound.stop();
					isEnd = true;
				}
					
			}
		}
		}
		if(skill == true && i % 2 == 0 && skillAttackList.size() <= 50) {
			skillAttack = new SkillAttack(20,(int)(Math.random()*700));
			skillAttackList.add(skillAttack);
		}

		for(int i = 0; i < skillAttackList.size(); i++) {
			skillAttack = skillAttackList.get(i);
			skillAttack.fire();
					
		}
		if(skillAttackList.size() >= 50) {
			skill = false;
		}
		
		for(int i=0; i < skillAttackList.size(); i++) {
			skillAttack = skillAttackList.get(i); 
			skillAttack.fire(); //공격 오른쪽으로 이동시키는 메소드
			
			//공격 판정
			if(score <=100) {
				for(int j = 0; j < enemyList.size(); j++) {
					enemy = enemyList.get(j);
					if((skillAttack.x+skillAttack.width>enemy.x&&skillAttack.x+skillAttack.width<enemy.x+enemy.width&&skillAttack.y+skillAttack.height>enemy.y&&skillAttack.y+skillAttack.height<enemy.y+enemy.height)||(skillAttack.x+skillAttack.width>enemy.x&&skillAttack.x+skillAttack.width<enemy.x+enemy.width&&skillAttack.y>enemy.y&&skillAttack.y<enemy.y+enemy.height)) { //히트박스 범위(좌표 기준은 항상 가장 좌측 위에 모서리)
						enemy.hp -= skillAttack.attack; //에너지 깎음
						skillAttackList.remove(skillAttack); //맞춘 공격 물체는 삭제
					}
					if(enemy.hp <= 0) {
						enemykillsound.start();
						enemyList.remove(enemy);
						score += 100;
					}
						
				}
			}
			if(score >= 200 && score <= 300) {
				for(int j = 0; j < enemy2List.size(); j++) {
					enemy2 = enemy2List.get(j);
					if((skillAttack.x+skillAttack.width>enemy2.x&&skillAttack.x+skillAttack.width<enemy2.x+enemy2.width&&skillAttack.y+skillAttack.height>enemy2.y&&skillAttack.y+skillAttack.height<enemy2.y+enemy2.height)||(skillAttack.x+skillAttack.width>enemy2.x&&skillAttack.x+skillAttack.width<enemy2.x+enemy2.width&&skillAttack.y>enemy2.y&&skillAttack.y<enemy2.y+enemy2.height)) { //히트박스 범위(좌표 기준은 항상 가장 좌측 위에 모서리)
						enemy2.hp -= skillAttack.attack; //에너지 깎음
						skillAttackList.remove(skillAttack); //맞춘 공격 물체는 삭제
					}
					if(enemy2.hp <= 0) {
						enemy2killsound.start();
						enemy2List.remove(enemy2);
						score += 100;
					}
						
				}
				
			}
			
			if(score >= 300 && score <= 1300) {
				for(int j = 0; j < enemy4List.size(); j++) {
					enemy4 = enemy4List.get(j);
					if((skillAttack.x+skillAttack.width>enemy4.x&&skillAttack.x+skillAttack.width<enemy4.x+enemy4.width&&skillAttack.y+skillAttack.height>enemy4.y&&skillAttack.y+skillAttack.height<enemy4.y+enemy4.height)||(skillAttack.x+skillAttack.width>enemy4.x&&skillAttack.x+skillAttack.width<enemy4.x+enemy4.width&&skillAttack.y>enemy4.y&&skillAttack.y<enemy4.y+enemy4.height)) { //��Ʈ�ڽ� ����(��ǥ ������ �׻� ���� ���� ���� �𼭸�)
						enemy4.hp -= skillAttack.attack; 
						skillAttackList.remove(skillAttack); 
					}
					if(enemy4.hp <= 0) {
						enemy4List.remove(enemy4);
						score += 1000;
					}
						
				}
				
			}
			
			if(score >= 1300 && score < 2300) {
				for(int j = 0; j < enemy3List.size(); j++) {
					enemy3 = enemy3List.get(j);
					if((skillAttack.x+skillAttack.width>enemy3.x&&skillAttack.x+skillAttack.width<enemy3.x+enemy3.width&&skillAttack.y+skillAttack.height>enemy3.y&&skillAttack.y+skillAttack.height<enemy3.y+enemy3.height)||(skillAttack.x+skillAttack.width>enemy3.x&&skillAttack.x+skillAttack.width<enemy3.x+enemy3.width&&skillAttack.y>enemy3.y&&skillAttack.y<enemy3.y+enemy3.height)) { //히트박스 범위(좌표 기준은 항상 가장 좌측 위에 모서리)					
						enemy3.hp -= skillAttack.attack; //에너지 깎음
						skillAttackList.remove(skillAttack); //맞춘 공격 물체는 삭제
					}
					if(enemy3.hp <= 0) {
						enemy3killsound.start();
						enemy3List.remove(enemy3);
						score += 1000;
					}
						
				}
				
			}
			
			if(score >= 2300 && score < 2500) {
				for(int j = 0; j < enemy5List.size(); j++) {
					enemy5 = enemy5List.get(j);
					if((skillAttack.x+skillAttack.width>enemy5.x&&skillAttack.x+skillAttack.width<enemy5.x+enemy5.width&&skillAttack.y+skillAttack.height>enemy5.y&&skillAttack.y+skillAttack.height<enemy5.y+enemy5.height)||(skillAttack.x+skillAttack.width>enemy5.x&&skillAttack.x+skillAttack.width<enemy5.x+enemy5.width&&skillAttack.y>enemy5.y&&skillAttack.y<enemy5.y+enemy5.height)) { //히트박스 범위(좌표 기준은 항상 가장 좌측 위에 모서리)
						enemy5.hp -= skillAttack.attack; //에너지 깎음
						skillAttackList.remove(skillAttack); //맞춘 공격 물체는 삭제
					}
					if(enemy5.hp <= 0) {
						enemy5killsound.start();
						enemy5List.remove(enemy5);
						score += 100;
					}
						
				}
				
			}
			
			if(score >= 2500 && score < 3500) {
				
				for(int j = 0; j < enemy6List.size(); j++) {
					enemy6 = enemy6List.get(j);
					if((skillAttack.x+skillAttack.width>enemy6.x&&skillAttack.x+skillAttack.width<enemy6.x+enemy6.width&&skillAttack.y+skillAttack.height>enemy6.y&&skillAttack.y+skillAttack.height<enemy6.y+enemy6.height)||(skillAttack.x+skillAttack.width>enemy6.x&&skillAttack.x+skillAttack.width<enemy6.x+enemy6.width&&skillAttack.y>enemy6.y&&skillAttack.y<enemy6.y+enemy6.height)) { //占쏙옙트占쌘쏙옙 占쏙옙占쏙옙(占쏙옙표 占쏙옙占쏙옙占쏙옙 占쌓삼옙 占쏙옙占쏙옙 占쏙옙占쏙옙 占쏙옙占쏙옙 占쏜서몌옙)
						enemy6.hp -= skillAttack.attack; 
						skillAttackList.remove(skillAttack); 
					}
					if(enemy6.hp <= 0) {
						killsound.start();
						enemy6List.remove(enemy6);
						score += 1000;
					}
						
				}
			}
			
			if(score >= 3500) {
				for(int j = 0; j < enemy7List.size(); j++) {
					enemy7 = enemy7List.get(j);
					if((skillAttack.x+skillAttack.width>enemy7.x&&skillAttack.x+skillAttack.width<enemy7.x+enemy7.width&&skillAttack.y+skillAttack.height>enemy7.y&&skillAttack.y+skillAttack.height<enemy7.y+enemy7.height)||(skillAttack.x+skillAttack.width>enemy7.x&&skillAttack.x+skillAttack.width<enemy7.x+enemy7.width&&skillAttack.y>enemy7.y&&skillAttack.y<enemy7.y+enemy7.height)) { //占쏙옙트占쌘쏙옙 占쏙옙占쏙옙(占쏙옙표 占쏙옙占쏙옙占쏙옙 占쌓삼옙 占쏙옙占쏙옙 占쏙옙占쏙옙 占쏙옙占쏙옙 占쏜서몌옙)
						enemy7.hp -= skillAttack.attack; 
						skillAttackList.remove(skillAttack); 
					}
					if(enemy7.hp <= 0) {
						killsound.start();
						enemy7List.remove(enemy7);
						enemy7AttackList.clear();
						enemy7Attack2List.clear();
						enemy7Attack3List.clear();
						enemy7Attack4List.clear();
						enemy7Attack5List.clear();
						enemy7WindList.clear();
						enemy7Attack6List.clear();
						enemy7WarningList.clear();
						backgroundMusic2.stop();
						fireEagleBackSound.stop();
						isEnd = true;
					}
						
				}
			}
		}
		
		
			
	}

	//적의 등장 구현 (y좌표값이 1~620에서 랜덤 출현)
	private void enemyAppearProcess() {
		if(i % 160 == 0) {
			enemy = new Enemy(1120,(int)(Math.random()*476));
			enemyList.add(enemy);
		}
	}
	
	
	//적의 이동 구현
	private void enemyMoveProcess() {
		for(int i= 0; i<enemyList.size(); i++) {
			enemy = enemyList.get(i);
			enemy.move(); //적 이동
				
		}
	}
	
	//적의 공격 구현
	private void enemyAttackProcess() {
		if(i % 100 == 0 && enemyList.size() > 0) { //enemyList.size() > 0 적은 이유 : 공격이 적 위치 기준으로 나오기 때문에 적이 나오기 전에 공격이 나오면 오류가 발생한다
			for(int j = 0; j < enemyList.size(); j++) { //enemyAttackList에 적 개체 수만큼 enemyAttack을 추가하기 위해 반복문 추가
			enemyAttack = new EnemyAttack(enemyList.get(j).x - 79, enemyList.get(j).y + 35); //적 개체 수가 여러 마리일 때 EnemyAttack 생성 좌표를 enemy.x, enemy.y로 두면 마지막에 나온 적에게서 EnemyAttack이 중첩으로 나온다. 생성 좌표를 enemyList의 각 인덱스 마다의 xy좌표로 둬야 적이 EnemyAttack을 각각 하나씩 날린다. 
			enemyAttackList.add(enemyAttack);
			enemyshotsound.start();
			}
		}
		
		for(int i = 0; i < enemyAttackList.size(); i++) {
			enemyAttack = enemyAttackList.get(i);
			enemyAttack.fire();
			
			if((enemyAttack.x<playerX&&enemyAttack.x+enemyAttack.width>playerX&&enemyAttack.y+enemyAttack.height>playerY&&enemyAttack.y+enemyAttack.height<playerY+playerHeight)||(enemyAttack.x<playerX+playerWidth&&enemyAttack.x>playerX&&enemyAttack.y>playerY&&enemyAttack.y<playerY+playerHeight)) {
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
	
	
	//적의 이동 구현
	private void enemy2MoveProcess() {
		for(int i= 0; i<enemy2List.size(); i++) {
			enemy2 = enemy2List.get(i);
			enemy2.move(); //적 이동
		}
	}
	
	private void enemy2AttackProcess() {
		if(i % 80 == 0 && enemy2List.size() > 0) {
			for(int j = 0; j < enemy2List.size(); j++) {
			enemy2Attack = new Enemy2Attack(enemy2List.get(j).x - 100 , enemy2List.get(j).y + 75);
			enemy2AttackList.add(enemy2Attack);
			enemy2shotsound.start();
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
	
	private void enemy4AppearProcess() {
		if(enemy4List.size() == 0) {
			enemy4 = new Enemy4(1120,360-169);
			enemy4List.add(enemy4);
		}
	}
	
	boolean m = false; //좌표에 따라 위아래로 움직이게 하기 위해 선언
	
	private void enemy4MoveProcess() {
		if(enemy4.y <= 20) m = true;
		if(enemy4.y >= 362) m = false;
		
		for(int i = 0; i<enemy4List.size(); i++) {
			enemy4 = enemy4List.get(i);
			if(enemy4.x >900)
				enemy4.move();
			else if(m == true)
				enemy4.move3();
			else if(m == false)
				enemy4.move2();
		}
	}
	
	boolean enemy4AttackPatternCycle;
	
	private void enemy4AttackProcess() {
		
		if(i % 720 == 0) {
			enemy4AttackPatternCycle = true;
			enemy4Attack2List.clear();
			enemy4Attack3List.clear();
		}
		if(i % 720 == 360) {
			enemy4AttackPatternCycle = false;
			enemy4AttackList.clear();
		}
		
		
		if(enemy4AttackPatternCycle == true) {
			if(i % 100 == 0 && enemy4List.size() > 0) {
				enemy4Attack = new Enemy4Attack(1280 ,(int)(Math.random()*525));
				enemy4AttackList.add(enemy4Attack);
				nunu_w_sound.start();
				
			}
			
			for(int i = 0; i < enemy4AttackList.size(); i++) {
				enemy4Attack = enemy4AttackList.get(i);
				enemy4Attack.fire();
				
				if((playerX+playerWidth>enemy4Attack.x&&playerX+playerWidth<enemy4Attack.x+enemy4Attack.width&&playerY+playerHeight>enemy4Attack.y&&playerY+playerHeight<enemy4Attack.y+enemy4Attack.height)||(playerX+playerWidth>enemy4Attack.x&&playerX+playerWidth<enemy4Attack.x+enemy4Attack.width&&playerY>enemy4Attack.y&&playerY<enemy4Attack.y+enemy4Attack.height)) {
					hitsound.start();
					playerHp -= enemy4Attack.attack;
					enemy4AttackList.remove(enemy4Attack);
					nunu_w_sound.stop();
				}
				if(playerHp <= 0) {
					isOver = true;
				}
						
				
						
			}
		
		}
	}
	
	private void enemy4Attack2Process() {
		
		if(enemy4AttackPatternCycle == false) {

			if(i % 7 == 0 && enemy4List.size() > 0) {
				for(int j = 0; j < enemy4List.size(); j++) {
				enemy4Attack2 = new Enemy4Attack2(enemy4.x + 62, enemy4.y + 79);
				enemy4Attack2List.add(enemy4Attack2);
				enemy4Attack2 = new Enemy4Attack2(enemy4.x + 62, enemy4.y + 80);
				enemy4Attack2List.add(enemy4Attack2);
				nunu_e_sound.start();
				}
			}
			
			for(int i = 0; i < enemy4Attack2List.size(); i++) {
				enemy4Attack2 = enemy4Attack2List.get(i);
				if(enemy4Attack2.y <= enemy4.y + 79)
					enemy4Attack2.fire2();
				else if(enemy4Attack2.y >= enemy4.y + 80)
					enemy4Attack2.fire3();
				if((enemy4Attack2.x<playerX+playerWidth&&enemy4Attack2.x>playerX&&enemy4Attack2.y+enemy4Attack2.height>playerY&&enemy4Attack2.y+enemy4Attack2.height<playerY+playerHeight)||(enemy4Attack2.x<playerX+playerWidth&&enemy4Attack2.x>playerX&&enemy4Attack2.y>playerY&&enemy4Attack2.y<playerY+playerHeight)) {
					hitsound.start();
					playerHp -= enemy4Attack2.attack;
					enemy4Attack2List.remove(enemy4Attack2);
				}
				
		
				
			}
		}
}

	private void enemy4Attack3Process() {
		
		if(enemy4AttackPatternCycle == true) {
		
		if(i % 120 == 0 && enemy4List.size() > 0 ) {
			
			for(int j = 0; j < enemy4List.size(); j++) {
			enemy4Attack3Warning = new Enemy4Attack3Warning((int)(Math.random()*601), (int)(Math.random()*140));
			enemy4Attack3WarningList.add(enemy4Attack3Warning);
			Timer loadingTimer = new Timer();
	        TimerTask loadingTask = new TimerTask() {
	            @Override
	            public void run() {
	            	enemy4Attack3WarningList.remove(enemy4Attack3Warning);
	            }
	        };
	        loadingTimer.schedule(loadingTask, 1000);
			}
		}
		
		if(i % 120 == 58 && enemy4List.size() > 0 && enemy4Attack3WarningList.size() >= 1) {
			for(int j = 0; j < enemy4List.size(); j++) {
			enemy4Attack3 = new Enemy4Attack3(enemy4Attack3Warning.x, enemy4Attack3Warning.y);
			enemy4Attack3List.add(enemy4Attack3);
			nunu_r_sound.start();
			
			}
		}
		
		if(i % 120 == 78  && enemy4Attack3List.size() >= 1) {
			enemy4Attack3List.remove(enemy4Attack3);
		}
		
		if(i % 120 == 58 && enemy4List.size() > 0 && enemy4Attack3WarningList.size() >= 1) {
			for(int j = 0; j < enemy4List.size(); j++) {
			enemy4EmptyAttack3 = new Enemy4EmptyAttack3(enemy4Attack3Warning.x, enemy4Attack3Warning.y);
			enemy4EmptyAttack3List.add(enemy4EmptyAttack3);
			
			}
		}
		
		for(int i = 0; i < enemy4EmptyAttack3List.size(); i++) {
			enemy4EmptyAttack3 = enemy4EmptyAttack3List.get(i);
			if(this.i % 120 <=63 && this.i % 120 >= 59 && enemy4EmptyAttack3List.get(i).y <= 1440)
			enemy4EmptyAttack3.fire();
	
			if((playerX+playerWidth/2>enemy4EmptyAttack3.x&&playerX+10<enemy4EmptyAttack3.x+enemy4EmptyAttack3.width&&playerY+playerHeight>enemy4EmptyAttack3.y&&playerY+playerHeight<enemy4EmptyAttack3.y+enemy4EmptyAttack3.height)||(playerX+playerWidth/2>enemy4EmptyAttack3.x&&playerX+10<enemy4EmptyAttack3.x+enemy4EmptyAttack3.width&&playerY>enemy4EmptyAttack3.y&&playerY<enemy4EmptyAttack3.y+enemy4EmptyAttack3.height)) {
				hitsound.start();
				playerHp -= enemy4Attack3.attack;
				enemy4EmptyAttack3List.remove(enemy4EmptyAttack3);
			}
			if(playerHp <= 0) {
				isOver = true;
			}
			
					
		}
		
		}
	}
	
	private void enemy3AppearProcess() {
		if(enemy3List.size() == 0) {
			enemy3 = new Enemy3(1120, 133);
			enemy3List.add(enemy3);
		}
		
		if(enemy3.hp <= 50 && onetimeUltimate == true) {
			Enemy3.isUltimate = true;
			enemy3ultimatesound.start();
			enemy3.hp = 262;
			onetimeUltimate = false; //onetimeUltimate로 if문이 한번만 실행되게 함
		}
		
	}
	
	
	//적의 이동 구현
	private void enemy3MoveProcess() {
		for(int i= 0; i<enemy3List.size(); i++) {
			enemy3 = enemy3List.get(i);
			if(enemy3.x >800)
			enemy3.move(); //적 이동
		}
	}
	
	private void enemy3AttackProcess() {
		if(i % 120 == 0 && enemy3List.size() > 0) {
			for(int j = 0; j < enemy3List.size(); j++) {
			enemy3Attack = new Enemy3Attack(enemy3List.get(j).x - 100 , enemy3List.get(j).y + 75);
			enemy3AttackList.add(enemy3Attack);
			enemy3shotsound.start();
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
			
		}
	}
			
			private void enemy3Attack2Process() {
				if(Enemy3.isUltimate == true) {
					if(i % 90 == 0 && enemy3List.size() > 0) {
						for(int j = 0; j < enemy3List.size(); j++) {
						enemy3Attack2 = new Enemy3Attack2((int)Math.random()*301 + 300, 720);
						enemy3Attack2List.add(enemy3Attack2);
						enemy3Attack2 = new Enemy3Attack2((int)Math.random()*301+ 600, 720);
						enemy3Attack2List.add(enemy3Attack2);
						enemy3shot2sound.start();
						}
					}
				
				for(int i = 0; i < enemy3Attack2List.size(); i++) {
					enemy3Attack2 = enemy3Attack2List.get(i);
					enemy3Attack2.fire();
					
					if((playerX+playerWidth>enemy3Attack2.x&&playerX+playerWidth<enemy3Attack2.x+enemy3Attack2.width&&playerY+playerHeight>enemy3Attack2.y&&playerY+playerHeight<enemy3Attack2.y+enemy3Attack2.height)||(playerX+playerWidth>enemy3Attack2.x&&playerX+playerWidth<enemy3Attack2.x+enemy3Attack2.width&&playerY>enemy3Attack2.y&&playerY<enemy3Attack2.y+enemy3Attack2.height)) {
						hitsound.start();
						playerHp -= enemy3Attack2.attack;
						enemy3Attack2List.remove(enemy3Attack2);
					}
					
			
					
				}
		}
		
	}
			
			private void enemy3Attack3Process() {
				if(playerX> 740 && enemy3Attack3List.size() == 0) {
					enemy3Attack3 = new Enemy3Attack3(740, -100);
					enemy3Attack3List.add(enemy3Attack3);
					enemy3shot3sound.start();
					
				}
				if(playerX <= 740 && enemy3Attack3List.size() == 1) {
					enemy3Attack3List.remove(enemy3Attack3);
					enemy3shot3sound.stop();
					
				}
				
				if(playerX > 740) {
					if(i % 30 == 0)
					hitsound.start();
					playerHp -= enemy3Attack3.attack;
					}
				
				
			}
			
				
			
	
	private void enemy5AppearProcess() {
		if(i % 160 == 0) {
			enemy5 = new Enemy5(1120,(int)(Math.random()*476));
			enemy5List.add(enemy5);
		}
	}
	
	
	//적의 이동 구현
	private void enemy5MoveProcess() {
		for(int i= 0; i<enemy5List.size(); i++) {
			enemy5 = enemy5List.get(i);
			enemy5.move(); //적 이동
		}
	}
	
	private void enemy5AttackProcess() {
		if((i % 90 == 0 || i % 100 == 0) && enemy5List.size() > 0) {
			for(int j = 0; j < enemy5List.size(); j++) {
			enemy5Attack = new Enemy5Attack(enemy5List.get(j).x - 100 , enemy5List.get(j).y + 75);
			enemy5AttackList.add(enemy5Attack);
			enemy5shotsound.start();
			}
		}
		
		for(int i = 0; i < enemy5AttackList.size(); i++) {
			enemy5Attack = enemy5AttackList.get(i);
			enemy5Attack.fire();
			
			if((enemy5Attack.x<playerX+playerWidth&&enemy5Attack.x>playerX&&enemy5Attack.y+enemy5Attack.height>playerY&&enemy5Attack.y+enemy5Attack.height<playerY+playerHeight)||(enemy5Attack.x<playerX+playerWidth&&enemy5Attack.x>playerX&&enemy5Attack.y>playerY&&enemy5Attack.y<playerY+playerHeight)) {
				hitsound.start();
				playerHp -= enemy5Attack.attack;
				enemy5AttackList.remove(enemy5Attack);
			}
			if(playerHp <= 0) {
				isOver = true;
			}
					
			
					
		}
		
	}
	
	private void enemy6AppearProcess() {
		if(enemy6List.size() == 0) {
			enemy6 = new Enemy6(1200, 133);
			enemy6List.add(enemy6);
		}
	}
	
	private void enemy6MoveProcess() {
			enemy6 = enemy6List.get(0);
			if(enemy6.x > 800)
			enemy6.move();
		
	}
	
	private void enemy6AttackProcess() {
		
		if(i % 120 == 0 && enemy6List.size() > 0 ) {
			
			for(int j = 0; j < enemy6List.size(); j++) {
			enemy6Warning = new Enemy6Warning((int)(Math.random()*601), 0);
			enemy6WarningList.add(enemy6Warning);
			Timer loadingTimer = new Timer();
	        TimerTask loadingTask = new TimerTask() {
	            @Override
	            public void run() {
	            	enemy6WarningList.remove(enemy6Warning);
	            }
	        };
	        loadingTimer.schedule(loadingTask, 700);
			}
		}
		
		if(i % 120 == 40 && enemy6List.size() > 0 && enemy6WarningList.size() >= 1) {
			for(int j = 0; j < enemy6List.size(); j++) {
			enemy6Attack = new Enemy6Attack(enemy6Warning.x, 0);
			enemy6AttackList.add(enemy6Attack);
			enemy6shotsound.start();
			
			}
		}
		
		if(i % 120 == 40 && enemy6List.size() > 0 && enemy6WarningList.size() >= 1) {
			for(int j = 0; j < enemy6List.size(); j++) {
			enemy6EmptyAttack = new Enemy6EmptyAttack(enemy6Warning.x, 0);
			enemy6EmptyAttackList.add(enemy6EmptyAttack);
			
			}
		}
		
		if(i % 120 == 100  && enemy6AttackList.size() >= 1) {
			enemy6AttackList.remove(enemy6Attack);
		}
		
		for(int i = 0; i < enemy6EmptyAttackList.size(); i++) {
			enemy6EmptyAttack = enemy6EmptyAttackList.get(i);
			if(this.i % 120 <=105 && this.i % 120 >= 100 && enemy6EmptyAttackList.get(i).y <= 1440)
			enemy6EmptyAttack.fire();
	
			if((playerX+playerWidth/2>enemy6EmptyAttack.x&&playerX+10<enemy6EmptyAttack.x+enemy6EmptyAttack.width&&playerY+playerHeight>enemy6EmptyAttack.y&&playerY+playerHeight<enemy6EmptyAttack.y+enemy6EmptyAttack.height)||(playerX+playerWidth/2>enemy6EmptyAttack.x&&playerX+10<enemy6EmptyAttack.x+enemy6EmptyAttack.width&&playerY>enemy6EmptyAttack.y&&playerY<enemy6EmptyAttack.y+enemy6EmptyAttack.height)) {
				hitsound.start();
				playerHp -= enemy6Attack.attack;
				enemy6EmptyAttackList.remove(enemy6EmptyAttack);
			}
			if(playerHp <= 0) {
				isOver = true;
			}
			
					
		}
		
	}
	
	private void enemy6Attack2Process() {
		if(i % 120 == 0 && enemy6List.size() > 0) {
			for(int j = 0; j < enemy6List.size(); j++) {
			enemy6Attack2 = new Enemy6Attack2(enemy6List.get(j).x - 100 , enemy6List.get(j).y + 75);
			enemy6Attack2List.add(enemy6Attack2);
			enemy6shot2sound.start();
			
			}
		}
		
		for(int i = 0; i < enemy6Attack2List.size(); i++) {
			enemy6Attack2 = enemy6Attack2List.get(i);
			enemy6Attack2.fire();
	
			if((enemy6Attack2.x<playerX+playerWidth&&enemy6Attack2.x>playerX&&enemy6Attack2.y+enemy6Attack2.height>playerY&&enemy6Attack2.y+enemy6Attack2.height<playerY+playerHeight)||(enemy6Attack2.x<playerX+playerWidth&&enemy6Attack2.x>playerX&&enemy6Attack2.y>playerY&&enemy6Attack2.y<playerY+playerHeight)) {
				hitsound.start();
				playerHp -= enemy6Attack2.attack;
				enemy6Attack2List.remove(enemy6Attack2);
			}
			if(playerHp <= 0) {
				isOver = true;
			}
			
					
		}
		
	}
	
	private void enemy6Attack3Process() {
		if(playerX> 700 && enemy6List.size() > 0 && i % 30 == 0) {
			enemy6Attack3 = new Enemy6Attack3(1280, 0);
			enemy6Attack3List.add(enemy6Attack3);
			
		}
		for(int i = 0; i < enemy6Attack3List.size(); i++) {
			enemy6Attack3 = enemy6Attack3List.get(i);
			enemy6Attack3.fire();
			
		if(enemy6Attack3.x < 700 && enemy6Attack3List.size() > 0) {
			enemy6Attack3List.remove(enemy6Attack3);
			
		}
		
		if(enemy6Attack3List.size() > 0 && (playerX+playerWidth/2>enemy6Attack3.x&&playerX+10<enemy6Attack3.x+enemy6Attack3.width&&playerY+playerHeight>enemy6Attack3.y&&playerY+playerHeight<enemy6Attack3.y+enemy6Attack3.height)||(playerX+playerWidth/2>enemy6Attack3.x&&playerX+10<enemy6Attack3.x+enemy6Attack3.width&&playerY>enemy6Attack3.y&&playerY<enemy6Attack3.y+enemy6Attack3.height)) {
			hitsound.start();
			playerHp -= enemy6Attack.attack;
			enemy6Attack3List.remove(enemy6Attack3);
		}
		if(playerHp <= 0) {
			isOver = true;
		}
		
		}
	}
	
	private void enemy7AppearProcess() {
		if(enemy7List.size() == 0) {
			enemy7 = new Enemy7(1200, 200);
			enemy7List.add(enemy7);
		}
	}
	
	private void enemy7MoveProcess() {
		if(enemy7.y <= 60) m = true;
		if(enemy7.y >= 322) m = false;
		
		for(int i = 0; i<enemy7List.size(); i++) {
			enemy7 = enemy7List.get(i);
			if(enemy7.x >800)
				enemy7.move();
			else if(m == true && fireBreath) {
				enemy7.move3();
			}
			else if(m == false && fireBreath)
				enemy7.move2();
			
		}
	}
	
	private void enemy7AttackProcess() {
		
		if(i % 120 == 0 && enemy7List.size() > 0 ) {
			
			for(int j = 0; j < enemy7List.size(); j++) {
			enemy7Warning = new Enemy7Warning((int)(Math.random()*601), 0);
			enemy7WarningList.add(enemy7Warning);
			Timer loadingTimer = new Timer();
	        TimerTask loadingTask = new TimerTask() {
	            @Override
	            public void run() {
	            	enemy7WarningList.remove(enemy7Warning);
	            }
	        };
	        loadingTimer.schedule(loadingTask, 700);
			}
		}
		
		if(i % 120 == 40 && enemy7List.size() > 0 && enemy7WarningList.size() >= 1) {
			for(int j = 0; j < enemy7List.size(); j++) {
			enemy7Attack = new Enemy7Attack(enemy7Warning.x, 0);
			enemy7AttackList.add(enemy7Attack);
			enemy7_thunder_sound.start();
			
			}
		}
		
		if(i % 120 == 40 && enemy7List.size() > 0 && enemy7WarningList.size() >= 1) {
			for(int j = 0; j < enemy7List.size(); j++) {
			enemy7EmptyAttack = new Enemy7EmptyAttack(enemy7Warning.x, 0);
			enemy7EmptyAttackList.add(enemy7EmptyAttack);
			}
		}
		
		if(i % 120 == 100  && enemy7AttackList.size() >= 1) {
			enemy7AttackList.remove(enemy7Attack);
		}
		
		for(int i = 0; i < enemy7EmptyAttackList.size(); i++) {
			enemy7EmptyAttack = enemy7EmptyAttackList.get(i);
			if(this.i % 120 <=105 && this.i % 120 >= 100 && enemy7EmptyAttackList.get(i).y <= 1440)
			enemy7EmptyAttack.fire();
	
			if((playerX+playerWidth/2>enemy7EmptyAttack.x&&playerX+10<enemy7EmptyAttack.x+enemy7EmptyAttack.width&&playerY+playerHeight>enemy7EmptyAttack.y&&playerY+playerHeight<enemy7EmptyAttack.y+enemy7EmptyAttack.height)||(playerX+playerWidth/2>enemy7EmptyAttack.x&&playerX+10<enemy7EmptyAttack.x+enemy7EmptyAttack.width&&playerY>enemy7EmptyAttack.y&&playerY<enemy7EmptyAttack.y+enemy7EmptyAttack.height)) {
				hitsound.start();
				playerHp -= enemy7Attack.attack;
				enemy7EmptyAttackList.remove(enemy7EmptyAttack);
			}
			if(playerHp <= 0) {
				isOver = true;
			}
			
					
		}
		
	}
	private boolean fireBreath = false;
	
	public void enemy7Attack2Process() {
		if(enemy7.hp <= 50 ) fireBreath = true;
		if(enemy7Attack2List.size() < 1 && fireBreath) {
		enemy7Attack2 = new Enemy7Attack2(0,enemy7.y);
		enemy7Attack2List.add(enemy7Attack2);
		enemy7_firebreath_sound.start();
		}
		
		for(int i = 0; i < enemy7Attack2List.size(); i++) {
			enemy7Attack2 = enemy7Attack2List.get(i);
			enemy7Attack2.y = enemy7.y;
			
			if((playerX+playerWidth/2>enemy7Attack2.x+120&&playerX+10<enemy7Attack2.x+enemy7Attack2.width&&playerY+playerHeight>enemy7Attack2.y+45&&playerY+playerHeight<enemy7Attack2.y+enemy7Attack2.height-45)||(playerX+playerWidth/2>enemy7Attack2.x+120&&playerX+10<enemy7Attack2.x+enemy7Attack2.width&&playerY>enemy7Attack2.y+45&&playerY<enemy7Attack2.y+enemy7Attack2.height-45)) {
				hitsound.start();
				playerHp -= enemy7Attack2.attack;
			}
			if(playerHp <= 0) {
				isOver = true;
			}
					
			
					
		}
		
	}
	
	public void enemy7Attack3Process() {
		if(i % 120 == 0 && enemy7List.size() > 0) {
		enemy7Attack3 = new Enemy7Attack3(enemy7.x+150,enemy7.y-130);
		enemy7Attack3List.add(enemy7Attack3);
		enemy7Attack3 = new Enemy7Attack3(enemy7.x+150,enemy7.y+160);
		enemy7Attack3List.add(enemy7Attack3);
		enemy7_tornado_sound.start();
		}
		
		for(int i = 0; i < enemy7Attack3List.size(); i++) {
			enemy7Attack3 = enemy7Attack3List.get(i);
			enemy7Attack3.fire();
			
			if((playerX+playerWidth/2>enemy7Attack3.x&&playerX+10<enemy7Attack3.x+enemy7Attack3.width&&playerY+playerHeight>enemy7Attack3.y&&playerY+playerHeight<enemy7Attack3.y+enemy7Attack3.height)||(playerX+playerWidth/2>enemy7Attack3.x&&playerX+10<enemy7Attack3.x+enemy7Attack3.width&&playerY>enemy7Attack3.y&&playerY<enemy7Attack3.y+enemy7Attack3.height)) {
				hitsound.start();
				playerHp -= enemy7Attack3.attack;
				enemy7Attack3List.remove(enemy7Attack3);
			}
			if(playerHp <= 0) {
				isOver = true;
			}
					
			
					
		}
		
		
	}
	
	public void enemy7Attack4Process() {
		if(i % 60 == 0 && enemy7List.size() > 0) {
		enemy7Attack4 = new Enemy7Attack4((int)(Math.random()*1261)+20,-209);
		enemy7Attack4List.add(enemy7Attack4);
		enemy7_eagleattack_sound.start();
		}
		
		for(int i = 0; i < enemy7Attack4List.size(); i++) {
			enemy7Attack4 = enemy7Attack4List.get(i);
			enemy7Attack4.fire();
			
			if((playerX+playerWidth>enemy7Attack4.x&&playerX+playerWidth<enemy7Attack4.x+enemy7Attack4.width&&playerY+playerHeight>enemy7Attack4.y&&playerY+playerHeight<enemy7Attack4.y+enemy7Attack4.height)||(playerX+playerWidth>enemy7Attack4.x&&playerX+playerWidth<enemy7Attack4.x+enemy7Attack4.width&&playerY>enemy7Attack4.y&&playerY<enemy7Attack4.y+enemy7Attack4.height)) {
				hitsound.start();
				playerHp -= enemy7Attack4.attack;
				enemy7Attack4List.remove(enemy7Attack4);
			}
			if(playerHp <= 0) {
				isOver = true;
			}
					
			
					
		}
	}
	
	public void enemy7Attack5Process() {
		if(i % 30 == 0 && enemy7List.size() > 0) {
			enemy7Attack5 = new Enemy7Attack5(1280 ,(int)(Math.random()*525));
			enemy7Attack5List.add(enemy7Attack5);
			
		}
		
		for(int i = 0; i < enemy7Attack5List.size(); i++) {
			enemy7Attack5 = enemy7Attack5List.get(i);
			enemy7Attack5.fire();
			
			if((playerX+playerWidth>enemy7Attack5.x&&playerX+playerWidth<enemy7Attack5.x+enemy7Attack5.width&&playerY+playerHeight>enemy7Attack5.y&&playerY+playerHeight<enemy7Attack5.y+enemy7Attack5.height)||(playerX+playerWidth>enemy7Attack5.x&&playerX+playerWidth<enemy7Attack5.x+enemy7Attack5.width&&playerY>enemy7Attack5.y&&playerY<enemy7Attack5.y+enemy7Attack5.height)) {
				hitsound.start();
				playerHp -= enemy7Attack5.attack;
				enemy7Attack5List.remove(enemy7Attack5);
			}
			if(playerHp <= 0) {
				isOver = true;
			}
					
			
					
		}
	}
	
	public void enemy7Attack6Process() {
		if(i % 25 == 0 && enemy7List.size() > 0) {
			enemy7Attack6 = new Enemy7Attack6((int)(Math.random()*1250) ,-200);
			enemy7Attack6List.add(enemy7Attack6);
			
		}
		
		for(int i = 0; i < enemy7Attack6List.size(); i++) {
			enemy7Attack6 = enemy7Attack6List.get(i);
			enemy7Attack6.fire();
			
			if((playerX+playerWidth/2>enemy7Attack6.x&&playerX<enemy7Attack6.x+enemy7Attack6.width&&playerY+playerHeight>enemy7Attack6.y&&playerY+playerHeight<enemy7Attack6.y+enemy7Attack6.height)||(playerX+playerWidth/2>enemy7Attack6.x&&playerX<enemy7Attack6.x+enemy7Attack6.width&&playerY>enemy7Attack6.y&&playerY<enemy7Attack6.y+enemy7Attack6.height)) {
				hitsound.start();
				playerHp -= enemy7Attack6.attack;
				enemy7Attack6List.remove(enemy7Attack6);
			}
			if(playerHp <= 0) {
				isOver = true;
			}
					
			
					
		}
	}
	
	public void enemy7WindProcess() {
		if(enemy7WindList.size() < 1 && i % 240 == 0) {
		enemy7Wind = new Enemy7Wind(0,0);
		enemy7WindList.add(enemy7Wind);
		enemy7_wind_sound.start();
		}
		
		if(i % 240 == 120 && enemy7WindList.size() >= 1) {
			enemy7WindList.remove(enemy7Wind);
			playerSpeed = 10;
		}
		
		for(int i = 0; i < enemy7WindList.size(); i++) {
			enemy7Wind = enemy7WindList.get(i);
			
			if(playerX >= 5) {
				playerX -= 5;
			}
					
			
					
		}
		
		
	}

	 
	 
	//게임 화면에 플레이어 캐릭터 그리기
	public void gameDraw(Graphics g) {
		enemyDraw(g);
		enemy2Draw(g);
		enemy4Draw(g);
		enemy3Draw(g);
		enemy5Draw(g);
		enemy6Draw(g);
		playerDraw(g);
		enemy7Draw(g);
		infoDraw(g);
		if(isEnd) endingDraw(g);
	}

	Image restart = new ImageIcon("src/images/Restart.png").getImage();
	Image theEnd = new ImageIcon("src/images/THE_END.png").getImage();
	
	public void infoDraw(Graphics g) {
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.BOLD, 40));
		g.drawString("SCORE : " + score, 40, 80);
		if(isOver == true) {
			g.drawImage(restart, 0, 0, null);
		}
		
	}
	int endingY=720;
	public void endingDraw(Graphics g) {
		g.drawImage(theEnd, 0, endingY, null);
		if(!(endingY==0)) endingY -= 5;
	}
	
	
	//플레이어 캐릭터 이미지 그리기
	public void playerDraw(Graphics g) {
		g.drawImage(player,  playerX, playerY, null);
		g.setColor(Color.GREEN);
		g.fillRect(playerX - 1, playerY - 40, (int)(playerHp *2), 20);
		
		//공격 이미지 생성
		
		for(int i=0; i < playerAttackList.size(); i++) {
			playerAttack = playerAttackList.get(i);
			if(score < 2300)
				g.drawImage(playerAttack.attackImage, playerAttack.x, playerAttack.y, null);
			else if(score >= 2300)
				g.drawImage(playerAttack.attackImage2, playerAttack.x, playerAttack.y, null);
		}
		
		for(int i = 0; i < skillAttackList.size(); i++) {
			skillAttack = skillAttackList.get(i);
			g.drawImage(skillAttack.skillImage, skillAttack.x, skillAttack.y, null);
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
	
	public void enemy4Draw(Graphics g) {
		for(int i= 0; i < enemy4List.size(); i++) {
			enemy4 = enemy4List.get(i);
			g.drawImage(enemy4.image, enemy4.x, enemy4.y, null);
			g.setColor(Color.red);
			g.fillRect(enemy4.x - 1, enemy4.y - 40, enemy4.hp, 20);
			
		}
		for(int i = 0; i < enemy4AttackList.size(); i++) {
			enemy4Attack = enemy4AttackList.get(i);
			g.drawImage(enemy4Attack.image, enemy4Attack.x, enemy4Attack.y, null);
		}
		for(int i = 0; i < enemy4Attack2List.size(); i++) {
			enemy4Attack2 = enemy4Attack2List.get(i);
			g.drawImage(enemy4Attack2.image, enemy4Attack2.x, enemy4Attack2.y, null);
		}
		for(int i = 0; i < enemy4Attack3List.size(); i++) {
			enemy4Attack3 = enemy4Attack3List.get(i);
			g.drawImage(enemy4Attack3.image, enemy4Attack3.x, enemy4Attack3.y, null);
		}
		for(int i = 0; i < enemy4Attack3WarningList.size(); i++) {
			enemy4Attack3Warning = enemy4Attack3WarningList.get(i);
			g.drawImage(enemy4Attack3Warning.image, enemy4Attack3Warning.x, enemy4Attack3Warning.y, null);
		}
		
		for(int i = 0; i < enemy4EmptyAttack3List.size(); i++) {
			enemy4EmptyAttack3 = enemy4EmptyAttack3List.get(i);
			g.drawImage(enemy4EmptyAttack3.image, enemy4EmptyAttack3.x, enemy4EmptyAttack3.y, null);
		}
		
		
	}
	
	public void enemy3Draw(Graphics g) {
		
		if(playerX > 740 && enemy3Attack3List.size() == 1) {
		enemy3Attack3 = enemy3Attack3List.get(0);
		g.drawImage(enemy3Attack3.image, enemy3Attack3.x, enemy3Attack3.y, null);			
		}
		
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
		
		for(int i = 0; i < enemy3Attack2List.size(); i++) {
			enemy3Attack2 = enemy3Attack2List.get(i);
			g.drawImage(enemy3Attack2.image, enemy3Attack2.x, enemy3Attack2.y, null);			
		}
		
	}
	
	public void enemy5Draw(Graphics g) {
		for(int i= 0; i < enemy5List.size(); i++) {
			enemy5 = enemy5List.get(i);
			g.drawImage(enemy5.image, enemy5.x, enemy5.y, null);
			g.setColor(Color.red);
			g.fillRect(enemy5.x - 1, enemy5.y - 40, enemy5.hp *7, 20);
			
		}
		for(int i = 0; i < enemy5AttackList.size(); i++) {
			enemy5Attack = enemy5AttackList.get(i);
			g.drawImage(enemy5Attack.image, enemy5Attack.x, enemy5Attack.y, null);
		}
		
	}
	
	public void enemy6Draw(Graphics g) {
		for(int i= 0; i < enemy6List.size(); i++) {
			enemy6 = enemy6List.get(i);
			g.drawImage(enemy6.image, enemy6.x, enemy6.y, null);
			if(enemy6.hp >= 700) {
				g.setColor(Color.black);
				g.fillRect(enemy6.x - 1, enemy6.y - 40, 400, 20);
			}
			if(enemy6.hp >= 400 && enemy6.hp < 700) {
				g.setColor(Color.gray);
				g.fillRect(enemy6.x - 1, enemy6.y - 40, 400, 20);
			}
			if(enemy6.hp < 400) {
			g.setColor(Color.red);
			g.fillRect(enemy6.x - 1, enemy6.y - 40, (int)(enemy6.hp) , 20);
			}
			
		}
		
		
		
		for(int i = 0; i < enemy6AttackList.size(); i++) {
			enemy6Attack = enemy6AttackList.get(i);
			g.drawImage(enemy6Attack.image, enemy6Attack.x, enemy6Attack.y, null);
		}
		
		for(int i = 0; i < enemy6EmptyAttackList.size(); i++) {
			enemy6EmptyAttack = enemy6EmptyAttackList.get(i);
			g.drawImage(enemy6EmptyAttack.image, enemy6EmptyAttack.x, enemy6EmptyAttack.y, null);
		}
		
		for(int i = 0; i < enemy6WarningList.size(); i++) {
			enemy6Warning = enemy6WarningList.get(i);
			g.drawImage(enemy6Warning.image, enemy6Warning.x, enemy6Warning.y, null);
		}
		

		for(int i = 0; i < enemy6Attack2List.size(); i++) {
			enemy6Attack2 = enemy6Attack2List.get(i);
			g.drawImage(enemy6Attack2.image, enemy6Attack2.x, enemy6Attack2.y, null);
		}
		for(int i = 0; i < enemy6Attack3List.size(); i++) {
			enemy6Attack3 = enemy6Attack3List.get(i);
			g.drawImage(enemy6Attack3.image, enemy6Attack3.x, enemy6Attack3.y, null);
		}
		
	}
	
	Image enemy7Attack3_2image = new ImageIcon("src/images/enemy7_attack3-2.gif").getImage();
	Image enemy7_2image = new ImageIcon("src/images/enemy7-2.gif").getImage();
	
	public void enemy7Draw(Graphics g) {
		for(int i= 0; i < enemy7List.size(); i++) {
			enemy7 = enemy7List.get(i);
			if(enemy7.hp >= 400) {
				g.drawImage(enemy7.image, enemy7.x, enemy7.y, null);
			}
			else if(enemy7.hp < 400) g.drawImage(enemy7_2image, enemy7.x, enemy7.y, null);
			g.setColor(Color.red);
			g.fillRect(enemy7.x - 1, enemy7.y - 40, enemy7.hp *7, 20);
			
		}
		for(int i = 0; i < enemy7AttackList.size(); i++) {
			enemy7Attack = enemy7AttackList.get(i);
			g.drawImage(enemy7Attack.image, enemy7Attack.x, enemy7Attack.y, null);
		}
		
		for(int i = 0; i < enemy7EmptyAttackList.size(); i++) {
			enemy7EmptyAttack = enemy7EmptyAttackList.get(i);
			g.drawImage(enemy7EmptyAttack.image, enemy7EmptyAttack.x, enemy7EmptyAttack.y, null);
		}
		
		for(int i = 0; i < enemy7WarningList.size(); i++) {
			enemy7Warning = enemy7WarningList.get(i);
			g.drawImage(enemy7Warning.image, enemy7Warning.x, enemy7Warning.y, null);
		}
		
		if(fireBreath&&enemy7Attack2List.size() > 0) 
			g.drawImage(enemy7Attack2.image, enemy7Attack2.x, enemy7Attack2.y, null);
		
		for(int i = 0; i < enemy7Attack3List.size(); i++) {
			enemy7Attack3 = enemy7Attack3List.get(i);
			if(enemy7.hp >= 400) g.drawImage(enemy7Attack3.image, enemy7Attack3.x, enemy7Attack3.y, null);
			else if(enemy7.hp < 400) g.drawImage(enemy7Attack3_2image, enemy7Attack3.x, enemy7Attack3.y, null);
		}
		
		for(int i = 0; i < enemy7Attack4List.size(); i++) {
			enemy7Attack4 = enemy7Attack4List.get(i);
			g.drawImage(enemy7Attack4.image, enemy7Attack4.x, enemy7Attack4.y, null);
		}
		
		for(int i = 0; i < enemy7Attack5List.size(); i++) {
			enemy7Attack5 = enemy7Attack5List.get(i);
			g.drawImage(enemy7Attack5.image, enemy7Attack5.x, enemy7Attack5.y, null);
		}
		
		for(int i = 0; i < enemy7Attack6List.size(); i++) {
			enemy7Attack6 = enemy7Attack6List.get(i);
			g.drawImage(enemy7Attack6.image, enemy7Attack6.x, enemy7Attack6.y, null);
		}
		
		for(int i = 0; i < enemy7WindList.size(); i++) {
			enemy7Wind = enemy7WindList.get(i);
			g.drawImage(enemy7Wind.image, enemy7Wind.x, enemy7Wind.y, null);
		}	
		
	}

	//up, down, left, right가 private이기 때문에 setter를 이용함
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

	public void setSkill(boolean skill) {
		this.skill = skill;
	}
	
	
	

}
