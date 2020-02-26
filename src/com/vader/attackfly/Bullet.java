package com.vader.attackfly;



/**
 * 普通子弹
 * @author Xmas
 */
public class Bullet extends FlyingObject{
	private int speed = 15; //子弹速度
	private int type = 0;
	
	
	public Bullet(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	//越界
	@Override
	public boolean outOfBounds( ) {
		// TODO Auto-generated method stub
		return ((y < 0) || (y > this.height) || (x < 0) || (x > this.width));

	}

	//运行方式 垂直向下
	@Override
	public void step() {
		// TODO Auto-generated method stub
		y-=speed;
	}
}
