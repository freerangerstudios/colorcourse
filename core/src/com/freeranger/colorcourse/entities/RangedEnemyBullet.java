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
import com.freeranger.colorcourse.handlers.MyContactListener;
import com.freeranger.colorcourse.screens.Play;

public class RangedEnemyBullet {
	
	// TODO check parent: RangedEnemy.java
	
	@SuppressWarnings("unused")
	private Main game;
	private Play play;
	private MyContactListener cl;
	private Body body;
	
	private Texture bulletTex;
	Animation<TextureRegion> bulletAnim;
	float stateTime;
	
	private Vector2 velocity;
	private int col = 1;
	private int row = 1;
	
	private boolean dead;
	private int direction; // 0 = left, 1 = right	
		
	public RangedEnemyBullet(Body body, Main game, Play play, MyContactListener cl, Vector2 position, int direction) {
		this.game = game;
		this.body = body;
		this.play = play;
		this.cl = cl;
		this.direction = direction;
		
		bulletTex = game.rangedEnemy_bullet;
		bulletTex.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

		velocity = new Vector2(5, 0);
		
		TextureRegion[][] tmpanim = TextureRegion.split(bulletTex, 
				bulletTex.getWidth() / row,
				bulletTex.getHeight() / col);
		
		TextureRegion[] animFrames = new TextureRegion[row * col];

		int index = 0;
		for (int i = 0; i < col; i++) {
			for (int j = 0; j < row; j++) {
				animFrames[index++] = tmpanim[i][j];
			}
		}
		
		
		bulletAnim = new Animation<TextureRegion>(0.0125f, animFrames);
		
		stateTime = 0f;
	}
	
	public void render(SpriteBatch sb){
		stateTime += Gdx.graphics.getDeltaTime(); // Accumulate elapsed animation time
		
		// Get current frame of animation for the current stateTime
		TextureRegion currentFrame = bulletAnim.getKeyFrame(stateTime, true);

		sb.begin();
		sb.draw(currentFrame, body.getPosition().x * B2DVars.PPM - (8/2), body.getPosition().y * B2DVars.PPM - (8/2));
		sb.end();
	}
	
	public void update(float delta){	
	    if(direction == 0){
	    	body.setLinearVelocity(-velocity.x, body.getLinearVelocity().y); // Move Bullet Left
	    }
	    else if(direction == 1){
	    	body.setLinearVelocity(velocity.x, body.getLinearVelocity().y); // Move Bullet Right
	    }
	    
	    // Destruction logic
	    if(body.getPosition().x < (play.getPlayerX() - 15) || body.getPosition().x > (play.getPlayerX() + 15)){
	    	cl.addBodyToRemove(body);
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