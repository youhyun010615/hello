package shoot;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Game extends Thread {
	
	//巴傾戚嬢 戚耕走
	private Image player = new ImageIcon("src/images/player.png").getImage();
	
	private int playerX, playerY; //巴傾戚嬢 蝶遣斗 疎妊
	private int playerWidth = player.getWidth(null); //巴傾戚嬢 蝶遣斗 亜稽掩戚
	private int playerHeight = player.getHeight(null); //巴傾戚嬢 蝶遣斗 室稽掩戚
	private int playerSpeed = 10; //巴傾戚嬢 蝶遣斗 戚疑 紗亀
	private double playerHp = 30; //巴傾戚嬢 蝶遣斗 端径
	private int playerAttackSpeed = 20; //巴傾戚嬢 因維 紗亀(碍聖呪系 装亜)
	
	
	private boolean up, down, left, right; //崇送戚奄 是背 痕呪 識情
	private boolean shooting; //true析 井酔 因維 降紫
	private boolean isOver; //惟績神獄 食採
	
	private Audio backgroundMusic,hitsound,killsound,enemykillsound,enemyshotsound,enemy2killsound,enemy2shotsound,enemy3killsound,enemy3shotsound,enemy3shot2sound,enemy3shot3sound,enemy3ultimatesound,enemy4killsound,enemy4shotsound,enemy6shotsound,enemy6shot2sound;
	
	private int score = 0; //繊呪研 蟹展馨 痕呪
	
	
	int delta;
	static int i,j = 1;
	
	
	
	
	
	
	//巴傾戚嬢税 因維聖 眼聖 ArrayList(ArrayList税 紫戚綜澗 亜痕戚奄 凶庚拭 床奄 疏製(紫戚綜 採膳馬檎 切疑生稽 装亜))
	ArrayList<PlayerAttack> playerAttackList = new ArrayList<PlayerAttack>();
	private PlayerAttack playerAttack; //ArrayList照税 鎧遂拭 襲惟 羨悦拝 呪 赤惟 痕呪 識情
	//旋税 戚疑引 旋税 因維聖 眼聖 ArrayList
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
	
	ArrayList<Enemy6> enemy6List = new ArrayList<Enemy6>();
	ArrayList<Enemy6Attack> enemy6AttackList = new ArrayList<Enemy6Attack>();
	ArrayList<Enemy6Attack2> enemy6Attack2List = new ArrayList<Enemy6Attack2>();
	ArrayList<Enemy6Warning> enemy6WarningList = new ArrayList<Enemy6Warning>();
	private Enemy6 enemy6;
	private Enemy6Attack enemy6Attack;
	private Enemy6Attack2 enemy6Attack2;
	private Enemy6Warning enemy6Warning;
	
	//床傾球亜 獣拙馬檎(start()研 搭背) run() 五社球 叔楳
	public void run() {
		/*惟績聖 60覗傾績生稽 竺舛
		  1/60段原陥 是帖亜 郊餓惟 硝壱軒葬聖 速製
		  季展(走貝 獣娃(腔軒段))税 葵戚 1000/60腔軒段亜 吃 凶 原陥 0生稽 段奄鉢 馬澗 号縦
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
			while(!isOver) { //isOver戚 false析 疑照 巷廃 欠覗
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
					if(score>=1500) {
						enemy6AppearProcess();
						enemy6MoveProcess();
						enemy6AttackProcess();
						enemy6Attack2Process();
						
					}
					System.out.println(playerX + "  " + playerY + "  hp : " + playerHp);
					i++; //因維紗亀 舛馬奄 是背 幻糾
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
		enemy6List.clear();
		enemy6AttackList.clear();
		playerHp = 100;
		playerX = 10;
		playerY = (Main.SCREEN_HEIGHT - playerHeight) / 2;
		playerAttackSpeed = 20;
		score = 0;
		Enemy3.isUltimate = false;
		isOver = false;
		backgroundMusic.start();
		
	}
	
	
	
	//雌馬疎酔 繕拙 竺舛(但 鉱生稽 蝶遣斗亜 蟹亜走 省亀系 繕闇亀 蓄亜敗)
	private void keyProcess() {
		if (up == true && playerY - playerSpeed > 0) playerY -= playerSpeed;
		if (down == true && playerY + playerHeight + playerSpeed < Main.SCREEN_HEIGHT) playerY += playerSpeed;
		if (left == true && playerX - playerSpeed > 0) playerX -= playerSpeed;
		if (right == true && playerX + playerWidth + playerSpeed < Main.SCREEN_WIDTH) playerX += playerSpeed;
		//因維 紗亀研 0.2段稽 竺舛
		if (shooting == true && i % playerAttackSpeed == 0) {
			if(score >= 1300) playerAttackSpeed = 10;
			playerAttack = new PlayerAttack(playerX + 100,playerY - 30); //持失切 古鯵痕呪研 搭背 因維 持失走繊 竺舛
			playerAttackList.add(playerAttack); //昔畿什拭 域紗 蓄亜馬悟 紫戚綜研 域紗 潅形姶(run()拭辞 keyProcess()亜 匙牽惟 域紗 宜奄 凶庚)
			
		}
	}
	
	/*購拭 蟹神澗 鋼差庚級拭 企廃 戚背
	  >床傾球稽 昔背 五社球級戚 古酔 匙牽惟 宜焼亜奄 凶庚拭
	  for(int i=0; i < けけけ.size(); i++) {
	  けけけけ = けけけ.get(i);
	  五社球級
	  }
	  旭精 姥繕研 亜遭 橿汐精 益撹 乞窮 i級拭 企背 五社球級戚 叔楳吉陥壱 持唖馬檎 畷馬陥
	 */
	
	//因維 蒋生稽 蟹亜澗暗 姥薄
	private void playerAttackProcess() {
		for(int i=0; i < playerAttackList.size(); i++) {
			playerAttack = playerAttackList.get(i); 
			playerAttack.fire(); //因維 神献楕生稽 戚疑獣徹澗 五社球
			
			//因維 毒舛
			if(score <=100) {
			for(int j = 0; j < enemyList.size(); j++) {
				enemy = enemyList.get(j);
				if((playerAttack.x+playerAttack.width>enemy.x&&playerAttack.x+playerAttack.width<enemy.x+enemy.width&&playerAttack.y+playerAttack.height>enemy.y&&playerAttack.y+playerAttack.height<enemy.y+enemy.height)||(playerAttack.x+playerAttack.width>enemy.x&&playerAttack.x+playerAttack.width<enemy.x+enemy.width&&playerAttack.y>enemy.y&&playerAttack.y<enemy.y+enemy.height)) { //備闘酵什 骨是(疎妊 奄層精 牌雌 亜舌 疎著 是拭 乞辞軒)
					enemy.hp -= playerAttack.attack; //拭格走 艶製
					playerAttackList.remove(playerAttack); //限秩 因維 弘端澗 肢薦
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
					if((playerAttack.x+playerAttack.width>enemy2.x&&playerAttack.x+playerAttack.width<enemy2.x+enemy2.width&&playerAttack.y+playerAttack.height>enemy2.y&&playerAttack.y+playerAttack.height<enemy2.y+enemy2.height)||(playerAttack.x+playerAttack.width>enemy2.x&&playerAttack.x+playerAttack.width<enemy2.x+enemy2.width&&playerAttack.y>enemy2.y&&playerAttack.y<enemy2.y+enemy2.height)) { //備闘酵什 骨是(疎妊 奄層精 牌雌 亜舌 疎著 是拭 乞辞軒)
						enemy2.hp -= playerAttack.attack; //拭格走 艶製
						playerAttackList.remove(playerAttack); //限秩 因維 弘端澗 肢薦
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
					if((playerAttack.x+playerAttack.width>enemy3.x&&playerAttack.x+playerAttack.width<enemy3.x+enemy3.width&&playerAttack.y+playerAttack.height>enemy3.y&&playerAttack.y+playerAttack.height<enemy3.y+enemy3.height)||(playerAttack.x+playerAttack.width>enemy3.x&&playerAttack.x+playerAttack.width<enemy3.x+enemy3.width&&playerAttack.y>enemy3.y&&playerAttack.y<enemy3.y+enemy3.height)) { //備闘酵什 骨是(疎妊 奄層精 牌雌 亜舌 疎著 是拭 乞辞軒)
						enemy3.hp -= playerAttack.attack; //拭格走 艶製
						playerAttackList.remove(playerAttack); //限秩 因維 弘端澗 肢薦
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
					if((playerAttack.x+playerAttack.width>enemy4.x&&playerAttack.x+playerAttack.width<enemy4.x+enemy4.width&&playerAttack.y+playerAttack.height>enemy4.y&&playerAttack.y+playerAttack.height<enemy4.y+enemy4.height)||(playerAttack.x+playerAttack.width>enemy4.x&&playerAttack.x+playerAttack.width<enemy4.x+enemy4.width&&playerAttack.y>enemy4.y&&playerAttack.y<enemy4.y+enemy4.height)) { //備闘酵什 骨是(疎妊 奄層精 牌雌 亜舌 疎著 是拭 乞辞軒)
						enemy4.hp -= playerAttack.attack; //拭格走 艶製
						playerAttackList.remove(playerAttack); //限秩 因維 弘端澗 肢薦
					}
					if(enemy4.hp <= 0) {
						enemy4killsound.start();
						enemy4List.remove(enemy4);
						score += 100;
					}
						
				}
				
			}
			
			if(score >= 1500) {
				
				for(int j = 0; j < enemy6List.size(); j++) {
					enemy6 = enemy6List.get(j);
					if((playerAttack.x+playerAttack.width>enemy6.x&&playerAttack.x+playerAttack.width<enemy6.x+enemy6.width&&playerAttack.y+playerAttack.height>enemy6.y&&playerAttack.y+playerAttack.height<enemy6.y+enemy6.height)||(playerAttack.x+playerAttack.width>enemy6.x&&playerAttack.x+playerAttack.width<enemy6.x+enemy6.width&&playerAttack.y>enemy6.y&&playerAttack.y<enemy6.y+enemy6.height)) { //鐃緒申闘鐃准緒申 鐃緒申鐃緒申(鐃緒申妊 鐃緒申鐃緒申鐃緒申 鐃竣誌申 鐃緒申鐃緒申 鐃緒申鐃緒申 鐃緒申鐃緒申 鐃署辞醐申)
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
	}

	//旋税 去舌 姥薄 (y疎妊葵戚 1~620拭辞 沓棋 窒薄)
	private void enemyAppearProcess() {
		if(i % 160 == 0) {
			enemy = new Enemy(1120,(int)(Math.random()*476));
			enemyList.add(enemy);
		}
	}
	
	
	//旋税 戚疑 姥薄
	private void enemyMoveProcess() {
		for(int i= 0; i<enemyList.size(); i++) {
			enemy = enemyList.get(i);
			enemy.move(); //旋 戚疑
				
		}
	}
	
	//旋税 因維 姥薄
	private void enemyAttackProcess() {
		if(i % 100 == 0 && enemyList.size() > 0) { //enemyList.size() > 0 旋精 戚政 : 因維戚 旋 是帖 奄層生稽 蟹神奄 凶庚拭 旋戚 蟹神奄 穿拭 因維戚 蟹神檎 神嫌亜 降持廃陥
			for(int j = 0; j < enemyList.size(); j++) { //enemyAttackList拭 旋 鯵端 呪幻鏑 enemyAttack聖 蓄亜馬奄 是背 鋼差庚 蓄亜
			enemyAttack = new EnemyAttack(enemyList.get(j).x - 79, enemyList.get(j).y + 35); //旋 鯵端 呪亜 食君 原軒析 凶 EnemyAttack 持失 疎妊研 enemy.x, enemy.y稽 砧檎 原走厳拭 蟹紳 旋拭惟辞 EnemyAttack戚 掻淡生稽 蟹紳陥. 持失 疎妊研 enemyList税 唖 昔畿什 原陥税 xy疎妊稽 丘醤 旋戚 EnemyAttack聖 唖唖 馬蟹梢 劾鍵陥. 
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
	
	
	//旋税 戚疑 姥薄
	private void enemy2MoveProcess() {
		for(int i= 0; i<enemy2List.size(); i++) {
			enemy2 = enemy2List.get(i);
			enemy2.move(); //旋 戚疑
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
		
	}
	
	
	//旋税 戚疑 姥薄
	private void enemy3MoveProcess() {
		for(int i= 0; i<enemy3List.size(); i++) {
			enemy3 = enemy3List.get(i);
			if(enemy3.x >800)
			enemy3.move(); //旋 戚疑
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
			if(enemy3.hp == 50) {
				Enemy3.isUltimate = true;
				enemy3ultimatesound.start();
				enemy3.hp = 262;
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
	
	
	//旋税 戚疑 姥薄
	private void enemy4MoveProcess() {
		for(int i= 0; i<enemy4List.size(); i++) {
			enemy4 = enemy4List.get(i);
			enemy4.move(); //旋 戚疑
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
	
	private void enemy6AppearProcess() {
		if(enemy6List.size() == 0) {
			enemy6 = new Enemy6(1120, 133);
			enemy6List.add(enemy6);
		}
	}
	
	private void enemy6MoveProcess() {
			enemy6 = enemy6List.get(0);
			if(enemy6.x > 800)
			enemy6.move();
		
	}
	
	private void enemy6AttackProcess() {
		if(i % 120 == 0 && enemy6List.size() > 0 && enemy6WarningList.size() >= 1) {
			for(int j = 0; j < enemy6List.size(); j++) {
			enemy6Attack = new Enemy6Attack((int)(Math.random()*801), 0);
			enemy6AttackList.add(enemy6Attack);
			enemy6shotsound.start();
			
			}
		}
		
		if(i % 120 == 60 && enemy6List.size() > 0 ) {
			
			for(int j = 0; j < enemy6List.size(); j++) {
			enemy6Warning = new Enemy6Warning((int)(Math.random()*801), 0);
			enemy6WarningList.add(enemy6Warning);
			enemy6shotsound.start();
			}
		}
		
		for(int i = 0; i < enemy6AttackList.size(); i++) {
			enemy6Attack = enemy6AttackList.get(i);
	
			if((enemy6Attack.x<playerX+playerWidth&&enemy6Attack.x>playerX&&enemy6Attack.y+enemy6Attack.height>playerY&&enemy6Attack.y+enemy6Attack.height<playerY+playerHeight)||(enemy6Attack.x<playerX+playerWidth&&enemy6Attack.x>playerX&&enemy6Attack.y>playerY&&enemy6Attack.y<playerY+playerHeight)) {
				hitsound.start();
				playerHp -= enemy6Attack.attack;
				enemy6AttackList.remove(enemy6Attack);
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

	 
	 
	//惟績 鉢檎拭 巴傾戚嬢 蝶遣斗 益軒奄
	public void gameDraw(Graphics g) {
		playerDraw(g);
		enemyDraw(g);
		enemy2Draw(g);
		enemy3Draw(g);
		enemy4Draw(g);
		enemy6Draw(g);
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
	
	//巴傾戚嬢 蝶遣斗 戚耕走 益軒奄
	public void playerDraw(Graphics g) {
		g.drawImage(player,  playerX, playerY, null);
		g.setColor(Color.GREEN);
		g.fillRect(playerX - 1, playerY - 40, (int)(playerHp *2), 20);
		
		//因維 戚耕走 持失
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
	
	public void enemy6Draw(Graphics g) {
		for(int i= 0; i < enemy6List.size(); i++) {
			enemy6 = enemy6List.get(i);
			g.drawImage(enemy6.image, enemy6.x, enemy6.y, null);
			g.setColor(Color.red);
			g.fillRect(enemy6.x - 1, enemy6.y - 40, enemy6.hp *7, 20);
			
		}
		
		for(int i = 0; i < enemy6AttackList.size(); i++) {
			enemy6Attack = enemy6AttackList.get(i);
			g.drawImage(enemy6Attack.image, enemy6Attack.x, enemy6WarningList.get(0).y, null);
		}
		
		for(int i = 0; i < enemy6WarningList.size(); i++) {
			enemy6Warning = enemy6WarningList.get(i);
			g.drawImage(enemy6Warning.image, enemy6Warning.x, enemy6WarningList.get(0).y, null);
		}
		

		for(int i = 0; i < enemy6Attack2List.size(); i++) {
			enemy6Attack2 = enemy6Attack2List.get(i);
			g.drawImage(enemy6Attack2.image, enemy6Attack2.x, enemy6Attack2.y, null);
		}
		
	}

	//up, down, left, right亜 private戚奄 凶庚拭 setter研 戚遂敗
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
