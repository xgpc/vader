package com.vader.attackfly;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class run extends Frame {

	// 窗口 中心
	private int windows_X = this.getWidth() / 2;
	private int windows_Y = this.getHeight() / 2;

	/** 游戏的当前状态: START RUNNING PAUSE GAME_OVER */
	private int state;
	private static final int MENU = 0;
	private static final int START = 1;
	private static final int RUNNING = 2;
	private static final int PAUSE = 3;
	private static final int GAME_OVER = 4;

	private int score = 0; // 得分
	private int boom = 1; // 炸弹
	private Timer timer; // 定时器
	private int intervel = 40; // 时间间隔(毫秒)
	private int intervaltime = 50; // 400毫秒生成一个飞行物--10*40

	// 资源加载
	public static BufferedImage background; // 背景
	public static BufferedImage start; // 开始
	public static BufferedImage pause; // 暂停
	public static BufferedImage gameover; // 结束

	public static BufferedImage airplane;
	public static BufferedImage bee;
	public static BufferedImage bullet;

	public static BufferedImage hero0;
	public static BufferedImage hero1;

	public static BufferedImage boss1;
	public static BufferedImage boss2;
	public static BufferedImage boss3;
	public static BufferedImage boss4;

	public static BufferedImage fj1;
	public static BufferedImage fj2;
	public static BufferedImage fj3;
	public static BufferedImage fj4;

	public static BufferedImage ezd1;
	public static BufferedImage ezd2;
	public static BufferedImage ezd3;

	public static BufferedImage zd1;
	public static BufferedImage zd2;
	public static BufferedImage zd3;
	public static BufferedImage zd4;

	// 飞行物入场计数
	private int flyEnteredIndex = 0;

	// 主角
	private List<Hero> heros = new ArrayList<Hero>();

	// 飞行物
	private List<FlyingObject> enemys = new ArrayList<FlyingObject>();

	// 子弹
	private List<Bullet> bullets = new ArrayList<Bullet>();

	// 飞行物子弹
	private List<Bullet> enemy_bullets = new ArrayList<Bullet>();

	public boolean space;

	private int attacknum = 0;

	static {
		try {
			background = ImageIO.read(new File("images/background.jpg"));
			start = ImageIO.read(new File("images/start.png"));
			pause = ImageIO.read(new File("images/pause.png"));
			gameover = ImageIO.read(new File("images/gameover.png"));

			hero0 = ImageIO.read(new File("images/1p.png"));
			hero1 = ImageIO.read(new File("images/2p.png"));

			boss1 = ImageIO.read(new File("images/boss1.png"));
			boss2 = ImageIO.read(new File("images/boss2.png"));
			boss3 = ImageIO.read(new File("images/boss3.png"));
			boss4 = ImageIO.read(new File("images/boss4.png"));
			fj1 = ImageIO.read(new File("images/fj1.png"));
			fj2 = ImageIO.read(new File("images/fj2.png"));
			fj3 = ImageIO.read(new File("images/fj3.png"));
			fj4 = ImageIO.read(new File("images/fj4.png"));

			ezd1 = ImageIO.read(new File("images/ezd1.png"));
			ezd2 = ImageIO.read(new File("images/ezd2.png"));
			ezd3 = ImageIO.read(new File("images/ezd3.png"));

			zd1 = ImageIO.read(new File("images/zd1.png"));
			zd2 = ImageIO.read(new File("images/zd2.png"));
			zd3 = ImageIO.read(new File("images/zd3.png"));
			zd4 = ImageIO.read(new File("images/zd4.png"));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * ; 初始化窗口配置
	 */
	public void initgame() {
		this.setTitle("一起打飞机");
		this.setVisible(true);
		this.setSize(this.background.getWidth(), this.background.getHeight());
		this.setLocation(500, 100);
		this.state = 0;
		this.windows_X = this.getWidth() / 2;
		this.windows_Y = this.getHeight() / 2;

		// 初始化玩家
		Hero h1 = new Hero(windows_X - this.hero0.getHeight() / 2, this.getHeight() - 100);
		h1.setImage(this.hero0);
		h1.setHeight(this.background.getHeight());
		h1.setWidth(this.background.getWidth());

		Hero h2 = new Hero(0, 0);
		h2.setImage(this.hero1);
		h2.setHeight(this.background.getHeight());
		h2.setWidth(this.background.getWidth());

		this.heros.add(h1);
		this.heros.add(h2);

		for (int i = 0; i < heros.size(); i++) {
			heros.get(i).setZd1(zd1);
			heros.get(i).setZd2(zd2);
			heros.get(i).setZd3(zd3);
			heros.get(i).setZd4(zd4);
		}

		addKeyListener(new KeyMonitor()); // 监听键盘
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

	}

	// 监听键盘类
	class KeyMonitor extends KeyAdapter {

		@Override
		public void keyPressed(KeyEvent e) {
//			System.out.println(e.getKeyCode());
			PressKey(e);
		}

		@Override
		public void keyReleased(KeyEvent e) {
//			System.out.println(e.getKeyCode());
			ReleasedKey(e);
		}
	}

	/*
	 * 启动游戏*
	 */
	public void rungame() {

		timer = new Timer(); // 主流程控制
		timer.schedule(new TimerTask() {
			@Override
			public void run() {

				// 填写游戏过程
				runtask();
				repaint(); // 重绘，调用paint()方法
			}

		}, intervel, intervel);
	}

	// 关联键盘按键
	public void PressKey(KeyEvent e) {
//		System.out.println("按下-:" + e.getKeyChar());
		switch (e.getKeyCode()) {
		case KeyEvent.VK_SPACE:
			space = true;
			break;
		case KeyEvent.VK_UP:
			this.heros.get(0).setup(true);
			break;
		case KeyEvent.VK_DOWN:
			this.heros.get(0).setdown(true);
			break;
		case KeyEvent.VK_LEFT:
			this.heros.get(0).setleft(true);
			break;
		case KeyEvent.VK_RIGHT:
			this.heros.get(0).setright(true);
			break;
		case KeyEvent.VK_NUMPAD2:
			this.heros.get(0).setFire(true);
			break;
		case KeyEvent.VK_NUMPAD3:
			this.heros.get(0).boom = true;
			break;
		case KeyEvent.VK_W:
			this.heros.get(1).up = true;
			break;
		case KeyEvent.VK_S:
			this.heros.get(1).down = true;
			break;
		case KeyEvent.VK_A:
			this.heros.get(1).left = true;
			break;
		case KeyEvent.VK_D:
			this.heros.get(1).right = true;
			break;
		case KeyEvent.VK_J:
			this.heros.get(1).fire = true;
			break;
		case KeyEvent.VK_K:
			this.heros.get(1).boom = true;
			break;
		}
	}

	public void ReleasedKey(KeyEvent e) {
//		System.out.println("抬起-:" + e.getKeyChar());
		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
			this.heros.get(0).up = false;
			break;
		case KeyEvent.VK_DOWN:
			this.heros.get(0).down = false;
			break;
		case KeyEvent.VK_LEFT:
			this.heros.get(0).left = false;
			break;
		case KeyEvent.VK_RIGHT:
			this.heros.get(0).right = false;
			break;
		case KeyEvent.VK_NUMPAD2:
			this.heros.get(0).fire = false;
			break;
		case KeyEvent.VK_NUMPAD3:
			this.heros.get(0).boom = false;
			break;
		case KeyEvent.VK_W:
			this.heros.get(1).up = false;
			break;
		case KeyEvent.VK_S:
			this.heros.get(1).down = false;
			break;
		case KeyEvent.VK_A:
			this.heros.get(1).left = false;
			break;
		case KeyEvent.VK_D:
			this.heros.get(1).right = false;
			break;
		case KeyEvent.VK_J:
			this.heros.get(1).fire = false;
			break;
		case KeyEvent.VK_K:
			this.heros.get(1).boom = false;
			break;
		}
	}

	public void runtask() {
		// System.out.print("runtask \n");
		// 状态切换
		switch (this.state) {
		case MENU:
			if (this.space) {
				this.space = false;
				this.state = START;
			}
			break;
		case START:
			// 初始化 游戏状态

			this.heros.get(0).addlife();
			this.heros.get(0).addlife();
			this.heros.get(0).addlife();
			this.flyEnteredIndex = 0;
			this.attacknum = 0;
			this.space = false;
			this.state = RUNNING;

			break;

		case RUNNING:
			// 空格暂停
			if (this.space) {
				this.space = false;
				this.state = PAUSE;
			}

			// 判断生命是否为0
			int life = 0;
			for (int i = 0; i < heros.size(); i++) {
				life += heros.get(i).getLife();
			}
			if (life <= 0) {
				this.state = GAME_OVER;
			}

			// 生成机体
			createEnemy();

			// 物体移动
			objectstep();

			// 攻击
			objectgo();

			// 碰撞检测
			bangAction();

			// 清理越界
			outOfBoundsAction();

			break;

		case PAUSE: // 暂停状态
			if (this.space) {
				this.space = false;
				this.state = RUNNING;
			}
			break;
		case GAME_OVER: // 游戏终止状态
			if (this.space) {
				this.space = false;
				this.state = MENU;
			}
			break;
		}
	}

	// 碰撞检测
	private void bangAction() {


		// 子弹检测
		for (int j = 0; j < bullets.size(); j++) {
			Bullet b = bullets.get(j);
			for (int i = 0; i < enemys.size(); i++) {
				Enemy e = (Enemy) enemys.get(i);

				if (e.collision(b) == false) {
					continue;
				}
				// 子弹消失
				bullets.remove(b);
				// TODO: 子弹爆炸特效

				//
				if (e.subLife(b.getFire()) < 0) {
					this.score += e.getScore();
					if(e.isBoos()) {
						this.isBoss = false;
					}
					enemys.remove(e);
					break;
				}
			}
		}

		// 撞机检测
		for (int i = 0; i < heros.size(); i++) {
			Hero h = heros.get(i);
			for (int j = 0; j < enemys.size(); j++) {
				Enemy e = (Enemy) enemys.get(j);
				if (h.collision(e) == false) {
					continue;
				}
				System.out.println("pengdao");
				
				
			}
		}

	}

	private void objectgo() {
		attacknum++;
		// 英雄攻击
		for (int i = 0; i < heros.size(); i++) {
			Hero h = heros.get(i);

			// 控制攻击平率
			if (attacknum % h.getAttackspeed() == 0) {
//				System.out.println(attacknum+ "---" + h.getAttackspeed() + "---" + attacknum % h.getAttackspeed()+ "---" + h.fire);		
				Bullet b = h.attack();
				if (b != null) {
					this.bullets.add(b);
				}
			}

			// 使用炸弹
			if (h.Boom()) {
				if (this.boom == 0) {
					continue;
				}
				
				this.enemy_bullets.clear();
				for (int j = 0; j < enemys.size(); j++) {
					Enemy e = (Enemy)enemys.get(j);
					if(e.isBoos()== true) {
						continue;
					}
					enemys.remove(e);
				}
								
				
				this.boom--;
			}
		}
	}

	private void createEnemy() {
		flyEnteredIndex++;

		if (flyEnteredIndex % intervaltime == 0) {
			FlyingObject obj = nextOne(); // 随机生成一个飞行物

			// 设置飞行物的 界面边框
			obj.setHeight(this.background.getHeight());
			obj.setWidth(this.background.getWidth());
			this.enemys.add(obj);
		}
	}

	private boolean isBoss = false;

	private FlyingObject nextOne() {
		Random random = new Random();
		int type = random.nextInt(20); // [0,20)
		Enemy e = new Enemy();

		if (type < 8) {
			e.setImage(this.fj1);
			e.setBulletType(0);
			e.setLife(2);

		} else if (type < 12) {
			e.setImage(this.fj2);
			e.setBulletType(0);
			e.setLife(3);

		} else if (type < 13) {
			e.setImage(this.fj3);
			e.setBulletType(1);
			e.setLife(4);
		} else if (type < 20) {

			if ((type == 19) && (isBoss != true)) {
				SetBoss(e);
				isBoss = true;
				return e;
			}
			e.setImage(this.fj4);
			e.setBulletType(2);
			e.setLife(4);
		}

		e.setX(random.nextInt(this.background.getWidth() - e.getImage().getWidth()) + 10);

		return e;
	}

	private void SetBoss(Enemy e) {

		Random random = new Random();
		int type = random.nextInt(4); // [0,4)
		switch (type) {
		case 0:
			e.setImage(this.boss1);
			break;
		case 1:
			e.setImage(this.boss2);
			break;
		case 2:
			e.setImage(this.boss3);
			break;
		case 3:
			e.setImage(this.boss4);
			break;

		default:
			break;
		}

		e.setBoos(true);
		e.setLife(20);
		e.setScore(20);
		e.setSpeed(1);
		e.setBoos(true);

		// 设置Boss位置居中
		e.setX(this.windows_X - e.getImage().getWidth() / 2);
		e.setBulletType(10);
	}

	// objectstep 物体移动
	public void objectstep() {
		for (int i = 0; i < heros.size(); i++) {
			heros.get(i).step();
		}

		// 子弹移动
		for (int i = 0; i < this.bullets.size(); i++) {
			bullets.get(i).step();
		}

		// 敌机子弹移动
		for (int i = 0; i < this.enemy_bullets.size(); i++) {
			enemy_bullets.get(i).step();
		}

		// 飞行物移动
		for (int i = 0; i < enemys.size(); i++) {
			enemys.get(i).step();
		}
	}

	/**
	 * 描画所有的对象
	 */
	@Override
	public void paint(Graphics g) {
		g.drawImage(background, 0, 0, null); // 画背景图
		paintHero(g); // 画英雄机
		paintBullets(g); // 画子弹
		paintFlyingObjects(g); // 画飞行物
		paintScore(g); // 画分数
		paintState(g); // 画游戏状态
	}

	/** 画英雄机 */
	public void paintHero(Graphics g) {
		for (int i = 0; i < heros.size(); i++) {
			Hero hero = heros.get(i);
			if (hero.getLife() != 0) {
				g.drawImage(hero.getImage(), hero.getX(), hero.getY(), null);
			}

		}
	}

	/** 画子弹 */
	public void paintBullets(Graphics g) {

//		System.out.println("子弹数:  " + bullets.size());
		for (int i = 0; i < bullets.size(); i++) {
			Bullet b = bullets.get(i);
			g.drawImage(b.getImage(), b.getX() - b.getImage().getWidth() / 2, b.getY(), null);
			g.drawRect(b.getX() - b.getImage().getWidth() / 2, b.getY(), b.getImage().getWidth(),
					b.getImage().getHeight()); // FIX: 范围
		}

		for (int i = 0; i < enemy_bullets.size(); i++) {
			Bullet b = enemy_bullets.get(i);
			g.drawImage(b.getImage(), b.getX() - b.getImage().getWidth() / 2, b.getY(), null);
			g.drawRect(b.getX() - b.getImage().getWidth() / 2, b.getY(), b.getImage().getWidth(),
					b.getImage().getHeight()); // FIX: 范围
		}

	}

	/** 画飞行物 */
	public void paintFlyingObjects(Graphics g) {
		for (int i = 0; i < enemys.size(); i++) {
			FlyingObject f = enemys.get(i);
			g.drawImage(f.getImage(), f.getX(), f.getY(), null);
			g.drawRect(f.getX(), f.getY(), f.getImage().getWidth(), f.getImage().getHeight()); // FIX: 范围
		}
	}

	/** 画分数 */
	public void paintScore(Graphics g) {
		int x = 10; // x坐标
		int y = 60; // y坐标
		Font font = new Font(Font.SANS_SERIF, Font.BOLD, 22); // 字体
		g.setColor(new Color(0xFF0000));
		g.setFont(font); // 设置字体
		g.drawString("SCORE:" + score + "   炸弹:" + boom, x, y); // 画分数 炸弹

		for (int i = 0; i < heros.size(); i++) {
			y = y + 20; // y坐标增20
			g.drawString(String.valueOf(i + 1) + "P LIFE:" + heros.get(i).getLife(), x, y); // 画命
		}
	}

	/** 画游戏状态 */
	public void paintState(Graphics g) {
		switch (this.state) {
		case MENU:
			paintMenu(g);
			break;
		case START: // 启动状态
			g.drawImage(start, 10, 10, null);
			break;
		case PAUSE: // 暂停状态
			g.drawImage(pause, windows_X - pause.getWidth() / 2, windows_Y, null);
			break;
		case GAME_OVER: // 游戏终止状态
			g.drawImage(gameover, windows_X - gameover.getWidth() / 2, windows_Y, null);
			break;
		}
	}

	/**
	 * 画菜单 计分板
	 */
	class Scorecard {
		private String body;
		public int x, y;

		public void setbody(String body) {
			this.body = body;
		}

		public String getBody() {
			return body;
		}
	}

	public List<Scorecard> scorecards = new ArrayList<Scorecard>();;

	public void setscorecards() {
		for (int i = 0; i < 5; i++) {
			Scorecard s = new Scorecard();
			s.setbody("战机 ");
			s.x = windows_X - 80;
			s.y = windows_Y - 70 + (i * 40);
			scorecards.add(s);
		}
	}

	public void paintMenu(Graphics g) {
		if (scorecards.size() == 0) {
			System.out.print("获取计分板");
			setscorecards();
		}
		g.setColor(Color.black);
		g.fillRoundRect(windows_X - 100, windows_Y - 100, 200, 200, 60, 40);// 涂一个圆角矩形块
		g.setColor(Color.red);

		for (int i = 0; i < scorecards.size(); i++) {
			Scorecard s = scorecards.get(i);
			g.drawString(s.getBody(), s.x, s.y);
		}
	}

	/** 删除越界飞行物及子弹 */
	public void outOfBoundsAction() {
		// 飞行物
		List<FlyingObject> buf_enemys = new ArrayList<FlyingObject>();
		// 子弹
		List<Bullet> buf_bullets = new ArrayList<Bullet>();
		// 飞行物子弹
		List<Bullet> buf_enemy_bullets = new ArrayList<Bullet>();

		for (int i = 0; i < this.enemys.size(); i++) {
			FlyingObject f = enemys.get(i);
			if (!f.outOfBounds()) {
				buf_enemys.add(f);
			}
		}

		for (int i = 0; i < this.bullets.size(); i++) {
			Bullet f = bullets.get(i);
			if (!f.outOfBounds()) {
				buf_bullets.add(f);
			}
		}

		for (int i = 0; i < this.enemy_bullets.size(); i++) {
			Bullet f = enemy_bullets.get(i);
			if (!f.outOfBounds()) {
				buf_enemy_bullets.add(f);
			}
		}

		this.enemys = buf_enemys;
		this.bullets = buf_bullets;
		this.enemy_bullets = buf_enemy_bullets;
	}

	/**
	 * 双缓冲
	 */
	private Image offScreenImage = null;

	public void update(Graphics g) {
		if (offScreenImage == null)
			offScreenImage = this.createImage(this.getWidth(), this.getHeight());
		Graphics gOff = offScreenImage.getGraphics();
		paint(gOff);
		g.drawImage(offScreenImage, 0, 0, null);
	}

	public static void main(String[] args) {
		run r = new run();
		r.initgame();
		r.rungame();
		System.out.print("exit");

	}
}
