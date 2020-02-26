package com.vader.attackfly;


import java.awt.image.BufferedImage;

public abstract class FlyingObject {
	protected int x;    //x坐标
	protected int y;    //y坐标
	protected int width;    //宽
	protected int height;   //高
	protected BufferedImage image;   //图片
	

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
	public BufferedImage getImage() {
		return image;
	}

	// 碰撞检测
	public boolean collision(FlyingObject f) {
		int x = f.x;  //横坐标
		int y = f.y;  //纵坐标
		return this.x<x && x<this.x+width && this.y<y && y<this.y+height;
	}
	
	public void setImage(BufferedImage image) {
		this.image = image;
	}
	
	//	出界 对于出界的对象应该移除
	public abstract boolean outOfBounds();

	//	移动
	public abstract void step();
	

	
}
