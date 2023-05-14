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

import shoot.ShootingGame.KeyListener;

public class Game extends Thread {
	
	//플레이어 이미지
	private Image player = new ImageIcon("src/images/player.png").getImage();
	
	private int playerX, playerY; //플레이어 캐릭터 좌표
	private int playerWidth = player.getWidth(null); //플레이어 캐릭터 가로길이
	private int playerHeight = player.getHeight(null); //플레이어 캐릭터 세로길이
	private int playerSpeed = 10; //플레이어 캐릭터 이동 속도
	private double playerHp = 30; //플레이어 캐릭터 체력
	private int playerAttackSpeed = 20; //플레이어 공격 속도(낮을수록 증가)
	
	
	private boolean up, down, left, right; //움직이기 위해 변수 선언
	private boolean shooting; //true일 경우 공격 발사
	private boolean isOver; //게임오버 여부
	private boolean skill; //스킬 사용 여부
	private boolean onetimeUltimate = true; //isUltimate 한 번만 사용되게
	
	private Audio backgroundMusic,hitsound,killsound,enemykillsound,enemyshotsound,enemy2killsound,enemy2shotsound,enemy3killsound,enemy3shotsound,enemy3shot2sound,enemy3shot3sound,enemy3ultimatesound,enemy4killsound,enemy4shotsound,enemy6shotsound,enemy6shot2sound;
	
	public int score = 0; //점수를 나타낼 변수
	
	
	int delta;
	static int i,j,a = 1;
	
	
	
	
	
	
	//플레이어의 공격을 담을 ArrayList(ArrayList의 사이즈는 가변이기 때문에 쓰기 좋음(사이즈 부족하면 자동으로 증가))
	ArrayList<PlayerAttack> playerAttackList = new ArrayList<PlayerAttack>();
	private PlayerAttack playerAttack; //ArrayList안의 내용에 쉽게 접근할 수 있게 변수 선언
	ArrayList<PlayerAttackHitEffect> playerAttackHitEffectList = new ArrayList<PlayerAttackHitEffect>();
	private PlayerAttackHitEffect playerAttackHitEffect;
	
	ArrayList<SkillAttack> skillAttackList = new ArrayList<SkillAttack>();
	private SkillAttack skillAttack;
	ArrayList<SkillHitEffect> skillHitEffectList = new ArrayList<SkillHitEffect>();
	private SkillHitEffect skillHitEffect;
	
	//적의 이동과 적의 공격을 담을 ArrayList
	ArrayList<Enemy> enemyList = new ArrayList<Enemy>();
	ArrayList<EnemyAttack> enemyAttackList = new ArrayList<EnemyAttack>();
	private Enemy enemy;
	private EnemyAttack enemyAttack;
	
	ArrayList<Enemy2> enemy2List = new ArrayList<Enemy2>();
	ArrayList<Enemy2Attack> enemy2AttackList = new ArrayList<Enemy2Attack>();
	private Enemy2 enemy2;
	private Enemy2Attack enemy2Attack;
	
	ArrayList<Enemy3> enemy3List = new ArrayList<Enemy3>();
	ArrayList<Enemy3Attack> enemy3AttackList = new ArrayList<Enemy3Attack>();
	ArrayList<Enemy3Attack2> enemy3Attack2List = new ArrayList<Enemy3Attack2>();
	ArrayList<Enemy3Attack3> enemy3Attack3List = new ArrayList<Enemy3Attack3>();
	private Enemy3 enemy3;
	private Enemy3Attack enemy3Attack;
	private Enemy3Attack2 enemy3Attack2;
	private Enemy3Attack3 enemy3Attack3;
	
	ArrayList<Enemy4> enemy4List = new ArrayList<Enemy4>();
	ArrayList<Enemy4Attack> enemy4AttackList = new ArrayList<Enemy4Attack>();
	private Enemy4 enemy4;
	private Enemy4Attack enemy4Attack;
	
	ArrayList<Enemy5> enemy5List = new ArrayList<Enemy5>();
	ArrayList<Enemy5Attack> enemy5AttackList = new ArrayList<Enemy5Attack>();
	private Enemy5 enemy5;
	private Enemy5Attack enemy5Attack;
	
	ArrayList<Enemy6> enemy6List = new ArrayList<Enemy6>();
	ArrayList<Enemy6Attack> enemy6AttackList = new ArrayList<Enemy6Attack>();
	ArrayList<Enemy6EmptyAttack> enemy6EmptyAttackList = new ArrayList<Enemy6EmptyAttack>();
	ArrayList<Enemy6Attack2> enemy6Attack2List = new ArrayList<Enemy6Attack2>();
	ArrayList<Enemy6Warning> enemy6WarningList = new ArrayList<Enemy6Warning>();
	private Enemy6 enemy6;
	private Enemy6Attack enemy6Attack;
	private Enemy6EmptyAttack enemy6EmptyAttack;
	private Enemy6Attack2 enemy6Attack2;
	private Enemy6Warning enemy6Warning;
	
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
		enemy4killsound = new Audio("src/audio/enemy4killsound.wav",true);
		enemy4shotsound = new Audio("src/audio/enemy4shotsound.wav",true);
		enemy6shotsound = new Audio("src/audio/enemy6shotsound.wav",true);
		enemy6shot2sound = new Audio("src/audio/enemy6shot2sound.wav",true);
		
		
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
					enemy3Attack2Process();
					enemy3Attack3Process();
					}
					if(score==1300) {
						enemy3List.clear();
						enemy3AttackList.clear();
						enemy3Attack2List.clear();
						enemy3Attack3List.clear();
						}
					if(score>=1300 && score < 1500) {
						enemy4AppearProcess();
						enemy4MoveProcess();
						enemy4AttackProcess();
					}
					if(score==1500) {
						enemy4List.clear();
						enemy4AttackList.clear();
						}
					
					if(score>=1500 && score < 1800) {
						enemy5AppearProcess();
						enemy5MoveProcess();
						enemy5AttackProcess();
					}
					if(score==1800) {
						enemy5List.clear();
						enemy5AttackList.clear();
						}
					
					if(score>=1800) {
						enemy6AppearProcess();
						enemy6MoveProcess();
						enemy6AttackProcess();
						enemy6Attack2Process();
						
					}
					i++; //공격속도 정하기 위해 만듦
					delta = delta - (1000000000/60);

				}
				
				
			}
			
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
		enemy3Attack2List.clear();
		enemy3Attack3List.clear();
		enemy4List.clear();
		enemy4AttackList.clear();
		enemy5List.clear();
		enemy5AttackList.clear();
		enemy6List.clear();
		enemy6AttackList.clear();
		enemy6Attack2List.clear();
		enemy6WarningList.clear();
		skillAttackList.clear();
		skillHitEffectList.clear();
		playerAttackHitEffectList.clear();
		
		playerHp = 100;
		playerX = 10;
		playerY = (Main.SCREEN_HEIGHT - playerHeight) / 2;
		playerAttackSpeed = 20;
		score = 0;
		Enemy3.isUltimate = false;
		isOver = false;
		backgroundMusic.start();
		
	}
	
	
	
	//상하좌우 조작 설정(창 밖으로 캐릭터가 나가지 않도록 조건도 추가함)
	private void keyProcess() {
		if (up == true && playerY - playerSpeed > 0) playerY -= playerSpeed;
		if (down == true && playerY + playerHeight + playerSpeed < Main.SCREEN_HEIGHT) playerY += playerSpeed;
		if (left == true && playerX - playerSpeed > 0) playerX -= playerSpeed;
		if (right == true && playerX + playerWidth + playerSpeed < Main.SCREEN_WIDTH) playerX += playerSpeed;
		//공격 속도를 0.2초로 설정
		if (shooting == true && i % playerAttackSpeed == 0) {
			if(score >= 1300) playerAttackSpeed = 10;
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
			
			if(score >= 300 && score < 1300) {
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
			
			if(score >= 1300 && score < 1500) {
				for(int j = 0; j < enemy4List.size(); j++) {
					enemy4 = enemy4List.get(j);
					if((playerAttack.x+playerAttack.width>enemy4.x&&playerAttack.x+playerAttack.width<enemy4.x+enemy4.width&&playerAttack.y+playerAttack.height>enemy4.y&&playerAttack.y+playerAttack.height<enemy4.y+enemy4.height)||(playerAttack.x+playerAttack.width>enemy4.x&&playerAttack.x+playerAttack.width<enemy4.x+enemy4.width&&playerAttack.y>enemy4.y&&playerAttack.y<enemy4.y+enemy4.height)) { //히트박스 범위(좌표 기준은 항상 가장 좌측 위에 모서리)
						playerAttackHitEffect = new PlayerAttackHitEffect(playerAttack.x, playerAttack.y);
						playerAttackHitEffectList.add(playerAttackHitEffect);
						Timer loadingTimer = new Timer();
				        TimerTask loadingTask = new TimerTask() {
				            @Override
				            public void run() {playerAttackHitEffectList.remove(playerAttackHitEffect);}
				        };
				        loadingTimer.schedule(loadingTask, 150);
						enemy4.hp -= playerAttack.attack; //에너지 깎음
						playerAttackList.remove(playerAttack); //맞춘 공격 물체는 삭제
					}
					if(enemy4.hp <= 0) {
						enemy4killsound.start();
						enemy4List.remove(enemy4);
						score += 100;
					}
						
				}
				
			}
			
			if(score >= 1500 && score < 1800) {
				for(int j = 0; j < enemy5List.size(); j++) {
					enemy5 = enemy5List.get(j);
					if((playerAttack.x+playerAttack.width>enemy5.x&&playerAttack.x+playerAttack.width<enemy5.x+enemy5.width&&playerAttack.y+playerAttack.height>enemy5.y&&playerAttack.y+playerAttack.height<enemy5.y+enemy5.height)||(playerAttack.x+playerAttack.width>enemy5.x&&playerAttack.x+playerAttack.width<enemy5.x+enemy5.width&&playerAttack.y>enemy5.y&&playerAttack.y<enemy5.y+enemy5.height)) { //히트박스 범위(좌표 기준은 항상 가장 좌측 위에 모서리)
						playerAttackHitEffect = new PlayerAttackHitEffect(playerAttack.x, playerAttack.y);
						playerAttackHitEffectList.add(playerAttackHitEffect);
						Timer loadingTimer = new Timer();
				        TimerTask loadingTask = new TimerTask() {
				            @Override
				            public void run() {playerAttackHitEffectList.remove(playerAttackHitEffect);}
				        };
				        loadingTimer.schedule(loadingTask, 150);
						enemy5.hp -= playerAttack.attack; //에너지 깎음
						playerAttackList.remove(playerAttack); //맞춘 공격 물체는 삭제
					}
					if(enemy5.hp <= 0) {
						enemy5List.remove(enemy5);
						score += 100;
					}
						
				}
				
			}
			
			if(score >= 1800) {
				
				for(int j = 0; j < enemy6List.size(); j++) {
					enemy6 = enemy6List.get(j);
					if((playerAttack.x+playerAttack.width>enemy6.x&&playerAttack.x+playerAttack.width<enemy6.x+enemy6.width&&playerAttack.y+playerAttack.height>enemy6.y&&playerAttack.y+playerAttack.height<enemy6.y+enemy6.height)||(playerAttack.x+playerAttack.width>enemy6.x&&playerAttack.x+playerAttack.width<enemy6.x+enemy6.width&&playerAttack.y>enemy6.y&&playerAttack.y<enemy6.y+enemy6.height)) { //占쏙옙트占쌘쏙옙 占쏙옙占쏙옙(占쏙옙표 占쏙옙占쏙옙占쏙옙 占쌓삼옙 占쏙옙占쏙옙 占쏙옙占쏙옙 占쏙옙占쏙옙 占쏜서몌옙)
						playerAttackHitEffect = new PlayerAttackHitEffect(playerAttack.x, playerAttack.y);
						playerAttackHitEffectList.add(playerAttackHitEffect);
						Timer loadingTimer = new Timer();
				        TimerTask loadingTask = new TimerTask() {
				            @Override
				            public void run() {playerAttackHitEffectList.remove(playerAttackHitEffect);}
				        };
				        loadingTimer.schedule(loadingTask, 150);
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
					skillHitEffect = new SkillHitEffect(skillAttack.x, skillAttack.y);
					skillHitEffectList.add(skillHitEffect);
					Timer loadingTimer = new Timer();
			        TimerTask loadingTask = new TimerTask() {
			            @Override
			            public void run() {skillHitEffectList.remove(skillHitEffect);}
			        };
			        loadingTimer.schedule(loadingTask, 200);
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
						skillHitEffect = new SkillHitEffect(skillAttack.x, skillAttack.y);
						skillHitEffectList.add(skillHitEffect);
						Timer loadingTimer = new Timer();
				        TimerTask loadingTask = new TimerTask() {
				            @Override
				            public void run() {skillHitEffectList.remove(skillHitEffect);}
				        };
				        loadingTimer.schedule(loadingTask, 200);
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
			
			if(score >= 300 && score < 1300) {
				for(int j = 0; j < enemy3List.size(); j++) {
					enemy3 = enemy3List.get(j);
					if((skillAttack.x+skillAttack.width>enemy3.x&&skillAttack.x+skillAttack.width<enemy3.x+enemy3.width&&skillAttack.y+skillAttack.height>enemy3.y&&skillAttack.y+skillAttack.height<enemy3.y+enemy3.height)||(skillAttack.x+skillAttack.width>enemy3.x&&skillAttack.x+skillAttack.width<enemy3.x+enemy3.width&&skillAttack.y>enemy3.y&&skillAttack.y<enemy3.y+enemy3.height)) { //히트박스 범위(좌표 기준은 항상 가장 좌측 위에 모서리)
						skillHitEffect = new SkillHitEffect(skillAttack.x, skillAttack.y);
						skillHitEffectList.add(skillHitEffect);
						Timer loadingTimer = new Timer();
				        TimerTask loadingTask = new TimerTask() {
				            @Override
				            public void run() {skillHitEffectList.remove(skillHitEffect);}
				        };
				        loadingTimer.schedule(loadingTask, 200);
						
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
			
			if(score >= 1300 && score < 1500) {
				for(int j = 0; j < enemy4List.size(); j++) {
					enemy4 = enemy4List.get(j);
					if((skillAttack.x+skillAttack.width>enemy4.x&&skillAttack.x+skillAttack.width<enemy4.x+enemy4.width&&skillAttack.y+skillAttack.height>enemy4.y&&skillAttack.y+skillAttack.height<enemy4.y+enemy4.height)||(skillAttack.x+skillAttack.width>enemy4.x&&skillAttack.x+skillAttack.width<enemy4.x+enemy4.width&&skillAttack.y>enemy4.y&&skillAttack.y<enemy4.y+enemy4.height)) { //히트박스 범위(좌표 기준은 항상 가장 좌측 위에 모서리)
						skillHitEffect = new SkillHitEffect(skillAttack.x, skillAttack.y);
						skillHitEffectList.add(skillHitEffect);
						Timer loadingTimer = new Timer();
				        TimerTask loadingTask = new TimerTask() {
				            @Override
				            public void run() {skillHitEffectList.remove(skillHitEffect);}
				        };
				        loadingTimer.schedule(loadingTask, 200);
						enemy4.hp -= skillAttack.attack; //에너지 깎음
						skillAttackList.remove(skillAttack); //맞춘 공격 물체는 삭제
					}
					if(enemy4.hp <= 0) {
						enemy4killsound.start();
						enemy4List.remove(enemy4);
						score += 100;
					}
						
				}
				
			}
			
			if(score >= 1500 && score < 1800) {
				for(int j = 0; j < enemy5List.size(); j++) {
					enemy5 = enemy5List.get(j);
					if((skillAttack.x+skillAttack.width>enemy5.x&&skillAttack.x+skillAttack.width<enemy5.x+enemy5.width&&skillAttack.y+skillAttack.height>enemy5.y&&skillAttack.y+skillAttack.height<enemy5.y+enemy5.height)||(skillAttack.x+skillAttack.width>enemy5.x&&skillAttack.x+skillAttack.width<enemy5.x+enemy5.width&&skillAttack.y>enemy5.y&&skillAttack.y<enemy5.y+enemy5.height)) { //히트박스 범위(좌표 기준은 항상 가장 좌측 위에 모서리)
						skillHitEffect = new SkillHitEffect(skillAttack.x, skillAttack.y);
						skillHitEffectList.add(skillHitEffect);
						Timer loadingTimer = new Timer();
				        TimerTask loadingTask = new TimerTask() {
				            @Override
				            public void run() {skillHitEffectList.remove(skillHitEffect);}
				        };
				        loadingTimer.schedule(loadingTask, 200);
						enemy5.hp -= skillAttack.attack; //에너지 깎음
						skillAttackList.remove(skillAttack); //맞춘 공격 물체는 삭제
					}
					if(enemy5.hp <= 0) {
						
						enemy5List.remove(enemy5);
						score += 100;
					}
						
				}
				
			}
			
			if(score >= 1500) {
				
				for(int j = 0; j < enemy6List.size(); j++) {
					enemy6 = enemy6List.get(j);
					if((skillAttack.x+skillAttack.width>enemy6.x&&skillAttack.x+skillAttack.width<enemy6.x+enemy6.width&&skillAttack.y+skillAttack.height>enemy6.y&&skillAttack.y+skillAttack.height<enemy6.y+enemy6.height)||(skillAttack.x+skillAttack.width>enemy6.x&&skillAttack.x+skillAttack.width<enemy6.x+enemy6.width&&skillAttack.y>enemy6.y&&skillAttack.y<enemy6.y+enemy6.height)) { //占쏙옙트占쌘쏙옙 占쏙옙占쏙옙(占쏙옙표 占쏙옙占쏙옙占쏙옙 占쌓삼옙 占쏙옙占쏙옙 占쏙옙占쏙옙 占쏙옙占쏙옙 占쏜서몌옙)
						skillHitEffect = new SkillHitEffect(skillAttack.x, skillAttack.y);
						skillHitEffectList.add(skillHitEffect);
						Timer loadingTimer = new Timer();
				        TimerTask loadingTask = new TimerTask() {
				            @Override
				            public void run() {skillHitEffectList.remove(skillHitEffect);}
				        };
				        loadingTimer.schedule(loadingTask, 200);
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
					enemy3shot2sound.start();
					}
				}
				
				if(i % 90 == 0 && enemy3List.size() > 0) {
					for(int j = 0; j < enemy3List.size(); j++) {
					enemy3Attack2 = new Enemy3Attack2((int)Math.random()*301+ 600, 720);
					enemy3Attack2List.add(enemy3Attack2);
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
			
				
			
	
	private void enemy4AppearProcess() {
		if(i % 160 == 0) {
			enemy4 = new Enemy4(1120,(int)(Math.random()*476));
			enemy4List.add(enemy4);
		}
	}
	
	
	//적의 이동 구현
	private void enemy4MoveProcess() {
		for(int i= 0; i<enemy4List.size(); i++) {
			enemy4 = enemy4List.get(i);
			enemy4.move(); //적 이동
		}
	}
	
	private void enemy4AttackProcess() {
		if((i % 90 == 0 || i % 100 == 0) && enemy4List.size() > 0) {
			for(int j = 0; j < enemy4List.size(); j++) {
			enemy4Attack = new Enemy4Attack(enemy4List.get(j).x - 100 , enemy4List.get(j).y + 75);
			enemy4AttackList.add(enemy4Attack);
			enemy4shotsound.start();
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

	 
	 
	//게임 화면에 플레이어 캐릭터 그리기
	public void gameDraw(Graphics g) {
		enemyDraw(g);
		enemy2Draw(g);
		enemy3Draw(g);
		enemy4Draw(g);
		enemy5Draw(g);
		enemy6Draw(g);
		playerDraw(g);
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
	
	//플레이어 캐릭터 이미지 그리기
	public void playerDraw(Graphics g) {
		g.drawImage(player,  playerX, playerY, null);
		g.setColor(Color.GREEN);
		g.fillRect(playerX - 1, playerY - 40, (int)(playerHp *2), 20);
		
		//공격 이미지 생성
		
		for(int i=0; i < playerAttackList.size(); i++) {
			playerAttack = playerAttackList.get(i);
			if(score < 1300)
			g.drawImage(playerAttack.attackImage, playerAttack.x, playerAttack.y, null);
			else if(score >= 1300)
				g.drawImage(playerAttack.attackImage2, playerAttack.x, playerAttack.y, null);
		}
		
		for(int i = 0; i < skillAttackList.size(); i++) {
			skillAttack = skillAttackList.get(i);
			g.drawImage(skillAttack.skillImage, skillAttack.x, skillAttack.y, null);
		}
		for(int i = 0; i < skillHitEffectList.size(); i++) {
			skillHitEffect = skillHitEffectList.get(i);
			g.drawImage(skillHitEffect.skillHitImage, skillHitEffect.hitx, skillHitEffect.hity, null);
		}
		for(int i = 0; i < playerAttackHitEffectList.size(); i++) {
			playerAttackHitEffect = playerAttackHitEffectList.get(i);
			g.drawImage(playerAttackHitEffect.playerAttackHitImage, playerAttackHitEffect.hitx, playerAttackHitEffect.hity, null);
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
