package com.freeranger.colorcourse.entities;

import java.util.Random;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.freeranger.colorcourse.Main;
import com.freeranger.colorcourse.handlers.B2DVars;
import com.freeranger.colorcourse.screens.Play;

public class Doomfist {
	
	@SuppressWarnings("unused")
	private Main game;
	private Body body;
	private Play play;
	
	private Vector2 velocity;
	
	private Texture left_happy;
	private Texture right_happy;
	private Texture left_angry;
	private Texture right_angry;
	
	private boolean happy;
	
	private float happySpeed;
	private float angrySpeed;
	
	Random r = new Random();
	
	public Doomfist(Body body, Main game, Play play) {
		happy = true;
		this.game = game;
		this.body = body;
		this.play = play;
		
		happySpeed = 1f;
		angrySpeed = 2.8f;
		
		left_happy = game.doomfist_happy_left;
		left_happy.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		right_happy = game.doomfist_happy_right;
		right_happy.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		left_angry = game.doomfist_angry_left;
		left_angry.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		right_angry = game.doomfist_angry_right;
		right_angry.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

		velocity = new Vector2(-happySpeed, 0);
	}

	public void render(SpriteBatch sb){
		sb.begin();
		
		//temp - like sprites, anim later
		if(happy){
			if(velocity.x < 0)sb.draw(left_happy, body.getPosition().x * B2DVars.PPM - (35 / 2), body.getPosition().y * B2DVars.PPM - (33 / 2));
			else sb.draw(right_happy, body.getPosition().x * B2DVars.PPM - (35 / 2), body.getPosition().y * B2DVars.PPM - (33 / 2));
		}else {
			if(velocity.x < 0)sb.draw(left_angry, body.getPosition().x * B2DVars.PPM - (35 / 2), body.getPosition().y * B2DVars.PPM - (33 / 2));
			else sb.draw(right_angry, body.getPosition().x * B2DVars.PPM - (35 / 2), body.getPosition().y * B2DVars.PPM - (33 / 2));
		}
		
		sb.end();
	}
	
	public void update(float delta){
		body.setLinearVelocity(velocity.x, body.getLinearVelocity().y);
		
		if(play.getPlayerX() > (body.getPosition().x - 2) && play.getPlayerX() < (body.getPosition().x + 2)){
			happy = false;
		}
		
		if(happy == false){
			if(play.getPlayerX() < (body.getPosition().x - 5) || play.getPlayerX() > (body.getPosition().x + 5)){
				happy = true;
			}
		}
		
		// happy walking logic
		if(happy) {
			int random = r.nextInt(50);
			if(random == 10){
				if(velocity.x < 0){
					velocity.x = happySpeed;
				}else if (velocity.x > 0){
					velocity.x = -happySpeed;
				}
			}
		}
		// angry running logic
		if(!happy){
			if(play.getPlayerX() < body.getPosition().x){
				//run left
				velocity.x = -angrySpeed;
			}else {
				//run right
				velocity.x = angrySpeed;
			}
		}
	}

	public void setVelocityX(float x){
		if(happy){
			this.velocity.x = x;
		}
	}
	
	public float getVelocityX(){
		return velocity.x;
	}

	public Body getBody(){
		return body;
	}
}