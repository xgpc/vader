package com.vader.attackfly;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Enemy extends FlyingObject {
	private int speed = 4; // 速度
	private int score = 1; // 分数奖励
	private int bulletType; // 子弹类型
	private int life; // 命
	private int num = 0;
	private int frequency = 20;
	private boolean boos = false;
	
	private int herox, heroy = 0; 

	public static List<BufferedImage> zdImage  = new ArrayList<BufferedImage>();

	public boolean isBoos() {
		return boos;
	}

	public void setBoos(boolean boos) {
		this.boos = boos;
	}

	public void setheroxy(FlyingObject f) {
		this.herox = f.x;
		this.heroy = f.y;
	}
	
	public int getFrequency() {
		return frequency;
	}

	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}

	/** 获取分数 */
	public int getScore() {
		return this.score;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getBulletType() {
		return bulletType;
	}

	public void setBulletType(int bulletType) {
		this.bulletType = bulletType;
	}

	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}
	
	public int subLife(int life) {
		this.life-=life;
		return this.life;
	}

	public void setScore(int score) {
		this.score = score;
	}

	/** //越界处理 */
	@Override
	public boolean outOfBounds() {
		return y > this.height;
	}

	/** 移动 */
	@Override
	public void step() {
		
		//Boss飞行物移动方式
		if (this.boos == true) {
			if (this.y > 200) {
				speed = -1;
			} else if (this.y - 1 < 0) {
				Random random = new Random();
				int type = random.nextInt(3); // [0,100)
				 speed = type;
			}

		}
		
		y += speed;		

	}
	
	/**
	 * 攻击方式
	 */	
	public List<Bullet> attack() {

		if (this.boos) {
			return boosattack();
		}

		if(this.getfire() != true) {
			return null;
		}
		List<Bullet> bs = new ArrayList<Bullet>();
		Bullet b = new Bullet(this.x + this.image.getWidth()/2, this.y + this.image.getHeight());

		b.setHeight(this.height);
		b.setWidth(this.width);
		b.setSpeed(-8);
		b.setImage(zdImage.get(0)); //蓝色子弹
		
		if(this.bulletType == 1) {
			b.setSpeed(-24);
			b.setImage(zdImage.get(1)); //红色子弹	
		}
		
		if(this.bulletType == 2) {
			Bullet b1 = new Bullet(this.x + this.image.getWidth()/4 + 10, this.y + this.image.getHeight());
			b1.setHeight(this.height);
			b1.setWidth(this.width);
			b1.setSpeed(-9);
			b1.setImage(zdImage.get(0)); //蓝色子弹
			b1.setType(1);	//子弹运动方式
			b1.setheroxy(herox, heroy);
			
			b.setSpeed(-9);
			b.setImage(zdImage.get(0)); //蓝色子弹
			b.setType(1);	//子弹运动方式
			b.setheroxy(herox, heroy);

			b.setX(this.x + this.image.getWidth()/4 + this.image.getWidth()/2);
			bs.add(b1);
			
		}
		
		if(this.bulletType == 3) {
			Bullet b1 = new Bullet(this.x + this.image.getWidth()/4 + 10, this.y + this.image.getHeight());
			b1.setHeight(this.height);
			b1.setWidth(this.width);
			b1.setSpeed(-24);
			b1.setImage(zdImage.get(1)); //红色子弹
			b1.setType(1); //子弹运动方式
			b1.setheroxy(herox, heroy);
			
			b.setSpeed(-24);
			b.setImage(zdImage.get(1)); //红色子弹
			b.setType(1); //子弹运动方式
			b.setheroxy(herox, heroy);
			b.setX(this.x + this.image.getWidth()/2 + this.image.getWidth()/4);

			bs.add(b1);			
		}

		bs.add(b);
		return bs;
	}
	
	private  boolean getfire() {
		this.num++;
		if(this.num == this.frequency) {
			num = 0;
			return true;
		}
		return false;
	}
	
	List<Bullet> bossBullets = new ArrayList<Bullet>();

	List<Bullet> bossBullets1 = new ArrayList<Bullet>();

	private List<Bullet> boosattack() {
		// TODO Auto-generated method stub
		this.num++;
		if(this.num != this.frequency) {
			return null;
		}
		num = 0;
			
		List<Bullet> bs = new ArrayList<Bullet>();
		if (bossBullets.size() == 0) {
			Random random = new Random();
			int n = random.nextInt(8) + 1; // [0,2)
			
			int l = this.image.getWidth()/n;
			
			for (int i = 0; i < n; i++) {
				Bullet b = new Bullet(this.x + l * i, this.y + this.image.getHeight()/2);
				
				b.setHeight(this.height);
				b.setWidth(this.width);
				b.setSpeed(-10);
				b.setImage(zdImage.get(2)); 
				bossBullets.add(b);
			}
			
			if(n == 5) {
				bs.addAll(bossBullets);
				return bs;
			}
		}
		Bullet b = null;
		for (int i = 0; i < bossBullets.size(); i++) {
			b =bossBullets.get(i);
			bossBullets.remove(b);
			break;
		}
		bs.add(b);
		return bs;

	}
}
