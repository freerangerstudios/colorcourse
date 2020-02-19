package com.freeranger.colorcourse.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.freeranger.colorcourse.Main;
import com.freeranger.colorcourse.handlers.B2DVars;
import com.freeranger.colorcourse.screens.Play;

public class Ghost {
	
	@SuppressWarnings("unused")
	private Main game;
	private Body body;
	private Play play;
	
	private Texture left;
	private Texture right;
	private Texture up;
	private Texture down;
	private Texture up_left;
	private Texture up_right;
	private Texture down_left;
	private Texture down_right;

	//Animation<TextureRegion> leftAnim;
	//Animation<TextureRegion> rightAnim;
	float stateTime;
	
	private Vector2 velocity;
	private float speed;
	private int col = 1;
	private int row = 1;
	
	private boolean dead = false;
	private boolean canKill = true;
	private int hp = 3;
	
	public Ghost(Body body, Main game, Play play, int speedStep) {
		dead = false;
		this.body = body;
		this.game = game;
		this.play = play;
	
		this.speed = speedStep * 0.5f;	
		
		left = game.ghost_left;
		right = game.ghost_right;
		up = game.ghost_up;
		down = game.ghost_down;
		up_left = game.ghost_up_left;
		up_right = game.ghost_up_right;
		down_left = game.ghost_down_left;
		down_right = game.ghost_down_right;
		
		velocity = new Vector2(-0.5f, -2);
		
		
		// No animation currently needed
		/*TextureRegion[][] tmpright = TextureRegion.split(right, 
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
		
		rightAnim = new Animation<TextureRegion>(1f, rightFrames);
		leftAnim = new Animation<TextureRegion>(1f, leftFrames);
		*/
		
		stateTime = 0f;
	}
	
	public void render(SpriteBatch sb){
		stateTime += Gdx.graphics.getDeltaTime(); // Accumulate elapsed animation time
		
		// Get current frame of animation for the current stateTime
		//TextureRegion currentFrameRight = rightAnim.getKeyFrame(stateTime, true);
		//TextureRegion currentFrameLeft = leftAnim.getKeyFrame(stateTime, true);

		sb.begin();
		
		// Right
		if(body.getLinearVelocity().x > 0 && body.getLinearVelocity().y == 0) {
			sb.draw(right, body.getPosition().x * B2DVars.PPM - (35 / 2), body.getPosition().y * B2DVars.PPM - (33 / 2));
		}
		// Left
		else if(body.getLinearVelocity().x < 0 && body.getLinearVelocity().y == 0) {
			sb.draw(left, body.getPosition().x * B2DVars.PPM - (35 / 2), body.getPosition().y * B2DVars.PPM - (33 / 2));
		}
		// Up
		else if(body.getLinearVelocity().x == 0 && body.getLinearVelocity().y > 0) {
			sb.draw(up, body.getPosition().x * B2DVars.PPM - (35 / 2), body.getPosition().y * B2DVars.PPM - (33 / 2));
		}
		// Down
		else if(body.getLinearVelocity().x == 0 && body.getLinearVelocity().y < 0) {
			sb.draw(down, body.getPosition().x * B2DVars.PPM - (35 / 2), body.getPosition().y * B2DVars.PPM - (33 / 2));
		}
		// Up Left
		else if(body.getLinearVelocity().x < 0 && body.getLinearVelocity().y > 0) {
			sb.draw(up_left, body.getPosition().x * B2DVars.PPM - (35 / 2), body.getPosition().y * B2DVars.PPM - (33 / 2));
		}
		// Up Right
		else if(body.getLinearVelocity().x > 0 && body.getLinearVelocity().y > 0) {
			sb.draw(up_right, body.getPosition().x * B2DVars.PPM - (35 / 2), body.getPosition().y * B2DVars.PPM - (33 / 2));
		}
		// Down Left
		else if(body.getLinearVelocity().x < 0 && body.getLinearVelocity().y < 0) {
			sb.draw(down_left, body.getPosition().x * B2DVars.PPM - (35 / 2), body.getPosition().y * B2DVars.PPM - (33 / 2));
		}
		// Down Right
		else if(body.getLinearVelocity().x > 0 && body.getLinearVelocity().y < 0) {
			sb.draw(down_right, body.getPosition().x * B2DVars.PPM - (35 / 2), body.getPosition().y * B2DVars.PPM - (33 / 2));
		}else{
			// Draw some kind of idle frame, I will just draw the down frame again
			sb.draw(down, body.getPosition().x * B2DVars.PPM - (35 / 2), body.getPosition().y * B2DVars.PPM - (33 / 2));
		}

		sb.end();
	}
	
	public void update(float delta){
		// Ghost AI
		float xDiff = Math.abs(play.getPlayerX() - body.getPosition().x);
		float yDiff = Math.abs(play.getPlayerY() - body.getPosition().y);
		
		if(play.getPlayerX() > body.getPosition().x && xDiff > 32 / B2DVars.PPM){
			body.setLinearVelocity(speed, body.getLinearVelocity().y);
		}else if(play.getPlayerX() < body.getPosition().x && xDiff > 32 / B2DVars.PPM){
			body.setLinearVelocity(-speed, body.getLinearVelocity().y);
		}else{
			body.setLinearVelocity(body.getLinearVelocity().lerp(new Vector2(0, body.getLinearVelocity().y), 0.07f));
		}
		
		if(play.getPlayerY() > body.getPosition().y && yDiff > 32 / B2DVars.PPM){
			body.setLinearVelocity(body.getLinearVelocity().x, speed);
		}else if(play.getPlayerY() < body.getPosition().y && yDiff > 32 / B2DVars.PPM){
			body.setLinearVelocity(body.getLinearVelocity().x, -speed);
		}else{
			body.setLinearVelocity(body.getLinearVelocity().lerp(new Vector2(body.getLinearVelocity().x, 0), 0.07f));
		}
	}
	
	@SuppressWarnings("unused")
	private boolean between(float x, float xMin, float xMax){
	    if(x >= xMin && x <= xMax){
	        return true;
	    }
	    return false;
	}
	
	public void setVelocity(Vector2 velocity){
		this.velocity = velocity;
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