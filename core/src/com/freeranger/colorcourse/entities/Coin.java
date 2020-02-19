package com.freeranger.colorcourse.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.freeranger.colorcourse.Main;
import com.freeranger.colorcourse.handlers.B2DVars;

public class Coin {
	@SuppressWarnings("unused")
	private Main game;
	Body body;
	
	private int value;
	
	private Texture tex;
	Animation<TextureRegion> anim;
	float stateTime;
	
	int row;
	int col;

	public Coin(Body body, Main game, int value) {
		this.game = game;
		this.value = value;
		this.body = body;
		
		switch(value){
		case 1:
			tex = game.coin;
			row = 8;
			col = 1;
			break;
		case 5:
			tex = game.emerald;
			row = 1;
			col = 1;
			break;
	    default:
	    	tex = game.test;
	    	row = 1;
	    	col = 1;
	    	break;
		}
		
		tex.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		TextureRegion[][] tmp = TextureRegion.split(tex, 
				tex.getWidth() / row,
				tex.getHeight() / col);
		
		TextureRegion[] frames = new TextureRegion[row * col];
		
		int index = 0;
		for (int i = 0; i < col; i++) {
			for (int j = 0; j < row; j++) {
				frames[index++] = tmp[i][j];
			}
		}
		
		anim = new Animation<TextureRegion>(0.085f, frames);
		stateTime = 0f;
	}
	
	public void render(SpriteBatch sb){
		stateTime += Gdx.graphics.getDeltaTime(); // Accumulate elapsed animation time
		
		// Get current frame of animation for the current stateTime
		TextureRegion currentFrame = anim.getKeyFrame(stateTime, true);

		sb.begin();
		sb.draw(currentFrame, body.getPosition().x * B2DVars.PPM - (32 / 2), body.getPosition().y * B2DVars.PPM - (32 / 2));
		sb.end();
	}

	public int getValue() {
		return value;
	}
	
	public Body getBody(){
		return body;
	}
}
