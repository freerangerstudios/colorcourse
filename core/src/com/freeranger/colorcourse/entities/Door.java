package com.freeranger.colorcourse.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.freeranger.colorcourse.Main;
import com.freeranger.colorcourse.handlers.B2DVars;
import com.freeranger.colorcourse.screens.Play;

public class Door {

	@SuppressWarnings("unused")
	private Main game;
	private Play play;
	private Body body;
	private String location;
	
	private boolean hasPlayerOpened = false;
	
	private Texture sprite;
	
	private Texture portal;
	private Animation<TextureRegion> portalAnim;
	float stateTime;
	private int col = 1;
	private int row = 61;
	
	public Door(Body body, String location, Main game, Play play) {
		this.game = game;
		this.body = body;
		this.play = play;
		this.location = location;
		
		sprite = game.door;
		portal = game.door; // i need to change door texture to portal
		sprite.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		TextureRegion[][] tmpPortal = TextureRegion.split(portal, 
				portal.getWidth() / row,
				portal.getHeight() / col);
		
		TextureRegion[] portalFrames = new TextureRegion[row * col];
		
		int index = 0;
		for (int i = 0; i < col; i++) {
			for (int j = 0; j < row; j++) {
				portalFrames[index++] = tmpPortal[i][j];
			}
		}
		
		portalAnim = new Animation<TextureRegion>(0.0625f, portalFrames);
		
		stateTime = 0f;
	}
	
	public void render(SpriteBatch sb){
		stateTime += Gdx.graphics.getDeltaTime(); // Accumulate elapsed animation time
		
		TextureRegion currentFramePortal = portalAnim.getKeyFrame(stateTime, true);

		
		//TODO fix "press e to open thingy sprite when close"
		
		sb.begin();
		sb.draw(currentFramePortal, body.getPosition().x * B2DVars.PPM - (96 / 2), body.getPosition().y * B2DVars.PPM - (96 / 2));
		sb.end();
	}
	
	public void update(float delta){	
		if(hasPlayerOpened) {
			if(Gdx.input.isKeyJustPressed(Keys.SPACE) || Gdx.input.isKeyJustPressed(Keys.W) || Gdx.input.isKeyJustPressed(Keys.UP)){
				play.doorWarp(location);
			}
		}
		
	}
	
	public Body getBody(){
		return body;
	}

	public boolean hasPlayerOpened() {
		return hasPlayerOpened;
	}

	public void setHasPlayerOpened(boolean hasPlayerOpened) {
		this.hasPlayerOpened = hasPlayerOpened;
	}
	
}