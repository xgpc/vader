package com.vader.attackfly;

import java.awt.image.BufferedImage;

public class Hero extends FlyingObject {

	private int bulletType; // 子弹类型
	private int life; // 命
	private int speed; // 速度
	private int attackspeed = 10;

	public boolean up, down, left, right = false;
	public boolean fire, boom = false;

	

	public BufferedImage zd1;
	public BufferedImage zd2;
	public BufferedImage zd3;
	public BufferedImage zd4;

	public boolean isFire() {
		return fire;
	}

	public void setFire(boolean fire) {
		this.fire = fire;
	}
	
	public int getAttackspeed() {
		return attackspeed;
	}

	public void setAttackspeed(int attackspeed) {
		this.attackspeed = attackspeed;
	}

	public BufferedImage getZd1() {
		return zd1;
	}

	public void setZd1(BufferedImage zd1) {
		this.zd1 = zd1;
	}

	public BufferedImage getZd2() {
		return zd2;
	}

	public void setZd2(BufferedImage zd2) {
		this.zd2 = zd2;
	}

	public BufferedImage getZd3() {
		return zd3;
	}

	public void setZd3(BufferedImage zd3) {
		this.zd3 = zd3;
	}

	public BufferedImage getZd4() {
		return zd4;
	}

	public void setZd4(BufferedImage zd4) {
		this.zd4 = zd4;
	}

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
		life = 0; // 初始3条命
		bulletType = 0; // 初始火力为0
		speed = 10;
		this.x = x;
		this.y = y;
	}

	// 设置子弹类型
	public void setBullrtType(int type) {
		this.bulletType = type;
	}

	// 增加生命
	public void addlife() {
		this.life++;
	}

	// 减命
	public void subtractLife() {
		this.life--;
	}

	// 获取当前生命
	public int getLife() {
		return this.life;
	}

	/**
	 * 发射子弹
	 */
	public Bullet attack() {
		
		if (this.fire == false) {
			return null;
		}
		
		Bullet b = new Bullet(this.x + this.getImage().getWidth()/2, this.y);
		b.setHeight(this.height);
		b.setWidth(this.width);
		switch (this.bulletType) {
		
		case 0:
			b.setImage(zd1);
			
			break;
		case 1:
			b.setImage(zd2);
			break;
		case 2:
			b.setImage(zd3);
			break;
		case 3:
			b.setImage(zd4);
			break;
		}
		return b;
	}

	/**
	 * 使用炸弹
	 */
	public boolean Boom() {
		return this.boom;
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

		if (this.life == 0) {
			return;
		}

		if (this.up == true) {
			if (this.y < 0)
				return;
			this.y = this.y - speed;

		}
		if (this.down == true) {
			if (this.y > (this.getHeight() - this.getImage().getHeight()))
				return;
			this.y = this.y + speed;

		}
		if (this.left == true) {
			if (this.x < 0)
				return;
			this.x = this.x - speed;

		}
		if (this.right == true) {
			if (this.x > this.getWidth() - this.getImage().getWidth())
				return;
			this.x = this.x + speed;

		}
	}



}
