package com.vader.attackfly;

import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class run extends JFrame {
	public static final int WIDTH = 400; // 面板宽
	public static final int HEIGHT = 654; // 面板高
	/** 游戏的当前状态: START RUNNING PAUSE GAME_OVER */
	private int state;
	private static final int START = 0;
	private static final int RUNNING = 1;
	private static final int PAUSE = 2;
	private static final int GAME_OVER = 3;
	
	private int score = 0; // 得分
	private Timer timer; // 定时器
	private int intervel = 1000 / 100; // 时间间隔(毫秒)
	
	//资源加载
	public static BufferedImage background;  //背景
	public static BufferedImage start;		//开始
	public static BufferedImage pause;		//暂停
	public static BufferedImage gameover;	//结束
	
	public static BufferedImage airplane;
	public static BufferedImage bee;		
	public static BufferedImage bullet;	
	
	public static BufferedImage hero0;
	public static BufferedImage hero1;

	
	//主角
	
	//敌机
	
	//子弹
	
	//补给
	
	/*
	 * 初始化窗口配置
	 * */
	public void initgame() {
		this.setTitle("一起打飞机");
		this.setVisible(true);
		this.setSize(this.WIDTH, this.HEIGHT);
		this.setLocation(300,300);
		
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				
				System.exit(0);
			}
		});
		
		//导入图片
		
		//监听键盘
	}
	
	/*
	 * 启动游戏*
	 */
	public void rungame() {
		
		timer = new Timer(); // 主流程控制
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				int a = 1;
				//填写游戏过程
				System.out.print(a++);
				
				repaint(); // 重绘，调用paint()方法
			}

		}, intervel, intervel);
	}
	
	
	/**
	 * 描画所有的对象
	 */
	@Override
	public void paint(Graphics g) {
		
		
	}
	
	public static void main(String[] args) {
		run r = new run();
		r.initgame();
		r.rungame();
		System.out.print("exit");
		
	}
}
