package com.freeranger.colorcourse.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.freeranger.colorcourse.Main;
import com.freeranger.colorcourse.handlers.B2DVars;

public class DarkEvilE {
	
	@SuppressWarnings("unused")
	private Main game;
	private Body body;
	private String activeSensor = "dark_evile_left_sensor";
	private int hp = 2;
	
	private Texture left;
	private Texture right;
	Animation<TextureRegion> leftAnim;
	Animation<TextureRegion> rightAnim;
	float stateTime;
	
	private Vector2 velocity;
	private int col = 1;
	private int row = 62;
	
	private boolean dead = false;
	private boolean canKill = true;
	
	public DarkEvilE(Body body, Main game) {
		this.game = game;
		this.body = body;
		
		left = game.dark_evile_left;
		right = game.dark_evile_right;
		
		velocity = new Vector2(-1f, -2);
		
		TextureRegion[][] tmpright = TextureRegion.split(right, 
				right.getWidth() / row,
				right.getHeight() / col);
		TextureRegion[][] tmpleft = TextureRegion.split(left, 
				left.getWidth() / row,
				left.getHeight() / col);
		
		TextureRegion[] rightFrames = new TextureRegion[row * col];
		TextureRegion[] leftFrames = new TextureRegion[row * col];

		int index = 0;
		for (int i = 0; i < col; i++) {
			for (int j = 0; j < row; j++) {
				rightFrames[index++] = tmpright[i][j];
			}
		}
		
		int index2 = 0;
		for (int i = 0; i < col; i++) {
			for (int j = 0; j < row; j++) {
				leftFrames[index2++] = tmpleft[i][j];
			}
		}
		
		rightAnim = new Animation<TextureRegion>(0.00625f, rightFrames);
		leftAnim = new Animation<TextureRegion>(0.00625f, leftFrames);
		
		stateTime = 0f;
	}
	
	public void setVelocity(float x){
		velocity.x = x;
	}
	
	public void render(SpriteBatch sb){
		stateTime += Gdx.graphics.getDeltaTime(); // Accumulate elapsed animation time
		
		// Get current frame of animation for the current stateTime
		TextureRegion currentFrameRight = rightAnim.getKeyFrame(stateTime, true);
		TextureRegion currentFrameLeft = leftAnim.getKeyFrame(stateTime, true);

		sb.begin();
		if(body.getLinearVelocity().x > 0)sb.draw(currentFrameRight, body.getPosition().x * B2DVars.PPM - (35 / 2), body.getPosition().y * B2DVars.PPM - (33 / 2));
		else sb.draw(currentFrameLeft, body.getPosition().x * B2DVars.PPM - (35 / 2), body.getPosition().y * B2DVars.PPM - (33 / 2));

		sb.end();
	}
	
	public void update(float delta){
	    body.setLinearVelocity(velocity.x, body.getLinearVelocity().y);
	}
	
	public String getActiveSensor(){
		return activeSensor;
	}
	
	public Body getBody(){
		return body;
	}
	
	public void setDead(boolean dead){
		this.dead = dead;
	}
	
	public boolean getDead(){
		return dead;
	}
	
	public void setCanKill(boolean canKill){
		this.canKill = canKill;
	}
	
	public boolean getCanKill(){
		return canKill;
	}
	
	public int getHp(){
		return hp;
	}
	
	public void setHp(int hp){
		this.hp = hp;
	}
}