package com.vader.attackfly;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * 普通子弹
 * 
 * @author Xmas
 */
public class Bullet extends FlyingObject {
	private int speed = 15; // 子弹速度
	private int type = 0;
	private int fire = 1;

	private int herox, heroy = 0;

	public void setheroxy(int x, int y) {
		herox = x;
		heroy = y;
	}

	public Bullet(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getFire() {
		return fire;
	}

	public void setFire(int fire) {
		this.fire = fire;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	// 越界
	@Override
	public boolean outOfBounds() {
		// TODO Auto-generated method stub
		return ((y < 0) || (y > this.height) || (x < 0) || (x > this.width));

	}

	// 运行方式 垂直向下
	@Override
	public void step() {
		// TODO Auto-generated method stub

//		buf_x = this.buf_x;
//		buf_y = this.buf_y;

		y -= speed;
//
//		if (type == 1) {
//			x = stepx();
//		}
	}

//	private int buf_x = 0;
//	private int buf_y = 0;
//	private double slope = 0.0;
//
//	private int stepx() {
////		x-x1/y-y1 = x2-x1/y2-y1
//		if(slope == 0.0) {
//			int b = this.y - this.heroy;
//			if(b == 0) {
//				b = 1;
//			}
//			slope = (this.x - this.herox) / b;
//		}
//		
//		return (int)(slope * (y - this.heroy));
//	}

}
