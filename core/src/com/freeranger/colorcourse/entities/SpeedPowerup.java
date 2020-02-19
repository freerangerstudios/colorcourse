package com.freeranger.colorcourse.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.freeranger.colorcourse.Main;
import com.freeranger.colorcourse.handlers.B2DVars;

public class SpeedPowerup {

	@SuppressWarnings("unused")
	private Main game;
	private Body body;
	
	private Texture sprite;
	
	public SpeedPowerup(Body body, Main game) {
		this.game = game;
		this.body = body;
		
		sprite = game.speed_powerup;
		sprite.setFilter(TextureFilter.Linear, TextureFilter.Linear);
	}
	
	public void render(SpriteBatch sb){
		sb.begin();
		sb.draw(sprite, body.getPosition().x * B2DVars.PPM - (32 / 2), body.getPosition().y * B2DVars.PPM - (32 / 2));
		sb.end();
	}
	
	public void update(float delta){	
		
	}
	
	public Body getBody(){
		return body;
	}
}