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

public class BossBullet {

	/*
	 * TODO remove direction crap and shoot towards player
	 */
		
	@SuppressWarnings("unused")
	private Main game;
	private Play play;
	private MyContactListener cl;
	private Body body;
	
	private Texture bulletTex;
	Animation<TextureRegion> bulletAnim;
	float stateTime;
	
	private int col = 1;
	private int row = 1;
	
	float angle;
	Vector2 vel;
	
	private boolean dead;
	private int mode;

	private Vector2 um_bulletVel;

	public BossBullet(Body body, Main game, Play play, MyContactListener cl, Vector2 position, int mode, Vector2 um_bulletVel, float bulletSpeed) {
		this.game = game;
		this.body = body;
		this.play = play;
		this.cl = cl;
		this.mode = mode;
		this.um_bulletVel = um_bulletVel;

		if(bulletSpeed == 0f){
			bulletSpeed = 8.7f;
		}
		System.out.println("ffff"+bulletSpeed);
		
		bulletTex = game.rangedEnemy_bullet;
		bulletTex.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
				
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
		
		
		bulletAnim = new Animation<TextureRegion>(10.0125f, animFrames); // should probably be something like 0.0125f not 10.0125f
		
		stateTime = 0f;
		if(mode == 0) {
            angle = (float) Math.atan2(play.getPlayerY() * B2DVars.PPM - body.getPosition().y * B2DVars.PPM,
                    play.getPlayerX() * B2DVars.PPM - body.getPosition().x * B2DVars.PPM);

            vel = new Vector2();
            vel.x = (float) (bulletSpeed * Math.cos(angle));
            vel.y = (float) (bulletSpeed * Math.sin(angle));
        }else if(mode == 1){
		    vel = new Vector2();
		    vel.x = (float) (bulletSpeed * Math.sin(um_bulletVel.nor().x));
            vel.y = (float) (bulletSpeed * Math.sin(um_bulletVel.nor().y));
        }
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
		body.setLinearVelocity(new Vector2(vel.x, vel.y));

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