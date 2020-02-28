package com.vader.attackfly;


import java.awt.image.BufferedImage;

public abstract class FlyingObject {
	protected int x;    //x坐标
	protected int y;    //y坐标
	protected int width;    //边框宽
	protected int height;   //变宽高
	
	
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
//		boolean b = true;
//		if(f.y > this.y + this.image.getHeight()) {
//			b =  false;
//		}else if(f.x < this.x){
//			b = false;
//		}else if(f.x + f.image.getWidth() > this.x + this.image.getWidth()){
//			b = false;
//		}else if(f.y + f.image.getHeight() < this.y){
//			b = false;
//		}
//		return b;
//		return this.x<x && x<this.x+image.getWidth() && this.y<y && y<this.y+image.getHeight();
		boolean b = false;
		int x1 = this.x + this.image.getWidth() / 2;
		int x2 = this.x + this.image.getWidth();

		int y1 = this.y + this.image.getHeight() / 2;
		int y2 = this.y + this.image.getHeight();

		int fx = f.x;
		int fx1 = f.x + f.image.getWidth();

		int fy = f.y;
		int fy1 = f.y + f.image.getHeight();

		if ((cr(fx, x, fx1) || cr(fx, x1, fx1) || cr(fx, x2, fx1))
				&& (cr(fy, y, fy1) || cr(fy, y1, fy1) || cr(fy, y2, fy1))) {
			b = true;
		}
				
		return b;
	}
	
	public boolean cr(int x, int y, int z) {
		boolean b = false;
		if (x < y) {
			if (y < z) {
				b = true;
			}
		}
		return b;
	}
	public void setImage(BufferedImage image) {
		this.image = image;
	}
	
	//	出界 对于出界的对象应该移除
	public abstract boolean outOfBounds();

	//	移动
	public abstract void step();
		
}
