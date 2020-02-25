package com.vader.attackfly;

public class Enemy extends FlyingObject{
	private int speed = 3; //子弹速度
	private int score = 1;	//分数奖励
	private int bulletType;   //子弹类型
	private int life;   //命
		
	/** 获取分数 */

	public int getScore() {  
		return this.score;
	}
	
	/** //越界处理 */
	@Override
	public 	boolean outOfBounds() {   
		return y>run.HEIGHT;
	}

	/** 移动 */
	@Override
	public void step() {   
		y += speed;
	}
}
