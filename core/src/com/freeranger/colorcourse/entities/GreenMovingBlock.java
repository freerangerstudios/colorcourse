package com.freeranger.colorcourse.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.freeranger.colorcourse.Main;
import com.freeranger.colorcourse.handlers.B2DVars;
import com.freeranger.colorcourse.screens.Play;

public class GreenMovingBlock {
	
	private Body body;
	private Play play;
	private Texture sprite;
	@SuppressWarnings("unused")
	private Main game;
	
	private int direction; // 0 = horizontal, 1 = vertical
	private float velocity;
	private boolean runTimer = false;
	private int timer = 0;
	private int timerLength;
	private int movingDirection; // 0 = left or down, 1 = right or up
	private int minPos, maxPos;
	private boolean playerOnPlatform = false;
	
	public GreenMovingBlock(Body body, Main game, Play play, int direction, int minPos, int maxPos, float velocity, int timerLength) {
		this.body = body;
		this.game = game;
		this.play = play;
			
		this.direction = direction;
		this.minPos = minPos;
		this.maxPos = maxPos;
		this.velocity = velocity;
		this.timerLength = timerLength;
		
		sprite = game.green_block;
		
		movingDirection = 0;
	}
	
	public void render(SpriteBatch sb){
		sb.begin();
		
		sb.draw(sprite, body.getPosition().x * B2DVars.PPM - (32 / 2), body.getPosition().y * B2DVars.PPM - (32 / 2));

		sb.end();
	}
	
	public void update(float delta){
		if(direction == 0){
			if(!runTimer){
				if(playerOnPlatform){
					play.getPlayer().getBody().setLinearVelocity(play.getPlayer().getBody().getLinearVelocity().x + body.getLinearVelocity().x,
							play.getPlayer().getBody().getLinearVelocity().y);
				}
				
				if(movingDirection == 0 && body.getPosition().x*B2DVars.PPM - 16 > minPos){
					// move left
					body.setLinearVelocity(new Vector2(-velocity, 0));
				}else if(movingDirection == 1 && body.getPosition().x*B2DVars.PPM - 16 < maxPos){
					// move right
					body.setLinearVelocity(new Vector2(velocity, 0));
				}else if(movingDirection == 0 && body.getPosition().x*B2DVars.PPM - 16 <= minPos) {
					if(!runTimer){
						runTimer = true;
						timer = timerLength;
						body.getPosition().x = minPos;
					}
				}else if(movingDirection == 1 && body.getPosition().x*B2DVars.PPM - 16 >= maxPos) {
					if(!runTimer){
						runTimer = true;
						timer = timerLength;
						body.getPosition().x = maxPos;
					}
				}
			}else if(runTimer){
				timer--;
				body.setLinearVelocity(new Vector2(0, 0));
				
				if(timer <= 0){
					runTimer = false;
					if(movingDirection == 0){
						movingDirection = 1;
					}else if(movingDirection == 1){
						movingDirection = 0;			
					}
				}
			}
		}else if(direction == 1){
			if(!runTimer){
				if(playerOnPlatform){
					if(Gdx.input.isKeyPressed(Keys.W) || Gdx.input.isKeyPressed(Keys.SPACE) || Gdx.input.isKeyPressed(Keys.UP)){
						play.getPlayer().getBody().setLinearVelocity(play.getPlayer().getBody().getLinearVelocity().x,
								body.getLinearVelocity().y + play.getPlayer().getBody().getLinearVelocity().y);
					}else {
						play.getPlayer().getBody().setLinearVelocity(play.getPlayer().getBody().getLinearVelocity().x,
								body.getLinearVelocity().y );
					}
				}
				
				if(movingDirection == 0 && body.getPosition().y*B2DVars.PPM - 16 > minPos){
					// move down
					body.setLinearVelocity(new Vector2(0, -velocity));
				}else if(movingDirection == 1 && body.getPosition().y*B2DVars.PPM - 16 < maxPos){
					// move up
					body.setLinearVelocity(new Vector2(0, velocity));
				}else if(movingDirection == 0 && body.getPosition().y*B2DVars.PPM - 16 <= minPos) {
					if(!runTimer){
						runTimer = true;
						timer = timerLength;
						body.getPosition().y = minPos;
					}
				}else if(movingDirection == 1 && body.getPosition().y*B2DVars.PPM - 16 >= maxPos) {
					if(!runTimer){
						runTimer = true;
						timer = timerLength;
						body.getPosition().y = maxPos;
					}
				}
			}else if(runTimer){
				timer--;
				body.setLinearVelocity(new Vector2(0, 0));
				
				if(timer <= 0){
					runTimer = false;
					if(movingDirection == 0){
						movingDirection = 1;
					}else if(movingDirection == 1){
						movingDirection = 0;			
					}
				}
			}
		}
	}
	
	public boolean isPlayerOnPlatform() {
		return playerOnPlatform;
	}

	public void setPlayerOnPlatform(boolean playerOnPlatform) {
		this.playerOnPlatform = playerOnPlatform;
	}

	public Body getBody(){
		return body;
	}
}