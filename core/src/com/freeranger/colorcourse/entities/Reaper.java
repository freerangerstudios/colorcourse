package com.freeranger.colorcourse.entities;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.freeranger.colorcourse.Main;
import com.freeranger.colorcourse.handlers.B2DVars;
import com.freeranger.colorcourse.screens.Play;

public class Reaper {
	
	@SuppressWarnings("unused")
	private Main game;
	private Body body;
	private Play play;
	
	private Texture left;
	private Texture right;
	private Texture idle;
	
	private boolean dead = false;
	private boolean canKill = true;
	private int hp = 3;
	
	private Vector2 velocity;
	private float speed;
	private Random r;
	private boolean onGround = false;

	Animation<TextureRegion> leftAnim;
	Animation<TextureRegion> rightAnim;
	Animation<TextureRegion> idleAnim;
	float stateTime;

	private int col = 1;
	private int row = 2;
	
	public Reaper(Body body, Main game, Play play) {
		dead = false;
		this.body = body;
		this.game = game;
		this.play = play;
		
		left = game.reaper_left_sheet;
		left.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		right = game.reaper_right_sheet;
		right.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		idle = game.reaper_idle_sheet;
		idle.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

		TextureRegion[][] tmpleft = TextureRegion.split(left,
				left.getWidth() / row,
				left.getHeight() / col);
		TextureRegion[][] tmpright = TextureRegion.split(right,
				right.getWidth() / row,
				right.getHeight() / col);
		TextureRegion[][] tmpidle = TextureRegion.split(idle,
				idle.getWidth() / row,
				idle.getHeight() / col);

		TextureRegion[] leftFrames = new TextureRegion[row * col];
		TextureRegion[] rightFrames = new TextureRegion[row * col];
		TextureRegion[] idleFrames = new TextureRegion[row * col];

		int index = 0;
		for (int i = 0; i < col; i++) {
			for (int j = 0; j < row; j++) {
				leftFrames[index++] = tmpleft[i][j];
			}
		}
		int index2 = 0;
		for (int i = 0; i < col; i++) {
			for (int j = 0; j < row; j++) {
				rightFrames[index2++] = tmpright[i][j];
			}
		}
		int index3 = 0;
		for (int i = 0; i < col; i++) {
			for (int j = 0; j < row; j++) {
				idleFrames[index3++] = tmpidle[i][j];
			}
		}

		leftAnim = new Animation<TextureRegion>(0.425f, leftFrames);
		rightAnim = new Animation<TextureRegion>(0.425f, rightFrames);
		idleAnim = new Animation<TextureRegion>(0.425f, idleFrames);

		velocity = new Vector2(0f, 0f);	
		speed = 1.7f;
		r = new Random();
	}
	
	public void render(SpriteBatch sb){
		stateTime += Gdx.graphics.getDeltaTime();
		sb.begin();

		TextureRegion currentFrameLeft = leftAnim.getKeyFrame(stateTime, true);
		TextureRegion currentFrameRight = rightAnim.getKeyFrame(stateTime, true);
		TextureRegion currentFrameIdle = idleAnim.getKeyFrame(stateTime, true);

		if(velocity.x == 0) {
			sb.draw(currentFrameIdle, body.getPosition().x * B2DVars.PPM - (35 / 2), body.getPosition().y * B2DVars.PPM - (33 / 2));
		}else if(velocity.x < 0) {
			sb.draw(currentFrameLeft, body.getPosition().x * B2DVars.PPM - (35 / 2), body.getPosition().y * B2DVars.PPM - (33 / 2));
		}else if(velocity.x > 0) {
			sb.draw(currentFrameRight, body.getPosition().x * B2DVars.PPM - (35 / 2), body.getPosition().y * B2DVars.PPM - (33 / 2));
		}

		sb.end();
	}
	
	public void update(float delta){
		//AI
		body.setLinearVelocity(velocity.x, body.getLinearVelocity().y);
		
		if(body.getPosition().x > (play.getPlayerX() - 3) && body.getPosition().x < (play.getPlayerX() + 3)){
			if(play.getPlayerX() < body.getPosition().x - 32/B2DVars.PPM){
				velocity.x = -speed;
			}else if(play.getPlayerX() > body.getPosition().x + 32/B2DVars.PPM){
				velocity.x = speed;
			}else {
				// DO NOTHING FOR NOW AT LEAST
			}
		}else velocity.x = 0f;
		
		//Speed Randomness
		if(r.nextInt(60) == 5){
			speed = (float)r.nextInt(3) + 2;
		}
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

	public boolean isOnGround() {
		return onGround;
	}

	public void setOnGround(boolean onGround) {
		this.onGround = onGround;
	}
}