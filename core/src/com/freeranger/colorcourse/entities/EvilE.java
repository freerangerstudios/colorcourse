package com.freeranger.colorcourse.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.freeranger.colorcourse.Main;
import com.freeranger.colorcourse.handlers.B2DVars;

public class EvilE {
	
	@SuppressWarnings("unused")
	private Main game;
	private Body body;
	private String activeSensor = "evile_left_sensor";
	
	private Texture left;
	private Texture right;
	Animation<TextureRegion> leftAnim;
	Animation<TextureRegion> rightAnim;
	float stateTime;
	
	private Vector2 velocity;
	private int col = 1;
	private int row = 62;
	
	private boolean dead;
	
	public EvilE(Body body, Main game) {
		dead = false;
		this.game = game;
		this.body = body;
		
		left = game.evile_left;
		right = game.evile_right;
		left.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		right.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		
		velocity = new Vector2(-0.5f, -2);
		
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
		
		rightAnim = new Animation<TextureRegion>(0.0125f, rightFrames);
		leftAnim = new Animation<TextureRegion>(0.0125f, leftFrames);
		
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
}