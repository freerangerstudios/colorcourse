package com.freeranger.colorcourse.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.freeranger.colorcourse.Main;
import com.freeranger.colorcourse.handlers.B2DVars;
import com.freeranger.colorcourse.screens.Play;

public class ThePipeUnder {	
	
	@SuppressWarnings("unused")
	private Main game;
	private Body body;
	private Play play;
	
	private Texture sprite;
	
	public ThePipeUnder(Body body, Main game, Play play) {
		this.game = game;
		this.body = body;
		this.play = play;
		
		sprite = game.the_pipe_under;
		sprite.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
	}

	public void render(SpriteBatch sb){
		sb.begin();
		
		sb.draw(sprite, body.getPosition().x * B2DVars.PPM - (26 / 2), body.getPosition().y * B2DVars.PPM - (32 / 2));

		sb.end();
	}
	
	public Body getBody(){
		return body;
	}
}