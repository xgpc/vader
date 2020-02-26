package com.vader.attackfly;

import java.awt.image.BufferedImage;

public class Hero extends FlyingObject{
	
	private int bulletType;   //子弹类型
	private int life;   //命
	private int speed; // 速度
	
	public boolean up, down, left, right = false;
	
	
	public void setup(boolean b) {
		this.up = b;
	}
	public void setdown(boolean b) {
		this.down = b;
	}
	public void setleft(boolean b) {
		this.left = b;
	}
	public void setright(boolean b) {
		this.right = b;
	}
	
	/** 初始化数据 */
	public Hero(int x, int y) {
		life = 0;   //初始3条命
		bulletType = 0;   //初始火力为0
		speed = 10;
		this.x = x;
		this.y = y;
	}
	
	//设置子弹类型
	public void setBullrtType(int type) {
		this.bulletType = type;
	}
	
	//增加生命
	public void addlife() {
		this.life++;
	}
	
	//减命
	public void subtractLife() {
		this.life--;
	}
	
	//获取当前生命
	public int getLife() {
		return this.life;
	}
		
	/**
	 * 发射子弹
	 */
	public Bullet attack() {
		switch (this.bulletType) {
		case 1:
			return null;
		default:
			Bullet b = new Bullet(this.x + this.width/2, this.y);
			return b;
		}
	}
	
	
	/** 越界处理 */
	@Override
	public boolean outOfBounds() {
		return false;  
	}

	@Override
	public void step() {
		// TODO Auto-generated method stub
//		System.out.println("up "+ this.up);
//		System.out.println("down "+ this.down);
//		System.out.println("left "+ this.left);
//		System.out.println("right "+ this.right);
		
		if(this.life == 0) {
			return;
		}
		
		if(this.up == true) {
			this.y = this.y - speed;
		}
		if(this.down  == true) {
			this.y = this.y + speed;
		}
		if(this.left  == true) {
			this.x = this.x - speed;
		}
		if(this.right  == true) {
			this.x = this.x + speed;
		}
	}
	
}
