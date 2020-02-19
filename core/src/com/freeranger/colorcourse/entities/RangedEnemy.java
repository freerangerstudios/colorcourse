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

public class RangedEnemy {
	
	@SuppressWarnings("unused")
	private Main game;
	private Body body;
	private Play play;
	
	private Texture idle_left;
	private Texture idle_right;
	private Texture shooting_left;
	private Texture shooting_right;
	Animation<TextureRegion> idleLeftAnim;
	Animation<TextureRegion> idleRightAnim;
	Animation<TextureRegion> shootingLeftAnim;
	Animation<TextureRegion> shootingRightAnim;
	float stateTime;
	
	private Vector2 velocity;
	private int col1 = 1;
	private int row1 = 4;
	private int col2 = 1;
	private int row2 = 6;
	
	private boolean dead;
	
	private float shootingTimer;
	private float shootingSpeed = 2f;
	private Random random;

	private boolean shotBullet = false;
	private int shotBulletDirection = 0;
	
	public RangedEnemy(Body body, Main game, Play play) {
		dead = false;
		this.game = game;
		this.body = body;
		this.play = play;
		
		idle_left = game.rangedEnemy_idle_left;
		idle_left.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		idle_right = game.rangedEnemy_idle_right;
		idle_right.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

		shooting_left = game.rangedEnemy_shooting_left;
		shooting_left.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		shooting_right = game.rangedEnemy_shooting_right;
		shooting_right.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

		velocity = new Vector2(0, 0);
		
		TextureRegion[][] tmpidleright = TextureRegion.split(idle_right,
				idle_right.getWidth() / row1,
				idle_right.getHeight() / col1);
		TextureRegion[][] tmpidleleft = TextureRegion.split(idle_left,
				idle_left.getWidth() / row1,
				idle_left.getHeight() / col1);

		TextureRegion[][] tmpshootingright = TextureRegion.split(shooting_right,
				shooting_right.getWidth() / row2,
				shooting_right.getHeight() / col2);
		TextureRegion[][] tmpshootingleft = TextureRegion.split(shooting_left,
				shooting_left.getWidth() / row2,
				shooting_left.getHeight() / col2);
		
		TextureRegion[] idleRightFrames = new TextureRegion[row1 * col1];
		TextureRegion[] idleLeftFrames = new TextureRegion[row1 * col1];
		TextureRegion[] shootingRightFrames = new TextureRegion[row2 * col2];
		TextureRegion[] shootingLeftFrames = new TextureRegion[row2 * col2];

		int index1 = 0;
		for (int i = 0; i < col1; i++) {
			for (int j = 0; j < row1; j++) {
				idleRightFrames[index1++] = tmpidleright[i][j];
			}
		}
		int index2 = 0;
		for (int i = 0; i < col1; i++) {
			for (int j = 0; j < row1; j++) {
				idleLeftFrames[index2++] = tmpidleleft[i][j];
			}
		}

		int index3 = 0;
		for (int i = 0; i < col2; i++) {
			for (int j = 0; j < row2; j++) {
				shootingRightFrames[index3++] = tmpshootingright[i][j];
			}
		}
		int index4 = 0;
		for (int i = 0; i < col2; i++) {
			for (int j = 0; j < row2; j++) {
				shootingLeftFrames[index4++] = tmpshootingleft[i][j];
			}
		}
		
		idleRightAnim = new Animation<TextureRegion>(0.225f, idleRightFrames);
		idleLeftAnim = new Animation<TextureRegion>(0.225f, idleLeftFrames);
		shootingRightAnim = new Animation<TextureRegion>(0.1f, shootingRightFrames);
		shootingLeftAnim = new Animation<TextureRegion>(0.1f, shootingLeftFrames);
		
		stateTime = 0f;

		
		random = new Random();
		shootingTimer = (float)random.nextInt((int)shootingSpeed);
	}
	
	public void setVelocity(float x){
		velocity.x = x;
	}
	
	public void render(SpriteBatch sb){
		stateTime += Gdx.graphics.getDeltaTime(); // Accumulate elapsed animation time
		
		// Get current frame of animation for the current stateTime
		TextureRegion currentFrameIdleRight = idleRightAnim.getKeyFrame(stateTime, true);
		TextureRegion currentFrameIdleLeft = idleLeftAnim.getKeyFrame(stateTime, true);
		TextureRegion currentFrameShootingRight = shootingRightAnim.getKeyFrame(stateTime);
		TextureRegion currentFrameShootingLeft = shootingLeftAnim.getKeyFrame(stateTime);

		sb.begin();
		if(shotBullet){
			if(shotBulletDirection == 0){
				// left
				sb.draw(currentFrameShootingLeft, body.getPosition().x * B2DVars.PPM - (35 / 2) - 10, body.getPosition().y * B2DVars.PPM - (33 / 2));
				if(shootingLeftAnim.isAnimationFinished(stateTime)){
					shotBullet = false;
					stateTime = 0f;
				}
			}else if(shotBulletDirection == 1){
				// right
				sb.draw(currentFrameShootingRight, body.getPosition().x * B2DVars.PPM - (35 / 2) + 10, body.getPosition().y * B2DVars.PPM - (33 / 2));
				if(shootingRightAnim.isAnimationFinished(stateTime)){
					shotBullet = false;
					stateTime = 0f;
				}

			}else{
				// should not ever be ran
				shotBulletDirection = 0;
			}
		}else{
			if(play.getPlayerX() > (body.getPosition().x + 14 / B2DVars.PPM)){
				sb.draw(currentFrameIdleRight, body.getPosition().x * B2DVars.PPM - (35 / 2) + 10, body.getPosition().y * B2DVars.PPM - (33 / 2));
			}else{
				sb.draw(currentFrameIdleLeft, body.getPosition().x * B2DVars.PPM - (35 / 2) - 10, body.getPosition().y * B2DVars.PPM - (33 / 2));
			}
		}

		sb.end();
	}
	
	public void update(float delta){
	    body.setLinearVelocity(velocity.x, body.getLinearVelocity().y);
	    
	    // Shooting logic  
	    if(shootingTimer <= 0){
	    	// Shoot
	    	if(play.getPlayerX() > (body.getPosition().x + 14 / B2DVars.PPM)) play.createBullet(body.getPosition(), 1);
	    	else{
				if(play.getPlayerX() > (body.getPosition().x + 14 / B2DVars.PPM)){
					// right
					play.createBullet(new Vector2(body.getPosition().x + 17 / B2DVars.PPM, body.getPosition().y), 0);
					shotBullet = true;
					shotBulletDirection = 1;
					stateTime = 0f;
				}else {
					// left
					play.createBullet(new Vector2(body.getPosition().x - 17 / B2DVars.PPM, body.getPosition().y), 0);
					shotBullet = true;
					shotBulletDirection = 0;
					stateTime = 0f;
				}
			}

	    	// play sound
			if(game.getSoundSetting())game.shot_sound.play(0.2f);
	    	shootingTimer = shootingSpeed;
	    }else {
	    	shootingTimer -= delta;
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
}